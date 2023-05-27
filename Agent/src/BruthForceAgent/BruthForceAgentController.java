package BruthForceAgent;

import MainScreensAgent.MainScreensAgentController;
import com.google.gson.Gson;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ClearAll.Configuration.Configuration.BASE_URL;
import static ClearAll.Configuration.Configuration.HTTP_CLIENT;

public class BruthForceAgentController {
    @FXML
    private TableView<SuccessCode> TableSuccessCodes;
    @FXML
    private TableColumn<SuccessCode, String> CandidateStringColumn;

    @FXML
    private TableColumn<SuccessCode, String> rotorsColumn;

    @FXML
    private TableColumn<SuccessCode, String> positionsColumn;

    @FXML
    private TableColumn<SuccessCode, Character> reflectorColumn;
    private MainScreensAgentController mainScreensAgentController;
    private List<AgentCurrCode> SuccessCode;
    private boolean isWinContest=false;
    private int totalDoneTasks;
    private int totalPullTasks;
    private ExecutorService ThreadExecutor;
    private boolean taskExist;

    public BruthForceAgentController() {
        SuccessCode = new ArrayList<>();
        totalDoneTasks=0;
        totalPullTasks=0;

    }

    public void setMainScreensAgentController(MainScreensAgentController mainScreensAgentController) {
        this.mainScreensAgentController = mainScreensAgentController;

    }
    public void setIsWinContest(boolean isWin){
        isWinContest=isWin;
    }
    public void StartBruthForce() {
        if(mainScreensAgentController.getUBoatNameLabel().getText().equals("")){
mainScreensAgentController.setUBoatSecond();
        }
        mainScreensAgentController.getclearAllController().startListRefresher();
        UIAdapter uiAdapter = createUIAdapter();
        int tasksToPull = mainScreensAgentController.getTasksToPull();
         taskExist = true;
       int numOfThreads = mainScreensAgentController.getnumOfThreads();
        ThreadExecutor = Executors.newFixedThreadPool(numOfThreads);

        while (taskExist) {
            String finalUrl = HttpUrl
                    .parse(BASE_URL + "/pull-tasks")
                    .newBuilder()
                    .addQueryParameter("tasksToPull", String.valueOf(tasksToPull))
                    .addQueryParameter("nameAllies", String.valueOf(mainScreensAgentController.getNameAllies()))
                    .addQueryParameter("nameAgent", String.valueOf(mainScreensAgentController.getAgentName()))
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
            List<TaskAgent> TasksLst;
            Gson gson = new Gson();
            String dtoString = null;
            try {
                dtoString = response.body().string();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            TaskAgent[] lst = gson.fromJson(dtoString, TaskAgent[].class);

            TasksLst = Arrays.asList(lst);/*check*/

            if (TasksLst.size() == 0) {/*check if null*/
                taskExist = false;
            } else {
                totalPullTasks+=TasksLst.size();

                for (int i = 0; i < TasksLst.size(); i++) {
                        ThreadAgent agent=new ThreadAgent(TasksLst.get(i), mainScreensAgentController.getAgentName(), uiAdapter, mainScreensAgentController.getNameAllies());
                        ThreadExecutor.execute(agent);
                        totalDoneTasks++;
                    }
            }

        }}//ThreadExecutor.shutdown();}


    public synchronized void addToTable(SuccessCode code){

        if(TableSuccessCodes.getItems().size()==0){
            CandidateStringColumn.setCellValueFactory(new PropertyValueFactory<SuccessCode, String>("strCandidate"));

            rotorsColumn.setCellValueFactory(new PropertyValueFactory<SuccessCode, String>("rotors"));

            positionsColumn.setCellValueFactory(new PropertyValueFactory<SuccessCode, String>("positions"));

            reflectorColumn.setCellValueFactory(new PropertyValueFactory<SuccessCode, Character>("reflector"));
        }
        final ObservableList<SuccessCode> data =
                FXCollections.observableArrayList(code);
        TableSuccessCodes.setEditable(true);
        TableSuccessCodes.getItems().add(code);
        mainScreensAgentController.getTotalCandidatesLabel().setText(String.valueOf(TableSuccessCodes.getItems().size()));

    }
    private UIAdapter createUIAdapter() {
        return new UIAdapter(
                successCode -> {
                    addToTable(successCode);
                }
        );
    }

    public int getTotalDoneTasks() {
        return totalDoneTasks;
    }

    public int getTotalPullTasks() {
        return totalPullTasks;
    }

    public ExecutorService getThreadExecutor() {
        return ThreadExecutor;
    }

    public void setTaskExist(boolean taskExist) {
        this.taskExist = taskExist;
    }

    public TableView<SuccessCode> getTableSuccessCodes() {
        return TableSuccessCodes;
    }
}