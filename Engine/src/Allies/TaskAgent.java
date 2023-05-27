package Allies;


import Agent.AgentCurrCode;
import Dictionary.MachineDictionary;
import Machine.Machine;

import java.util.ArrayList;
import java.util.List;

public class TaskAgent  {
    private Machine copyMachine;
    private MachineDictionary Dictionary;
    private String strToDec;
    private String startRotorPos;
    private int sizeTask;
    private int totalTasks;
    private List<AgentCurrCode> successCodes;
  //  private UIAdapter uiAdapter;
   // private boolean pause;
  //  private DM dm;

    public TaskAgent(Machine copyMachine, MachineDictionary Dictionary, String strToDec, String startRotorPos, int sizeTask, int totalTasks){
        this.copyMachine=copyMachine;
        this.Dictionary=Dictionary;
        this.strToDec=strToDec;
        this.startRotorPos=startRotorPos;
        this.sizeTask=sizeTask;
        this.totalTasks=totalTasks;
        successCodes=new ArrayList<>();


    }

    public String getStartRotorPos() {
        return startRotorPos;
    }

    public Machine getCopyMachine() {
        return copyMachine;
    }

    public String getStrToDec() {
        return strToDec;
    }

    public int getSizeTask() {
        return sizeTask;
    }

    public MachineDictionary getDictionary() {
        return Dictionary;
    }
    public void setStartRotorPos(String startRotorPos){
        this.startRotorPos=startRotorPos;
    }

    public void setSuccessList(List<AgentCurrCode> successCodes){
        this.successCodes=successCodes;
    }
    public List<AgentCurrCode>getSuccessCodes(){
        return successCodes;
    }
    public int getTotalTasks(){return totalTasks;}

//public DM getDm(){return dm;}

}
