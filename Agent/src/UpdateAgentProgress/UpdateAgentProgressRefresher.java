package UpdateAgentProgress;

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

public class UpdateAgentProgressRefresher extends TimerTask {

    private final Consumer<String> Consumer;
    private final Consumer<String> Consumer2;
    private final Consumer<String> Consumer3;
    private final Consumer<String> Consumer4;
    private final Consumer<String> Consumer5;
    private final Consumer<String> Consumer6;
    private String alliesName;
    private String agentName;
private MainScreensAgentController mainScreensAgentController;


    public UpdateAgentProgressRefresher( Consumer<String> Consumer,Consumer<String> Consumer2,Consumer<String> Consumer3,Consumer<String> Consumer4,Consumer<String> Consumer5,Consumer<String> Consumer6,String alliesName,String agentName,MainScreensAgentController mainScreensAgentController) {
        this.Consumer = Consumer;
        this.Consumer2 = Consumer2;
        this.Consumer3 = Consumer3;
        this.Consumer4 = Consumer4;
        this.Consumer5 = Consumer5;
        this.Consumer6 = Consumer6;
        this.alliesName=alliesName;
        this.mainScreensAgentController=mainScreensAgentController;
this.agentName=agentName;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/update-status")
                .newBuilder()
                .addQueryParameter("nameAllies", alliesName)
                .addQueryParameter("nameAgent", agentName)
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

        String[] details = gson.fromJson(dtoString, String[].class);

        Consumer.accept(details[0]);
        Consumer2.accept(details[1]);
        Consumer3.accept(details[2]);
        Consumer5.accept(String.valueOf(Integer.parseInt(details[1])-Integer.parseInt(details[2])));
        Consumer6.accept(details[3]);

        String finalUrl2= HttpUrl
                .parse(BASE_URL + "/check-winner")
                .newBuilder()
                .addQueryParameter("nameContest","")
                .addQueryParameter("nameBattle",mainScreensAgentController.getBattleFieldLabel())
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
        Consumer4.accept(result2);

    }


    }
