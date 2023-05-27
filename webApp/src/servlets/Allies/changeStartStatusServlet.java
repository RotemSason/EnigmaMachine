package servlets.Allies;

import EngineManager.SingleUBoatEntry;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
@WebServlet(urlPatterns = "/change-start-status")
public class changeStartStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        String alliesname = request.getParameter("alliesname");
        alliesname=alliesname.trim();
        synchronized (this) {
            SingleUBoatEntry single= ServletUtils.getEngineManager(getServletContext()).getUBoatByAlliesName(alliesname);
            for (int i = 0; i < single.getAllies().size(); i++) {
                single.getAllies().get(i).setStart(true);
            }
        }
    }
}