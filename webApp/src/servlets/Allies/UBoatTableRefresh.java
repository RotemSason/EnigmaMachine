package servlets.Allies;

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
import java.util.Set;
@WebServlet(urlPatterns = "/table-refresh")
public class UBoatTableRefresh extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            EngineManager manager = ServletUtils.getEngineManager(getServletContext());
            List<DetailsUBoat> detailsList = new ArrayList<>();
            for (int i = 0; i < manager.getContestsList().size(); i++) {
                detailsList.add( manager.getContestsList().get(i).getDetailsUBoat());
                detailsList.get(i).setAllies(manager.getContestsList().get(i).getAlliesSize()+manager.getContestsList().get(i).getDetailsUBoat().getAllies());

            }
            String json = gson.toJson(detailsList);
            out.write(json);
            out.flush();
        }
    }
}
