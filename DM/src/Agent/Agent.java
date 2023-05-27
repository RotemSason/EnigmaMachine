package Agent;

import EngineUI.CurrCode;
import Machine.Machine;
import Screens.BruteForceProgress.SuccessCode;
//import UI.UIAdapter;

import java.util.ArrayList;

import java.util.List;
import java.util.Set;

import static java.lang.Thread.sleep;
/*
public class Agent implements Runnable {

    private TaskAgent task;
    private  UIAdapter uiadaptar;
private  int tasksCount;

    public Agent(TaskAgent task, UIAdapter uiadaptar){
        this.task= task;
        this.tasksCount=tasksCount;
        this.uiadaptar=uiadaptar;
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
                AgentCurrCode agentdetails = new AgentCurrCode(Thread.currentThread().getName(), code);
                successCodes.add(agentdetails);
                uiadaptar.addRowToTable(ConvertToSuccessCode(agentdetails));
            }
            task.setStartRotorPos(nextRotorPos(task.getCopyMachine().getKeyboardmap().getABC(), task.getStartRotorPos(), task.getCopyMachine().allrotors.size()));
        }
        long endTime=System.nanoTime();
        synchronized (this){
            task.getDm().setAvg(endTime-startTime);
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
       SuccessCode code=new SuccessCode(agentdetails.getID(),agentdetails.getRotors(),agentdetails.getPositions(),agentdetails.getReflector());
       return  code;
   }
}
*/