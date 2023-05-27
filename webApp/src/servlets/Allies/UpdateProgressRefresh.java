package servlets.Allies;

import Contests.updateData;
import EngineManager.SingleAlliesManager;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/update-progress-refresh")
public class UpdateProgressRefresh extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        String alliesname = request.getParameter("AlliesName");

        try (PrintWriter out = response.getWriter()) {
            alliesname = alliesname.trim();
            synchronized (this) {
                updateData data;
                SingleAlliesManager single = ServletUtils.getEngineManager(getServletContext()).getSingleAllies(alliesname);
                if(single==null){
                     data=new updateData("0","0");
                }
                else {
                     data = new updateData(String.valueOf(single.getPushTasks()), String.valueOf(single.getFinishTasks()));
                }
                Gson gson = new Gson();
                String json = gson.toJson(data);
                out.write(json);
                out.flush();
            }
        }
    }
}
