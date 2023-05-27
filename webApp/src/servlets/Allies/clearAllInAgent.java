package servlets.Allies;

import EngineManager.EngineManager;
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

@WebServlet(urlPatterns = "/clear-all-agent")
public class clearAllInAgent extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        String alliesname = request.getParameter("AlliesName");
        alliesname = alliesname.trim();
        EngineManager manager = ServletUtils.getEngineManager(getServletContext());
        SingleAlliesManager Single = manager.getSingleAllies(alliesname);
        SingleUBoatEntry singleUBoat=manager.getUBoatByAlliesName(alliesname);
        Single.setIsClear("true");
        for (int i = 0; i < Single.getAgentLst().size(); i++) {
            Single.getAgentLst().get(i).clearData();
        }
        Single.clearData();

        if(singleUBoat.getStatus().equals("Active")){
            singleUBoat.clearAll();
        }
    }
}
