package servlets;

import Engine.EngineImpl;
import EngineManager.SingleUBoatEntry;
import EngineUI.CurrCode;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

@WebServlet(urlPatterns = "/encode-string")
public class EncodeString extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String contestname = request.getParameter("contestname");
        contestname = contestname.trim();
        EngineImpl MainEngine = ServletUtils.getEngineManager(getServletContext()).getMainEngine(contestname);
        Properties prop = new Properties();
        prop.load(request.getInputStream());
        String encodeJson = prop.getProperty("encode");
        Gson gson = new Gson();
        String encodeString=gson.fromJson(encodeJson, String.class);
        String decodeString = MainEngine.DecodeStr(encodeString);
        SingleUBoatEntry single=ServletUtils.getEngineManager(getServletContext()).getContest(contestname);
        single.setStrDec(decodeString);
        single.setStrEncode(encodeString);
        for (int i = 0; i < single.getAllies().size(); i++) {/*check if null*/
            single.getAllies().get(i).setStrToDec(decodeString);
        }
        Gson gson2 = new Gson();
        String json = "";

        json = gson2.toJson( MainEngine.getCurrentCode());
        out.write("currCode=" +json+System.getProperty("line.separator") +"decode=" + decodeString);
        out.flush();
    }
}
