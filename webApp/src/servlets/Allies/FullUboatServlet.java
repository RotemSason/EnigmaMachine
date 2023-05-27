package servlets.Allies;

import EngineManager.SingleAlliesManager;
import EngineManager.SingleUBoatEntry;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
@WebServlet(urlPatterns = "/full-uboat")
public class FullUboatServlet  extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("text/plain;charset=UTF-8");


            String alliesuboat = request.getParameter("alliesuboat");
            alliesuboat = alliesuboat.trim();

            synchronized (this) {
                ServletUtils.getEngineManager(getServletContext()).setFullUBoat(alliesuboat);
            }
        }
    }

