package servlets.Allies;

import Allies.AlliesManager;
import Contests.ContestManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;
import utilis.SessionUtils;

import java.io.IOException;
@WebServlet(urlPatterns = "/login-allies")
public class LoginAlliesServlet extends HttpServlet{
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("text/plain;charset=UTF-8");

            String alliesnameFromSession = SessionUtils.getAlliesname(request);
            AlliesManager alliesManager = ServletUtils.getAlliesManager(getServletContext());

            if (alliesnameFromSession == null) { //user is not logged in yet

                String alliesnameFromParameter = request.getParameter("alliesname");
                if (alliesnameFromParameter == null || alliesnameFromParameter.isEmpty()) {
                    //no username in session and no username in parameter - not standard situation. it's a conflict

                    // stands for conflict in server state
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                } else {
                    //normalize the username value
                    alliesnameFromParameter = alliesnameFromParameter.trim();


                    synchronized (this) {
                        if (alliesManager.isUserExists(alliesnameFromParameter) || ServletUtils.getContestManager(getServletContext()).isUserExists(alliesnameFromParameter)) {
                            String errorMessage = "Allies name " + alliesnameFromParameter + " already exists. Please enter a different username.";

                            // stands for unauthorized as there is already such user with this name
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getOutputStream().print(errorMessage);
                        }
                        else {
                            //add the new user to the users list
                            alliesManager.addUser(alliesnameFromParameter);
                            ServletUtils.getEngineManager(getServletContext()).addAllies(alliesnameFromParameter);
                            //set the username in a session so it will be available on each request
                            //the true parameter means that if a session object does not exists yet
                            //create a new one
                            request.getSession(true).setAttribute("alliesname", alliesnameFromParameter);

                            //redirect the request to the chat room - in order to actually change the URL
                            System.out.println("On login, request URI is: " + request.getRequestURI());
                            response.setStatus(HttpServletResponse.SC_OK);
                        }
                    }
                }
            } else {
                //user is already logged in
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }
}
