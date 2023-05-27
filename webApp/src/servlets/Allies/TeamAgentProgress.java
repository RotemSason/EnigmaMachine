package servlets.Allies;

import Contests.DetailsAgent;
import EngineManager.EngineManager;
import EngineManager.SingleAlliesManager;
import EngineUI.AgentProgress;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns ="/update-TeamAgent-Progress")
public class TeamAgentProgress extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            String AlliesName = request.getParameter("AlliesName");
            AlliesName = AlliesName.trim();
            Gson gson = new Gson();
            EngineManager manager = ServletUtils.getEngineManager(getServletContext());
            List<AgentProgress> detailsList = new ArrayList<>();
            SingleAlliesManager alliesManager=manager.getSingleAllies(AlliesName);

            for (int i = 0; i < alliesManager.getAgentLst().size(); i++){
              AgentProgress tmp=new AgentProgress(alliesManager.getAgentLst().get(i).getName(),String.valueOf(alliesManager.getAgentLst().get(i).getTotalPullTasks()),String.valueOf(alliesManager.getAgentLst().get(i).getTotalPullTasks()-alliesManager.getAgentLst().get(i).getTotalDoneTasks()),String.valueOf(alliesManager.getAgentLst().get(i).getSuccessCode().size()));
                detailsList.add(tmp);
                }
            String json = gson.toJson(detailsList);
            out.write(json);
            out.flush();
        }
    }

}
