package MainScreensAgent;

import AlliesDetails.AlliesDetailsController;
import BruthForceAgent.BruthForceAgentController;
import ClearAll.ClearAllController;
import EngineUI.DetailsUBoat;
import UpdateAgentProgress.UpdateAgentProgressController;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static ClearAll.Configuration.Configuration.BASE_URL;
import static ClearAll.Configuration.Configuration.HTTP_CLIENT;

public class MainScreensAgentController {
    @FXML
    private Label InternalQueueLabel;
    @FXML
    private Label TotalPullTaskLabel;
    @FXML
    private Label TotalDoneTasksLabel;
    @FXML
    private Label TotalCandidatesLabel;
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
    private ScrollPane mainScrolll;

    @FXML
    private VBox vboxMainScreens;

    @FXML
    private VBox vboxLoadFile;

    @FXML
    private Label TheEnigmaMachine;

    @FXML
    private Label alliesNameLabel;

    @FXML
    private HBox hboxLogin;

    @FXML
    private TextField AgentNameId;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private ChoiceBox alliesChoiceBox;

    @FXML
    private ChoiceBox<String> threadsChoiceBox;
    private final StringProperty errorMessageProperty = new SimpleStringProperty();
    @FXML
    private TextField tasksToPull;
    @FXML
    private Label AgentTitle;
    private String agentName;
    private String nameAllies;
    //@FXML
   // private FlowPane alliesDetailsComponent;
    @FXML
    private HBox hboxAgentDetail;

    private AlliesDetailsController alliesDetailsComponentController;
    @FXML
    private FlowPane bruthForceAgentComponent;
    @FXML
    private BruthForceAgentController bruthForceAgentComponentController;
    private boolean StartConteset=false;
    private int totalDone=0;
    private UpdateAgentProgressController updateAgentProgressController;
    private ClearAllController clearAllController=new ClearAllController();
    @FXML
    public void initialize() {
        hboxAgentDetail.setVisible(false);
        errorMessageLabel.textProperty().bind(errorMessageProperty);
        threadsChoiceBox.getItems().add("1");
        threadsChoiceBox.getItems().add("2");
        threadsChoiceBox.getItems().add("3");
        threadsChoiceBox.getItems().add("4");
        //if (alliesDetailsComponentController != null) {
        alliesDetailsComponentController=new AlliesDetailsController();
            alliesDetailsComponentController.setMainScreensController(this);
            alliesDetailsComponentController.startListRefresher();
            updateAgentProgressController=new UpdateAgentProgressController();
        updateAgentProgressController.setMainScreensController(this);


        //}
        if (bruthForceAgentComponentController != null) {
            bruthForceAgentComponentController.setMainScreensAgentController(this);
        }
        if (bruthForceAgentComponentController != null) {
            bruthForceAgentComponentController.setMainScreensAgentController(this);
        }

            clearAllController.setMainScreensController(this);
          //  clearAllController.startListRefresher();
    }
    public synchronized boolean getStartConteset(){
        return StartConteset;
    }
    public synchronized void setStartConteset(boolean StartConteset){
        this.StartConteset=StartConteset;
    }
public String getNameAllies(){
        return nameAllies;
}
public Label getAlliesInContestLabel(){return AlliesInContestLabel;}
    @FXML
    void clickButtonloginAction(ActionEvent event) throws IOException {
        String agentName = AgentNameId.getText();
        AgentTitle.setText(agentName);

        if (agentName.isEmpty()) {
            //errorMessageProperty.set("User name is empty. You can't login with empty user name");
            return;
        }

        String finalUrl = HttpUrl
                .parse(BASE_URL+"/login-agent")
                .newBuilder()
                .addQueryParameter("agentname", agentName)
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

                this.agentName=agentName;
                errorMessageProperty.set("");
                hboxLogin.setVisible(false);
                hboxAgentDetail.setVisible(true);
            });
        }

    }

    public String getBattleFieldLabel() {
        return BattleFieldLabel.getText();
    }

    @FXML
    void clickButtonReadyAction(ActionEvent event) throws IOException {
        hboxAgentDetail.setVisible(false);

        nameAllies= (String) alliesChoiceBox.getValue();
        alliesNameLabel.setText(nameAllies);
        String threadsAmount=threadsChoiceBox.getValue();
        agentName=AgentNameId.getText();
        String taskToPull= tasksToPull.getText();

        String finalUrl = HttpUrl
                .parse(BASE_URL+"/ready-agent")
                .newBuilder()
                .addQueryParameter("agentname", agentName)
                .addQueryParameter("nameallies",nameAllies)
                .addQueryParameter("threadsamount",threadsAmount)
                .addQueryParameter("tasktopull", taskToPull)
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

        DetailsUBoat DetailUBoat = gson.fromJson(dtoString, DetailsUBoat.class);
        BattleFieldLabel.setText(DetailUBoat.getBattle());
        UBoatNameLabel.setText(DetailUBoat.getUboatName());
        StatusLabel.setText(DetailUBoat.getStatus());
        LevelLabel.setText(DetailUBoat.getLevel());
        AlliesInContestLabel.setText(DetailUBoat.getAllies());
        updateAgentProgressController.startListRefresher();
    }
    public void setUBoatSecond(){
        String finalUrl = HttpUrl
                .parse(BASE_URL+"/second-uboat")
                .newBuilder()
                .addQueryParameter("nameallies",nameAllies)
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

        DetailsUBoat DetailUBoat = gson.fromJson(dtoString, DetailsUBoat.class);
        BattleFieldLabel.setText(DetailUBoat.getBattle());
        UBoatNameLabel.setText(DetailUBoat.getUboatName());
        StatusLabel.setText(DetailUBoat.getStatus());
        LevelLabel.setText(DetailUBoat.getLevel());
        AlliesInContestLabel.setText(DetailUBoat.getAllies());
        updateAgentProgressController.startListRefresher();
    }

    public Label getUBoatNameLabel() {
        return UBoatNameLabel;
    }

    public ChoiceBox getChoiceBoxAllies(){return alliesChoiceBox;}
    public int getTasksToPull(){
        return Integer.parseInt(tasksToPull.getText());
    }
    public int getnumOfThreads(){
        return Integer.parseInt(threadsChoiceBox.getValue());
    }
    public String getAgentName(){
        return agentName;
    }
    public BruthForceAgentController getbruthForceAgentComponentController(){
        return bruthForceAgentComponentController;
    }

    public Label getTotalDoneTasksLabel() {
        return TotalDoneTasksLabel;
    }
    public Label getTotalPullTaskLabel(){return TotalPullTaskLabel;}
    public Label getTotalCandidatesLabel(){return TotalCandidatesLabel;}

    public int getTotalDone() {
        return totalDone;
    }

    public synchronized void setTotalDone(int totalDone) {
        this.totalDone = totalDone;
    }
    public Label getStatusLabel(){return StatusLabel;}
    public Label getInternalQueueLabel(){return InternalQueueLabel;}
    public void clearAll(){
        InternalQueueLabel.setText("");
         TotalPullTaskLabel.setText("");
        TotalDoneTasksLabel.setText("");
       TotalCandidatesLabel.setText("");
        BattleFieldLabel.setText("");
      UBoatNameLabel.setText("");
      StatusLabel.setText("");
      LevelLabel.setText("");
      AlliesInContestLabel.setText("");
      bruthForceAgentComponentController.getTableSuccessCodes().getItems().clear();
    }
    public AlliesDetailsController getalliesDetailsComponentController(){
        return alliesDetailsComponentController;
    }
    public ClearAllController getclearAllController(){
        return clearAllController;
    }
}
