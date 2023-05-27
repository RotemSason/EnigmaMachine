package TeamAgentProgress;

import EngineUI.AgentProgress;
import EngineUI.SuccessCode;
import MainScreensAllies.ContestAlliesComponentController;
import MainScreensAllies.MainScreensAlliesController;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import updateProgress.updateProgressRefresher;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TeamAgentProgressController {

    @FXML
    private TableView<AgentProgress> AgentDetailsTable;

    @FXML
    private TableColumn<AgentProgress, String> NameAgentColumn;

    @FXML
    private TableColumn<AgentProgress, String>TotalRecievedTasksColumn;
    @FXML
    private TableColumn<AgentProgress, String>TotalCandidateStringColumn;
    private TimerTask listRefresher;
    private Timer timer;
    ContestAlliesComponentController mainScreensAlliesController;
    public final static int REFRESH_RATE = 1000;
    public void startListRefresher() {
        listRefresher = new TeamAgentProgressRefresher(
                this::updateTable, mainScreensAlliesController);
        timer = new Timer();
        timer.schedule(listRefresher, REFRESH_RATE, REFRESH_RATE);
    }
    private void updateTable(List<AgentProgress> lst) {
        Platform.runLater(() -> {
            if(lst.size()>0) {
                mainScreensAlliesController.getTeamAgentProgressComponentController().getAgentDetailsTable().getItems().clear();
                for (int i = 0; i < lst.size(); i++) {
                    addToTaskAgentProgressTable(lst.get(i));
                }
            } });
    }
    @FXML
    private TableColumn<AgentProgress, String>TotalWaitingTasksColumn;
    public synchronized void addToTaskAgentProgressTable(AgentProgress code){

        if(AgentDetailsTable.getItems().size()==0){
            NameAgentColumn.setCellValueFactory(new PropertyValueFactory<AgentProgress, String>("NameAgent"));

            TotalRecievedTasksColumn.setCellValueFactory(new PropertyValueFactory<AgentProgress, String>("TotalRecievedTasks"));

            TotalWaitingTasksColumn.setCellValueFactory(new PropertyValueFactory<AgentProgress, String>("TotalWaitingTasks"));

            TotalCandidateStringColumn.setCellValueFactory(new PropertyValueFactory<AgentProgress, String>("TotalCandidateString"));

        }
        final ObservableList<AgentProgress> data =
                FXCollections.observableArrayList(code);
        AgentDetailsTable.setEditable(true);
        AgentDetailsTable.getItems().add(code);
        if(AgentDetailsTable.getItems().size()==1){
            mainScreensAlliesController.getReadyButton().setDisable(false);
        }
    }
    public void setMainScreensController(ContestAlliesComponentController mainScreensController) {
        this.mainScreensAlliesController = mainScreensController;

    }
    public TableView<AgentProgress>getAgentDetailsTable(){
        return AgentDetailsTable;
    }
public void cancelTimer(){
        timer.cancel();
}
}

