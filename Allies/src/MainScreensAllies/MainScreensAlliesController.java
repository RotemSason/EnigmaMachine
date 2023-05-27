package MainScreensAllies;

import AgentsDetails.AgentsDetailsController;
import AlliesDetails.AlliesDetailsController;
import CandidateTable.CandidateTableController;
import ContestsDetails.ContestsDetailsController;
import StartPushTasks.StartPushTaskController;
import TeamAgentProgress.TeamAgentProgressController;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import updateProgress.updateProgressController;

import java.io.IOException;
import java.net.URL;

import static Configuration.Configuration.*;

public class MainScreensAlliesController {

    @FXML
    private ScrollPane mainScrolll;

    @FXML
    private VBox vboxMainScreens;

    @FXML
    private VBox vboxLoadFile;

    @FXML
    private Label TheEnigmaMachine;

    @FXML
    private HBox hboxLogin;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private TextField AlliesNameId;
    @FXML
    private ChoiceBox choiceBoxContest;
    @FXML
    private FlowPane contestsDetailsComponent;
    @FXML
    private ContestsDetailsController contestsDetailsComponentController;
    @FXML
    private Label AlliesTitle;

    private final StringProperty errorMessageProperty = new SimpleStringProperty();

    private String alliesName;
    private String nameBattle;

    @FXML private AnchorPane mainPaneAllies;
    private Parent ContestAlliesComponent;

    private ContestAlliesComponentController contestAlliesComponentController;
    private  StartPushTaskController startPushTaskController;
    @FXML private Button joinButton;

    @FXML
    public void initialize() {
        loadContestPage();
        joinButton.setDisable(true);
        errorMessageLabel.textProperty().bind(errorMessageProperty);
        if (contestsDetailsComponentController != null) {
            contestsDetailsComponentController.setMainScreensController(this);
            contestsDetailsComponentController.startListRefresher();
        }

       startPushTaskController=new StartPushTaskController(this);
        startPushTaskController.startListRefresher();
    }
public  StartPushTaskController getstartPushTaskController(){
        return startPushTaskController;
}
    @FXML
    void clickButtonloginAction(ActionEvent event) throws IOException {
        String alliesName = AlliesNameId.getText();
        AlliesTitle.setText(alliesName);
        contestAlliesComponentController.getAlliesTitle().setText("Hi "+alliesName+ " Welcome To The Contest");
        if (alliesName.isEmpty()) {
            //errorMessageProperty.set("User name is empty. You can't login with empty user name");
            return;
        }

        String finalUrl = HttpUrl
                .parse(BASE_URL + "/login-allies")
                .newBuilder()
                .addQueryParameter("alliesname", alliesName)
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();
        Call call = HTTP_CLIENT.newCall(request);

        Response response = call.execute();

        if (response.code() != 200) {
            String responseBody = response.body().string();
            Platform.runLater(() ->
                    errorMessageProperty.set("Something went wrong: " + responseBody)
            );
        } else {
            Platform.runLater(() -> {

                this.alliesName = alliesName;
                hboxLogin.setVisible(false);
                errorMessageProperty.set("");
                joinButton.setDisable(false);
            });
        }
    }

    public ChoiceBox getChoiceBoxContest() {
        return choiceBoxContest;
    }
    @FXML
    void clickButtonJoinAction(ActionEvent event) throws IOException {
        choiceBoxContest.setDisable(true);
        nameBattle = (String) choiceBoxContest.getValue();

        String finalUrl = HttpUrl
                .parse(BASE_URL + "/join-allies")
                .newBuilder()
                .addQueryParameter("alliesbattle", nameBattle)
                .addQueryParameter("alliesname", alliesName)
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();
        Call call = HTTP_CLIENT.newCall(request);

        Response response = call.execute();
        Gson gson = new Gson();
        String dtoString = null;
        try {
            dtoString = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DetailsUBoat detailUBoat = gson.fromJson(dtoString, DetailsUBoat.class);
        contestAlliesComponentController.setDetailsUBoat(detailUBoat);
        contestAlliesComponentController.getTeamAgentProgressComponentController().startListRefresher();
        switchToContest();

    }




    public AgentsDetailsController getAgentsDetailsController() {
        return contestAlliesComponentController.getAgentsDetailsComponentController();

    }
    public String getAlliesName(){
        return alliesName;
    }
    public void getTotalTasksFromServer(){
        contestAlliesComponentController.getUpdateProgressComponentController().setTotalTasks();
        contestAlliesComponentController.getUpdateProgressComponentController().startListRefresher();
    }

      public String getNameBattle(){return nameBattle;}

    public CandidateTableController getCandidateTableComponentController(){
        return contestAlliesComponentController.getCandidateTableComponentController();
    }
    public TeamAgentProgressController getTeamAgentProgressController(){
        return contestAlliesComponentController.getTeamAgentProgressComponentController();
    }

    public void switchToContest() {
        setMainPanelTo(ContestAlliesComponent);
    }

    public void setMainPanelTo(Parent pane) {
        mainPaneAllies.getChildren().clear();
        mainPaneAllies.getChildren().add(pane);
        AnchorPane.setBottomAnchor(pane, 1.0);
        AnchorPane.setTopAnchor(pane, 1.0);
        AnchorPane.setLeftAnchor(pane, 1.0);
        AnchorPane.setRightAnchor(pane, 1.0);
    }

    private void loadContestPage() {
        URL loginPageUrl = getClass().getResource(CONTESTALLIES_ROOM_FXML_RESOURCE_LOCATION);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            ContestAlliesComponent = fxmlLoader.load();
            contestAlliesComponentController = fxmlLoader.getController();
            contestAlliesComponentController.setMainScreensAlliesController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ContestAlliesComponentController getContestAlliesComponentController() {
        return contestAlliesComponentController;
    }
    public ScrollPane getMainScrolll(){
        return mainScrolll;
    }
    public ChoiceBox getchoiceBoxContest(){
        return choiceBoxContest;
    }
}

