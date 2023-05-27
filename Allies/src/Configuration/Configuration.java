package Configuration;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Configuration {
public final static String CONTEST_NAME="contestname";
    //public final static String BASE_URL = "http://localhost:8080/webApp_Web_exploded/";
    public final static String BASE_URL="http://localhost:8080/webApp_Web";
   public final static String CONTESTALLIES_ROOM_FXML_RESOURCE_LOCATION = "/MainScreensAllies/contestPage.fxml";
    public final static OkHttpClient HTTP_CLIENT = new OkHttpClient();
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");

    public static Callback SIMPLE_CALLBACK = new Callback() {
        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            System.out.println("Ooops... something went wrong... error: " + e.getMessage());
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            System.out.println("Response:");
            System.out.println(response.body().string());
        }
    };
}
