package BruthForceAgent;


import javafx.application.Platform;

import java.util.function.Consumer;

public class UIAdapter {

    private Consumer<SuccessCode> codeConsumer;

    public UIAdapter(Consumer<SuccessCode> codeConsumer) {
        this.codeConsumer = codeConsumer;
    }

    public synchronized void addRowToTable(SuccessCode code) {
        Platform.runLater(
                () -> {
                    codeConsumer.accept(code);
                }
        );
    }
}

