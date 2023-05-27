package UI;


import Screens.BruteForceProgress.SuccessCode;
import javafx.application.Platform;

import java.util.function.Consumer;
/*
public class UIAdapter {

    private Consumer<SuccessCode> codeConsumer;
    //private Consumer<HistogramData> updateExistingWord;
   private Runnable updateDistinct;
    private Consumer<Integer> updateTotalProcessedWords;

    public UIAdapter(Consumer<SuccessCode> codeConsumer,Consumer<Integer> updateTotalProcessedWords) {
        this.codeConsumer = codeConsumer;
        //this.updateExistingWord = updateExistingWord;
       // this.updateDistinct = updateDistinct;
        this.updateTotalProcessedWords = updateTotalProcessedWords;
    }

    public synchronized void addRowToTable(SuccessCode code) {

        Platform.runLater(
                () -> {
                    codeConsumer.accept(code);
                  //updateDistinct.run();
                }
        );
    }
/*
    public void updateExistingWord(HistogramData histogramData) {
        Platform.runLater(
                () -> updateExistingWord.accept(histogramData)
        );
    }

    public void updateTotalProcessedWords(int delta) {
        Platform.runLater(
                () -> updateTotalProcessedWords.accept(delta)
        );
    }

}*/
