package servlets.Agent;

import EngineManager.EngineManager;
import EngineManager.SingleAlliesManager;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/check-status-pushtasks")
public class CheckStatusPushTasks extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        String CheckReady;
        String alliesname = request.getParameter("alliesname");
        if (alliesname.isEmpty()) {
            CheckReady = "false";
        } else {
            alliesname = alliesname.trim();

            EngineManager manager = ServletUtils.getEngineManager(getServletContext());
            SingleAlliesManager Single = manager.getSingleAllies(alliesname);
            if (Single == null) {
                CheckReady = "false";
            } else {
                        if (Single.isPushTasks()) {
                            CheckReady = "true";
                        }
                        else {
                            CheckReady = "false";
                }
            }

        }
        String json = gson.toJson(CheckReady);
        out.write(json);
        out.flush();

    }
}
