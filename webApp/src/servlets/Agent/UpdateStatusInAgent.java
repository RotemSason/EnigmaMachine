package servlets.Agent;
import EngineManager.SingleAlliesManager;
import EngineManager.EngineManager;
import EngineManager.SingleUBoatEntry;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/update-status")
public class UpdateStatusInAgent extends HttpServlet {
    @Override
    protected synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String alliesname = request.getParameter("nameAllies");
        String agentname = request.getParameter("nameAgent");
        alliesname = alliesname.trim();
        agentname=agentname.trim();
        EngineManager manager = ServletUtils.getEngineManager(getServletContext());
        SingleUBoatEntry Single = manager.getUBoatByAlliesName(alliesname);
        SingleAlliesManager alliesManager=manager.getSingleAllies(alliesname);

String[]arr=new String[4];
arr[0]=Single.getStatus();
arr[1]= String.valueOf(alliesManager.getAgentByName(agentname).getTotalPullTasks());
arr[2]= String.valueOf(alliesManager.getAgentByName(agentname).getTotalDoneTasks());
        arr[3] = Single.getAllies().size() + "/" + Single.getMainEngine().getBattle().getNumOfAllies();
        Gson gson = new Gson();
        String json = gson.toJson(arr);
        out.write(json);
        out.flush();
    }
}
