package servlets;


import Engine.EngineImpl;

import EngineUI.CurrCode;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

@WebServlet(urlPatterns = "/manual-code")
public class ManualCode extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String contestname = request.getParameter("contestname");
        contestname = contestname.trim();
        EngineImpl MainEngine = ServletUtils.getEngineManager(getServletContext()).getMainEngine(contestname);
        Properties prop = new Properties();
        prop.load(request.getInputStream());

        String tmpCode = prop.getProperty("json");
        Gson gson = new Gson();
        CurrCode code=gson.fromJson(tmpCode, CurrCode.class);

            MainEngine.InitialCode(code);

            Gson gson2 = new Gson();
            String json2="";

            json2 = gson2.toJson(MainEngine.getCurrCode());
            out.write(json2);
            out.flush();

        }

    }

