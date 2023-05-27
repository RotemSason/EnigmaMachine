package AlliesDetails;

import MainScreensAgent.MainScreensAgentController;
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

import static ClearAll.Configuration.Configuration.BASE_URL;
import static ClearAll.Configuration.Configuration.HTTP_CLIENT;

public class AlliesDetailsRefresher extends TimerTask {
    //private final Consumer<String> httpRequestLoggerConsumer;
    private final Consumer<List<String>> ListConsumer;
    private final Consumer<String>Consumer2;
    private String alliesName;

    private final BooleanProperty shouldUpdate;
    MainScreensAgentController mainScreensAgentController;

    public AlliesDetailsRefresher(BooleanProperty shouldUpdate, Consumer<List<String>> ListConsumer, Consumer<String>Consumer2, MainScreensAgentController  mainScreensAgentController) {
        this.shouldUpdate = shouldUpdate;
        this.ListConsumer = ListConsumer;
        this.Consumer2 = Consumer2;
       //this.alliesName=alliesName;
        this.mainScreensAgentController =mainScreensAgentController;
    }

    @Override
    public void run() {
        //  if (!shouldUpdate.get()) {
        //     return;
        //  }

        Request request = new Request.Builder()
                .url(BASE_URL+"/allies-refresh")
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

       String[] lst = gson.fromJson(dtoString, String[].class);
        ListConsumer.accept(Arrays.asList(lst));
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/check-start-contest")
                .newBuilder()
                .addQueryParameter("AlliesName", mainScreensAgentController.getNameAllies())
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

