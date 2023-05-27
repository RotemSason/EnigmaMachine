package UpdateAgentProgress;

import AlliesDetails.AlliesDetailsRefresher;
import MainScreensAgent.MainScreensAgentController;
import javafx.application.Platform;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateAgentProgressController {
    private TimerTask listRefresher;
    private Timer timer;
    private boolean isTimerCancel=false;
    private MainScreensAgentController mainScreensAgentController;

    public void startListRefresher() {
        listRefresher = new UpdateAgentProgressRefresher(
                this::updateStatus,this::updateTotalPull,this::updateTotalDone,this::updateWin,this::updateinternQueue,this::updateNumAllies,mainScreensAgentController.getNameAllies(),mainScreensAgentController.getAgentName(),mainScreensAgentController);
        timer = new Timer();
        timer.schedule(listRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    private void updateStatus(String status) {
        Platform.runLater(() -> {
           mainScreensAgentController.getStatusLabel().setText(status);

           });
    }


    private void updateNumAllies(String details) {
        Platform.runLater(() -> {
            mainScreensAgentController.getAlliesInContestLabel().setText(details);

        });
    }
    private void updateTotalPull(String totalPull) {
        Platform.runLater(() -> {
            mainScreensAgentController.getTotalPullTaskLabel().setText(totalPull);
        });
    }
    private void updateTotalDone(String totalDone) {
        Platform.runLater(() -> {
            mainScreensAgentController.getTotalDoneTasksLabel().setText(totalDone);

        });
    }

    private void updateinternQueue(String totalDone) {
        Platform.runLater(() -> {
            mainScreensAgentController.getInternalQueueLabel().setText(totalDone);

        });
    }
    public final static int REFRESH_RATE = 10;
    public void setMainScreensController(MainScreensAgentController mainScreensController) {
        this.mainScreensAgentController = mainScreensController;
    }
    private void updateWin(String details) {
        Platform.runLater(() -> {
            if(!details.isEmpty()){
                mainScreensAgentController.getbruthForceAgentComponentController().setIsWinContest(true);
                if(mainScreensAgentController.getbruthForceAgentComponentController().getThreadExecutor()!=null) {
                    mainScreensAgentController.getbruthForceAgentComponentController().getThreadExecutor().shutdown();
                }
                mainScreensAgentController.getbruthForceAgentComponentController().setTaskExist(false);
                timer.cancel();

            }});
    }
}
