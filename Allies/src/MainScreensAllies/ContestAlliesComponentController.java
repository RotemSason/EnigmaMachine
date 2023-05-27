package MainScreensAllies;

import AgentsDetails.AgentsDetailsController;
import AlliesDetails.AlliesDetailsController;
import CandidateTable.CandidateTableController;
import TeamAgentProgress.TeamAgentProgressController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import updateProgress.updateProgressController;

import java.io.IOException;

import static Configuration.Configuration.BASE_URL;
import static Configuration.Configuration.HTTP_CLIENT;

public class ContestAlliesComponentController {
    @FXML
    private Label BattleFieldLabel;
    @FXML
    private Label UBoatNameLabel;
    @FXML
    private Label StatusLabel;
    @FXML
    private Label LevelLabel;
    @FXML
    private Label AlliesInContestLabel;
    @FXML
    private Label errorMessageLabel;
    @FXML
    private Label AlliesTitle;
    @FXML
    private Label stringProgress;

    @FXML
    private FlowPane updateProgressComponent;
    @FXML
    private updateProgressController updateProgressComponentController;
    @FXML
    private FlowPane agentsDetailsComponent;
    @FXML
    private AgentsDetailsController agentsDetailsComponentController;
    private final StringProperty errorMessageProperty = new SimpleStringProperty();
    @FXML
    private TextField sizeTask;
    @FXML
    private FlowPane detailsAlliesComponent;

    @FXML
    private AlliesDetailsController detailsAlliesComponentController;
    @FXML
    private FlowPane CandidateTableComponent;
    @FXML
    private CandidateTableController CandidateTableComponentController;


    @FXML
    private FlowPane TeamAgentProgressComponent;
    @FXML
    private TeamAgentProgressController TeamAgentProgressComponentController;
    private MainScreensAlliesController mainScreensAlliesController;
    @FXML
    private Button readyButton;

    @FXML
    public void initialize() {
        readyButton.setDisable(true);
        errorMessageLabel.textProperty().bind(errorMessageProperty);
        if (TeamAgentProgressComponentController != null) {
            TeamAgentProgressComponentController.setMainScreensController(this);
        }
        if (detailsAlliesComponentController != null) {
            detailsAlliesComponentController.setMainScreensController(this);
        }
        if (agentsDetailsComponentController != null) {
            agentsDetailsComponentController.setMainScreensController(this);
            //agentsDetailsComponentController.startListRefresher();
        }
        if (CandidateTableComponentController != null) {
            CandidateTableComponentController.setMainScreensController(this);
            //agentsDetailsComponentController.startListRefresher();
        }
        if (updateProgressComponentController != null) {
            updateProgressComponentController.setMainScreensController(this);
            updateProgressComponentController.getClearButton().setVisible(false);

        }
    }

    public void setMainScreensAlliesController(MainScreensAlliesController mainScreensAlliesController) {
        this.mainScreensAlliesController = mainScreensAlliesController;
    }

    public void setDetailsUBoat(DetailsUBoat detailUBoat) {
        BattleFieldLabel.setText(detailUBoat.getBattle());
        UBoatNameLabel.setText(detailUBoat.getUboatName());
        StatusLabel.setText(detailUBoat.getStatus());
        LevelLabel.setText(detailUBoat.getLevel());
        AlliesInContestLabel.setText(detailUBoat.getAllies());
        detailsAlliesComponentController.startListRefresher();
    }
public MainScreensAlliesController getMainScreensAlliesController(){
        return mainScreensAlliesController;
}
    @FXML
    void clickButtonReadyAction(ActionEvent event) throws IOException {
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/ready-allies")
                .newBuilder()
                .addQueryParameter("alliesname", mainScreensAlliesController.getAlliesName())
                .addQueryParameter("sizetask", sizeTask.getText())
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();
        Call call = HTTP_CLIENT.newCall(request);

        Response response = call.execute();

      //  TeamAgentProgressComponentController.startListRefresher();
    }

    public AlliesDetailsController getDetailsAlliesComponentController() {
        return detailsAlliesComponentController;
    }

    public AgentsDetailsController getAgentsDetailsComponentController() {
        return agentsDetailsComponentController;
    }

    public updateProgressController getUpdateProgressComponentController() {
        return updateProgressComponentController;
    }

    public CandidateTableController getCandidateTableComponentController() {
        return CandidateTableComponentController;
    }

    public TeamAgentProgressController getTeamAgentProgressComponentController() {
        return TeamAgentProgressComponentController;
    }

    public void setStringProgress(String str){
        stringProgress.setText(str);
    }
    public Label getStatusLabel(){return StatusLabel;}
    public Label getAlliesInContestLabel(){
        return AlliesInContestLabel;
    }
    public String getAlliesName(){
        return mainScreensAlliesController.getAlliesName();
    }
    public String getNameBattle(){
        return mainScreensAlliesController.getNameBattle();
    }

    public Label getAlliesTitle() {
        return AlliesTitle;
    }
    public Button getReadyButton(){return readyButton;}
    public void clearAll(){
         BattleFieldLabel.setText("");
         UBoatNameLabel.setText("");
       StatusLabel.setText("");
       LevelLabel.setText("");
       AlliesInContestLabel.setText("");
         stringProgress.setText("");
         sizeTask.clear();
    }
    public void removeFromChoiceBox(String nameBattle){
        for (int i = 0; i < mainScreensAlliesController.getchoiceBoxContest().getItems().size(); i++) {
 if( mainScreensAlliesController.getchoiceBoxContest().getItems().get(i).equals(nameBattle)){
     mainScreensAlliesController.getchoiceBoxContest().getItems().remove(i);
 }
        }
    }
}
