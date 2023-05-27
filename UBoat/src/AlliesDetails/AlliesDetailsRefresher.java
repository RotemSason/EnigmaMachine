package AlliesDetails;

import MainScreensUBoat.DetailsAllies;
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

public class AlliesDetailsRefresher extends TimerTask {
    private final Consumer<List<DetailsAllies>> ListConsumer;
    private String battle;

    public AlliesDetailsRefresher(Consumer<List<DetailsAllies>> ListConsumer,String battle) {

        this.ListConsumer = ListConsumer;
        this.battle=battle;
    }

    @Override
    public void run() {
        //  if (!shouldUpdate.get()) {
        //     return;
        //  }
        String finalUrl = HttpUrl
                .parse(BASE_URL + "/table-alliesUboat")
                .newBuilder()
                .addQueryParameter("battle",battle)
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrl)
                .build();
if(battle.length()>2){
    int x=0;
}
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

        DetailsAllies[] lst={};
        if(dtoString.length()>2) {
            lst = gson.fromJson(dtoString, DetailsAllies[].class);////////tambal
        }
        ListConsumer.accept(Arrays.asList(lst));
    }
}
