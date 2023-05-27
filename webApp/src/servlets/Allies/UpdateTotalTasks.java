package servlets.Allies;

import EngineManager.SingleAlliesManager;
import EngineManager.SingleUBoatEntry;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/update-total-tasks")
public class UpdateTotalTasks extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        String alliesname = request.getParameter("AlliesName");
        try (PrintWriter out = response.getWriter()) {
            alliesname = alliesname.trim();
            synchronized (this) {
                SingleAlliesManager single = ServletUtils.getEngineManager(getServletContext()).getSingleAllies(alliesname);
                int totalTasks = single.getTotalTasks();
                String strToDec=single.getStrToDec();
                String [] arr=new String[2];
                arr[0]=String.valueOf(totalTasks);
                arr[1]=strToDec;
                Gson gson = new Gson();
                String json = gson.toJson(arr);
                out.write(json);
                out.flush();
            }
        }
    }
}
