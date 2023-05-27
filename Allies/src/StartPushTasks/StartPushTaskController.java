package StartPushTasks;

import MainScreensAllies.MainScreensAlliesController;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static Configuration.Configuration.BASE_URL;
import static Configuration.Configuration.HTTP_CLIENT;

public class StartPushTaskController {
    private TimerTask listRefresher;
    private Timer timer;
   private MainScreensAlliesController mainScreensAlliesController;
    private final BooleanProperty autoUpdate;
    public StartPushTaskController(MainScreensAlliesController mainScreensAlliesController) {
        autoUpdate = new SimpleBooleanProperty();
        this.mainScreensAlliesController=mainScreensAlliesController;
    }
    public void startListRefresher() {
        listRefresher = new StartPushTaskRefresher(
                autoUpdate,
                 this::updateStr, mainScreensAlliesController);//change to main screenagent
        timer = new Timer();
        timer.schedule(listRefresher, REFRESH_RATE, REFRESH_RATE);
    }
    private void updateStr(String details) {
        Platform.runLater(() -> {
            if (details.equals("true")) {
                if (!checkIfContestStart()) {
                     changeStartStatus();
                    //to change that the contest start
                    //mainScreensAgentController.setStartConteset(true);
                    //timer.cancel();
                    //   isTimerCancel = true;

                    StartPushTaskToServlet();
                    mainScreensAlliesController.getTotalTasksFromServer();

                }
                else{
                    mainScreensAlliesController.getTotalTasksFromServer();
                    timer.cancel();
                }
               // }
            }
        });
    }
    public final static int REFRESH_RATE = 2000;
    public synchronized void changeStartStatus(){
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/change-start-status")
                .newBuilder()
                .addQueryParameter("alliesname", mainScreensAlliesController.getAlliesName())
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

    public synchronized void StartPushTaskToServlet() {
        timer.cancel();
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/push-tasks")
                .newBuilder()
                .addQueryParameter("alliesname", mainScreensAlliesController.getAlliesName())
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
                .addQueryParameter("alliesname", mainScreensAlliesController.getAlliesName())
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
