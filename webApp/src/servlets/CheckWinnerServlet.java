package servlets;
import  EngineManager.SingleUBoatEntry;
import EngineManager.EngineManager;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns ="/check-winner")
public class CheckWinnerServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String checkWin = "";
        try (PrintWriter out = response.getWriter()) {
            String nameContest = request.getParameter("nameContest");
            String nameBattle = request.getParameter("nameBattle");
            nameContest = nameContest.trim();
            nameBattle = nameBattle.trim();
            if (!nameContest.isEmpty()) {
                EngineManager manager = ServletUtils.getEngineManager(getServletContext());
                SingleUBoatEntry singleUBoatEntry = manager.getContest(nameContest);
                checkWin = singleUBoatEntry.getWinAllies();
            } else {
                if (!nameBattle.isEmpty()) {
                    EngineManager manager = ServletUtils.getEngineManager(getServletContext());
                    SingleUBoatEntry singleUBoatEntry = manager.getBattleField(nameBattle);
                    checkWin = singleUBoatEntry.getWinAllies();
                }
            }
                Gson gson = new Gson();
                String json = gson.toJson(checkWin);
                out.write(json);
                out.flush();

        }
    }
}
