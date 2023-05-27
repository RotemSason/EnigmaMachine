package servlets.Allies;

import Allies.AlliesManager;
import EngineManager.SingleAlliesManager;
import EngineManager.SingleUBoatEntry;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;
import utilis.SessionUtils;

import java.io.IOException;

@WebServlet(urlPatterns = "/ready-allies")
public class ReadyAlliesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");


    String alliesName = request.getParameter("alliesname");
    int sizeTask = Integer.parseInt(request.getParameter("sizetask"));
        alliesName = alliesName.trim();


        synchronized (this) {
            SingleAlliesManager alliesManager=ServletUtils.getEngineManager(getServletContext()).getSingleAllies(alliesName);
             alliesManager.setSizeTask(sizeTask);
             alliesManager.setReady(true);
            }
        }
    }


