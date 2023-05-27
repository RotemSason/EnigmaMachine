package ClearAll;

import MainScreensAgent.MainScreensAgentController;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.TimerTask;
import java.util.function.Consumer;

import static ClearAll.Configuration.Configuration.BASE_URL;
import static ClearAll.Configuration.Configuration.HTTP_CLIENT;

public class ClearAllRefresher extends TimerTask {

    private final Consumer<String> Consumer;
    private MainScreensAgentController mainScreensAgentController;

    public ClearAllRefresher(Consumer<String> Consumer, MainScreensAgentController mainScreensAgentController) {
        this.Consumer = Consumer;
        this.mainScreensAgentController = mainScreensAgentController;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/check-clear")
                .newBuilder()
                .addQueryParameter("nameAllies", mainScreensAgentController.getNameAllies())
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

        String isClear = gson.fromJson(dtoString, String.class);
        Consumer.accept(isClear);
    }
}
