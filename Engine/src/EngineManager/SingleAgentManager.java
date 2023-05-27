package EngineManager;

import Agent.AgentCurrCode;

import java.util.ArrayList;
import java.util.List;

public class SingleAgentManager {
    private SingleAlliesManager alliesManager;
    private String name;
    private int amountThreads;
    private int numOfTasks;
    private List<AgentCurrCode>SuccessCode;
    private int totalPullTasks;
    private int totalDoneTasks;
    private int totalrecieved;
    private int totalwaiting;

    public SingleAgentManager(SingleAlliesManager alliesManager, String name, int amountThreads, int numOfTasks){
        this.alliesManager=alliesManager;
        this.amountThreads=amountThreads;
        this.name=name;
        this.numOfTasks=numOfTasks;
        this.SuccessCode=new ArrayList<>();
        this.totalPullTasks=0;
        this.totalDoneTasks=0;
        this.totalrecieved=0;
        this.totalwaiting=0;
    }

   public int getTotalrecieved(){
        return totalrecieved;
   }

    public int getTotalwaiting() {
        return totalwaiting;
    }

    public void setTotalrecieved(int totalrecieved) {
        this.totalrecieved = totalrecieved;
    }

    public void setTotalwaiting(int totalwaiting) {
        this.totalwaiting = totalwaiting;
    }

    public void setTotalPullTasks(int totalPullTasks) {
        this.totalPullTasks = totalPullTasks;
    }

    public int getTotalPullTasks() {
        return totalPullTasks;
    }
    public void setTotalDoneTasks(int totalDoneTasks) {
        this.totalDoneTasks = totalDoneTasks;
    }

    public int getTotalDoneTasks() {
        return totalPullTasks;
    }

    public String getalliesName(){return alliesManager.getAlliesName();}
    public String getName(){return name;}

    public int getAmountThreads() {
        return amountThreads;
    }

    public int getNumOfTasks() {
        return numOfTasks;
    }
    public List<AgentCurrCode> getSuccessCode(){
        return SuccessCode;

    }
    public void clearData(){
        SuccessCode.clear();
        totalrecieved=0;
        totalwaiting=0;
        totalDoneTasks=0;
        totalPullTasks=0;
    }
}
