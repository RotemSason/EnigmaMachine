package servlets.Allies;

import Agent.AgentCurrCode;
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
import java.util.ArrayList;
import java.util.List;
@WebServlet(urlPatterns ="/Update-SuccessCode-Allies")
public class updateSuccessCodeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        String nameAllies = request.getParameter("nameAllies");
        nameAllies=nameAllies.trim();
        EngineManager manager = ServletUtils.getEngineManager(getServletContext());
        SingleAlliesManager CurrAllies = manager.getSingleAllies(nameAllies);
        List<AgentCurrCode> lst=new ArrayList<>();
        for (int i = 0; i < CurrAllies.getAgentLst().size(); i++) {
            for (int j = 0; j <CurrAllies.getAgentLst().get(i).getSuccessCode().size() ; j++) {
                lst.add(CurrAllies.getAgentLst().get(i).getSuccessCode().get(j));
            }
        }
        String json = gson.toJson(lst);
        out.write(json);
        out.flush();
    }
}