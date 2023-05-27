package ClearAll;

import MainScreensAgent.MainScreensAgentController;
import UpdateAgentProgress.UpdateAgentProgressRefresher;
import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public class ClearAllController {
    private TimerTask listRefresher;
    private Timer timer;
    private MainScreensAgentController mainScreensAgentController;

    public void startListRefresher() {
        listRefresher = new ClearAllRefresher(
                this::updateClear,mainScreensAgentController);
        timer = new Timer();
        timer.schedule(listRefresher, REFRESH_RATE, REFRESH_RATE);
    }
    public final static int REFRESH_RATE = 10;

    private void updateClear(String clear) {
        Platform.runLater(() -> {
            if(clear.equals("true")) {
                mainScreensAgentController.clearAll();
                timer.cancel();
                mainScreensAgentController.getalliesDetailsComponentController().startListRefresher();
            }
        });
    }
    public void setMainScreensController(MainScreensAgentController mainScreensController) {
        this.mainScreensAgentController = mainScreensController;

    }

}
