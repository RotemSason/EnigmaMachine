package AlliesDetails;


import MainScreensUBoat.ContestUBoatComponentController;
import MainScreensUBoat.DetailsAllies;
import MainScreensUBoat.MainScreensUBoatController;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static Configuration.Configuration.BASE_URL;
import static Configuration.Configuration.HTTP_CLIENT;

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

    private ContestUBoatComponentController mainScreensUBoatController;




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
                this::updateUsersList,mainScreensUBoatController.getContestName());
        timer = new Timer();
        timer.schedule(listRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    private void updateUsersList(List<DetailsAllies> details) {
        Platform.runLater(() -> {
            addToTable(details);
        });
    }


    public final static int REFRESH_RATE = 2000;



    public void setMainScreensController(ContestUBoatComponentController mainScreensController) {
        this.mainScreensUBoatController = mainScreensController;

    }

    public TableView<DetailsAllies> getTableAlliesDetails() {
        return TableAlliesDetails;
    }

    public Timer getTimer() {
        return timer;
    }
}

