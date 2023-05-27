package servlets.Allies;

import EngineManager.SingleAlliesManager;
import EngineManager.SingleUBoatEntry;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
@WebServlet(urlPatterns = "/push-tasks")
public class PushTasksToQueue extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        String alliesname = request.getParameter("alliesname");

        alliesname=alliesname.trim();
        synchronized (this) {
            SingleUBoatEntry single= ServletUtils.getEngineManager(getServletContext()).getUBoatByAlliesName(alliesname);
            single.setStatus("Active");
            for (int i = 0; i < single.getAllies().size(); i++) {
                single.getAllies().get(i).setPushTasks(true);
                  //  single.getAllies().get(i).setStart(true);
            //SingleAlliesManager single=ServletUtils.getEngineManager(getServletContext()).getSingleAllies(alliesname);
                    SimpleServer simpleServer = new SimpleServer(single.getAllies().get(i));
                    //SimpleServer simpleServer = new SimpleServer(single);
                    simpleServer.startContestOnDifferentThread();

                    //single.getAllies().get(i).StartContest();

           // }
        }
    }
    }}