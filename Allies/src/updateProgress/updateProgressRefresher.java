package updateProgress;

import ContestsDetails.ContestsDetailsRefresher;
import EngineUI.AgentCurrCode;
import MainScreensAllies.ContestAlliesComponentController;
import MainScreensAllies.DetailsUBoat;
import MainScreensAllies.MainScreensAlliesController;
import api.HttpStatusUpdate;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import static Configuration.Configuration.BASE_URL;
import static Configuration.Configuration.HTTP_CLIENT;


public class updateProgressRefresher extends TimerTask {

    private final Consumer<updateData> Consumer;
    private final Consumer<String> Consumer2;
    private final Consumer<String> Consumer3;
    private final Consumer<String> Consumer4;
    private final Consumer<List<AgentCurrCode>> ListConsumer;
    private ContestAlliesComponentController mainScreensAlliesController;

    public updateProgressRefresher( Consumer<updateData> Consumer,Consumer<String> Consumer2,Consumer<String> Consumer3,Consumer<String> Consumer4,Consumer<List<AgentCurrCode>> ListConsumer, ContestAlliesComponentController mainScreensAlliesController) {
        this.Consumer = Consumer;
        this.Consumer2 = Consumer2;
        this.Consumer3 = Consumer3;
        this.Consumer4 = Consumer4;
        this.ListConsumer=ListConsumer;
        this.mainScreensAlliesController=mainScreensAlliesController;
    }
    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/update-progress-refresh")
                .newBuilder()
                .addQueryParameter("AlliesName", mainScreensAlliesController.getAlliesName())
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

        updateData result = gson.fromJson(dtoString, updateData.class);
        Consumer.accept(result);


        String finalUrl2 = HttpUrl
                .parse(BASE_URL + "/check-winner")
                .newBuilder()
                .addQueryParameter("nameContest","")
                .addQueryParameter("nameBattle",mainScreensAlliesController.getNameBattle())
                .build()
                .toString();
        Request request2 = new Request.Builder()
                .url(finalUrl2)
                .build();

        Call call2 = HTTP_CLIENT.newCall(request2);

        Response response2 = null;

        try {
            response2 = call2.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson2 = new Gson();
        String dtoString2 = null;
        try {
            dtoString2 = response2.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String result2 = gson.fromJson(dtoString2, String.class);
        Consumer2.accept(result2);


        String finalUrl3 = HttpUrl
                .parse(BASE_URL + "/update-status-allies")
                .newBuilder()
                .addQueryParameter("nameAllies", mainScreensAlliesController.getAlliesName())
                .build()
                .toString();

        Request request3 = new Request.Builder()
                .url(finalUrl3)
                .build();

        Call call3 = HTTP_CLIENT.newCall(request3);

        Response response3 = null;

        try {
            response3 = call3.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson3 = new Gson();
        String dtoString3 = null;
        try {
            dtoString3 = response3.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] details = gson.fromJson(dtoString3, String[].class);
        Consumer3.accept(details[0]);
        Consumer4.accept(details[1]);

        String finalUrl5 = HttpUrl
                .parse(BASE_URL + "/Update-SuccessCode-Allies")
                .newBuilder()
                .addQueryParameter("nameAllies",mainScreensAlliesController.getAlliesName())
                .build()
                .toString();
        Request request5 = new Request.Builder()
                .url(finalUrl5)
                .build();

        Call call5 = HTTP_CLIENT.newCall(request5);

        Response response5 = null;

        try {
            response5 = call5.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson5 = new Gson();
        String dtoString5 = null;
        try {
            dtoString5 = response5.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AgentCurrCode[] lst1 = gson.fromJson(dtoString5, AgentCurrCode[].class);
        ListConsumer.accept(Arrays.asList(lst1));
    }
    }

