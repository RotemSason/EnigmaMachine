package servlets.Allies;

import EngineManager.EngineManager;
import EngineManager.SingleUBoatEntry;
import EngineManager.SingleAlliesManager;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
@WebServlet(urlPatterns = "/update-status-allies")
public class UpdateStatusAllies extends HttpServlet {
    @Override
    protected synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String alliesname = request.getParameter("nameAllies");
        alliesname = alliesname.trim();
        EngineManager manager = ServletUtils.getEngineManager(getServletContext());
        SingleUBoatEntry Single = manager.getUBoatByAlliesName(alliesname);
        SingleAlliesManager alliesManager = manager.getSingleAllies(alliesname);
        String[] arr = new String[2];
        arr[0] = Single.getStatus();
        arr[1] = Single.getAllies().size() + "/" + Single.getMainEngine().getBattle().getNumOfAllies();
        Gson gson = new Gson();
        String json = gson.toJson(arr);
        out.write(json);
        out.flush();
    }
}