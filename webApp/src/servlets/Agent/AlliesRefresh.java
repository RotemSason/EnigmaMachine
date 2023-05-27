package servlets.Agent;

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

@WebServlet(urlPatterns = "/allies-refresh")
public class AlliesRefresh extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            EngineManager manager = ServletUtils.getEngineManager(getServletContext());
            List<String> detailsList = new ArrayList<>();
            detailsList=manager.getNamesOfAllie();
            String json = gson.toJson(detailsList);
            out.write(json);
            out.flush();
        }
    }
}
