package TeamAgentProgress;

import EngineUI.AgentCurrCode;
import EngineUI.AgentProgress;
import MainScreensAllies.ContestAlliesComponentController;
import MainScreensAllies.MainScreensAlliesController;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import updateProgress.updateData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

import static Configuration.Configuration.BASE_URL;
import static Configuration.Configuration.HTTP_CLIENT;

public class TeamAgentProgressRefresher extends TimerTask {
    private final Consumer<List<AgentProgress>> Consumer;
    private ContestAlliesComponentController mainScreensAlliesController;

    public TeamAgentProgressRefresher(Consumer<List<AgentProgress>> Consumer, ContestAlliesComponentController mainScreensAlliesController) {
        this.Consumer = Consumer;
       this.mainScreensAlliesController=mainScreensAlliesController;
    }
    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/update-TeamAgent-Progress")
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
if(dtoString.length()<=2){
    List<AgentProgress>tmp=new ArrayList<>();
    Consumer.accept(tmp);
    if(dtoString.length()==0){
        int x=5;
    }
}
else{
        AgentProgress[] result = gson.fromJson(dtoString, AgentProgress[].class);
        Consumer.accept(Arrays.asList(result));
}}}
