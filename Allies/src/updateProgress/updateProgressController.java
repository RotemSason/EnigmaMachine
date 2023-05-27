package updateProgress;

import ContestsDetails.ContestsDetailsRefresher;
import EngineUI.AgentCurrCode;
import EngineUI.SuccessCode;
import MainScreensAllies.ContestAlliesComponentController;
import MainScreensAllies.DetailsAgent;
import MainScreensAllies.DetailsUBoat;
import MainScreensAllies.MainScreensAlliesController;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import static Configuration.Configuration.BASE_URL;
import static Configuration.Configuration.HTTP_CLIENT;

public class updateProgressController {
    @FXML
    private Label totalTasks;
    @FXML
    private Label winAllies;
    @FXML
    private Label winMessage;
    @FXML
    private Label finishTasks;
    @FXML
    private Label pushTasks;
    @FXML
    private Button clearButton;

    private TimerTask listRefresher;
    private Timer timer;
    private ContestAlliesComponentController mainScreensAlliesController;
    public final static int REFRESH_RATE = 1000;

    @FXML
    void clickClearButtonAction(ActionEvent event){
        mainScreensAlliesController.removeFromChoiceBox(mainScreensAlliesController.getNameBattle());
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/clear-all-agent")
                .newBuilder()
                .addQueryParameter("AlliesName", mainScreensAlliesController.getAlliesName())
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();


        Call call = HTTP_CLIENT.newCall(request);

        Response response = null;

        try {
            response = call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        clearAll();
        mainScreensAlliesController.getMainScreensAlliesController().setMainPanelTo(mainScreensAlliesController.getMainScreensAlliesController().getMainScrolll());
        mainScreensAlliesController.getMainScreensAlliesController().getstartPushTaskController().startListRefresher();
        mainScreensAlliesController.getMainScreensAlliesController().getchoiceBoxContest().setDisable(false);
        clearButton.setVisible(false);
    }
    public void startListRefresher() {
        listRefresher = new updateProgressRefresher(
                this::updateUsersList, this::updateWin, this::updateStatus, this::updateNumAllies, this::updateSuccess, mainScreensAlliesController);
        timer = new Timer();
        timer.schedule(listRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    private void updateUsersList(updateData details) {
        Platform.runLater(() -> {
            pushTasks.setText(details.getPushTasks());
            finishTasks.setText(details.getFinishTasks());
        });
    }

    private void updateWin(String details) {
        Platform.runLater(() -> {
            if (!details.isEmpty()) {
                winMessage.setText("Contest over!");
                winAllies.setText("The winner team is: " + details);
                clearButton.setVisible(true);
                mainScreensAlliesController.getTeamAgentProgressComponentController().cancelTimer();
            }
        });
    }

    private void updateNumAllies(String details) {
        Platform.runLater(() -> {
            mainScreensAlliesController.getAlliesInContestLabel().setText(details);
        });
    }

    private void updateStatus(String status) {
        Platform.runLater(() -> {
            mainScreensAlliesController.getStatusLabel().setText(status);
        });
    }

    private void updateSuccess(List<AgentCurrCode> details) {
        Platform.runLater(() -> {
            if (!details.isEmpty()) {
                mainScreensAlliesController.getCandidateTableComponentController().getTableSuccessCodes().getItems().clear();
                for (int i = 0; i < details.size(); i++) {
                    SuccessCode code = ConvertToSuccessCode(details.get(i));
                    mainScreensAlliesController.getCandidateTableComponentController().addToTable(code);
                }
            }
        });
    }

    public void setMainScreensController(ContestAlliesComponentController mainScreensController) {
        this.mainScreensAlliesController = mainScreensController;

    }

    public void setTotalTasks() {
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/update-total-tasks")
                .newBuilder()
                .addQueryParameter("AlliesName", mainScreensAlliesController.getAlliesName())
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();


        Call call = HTTP_CLIENT.newCall(request);

        Response response = null;

        try {
            response = call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();
        String dtoString = null;
        try {
            dtoString = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] result = gson.fromJson(dtoString, String[].class);
        totalTasks.setText(result[0]);
        mainScreensAlliesController.setStringProgress(result[1]);
    }

    SuccessCode ConvertToSuccessCode(AgentCurrCode agentdetails) {
        SuccessCode code = new SuccessCode(agentdetails.getRotors(), agentdetails.getPositions(), agentdetails.getReflector(), agentdetails.getStrCandidate(),mainScreensAlliesController.getAlliesName());
        return code;
    }

    public Button getClearButton() {
        return clearButton;
    }
    public void clearAll(){
        totalTasks.setText("");
      winAllies.setText("");
      winMessage.setText("");
      finishTasks.setText("");
      pushTasks.setText("");
      mainScreensAlliesController.clearAll();
      mainScreensAlliesController.getTeamAgentProgressComponentController().getAgentDetailsTable().getItems().clear();
    mainScreensAlliesController.getCandidateTableComponentController().getTableSuccessCodes().getItems().clear();
        mainScreensAlliesController.getDetailsAlliesComponentController().cancelTimer();
    mainScreensAlliesController.getDetailsAlliesComponentController().getTableAlliesDetails().getItems().clear();
timer.cancel();
    }
}

