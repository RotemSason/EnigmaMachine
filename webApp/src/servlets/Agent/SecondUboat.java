package servlets.Agent;

import Contests.DetailsUBoat;
import EngineManager.SingleAgentManager;
import EngineManager.SingleAlliesManager;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/second-uboat")
public class SecondUboat extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        DetailsUBoat detailsUBoat;
        String nameallies = request.getParameter("nameallies");
        nameallies = nameallies.trim();
        synchronized (this) {
            SingleAlliesManager alliesManager = ServletUtils.getEngineManager(getServletContext()).getSingleAllies(nameallies);
            detailsUBoat = new DetailsUBoat(alliesManager.getUBoatManager().getBattle(), alliesManager.getUBoatManager().getContestname(), "false", alliesManager.getUBoatManager().getMainEngine().getBattle().getLevel(), alliesManager.getUBoatManager().getAllies().size() + "/" + alliesManager.getUBoatManager().getMainEngine().getBattle().getNumOfAllies(),false);
        }
        Gson gson = new Gson();
        String json = gson.toJson(detailsUBoat);
        out.write(json);
        out.flush();
    }
}