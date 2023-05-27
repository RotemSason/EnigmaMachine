package winContest;

import AlliesDetails.AlliesDetailsRefresher;
import EngineUI.AgentCurrCode;
import EngineUI.SuccessCode;
import MainScreensUBoat.ContestUBoatComponentController;
import MainScreensUBoat.MainScreensUBoatController;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WinContestController {
    @FXML
    private Label winMessage;
    @FXML
    private Label winAllies;
    @FXML
    private Button ButtonClearUBoat;
    private TimerTask listRefresher;
    private Timer timer;
    private ContestUBoatComponentController mainScreensUBoatController;
    private final BooleanProperty autoUpdate;

    private boolean finishcontest;
    public void setMainScreensController(ContestUBoatComponentController mainScreensController) {
        this.mainScreensUBoatController = mainScreensController;

    }

    public WinContestController() {
        autoUpdate = new SimpleBooleanProperty();
    }

    public void startListRefresher() {
        finishcontest=false;
        listRefresher = new WinContestRefresher(
                this::updateStr,this::updateSuccess,mainScreensUBoatController);//change to main screenagent
        timer = new Timer();
        timer.schedule(listRefresher, REFRESH_RATE, REFRESH_RATE);
    }
    private void updateStr(String details) {
        Platform.runLater(() -> {
            if(!details.isEmpty()){
finishcontest=true;
                ButtonClearUBoat.setVisible(true);
            winMessage.setText("Contest over!");
            winAllies.setText("The winner team is: "+details);
            mainScreensUBoatController.getLogoutButton().setVisible(true);
            timer.cancel();
            mainScreensUBoatController.getDetailsAlliesComponentController().getTimer().cancel();


        }});
    }
    private void updateSuccess(List<AgentCurrCode>details){
        Platform.runLater(() -> {
            if(!details.isEmpty()&&!finishcontest){
                mainScreensUBoatController.getSuccessCodeDetailsComponentController().getAllSuccessCodeTable().getItems().clear();
                for (int i = 0; i <details.size(); i++) {
                    SuccessCode code=ConvertToSuccessCode(details.get(i));
                    mainScreensUBoatController.getSuccessCodeDetailsComponentController().addToAllSuccessTable(code);
                }
            }});
    }
    public final static int REFRESH_RATE = 2000;
    SuccessCode ConvertToSuccessCode(AgentCurrCode agentdetails){
        SuccessCode code=new SuccessCode(agentdetails.getRotors(),agentdetails.getPositions(),agentdetails.getReflector(),agentdetails.getStrCandidate(),agentdetails.getNameAllies());
        return  code;
    }
    public void clearWin(){
        winMessage.setText("");
        winAllies.setText("");
    }

    @FXML
    void clickButtonClearUBoat(ActionEvent event) {
        mainScreensUBoatController.getSuccessCodeDetailsComponentController().getAllSuccessCodeTable().getItems().clear();
        mainScreensUBoatController.getDetailsAlliesComponentController().getTableAlliesDetails().getItems().clear();
clearWin();
        ButtonClearUBoat.setVisible(false);
    }
    public Button getButtonClearUBoat(){
        return ButtonClearUBoat;
    }
}
