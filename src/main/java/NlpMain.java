package main.java;

import java.io.*;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class NlpMain {

	public static void main (String[] args) {

		// always start with a model, a model is learned from training data
		try {
			// Transformamos el audio a texto
			MyTranscriber transcriptor = new MyTranscriber(
					"resource:/edu/cmu/sphinx/models/en-us/en-us",
					"resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict",
					"resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin"
			);

			String paragraph = transcriptor.speechToText("./data/output.wav");

			// Y lo analizamos
			InputStream is = new FileInputStream("data/en-sent.bin");
			SentenceModel model = new SentenceModel(is);
			SentenceDetectorME sdetector = new SentenceDetectorME(model);

			// Extracción de sentencias
			String sentences[] = sdetector.sentDetect(paragraph);
			for (double prob : sdetector.getSentenceProbabilities()) {
				System.out.println("Probabilidad: " + prob);
			}

			System.out.println();

			for (String sentence : sentences) {
				System.out.println(sentence);
			}

			is.close();

		} catch (Exception e) {
			System.out.println("Error " + e.toString());
		}
	}

}
