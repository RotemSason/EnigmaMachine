package servlets;

import EngineManager.EngineManager;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/ReadyUBoat")
public class ReadyUBoatServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        String UBoatName = request.getParameter("UBoatName");
        UBoatName = UBoatName.trim();
        EngineManager manager = ServletUtils.getEngineManager(getServletContext());
        manager.getContest(UBoatName).setReadyButton(true);
    }
}