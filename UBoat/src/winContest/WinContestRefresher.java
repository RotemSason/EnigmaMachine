package winContest;

import EngineUI.AgentCurrCode;
import MainScreensUBoat.ContestUBoatComponentController;
import MainScreensUBoat.MainScreensUBoatController;
import com.google.gson.Gson;
import javafx.beans.property.BooleanProperty;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

import static Configuration.Configuration.BASE_URL;
import static Configuration.Configuration.HTTP_CLIENT;

public class WinContestRefresher extends TimerTask {
    private final Consumer<String>Consumer2;
    private final Consumer<List<AgentCurrCode>> ListConsumer;
    //private final BooleanProperty shouldUpdate;
    ContestUBoatComponentController mainScreensUBoatController;
    private String nameContest;
    public WinContestRefresher(Consumer<String>Consumer2, Consumer<List<AgentCurrCode>> ListConsumer, ContestUBoatComponentController mainScreensUBoatController) {
      //  this.ListConsumer = ListConsumer;
        this.Consumer2 = Consumer2;
        this.ListConsumer=ListConsumer;
        this.mainScreensUBoatController=mainScreensUBoatController;
        //this.alliesName=alliesName;
       // this.mainScreensAgentController =mainScreensAgentController;
    }
    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/check-winner")
                .newBuilder()
                .addQueryParameter("nameContest",mainScreensUBoatController.getContestName())
                .addQueryParameter("nameBattle","")
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

        String lst = gson.fromJson(dtoString, String.class);
        Consumer2.accept(lst);
        String finalUrl1 = HttpUrl
                .parse(BASE_URL + "/Update-SuccessCode-Uboat")
                .newBuilder()
                .addQueryParameter("nameContest",mainScreensUBoatController.getContestName())
                .build()
                .toString();
        Request request1 = new Request.Builder()
                .url(finalUrl1)
                .build();

        Call call1 = HTTP_CLIENT.newCall(request1);

        Response response1 = null;

        try {
            response1 = call1.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson1 = new Gson();
        String dtoString1 = null;
        try {
            dtoString = response1.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AgentCurrCode[] lst1 = gson.fromJson(dtoString, AgentCurrCode[].class);
        ListConsumer.accept(Arrays.asList(lst1));
    }
    }


