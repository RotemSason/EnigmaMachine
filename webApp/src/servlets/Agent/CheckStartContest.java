package servlets.Agent;

import Allies.AlliesManager;
import EngineManager.EngineManager;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import EngineManager.SingleUBoatEntry;
import utilis.ServletUtils;
import utilis.SessionUtils;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/check-start-contest")
public class CheckStartContest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        String CheckReady;
        String alliesname = request.getParameter("AlliesName");
        if (alliesname.isEmpty()) {
            CheckReady = "false";
        } else {
            alliesname = alliesname.trim();

                EngineManager manager = ServletUtils.getEngineManager(getServletContext());
                SingleUBoatEntry Single = manager.getUBoatByAlliesName(alliesname);
                if (Single == null) {
                    CheckReady = "false";
                } else {
                    if (Single.isReadyButton() && (Single.getIsFull())) {
                        CheckReady = "true";
                        for (int i = 0; i < Single.getAllies().size(); i++) {
                            if (!Single.getAllies().get(i).isReady()) {
                                CheckReady = "false";
                            }
                        }
                    } else {
                        CheckReady = "false";
                    }
                }

            }
        String json = gson.toJson(CheckReady);
                out.write(json);
                out.flush();

        }
    }

