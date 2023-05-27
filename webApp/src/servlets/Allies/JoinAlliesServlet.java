package servlets.Allies;

import Contests.DetailsUBoat;
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

@WebServlet(urlPatterns = "/join-allies")
public class JoinAlliesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out=response.getWriter();
        DetailsUBoat detailsUBoat;
        String alliesbattle = request.getParameter("alliesbattle");
        String alliesName = request.getParameter("alliesname");
        alliesbattle = alliesbattle.trim();
        alliesName = alliesName.trim();
        synchronized (this) {
            SingleUBoatEntry single= ServletUtils.getEngineManager(getServletContext()).getBattleField(alliesbattle);
            SingleAlliesManager alliesManager=ServletUtils.getEngineManager(getServletContext()).getSingleAllies(alliesName);
            alliesManager.setUBoatManager(single);
            alliesManager.setLevel(single.getMainEngine().getBattle().getLevel());

            single.addAlliesToList(alliesManager);

            if(single.getAllies().size()==single.getMainEngine().getBattle().getNumOfAllies()){
                single.setIsFull();
            }

            detailsUBoat = new DetailsUBoat(alliesManager.getUBoatManager().getBattle(),alliesManager.getUBoatManager().getContestname(),"false",alliesManager.getUBoatManager().getMainEngine().getBattle().getLevel(),alliesManager.getUBoatManager().getAllies().size()+"/"+alliesManager.getUBoatManager().getMainEngine().getBattle().getNumOfAllies(),false);/*checkisfull*/
            if(single.getStrDec()==null){
                single.setStrDec("");
                alliesManager.setStrToDec(single.getStrDec());
            }
            else{
                alliesManager.setStrToDec(single.getStrDec());
            }
        }
        Gson gson=new Gson();
        String json = gson.toJson(detailsUBoat);
        out.write(json);
        out.flush();
}}