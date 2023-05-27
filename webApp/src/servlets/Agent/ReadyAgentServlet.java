package servlets.Agent;

import Contests.DetailsUBoat;
import EngineManager.SingleAgentManager;
import EngineManager.SingleAlliesManager;
import EngineManager.SingleUBoatEntry;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/ready-agent")
public class ReadyAgentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
         PrintWriter out=response.getWriter();
DetailsUBoat detailsUBoat;
        String agentname = request.getParameter("agentname");
        String nameallies = request.getParameter("nameallies");
        int threadsAmount = Integer.parseInt(request.getParameter("threadsamount"));
        int taskToPull = Integer.parseInt(request.getParameter("tasktopull"));
        agentname = agentname.trim();
        nameallies = nameallies.trim();


        synchronized (this) {
            SingleAlliesManager alliesManager = ServletUtils.getEngineManager(getServletContext()).getSingleAllies(nameallies);
            SingleAgentManager agent = new SingleAgentManager(alliesManager, agentname, threadsAmount, taskToPull);
            alliesManager.addAgent(agent);
            detailsUBoat = new DetailsUBoat(alliesManager.getUBoatManager().getBattle(),alliesManager.getUBoatManager().getContestname(),"false",alliesManager.getUBoatManager().getMainEngine().getBattle().getLevel(),alliesManager.getUBoatManager().getAllies().size()+"/"+alliesManager.getUBoatManager().getMainEngine().getBattle().getNumOfAllies(),false);
        }
        Gson gson=new Gson();
        String json = gson.toJson(detailsUBoat);
        out.write(json);
        out.flush();}

    }



