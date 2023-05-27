package servlets;


import Engine.EngineImpl;
import EngineUI.CurrCode;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utilis.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(urlPatterns = "/random")
public class RandomCode extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String json="";
        String contestname = request.getParameter("contestname");
        contestname = contestname.trim();
        CurrCode code=Random(contestname);

        json = gson.toJson(code);
        out.write(json);
        out.flush();
    }


    public CurrCode Random(String name) {
        EngineImpl MainEngine = ServletUtils.getEngineManager(getServletContext()).getMainEngine(name);
        int selectedreflrctor;
        Map <Character,Character> autoplug=new HashMap<>();
        List<Integer> lstnumrotors = new ArrayList<>();
        List<Character> lstpos = new ArrayList<>();
        String MachineABC = MainEngine.getKeyboard();
        Random random = new Random();
        int numofrotors = MainEngine.getUseRotors();

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= MainEngine.getLstOfRotors().size(); i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i = 0; i < numofrotors; i++) {
            lstnumrotors.add(list.get(i));
        }
        for (int i = 0; i < numofrotors; i++) {
            Character startpos = (MachineABC.charAt(random.nextInt(MachineABC.length())));//set the start pos in each rotor
            lstpos.add(startpos);
        }
        if (MainEngine.getlstOfReflector().size() == 1) {
            selectedreflrctor = 1;
        } else {
            selectedreflrctor = random.nextInt(MainEngine.getlstOfReflector().size() - 1) + 1;
        }
        int numofplugs = random.nextInt((MachineABC.length()) / 2 - 0) + 0;
        List<Character> listofmachineabc = new ArrayList<Character>();
        for (int i = 0; i < MachineABC.length(); i++) {
            listofmachineabc.add(MachineABC.charAt(i));
        }
        Collections.shuffle(listofmachineabc);
        CurrCode tmpcode = new CurrCode(lstnumrotors, null, lstpos, selectedreflrctor,autoplug);
        MainEngine.InitialCode(tmpcode);

        return tmpcode;

    }
}