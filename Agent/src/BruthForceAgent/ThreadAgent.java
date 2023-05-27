package BruthForceAgent;

import EngineUI.CurrCode;
import Machine.Machine;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static ClearAll.Configuration.Configuration.BASE_URL;
import static ClearAll.Configuration.Configuration.HTTP_CLIENT;

public class ThreadAgent implements Runnable {

    private TaskAgent task;
    private String NameAgent;
    private  UIAdapter uiadaptar;
    private String NameAllies;


//private  int tasksCount;

    public ThreadAgent(TaskAgent task,String NameAgent,UIAdapter uiadaptar,String NameAllies){
        this.task= task;
        this.NameAgent=NameAgent;
        this.uiadaptar=uiadaptar;
        this.NameAllies=NameAllies;

    }


    public void run()  {

        boolean foundDictionary = false;
        List<AgentCurrCode> successCodes = new ArrayList<>();
        long startTime=System.nanoTime();

        for (int i = 0; i < task.getSizeTask(); i++) {

            foundDictionary = false;
            List<Character> lstposition = stringToList(task.getStartRotorPos());
           synchronized (this) {
               task.getCopyMachine().SetFirstPos(lstposition);
           }

               String checkInDictionary = task.getCopyMachine().encode(task.getStrToDec());

            foundDictionary = searchInDictionary(checkInDictionary, task.getDictionary().getDictionary());

            if (foundDictionary) {

                CurrCode code = setCurrentCode(task.getCopyMachine(), lstposition);
                AgentCurrCode agentdetails = new AgentCurrCode(NameAllies, code,checkInDictionary);

                successCodes.add(agentdetails);

                uiadaptar.addRowToTable(ConvertToSuccessCode(agentdetails));
            }
            task.setStartRotorPos(nextRotorPos(task.getCopyMachine().getKeyboardmap().getABC(), task.getStartRotorPos(), task.getCopyMachine().allrotors.size()));
        }

        updateServlet(successCodes,NameAgent);
        long endTime=System.nanoTime();
        synchronized (this){
        //   task.getDm().setAvg(endTime-startTime);
        }

    }
public void updateServlet(List<AgentCurrCode>successCodes,String NameAgent){
        Gson gson=new Gson();
        String json=gson.toJson(successCodes);
    String finalUrl = HttpUrl
            .parse(BASE_URL + "/update-successcode")
            .newBuilder()
            .addQueryParameter("successCodes", json)
            .addQueryParameter("NameAgent", NameAgent)
            .addQueryParameter("NameAllies", NameAllies)
            .build()
            .toString();

    Request request = new Request.Builder()
            .url(finalUrl)
            .build();

    Call call = HTTP_CLIENT.newCall(request);

    Response response = null;

    try {
        response = call.execute();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

}

    public String nextRotorPos(String abc, String str, int numRotors) {
        int sizeAbc = abc.length();
        int sizeStr = str.length();
        int i = 1;
        while (i <= numRotors) {
            if (str.charAt(sizeStr - i) == abc.charAt(sizeAbc - 1)) {
                str = str.substring(0, sizeStr - i) + abc.charAt(0) + str.substring(sizeStr - i + 1);
                if (i == numRotors) {
                    return null;
                }
                i++;
            } else {
                int ind = sizeStr - i;
                Character newch = abc.charAt(abc.indexOf(str.charAt(sizeStr - i)) + 1);
                str = str.substring(0, ind) + newch + str.substring(ind + 1);
                return str;
            }
        }
        return str;
    }

    List<Character> stringToList(String startRotorPos) {
        List<Character> lstposition = new ArrayList<>();
        for (int i = startRotorPos.length() - 1; i >= 0; i--) {
            lstposition.add(startRotorPos.charAt(i));
        }
        return lstposition;
    }

    public boolean searchInDictionary(String checkInDictionary, Set<String> Dictionary) {
        boolean entertofor = false;
        for (String str : checkInDictionary.split(" ")) {
            entertofor = true;
            if (!(Dictionary.contains(str) || Dictionary.contains(str.toLowerCase()))) {
                return false;
            }
        }
        if (!entertofor) {
            return false;
        }
        return true;
    }

    public CurrCode setCurrentCode(Machine copyMachine,List<Character>lstposition){
        CurrCode code=new CurrCode(copyMachine.getIDrotors(),copyMachine.getNotches(),lstposition,copyMachine.reflectorM.getId(),null);
        return code;
    }

   SuccessCode ConvertToSuccessCode(AgentCurrCode agentdetails){
       SuccessCode code=new SuccessCode(agentdetails.getRotors(),agentdetails.getPositions(),agentdetails.getReflector(),agentdetails.getStrCandidate());
       return  code;
   }
}
