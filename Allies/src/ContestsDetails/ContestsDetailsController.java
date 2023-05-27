package ContestsDetails;

import MainScreensAllies.DetailsAgent;
import MainScreensAllies.DetailsUBoat;
import MainScreensAllies.MainScreensAlliesController;
import api.HttpStatusUpdate;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ContestsDetailsController {

    @FXML
    private TableView<DetailsUBoat> TableSuccessCodes;

    @FXML
    private TableColumn<DetailsUBoat, String> battleColumn;

    @FXML
    private TableColumn<DetailsUBoat, String> uboatNameColumn;

    @FXML
    private TableColumn<DetailsUBoat, String> statusColumn;

    @FXML
    private TableColumn<DetailsUBoat, String> levelColumn;

    @FXML
    private TableColumn<DetailsUBoat, String> alliesColumn;
    @FXML
    private TableView<DetailsAgent> TableSuccessCodes1;
    @FXML
    private TableColumn<DetailsAgent, String> AgentColumn;
    @FXML
    private TableColumn<DetailsAgent, String> ThreadsColumn;
    @FXML
    private TableColumn<DetailsAgent, String> TasksSizeColumn;


    private TimerTask listRefresher;
    private Timer timer;
    private HttpStatusUpdate httpStatusUpdate;
    private MainScreensAlliesController mainScreensAlliesController;
    private static int numInChoiceBox = 0;
    private final BooleanProperty autoUpdate;





    public ContestsDetailsController() {
        autoUpdate = new SimpleBooleanProperty();
    }

    public synchronized void addToChoiceBox(List<DetailsUBoat> lst) {
        // mainScreensAlliesController.getChoiceBoxContest().getItems().clear();
        int numFull = checkAmountFull(lst);
        //System.out.println(numFull);
        if (lst.size() != (mainScreensAlliesController.getChoiceBoxContest().getItems().size() + numFull)) {
            for (int i = mainScreensAlliesController.getChoiceBoxContest().getItems().size() + numFull; i < lst.size(); i++) {
                mainScreensAlliesController.getChoiceBoxContest().getItems().add(lst.get(i).getBattle());

            }

        }
    }

    public int checkAmountFull(List<DetailsUBoat> lst) {
        int counter = 0;
        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i).isFull() == true) {
                counter++;
            }
        }
        return counter;
    }

    public synchronized void addToTable(List<DetailsUBoat> lst) {
        if (TableSuccessCodes.getItems().size() == 0) {
            battleColumn.setCellValueFactory(new PropertyValueFactory<DetailsUBoat, String>("battle"));

            uboatNameColumn.setCellValueFactory(new PropertyValueFactory<DetailsUBoat, String>("uboatName"));

            alliesColumn.setCellValueFactory(new PropertyValueFactory<DetailsUBoat, String>("allies"));

            statusColumn.setCellValueFactory(new PropertyValueFactory<DetailsUBoat, String>("status"));
            levelColumn.setCellValueFactory(new PropertyValueFactory<DetailsUBoat, String>("level"));
        }
        // final ObservableList<DetailsUBoat> data =
        //       FXCollections.observableArrayList(lst);
        TableSuccessCodes.setEditable(true);
        TableSuccessCodes.getItems().clear();
        for (int i = 0; i < lst.size(); i++) {

            TableSuccessCodes.getItems().add(lst.get(i));
        }

    }

    public void startListRefresher() {
        listRefresher = new ContestsDetailsRefresher(
                autoUpdate,
                this::updateUsersList,this::updateAgentList);
        timer = new Timer();
        timer.schedule(listRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    private void updateUsersList(List<DetailsUBoat> details) {
        Platform.runLater(() -> {
            addToTable(details);
            addToChoiceBox(details);
            try {
                checkFullBattle(details);
            } catch (IOException e) {
                throw new RuntimeException(e);

            }
        });
    }

    private void updateAgentList(List<DetailsAgent> agents) {
        Platform.runLater(() -> {
            if(TableSuccessCodes1!=null){
            addToAgentTable(agents);}}

        );
    }

    public final static int REFRESH_RATE = 2000;

    public void setHttpStatusUpdate(HttpStatusUpdate httpStatusUpdate) {
        this.httpStatusUpdate = httpStatusUpdate;

    }

    public BooleanProperty autoUpdatesProperty() {
        return autoUpdate;
    }

    public void setMainScreensController(MainScreensAlliesController mainScreensController) {
        this.mainScreensAlliesController = mainScreensController;

    }

    public void checkFullBattle(List<DetailsUBoat> details) throws IOException {
        for (int i = 0; i < details.size(); i++) {
            String[] arr = details.get(i).getAllies().split("/");
            if (arr[0].equals(arr[1])) {
                if (!details.get(i).isFull()) {
                    mainScreensAlliesController.getChoiceBoxContest().getItems().remove(getIndexToRemove(details.get(i).getBattle()));

                    details.get(i).setFull(true);
                    setFullUBoatInServer(details.get(i).getUboatName());

                }
            }
        }
    }
    public synchronized void addToAgentTable(List<DetailsAgent> lst) {
        if (TableSuccessCodes1.getItems().size() == 0) {
            AgentColumn.setCellValueFactory(new PropertyValueFactory<DetailsAgent,String>("agent"));
            TasksSizeColumn.setCellValueFactory(new PropertyValueFactory<DetailsAgent,String>("tasksSize"));
            ThreadsColumn.setCellValueFactory(new PropertyValueFactory<DetailsAgent,String>("threads"));
        }
        // final ObservableList<DetailsUBoat> data =
        //       FXCollections.observableArrayList(lst);
        TableSuccessCodes1.setEditable(true);
        TableSuccessCodes1.getItems().clear();
        for (int i = 0; i < lst.size(); i++) {

            TableSuccessCodes1.getItems().add(lst.get(i));
        }
    }
    public void setFullUBoatInServer(String uboatName) throws IOException {
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/full-uboat")
                .newBuilder()
                .addQueryParameter("alliesuboat", uboatName)
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();
        Call call = HTTP_CLIENT.newCall(request);

        Response response = call.execute();
    }

    public int getIndexToRemove(String name) {
        for (int i = 0; i < mainScreensAlliesController.getChoiceBoxContest().getItems().size(); i++) {
            if (mainScreensAlliesController.getChoiceBoxContest().getItems().get(i).equals(name)) {
                return i;
            }
        }
        return -1;

    }
public void cancelTimer(){
        timer.cancel();
}


}
