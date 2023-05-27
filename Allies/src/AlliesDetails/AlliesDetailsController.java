package AlliesDetails;


import EngineUI.DetailsAllies;
import MainScreensAllies.ContestAlliesComponentController;
import MainScreensAllies.MainScreensAlliesController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AlliesDetailsController {

    @FXML
    private TableView<DetailsAllies> TableAlliesDetails;

    @FXML
    private TableColumn<DetailsAllies,String> AlliesNameColumn;

    @FXML
    private TableColumn<DetailsAllies,String> AgentsAmountColumn;

    @FXML
    private TableColumn<DetailsAllies,String>TasksSizeColumn;
    private TimerTask listRefresher;
    private Timer timer;

    private ContestAlliesComponentController mainScreensAlliesController;

    public TableView<DetailsAllies> getTableAlliesDetails() {
        return TableAlliesDetails;
    }

    public synchronized void addToTable(List<DetailsAllies> lst) {
        if (TableAlliesDetails.getItems().size() == 0) {
            AlliesNameColumn.setCellValueFactory(new PropertyValueFactory<DetailsAllies, String>("alliesName"));

            AgentsAmountColumn.setCellValueFactory(new PropertyValueFactory<DetailsAllies, String>("agentsAmount"));

            TasksSizeColumn.setCellValueFactory(new PropertyValueFactory<DetailsAllies, String>("tasksSize"));

        }
        // final ObservableList<DetailsUBoat> data =
        //       FXCollections.observableArrayList(lst);
        TableAlliesDetails.setEditable(true);
        TableAlliesDetails.getItems().clear();
        for (int i = 0; i < lst.size(); i++) {

            TableAlliesDetails.getItems().add(lst.get(i));
        }

    }

    public void startListRefresher() {
        listRefresher = new AlliesDetailsRefresher(
                this::updateUsersList,mainScreensAlliesController.getNameBattle());
        timer = new Timer();
        timer.schedule(listRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    private void updateUsersList(List<DetailsAllies> details) {
        Platform.runLater(() -> {
            addToTable(details);
        });
    }


    public final static int REFRESH_RATE = 2000;



    public void setMainScreensController(ContestAlliesComponentController mainScreensController) {
        this.mainScreensAlliesController = mainScreensController;

    }

    public void cancelTimer(){
        timer.cancel();
    }
}

