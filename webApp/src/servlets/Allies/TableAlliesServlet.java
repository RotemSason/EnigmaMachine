package servlets.Allies;

import Contests.DetailsAllies;
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
import java.util.ArrayList;
import java.util.List;
@WebServlet(urlPatterns ="/table-allies-contest")
public class TableAlliesServlet extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("application/json");
            try (PrintWriter out = response.getWriter()) {
                String alliesbattle = request.getParameter("battle");
                alliesbattle = alliesbattle.trim();
                Gson gson = new Gson();
                EngineManager manager = ServletUtils.getEngineManager(getServletContext());
                SingleUBoatEntry CurrUboat = manager.getBattleField(alliesbattle);
                List<DetailsAllies> detailsList = new ArrayList<>();
                if (CurrUboat != null) {
                    for (int i = 0; i < CurrUboat.getAllies().size(); i++) {
                        DetailsAllies tmp = new DetailsAllies(CurrUboat.getAllies().get(i).getAlliesName(), String.valueOf(CurrUboat.getAllies().get(i).getAgentLst().size()), String.valueOf(CurrUboat.getAllies().get(i).getSizeTask()));
                        detailsList.add(tmp);
                    }
                }
                String json = gson.toJson(detailsList);
                out.write(json);
                out.flush();
            }
        }
    }