package MainScreensUBoat;

import AlliesDetails.AlliesDetailsController;
import CurrentCode.CurrentCodeController;
import DMOperational.DMoperationalController;
import SuccessCodeDetails.SuccessCodeDetailsController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import winContest.WinContestController;

import java.io.IOException;

import static Configuration.Configuration.BASE_URL;
import static Configuration.Configuration.HTTP_CLIENT;

public class ContestUBoatComponentController {
    @FXML
    private Label TheEnigmaMachine;
    @FXML
    private Label UBoatTitle;
    @FXML
    private Button LogoutButton;
    @FXML
    private HBox dMoperationalComponent;
    @FXML
    private DMoperationalController dMoperationalComponentController;

    @FXML
    private FlowPane SuccessCodeDetailsComponent;
    @FXML
    private SuccessCodeDetailsController SuccessCodeDetailsComponentController;
    @FXML
    private VBox currentCodeComponent;
    @FXML
    private CurrentCodeController currentCodeComponentController;
    @FXML
    private FlowPane detailsAlliesComponent;
    @FXML
    private AlliesDetailsController detailsAlliesComponentController;
    @FXML
    private AnchorPane winContestComponent;
    @FXML
    private WinContestController winContestComponentController;
    @FXML
    private Label errorMessageLabel;
    private final StringProperty errorMessageProperty = new SimpleStringProperty();

    private MainScreensUBoatController mainScreensUBoatController;

    @FXML
    public void initialize() {
        errorMessageLabel.textProperty().bind(errorMessageProperty);
        LogoutButton.setVisible(false);
        if (SuccessCodeDetailsComponentController != null) {
            SuccessCodeDetailsComponentController.setMainScreensUBoattController(this);
        }
        if (currentCodeComponentController != null) {
            currentCodeComponentController.setMainScreensController(this);
            currentCodeComponentController.getSepOne().setVisible(false);
            currentCodeComponentController.getSepTwo().setVisible(false);
            currentCodeComponentController.getOldCodeButton().setVisible(false);

        }
        if (detailsAlliesComponentController != null) {
            detailsAlliesComponentController.setMainScreensController(this);
        }
        if (winContestComponentController != null) {
            winContestComponentController.setMainScreensController(this);
            winContestComponentController.getButtonClearUBoat().setVisible(false);

        }
        if(dMoperationalComponentController!=null){
            dMoperationalComponentController.setMainScreensController(this);
        }


    }
    public void setMainScreenUBoatController(MainScreensUBoatController mainScreensUBoatController){
        this.mainScreensUBoatController=mainScreensUBoatController;
    }
    @FXML
    void clickButtonLogoutAction(ActionEvent event){
mainScreensUBoatController.setMainPanelTo(mainScreensUBoatController.getMainScrolll());
        LogoutButton.setVisible(false);
        mainScreensUBoatController.getfilePath().setText("");
        mainScreensUBoatController.getMachineDetailsComponentController().clearalldetails();
        mainScreensUBoatController.getDMoperationalController().clearEncrypyDecrypt();
        winContestComponentController.clearWin();
        mainScreensUBoatController.getCodeConfigurationComponentController().initializeButtons();
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/logout-uboat")
                .newBuilder()
                .addQueryParameter("UBoatName",mainScreensUBoatController.getContestName())
                .build()
                .toString();

        Request request= new Request.Builder()
                .url(finalUrl)
                .build();

        Call call = HTTP_CLIENT.newCall(request);

        Response response= null;

        try {
            response = call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void clickButtonReadyAction(ActionEvent event){
        winContestComponentController.startListRefresher();
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/ReadyUBoat")
                .newBuilder()
                .addQueryParameter("UBoatName",mainScreensUBoatController.getContestName())
                .build()
                .toString();

        Request request= new Request.Builder()
                .url(finalUrl)
                .build();

        Call call = HTTP_CLIENT.newCall(request);

        Response response= null;

        try {
            response = call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AlliesDetailsController getDetailsAlliesComponentController() {
        return detailsAlliesComponentController;
    }

    public CurrentCodeController getCurrentCodeComponentController() {
        return currentCodeComponentController;
    }

    public DMoperationalController getdMoperationalComponentController() {
        return dMoperationalComponentController;
    }

    public SuccessCodeDetailsController getSuccessCodeDetailsComponentController() {
        return SuccessCodeDetailsComponentController;
    }
    public WinContestController getwinContestComponentController(){
        return winContestComponentController;
    }
    public String getContestName(){
        return mainScreensUBoatController.getContestName();
    }

    public MainScreensUBoatController getMainScreensUBoatController() {
        return mainScreensUBoatController;
    }

    public Label getUBoatTitle() {
        return UBoatTitle;
    }

    public Button getLogoutButton() {
        return LogoutButton;
    }

}
