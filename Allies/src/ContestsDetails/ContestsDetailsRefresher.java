package ContestsDetails;

import MainScreensAllies.DetailsAgent;
import MainScreensAllies.DetailsUBoat;
import com.google.gson.Gson;
import javafx.beans.property.BooleanProperty;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

import static Configuration.Configuration.BASE_URL;
import static Configuration.Configuration.HTTP_CLIENT;

public class ContestsDetailsRefresher extends TimerTask {
   //private final Consumer<String> httpRequestLoggerConsumer;
   private final Consumer<List<DetailsUBoat>> ListConsumer;
    private final Consumer<List<DetailsAgent>> List2Consumer;
    private int requestNumber;
    private final BooleanProperty shouldUpdate;
    /*public ContestsDetailsRefresher(BooleanProperty shouldUpdate, Consumer<List<DetailsUBoat>> ListConsumer,Consumer<List<DetailsAgent>> List2Consumer) {
        this.shouldUpdate = shouldUpdate;
       // this.httpRequestLoggerConsumer = httpRequestLoggerConsumer;
        this.ListConsumer = ListConsumer;
        this.List2Consumer=List2Consumer;
        requestNumber = 0;
    }*/
    public ContestsDetailsRefresher(BooleanProperty shouldUpdate,Consumer<List<DetailsUBoat>> ListConsumer,Consumer<List<DetailsAgent>> List2Consumer) {
        this.shouldUpdate = shouldUpdate;
        // this.httpRequestLoggerConsumer = httpRequestLoggerConsumer;
        this.ListConsumer = ListConsumer;
        this.List2Consumer=List2Consumer;
        requestNumber = 0;
    }

   @Override
    public void run() {
       //  if (!shouldUpdate.get()) {
       //     return;
       //  }

       Request request = new Request.Builder()
               .url(BASE_URL + "/table-refresh")
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



       DetailsUBoat[] lst = gson.fromJson(dtoString, DetailsUBoat[].class);////////tambal
       ListConsumer.accept(Arrays.asList(lst));

       Request request2 = new Request.Builder()
               .url(BASE_URL + "/agent-refresh")
               .build();

       Call call2 = HTTP_CLIENT.newCall(request2);

       Response response2 = null;

       try {
           response2 = call2.execute();
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

       Gson gson2 = new Gson();
       String dtoAgentString = null;
       try {
           dtoAgentString = response2.body().string();
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       DetailsAgent[] lst2={};
if( dtoAgentString.length()>2) {
   lst2 = gson2.fromJson(dtoAgentString, DetailsAgent[].class);////////tambal
}
       List2Consumer.accept(Arrays.asList(lst2));
   }

    }

