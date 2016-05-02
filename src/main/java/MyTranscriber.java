package main.java;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyTranscriber {
   private Configuration _configuration;
   private StreamSpeechRecognizer _recognizer;

   public MyTranscriber(String acModelPath, String dicPath, String lanModelPath) throws IOException {
      _configuration = new Configuration();
      _configuration.setAcousticModelPath(acModelPath);
      _configuration.setDictionaryPath(dicPath);
      _configuration.setLanguageModelPath(lanModelPath);
      _recognizer = new StreamSpeechRecognizer(_configuration);
   }

   public String speechToText(String strFilePath) throws IOException {
      String text = "";
      InputStream stream = new FileInputStream(new File(strFilePath));
      _recognizer.startRecognition(stream);
      SpeechResult result = null;
      while ((result = _recognizer.getResult()) != null) {
          System.out.format("Hypothesis: %s\n", result.getHypothesis());
          text = text + result.getHypothesis();
      }
      _recognizer.stopRecognition();

      return text;
   }
}
