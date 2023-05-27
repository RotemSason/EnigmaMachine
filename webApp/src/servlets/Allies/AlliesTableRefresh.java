package servlets.Allies;

import Contests.DetailsAgent;
import Contests.DetailsUBoat;
import EngineManager.EngineManager;
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
@WebServlet(urlPatterns ="/agent-refresh")
public class AlliesTableRefresh extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("application/json");
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                EngineManager manager = ServletUtils.getEngineManager(getServletContext());
                List<DetailsAgent> detailsList = new ArrayList<>();
                if( manager.getAlliesList().get(0).getAgentLst().size()>0){
                    int x=2;
                }
                for (int i = 0; i < manager.getAlliesList().size(); i++) {
                    for (int j = 0; j < manager.getAlliesList().get(i).getAgentLst().size(); j++) {
                        DetailsAgent tmp=new DetailsAgent(manager.getAlliesList().get(i).getAgentLst().get(j).getalliesName(), String.valueOf(manager.getAlliesList().get(i).getAgentLst().get(j).getAmountThreads()),String.valueOf(manager.getAlliesList().get(i).getAgentLst().get(j).getNumOfTasks()));
                        detailsList.add(tmp);
                    }
                }
                String json = gson.toJson(detailsList);
                out.write(json);
                out.flush();
            }
        }
}
