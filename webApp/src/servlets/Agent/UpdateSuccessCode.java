package servlets.Agent;

import Agent.AgentCurrCode;
import EngineManager.SingleAgentManager;
import EngineManager.SingleAlliesManager;
import EngineManager.SingleUBoatEntry;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = "/update-successcode")
public class UpdateSuccessCode extends HttpServlet {
    @Override
    protected synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        String agentname = request.getParameter("NameAgent");
        String alliesname = request.getParameter("NameAllies");
        String successCodesJson = request.getParameter("successCodes");
        agentname = agentname.trim();
        alliesname = alliesname.trim();
        Gson gson = new Gson();
        AgentCurrCode[] successCodesArr = gson.fromJson(successCodesJson, AgentCurrCode[].class);
        List<AgentCurrCode> successCodesLst = Arrays.asList(successCodesArr);
        SingleAlliesManager alliesManager= ServletUtils.getEngineManager(getServletContext()).getSingleAllies(alliesname);
        alliesManager.setFinishTasks(alliesManager.getFinishTasks()+1);
        SingleAgentManager agentManager=alliesManager.getAgentByName(agentname);
        SingleUBoatEntry singleUBoatEntry=ServletUtils.getEngineManager(getServletContext()).getUBoatByAlliesName(alliesname);

        agentManager.setTotalDoneTasks(agentManager.getTotalDoneTasks()+1);
        for (int i = 0; i <successCodesLst.size() ; i++) {
        agentManager.getSuccessCode().add(successCodesLst.get(i));
        singleUBoatEntry.addToAllSuccessLst(successCodesLst.get(i));
            String winName=checkWin(successCodesLst.get(i),singleUBoatEntry.getStrEncode());
            if((!winName.equals("notFound"))&&(singleUBoatEntry.getWinAllies().isEmpty())){
                singleUBoatEntry.setWinAllies(winName);
                i=successCodesLst.size();
            }
        }

    }
    public String checkWin(AgentCurrCode successCode,String str){
        if(successCode.getStrCandidate().equals(str)){
            return successCode.getNameAllies();
        }
        return "notFound";
    }
}
