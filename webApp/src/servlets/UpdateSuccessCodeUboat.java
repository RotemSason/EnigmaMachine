package servlets;
import Agent.AgentCurrCode;
import EngineManager.SingleUBoatEntry;
import EngineManager.EngineManager;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns ="/Update-SuccessCode-Uboat")
public class UpdateSuccessCodeUboat extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        String nameContest = request.getParameter("nameContest");
        nameContest=nameContest.trim();
        EngineManager manager = ServletUtils.getEngineManager(getServletContext());
        SingleUBoatEntry CurrUboat = manager.getContest(nameContest);
        List<AgentCurrCode> lst=CurrUboat.getAllsuccessCode();
        String json = gson.toJson(lst);
        out.write(json);
        out.flush();
    }
}
