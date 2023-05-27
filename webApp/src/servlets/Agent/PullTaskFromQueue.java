package servlets.Agent;

import Allies.TaskAgent;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/pull-tasks")
public class PullTaskFromQueue extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        int taskToPull = Integer.parseInt(request.getParameter("tasksToPull"));
        String nameallies = request.getParameter("nameAllies");
        String nameagent = request.getParameter("nameAgent");
        nameallies = nameallies.trim();
        nameagent = nameagent.trim();
        List<TaskAgent> Tasklst = new ArrayList<>();
        synchronized (this) {
            SingleAlliesManager alliesManager = ServletUtils.getEngineManager(getServletContext()).getSingleAllies(nameallies);
            if (alliesManager.getUboat().getWinAllies().isEmpty()) {
                if (taskToPull > alliesManager.getSizeQueue()) {
                    for (int i = 0; i < alliesManager.getSizeQueue(); i++) {
                        Tasklst.add(alliesManager.getTaskFromQueue());
                    }
                } else {
                    for (int i = 0; i < taskToPull; i++) {
                        Tasklst.add(alliesManager.getTaskFromQueue());
                    }
                }
                SingleAgentManager agent = alliesManager.getAgentByName(nameagent);
                agent.setTotalPullTasks(agent.getTotalPullTasks() + Tasklst.size());
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(Tasklst);
        out.write(json);
        out.flush();
    }
}
