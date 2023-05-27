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

@WebServlet(urlPatterns = "/check-clear")
public class checkClearServler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        String CheckClear;
        String alliesname = request.getParameter("nameAllies");
        if (alliesname.isEmpty()) {
            CheckClear = "false";
        } else {
            alliesname = alliesname.trim();

            EngineManager manager = ServletUtils.getEngineManager(getServletContext());
            SingleAlliesManager Single = manager.getSingleAllies(alliesname);
            CheckClear = Single.getIsClear();
            if(CheckClear.equals("true")){
             Single.setIsClear("false");
            }
        }
        String json = gson.toJson(CheckClear);
        out.write(json);
        out.flush();
    }
}
