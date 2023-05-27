package servlets;

import EngineManager.EngineManager;
import EngineManager.SingleUBoatEntry;
import EngineManager.SingleAlliesManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
@WebServlet(urlPatterns = "/logout-uboat")
public class LogOutUBoat extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        String battleName = request.getParameter("UBoatName");
        battleName = battleName.trim();
        EngineManager manager = ServletUtils.getEngineManager(getServletContext());
        SingleUBoatEntry singleUBoat=manager.getContest(battleName);
        synchronized (this) {
            manager.getContestsList().remove(singleUBoat);
        }

    }
}