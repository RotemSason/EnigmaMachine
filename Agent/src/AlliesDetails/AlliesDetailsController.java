package AlliesDetails;

import MainScreensAgent.MainScreensAgentController;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static ClearAll.Configuration.Configuration.BASE_URL;
import static ClearAll.Configuration.Configuration.HTTP_CLIENT;

public class AlliesDetailsController {
    private TimerTask listRefresher;
    private Timer timer;
    private boolean isTimerCancel=false;
    private MainScreensAgentController mainScreensAgentController;

    private final BooleanProperty autoUpdate;
private int counter=0;
    public AlliesDetailsController() {
        autoUpdate = new SimpleBooleanProperty();
    }

    public synchronized void addToChoiceBox(List<String> lst) {
        if (lst.size() != (mainScreensAgentController.getChoiceBoxAllies().getItems().size())) {
            for (int i = mainScreensAgentController.getChoiceBoxAllies().getItems().size(); i < lst.size(); i++) {
                mainScreensAgentController.getChoiceBoxAllies().getItems().add(lst.get(i));

            }

        }
    }

    public void startListRefresher() {
        listRefresher = new AlliesDetailsRefresher(
                autoUpdate,
                this::updateUsersList, this::updateStr, mainScreensAgentController);//change to main screenagent
        timer = new Timer();
        timer.schedule(listRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    private void updateUsersList(List<String> details) {
        Platform.runLater(() -> {
            addToChoiceBox(details);
        });
    }

    private void updateStr(String details) {
        Platform.runLater(() -> {
            if (details.equals("true")) {
               // System.out.println(details + checkIfContestStart());
                if (checkIfPushTasksStart()) {
                   // changeStartStatus();
                    //to change that the contest start
                    //mainScreensAgentController.setStartConteset(true);
                    timer.cancel();
                    isTimerCancel = true;

                  //  StartPushTaskToServlet();


                mainScreensAgentController.getbruthForceAgentComponentController().StartBruthForce();
            }}
        });
    }

    public final static int REFRESH_RATE = 2000;

    public void setMainScreensController(MainScreensAgentController mainScreensController) {
        this.mainScreensAgentController = mainScreensController;
    }
    public synchronized boolean checkIfPushTasksStart(){
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/check-status-pushtasks")
                .newBuilder()
                .addQueryParameter("alliesname", mainScreensAgentController.getNameAllies())
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
        String StartPush = null;
        try {
            StartPush = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String str = gson.fromJson(StartPush, String.class);
        if(str.equals("true")){
            return true;
        }
        else{
            return false;
        }
    }



    public synchronized void StartPushTaskToServlet() {
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/push-tasks")
                .newBuilder()
                .addQueryParameter("alliesname", mainScreensAgentController.getNameAllies())
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

    }

    public boolean checkIfContestStart() {
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/checkIf-Contest-Start")
                .newBuilder()
                .addQueryParameter("alliesname", mainScreensAgentController.getNameAllies())
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
        Gson gson2 = new Gson();
        String StartContest = null;
        try {
            StartContest = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String str = gson2.fromJson(StartContest, String.class);
        if(str.equals("true")){
            return true;
        }
        else{
            return false;
        }
    }

}

