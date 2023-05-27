package Agent;

import Dictionary.MachineDictionary;
import Machine.Machine;

public class Task implements Runnable{
    private Machine copyMachine;
    private MachineDictionary Dictionary;
    private String strToDec;
    private String startRotorPos;
    private int sizeTask;

    public Task(Machine copyMachine, MachineDictionary Dictionary, String strToDec, String startRotorPos, int sizeTask){
        this.copyMachine=copyMachine;
        this.Dictionary=Dictionary;
        this.strToDec=strToDec;
        this.startRotorPos=startRotorPos;
        this.sizeTask=sizeTask;

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

    @Override
    public void run() {

    }
}
