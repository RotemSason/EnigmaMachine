package servlets.Agent;

import EngineManager.EngineManager;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;
import EngineManager.SingleAlliesManager;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet(urlPatterns = "/checkIf-Contest-Start")
public class checkIfContestStartServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        String alliesname = request.getParameter("alliesname");
        alliesname=alliesname.trim();
        String CheckReady;
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            synchronized (this) {
                SingleAlliesManager alliesManager = ServletUtils.getEngineManager(getServletContext()).getSingleAllies(alliesname);
                String json = gson.toJson(alliesManager.isStart());
                out.write(json);
                out.flush();
            }
        }
    }}
