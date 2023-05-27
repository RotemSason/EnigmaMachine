package StartPushTasks;

import MainScreensAllies.MainScreensAlliesController;
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

public class StartPushTaskRefresher extends TimerTask {
    private final Consumer<String>Consumer2;
    private String alliesName;

    private final BooleanProperty shouldUpdate;
    MainScreensAlliesController mainScreensAlliesController;
    public StartPushTaskRefresher(BooleanProperty shouldUpdate,Consumer<String>Consumer2, MainScreensAlliesController  mainScreensAlliesController) {
        this.shouldUpdate = shouldUpdate;
        this.Consumer2 = Consumer2;
        //this.alliesName=alliesName;
        this.mainScreensAlliesController =mainScreensAlliesController;
    }
    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/check-start-contest")
                .newBuilder()
                .addQueryParameter("AlliesName", mainScreensAlliesController.getAlliesName())
                .build()
                .toString();

        Request request2 = new Request.Builder()
                .url(finalUrl)
                .build();

        Call call2 = HTTP_CLIENT.newCall(request2);

        Response response2= null;

        try {
            response2 = call2.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson2 = new Gson();
        String StartContest = null;
        try {
            StartContest = response2.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String str = gson2.fromJson(StartContest, String.class);
        Consumer2.accept(str);

    }

}
