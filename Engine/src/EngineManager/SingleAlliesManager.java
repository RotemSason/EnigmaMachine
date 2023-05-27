package EngineManager;


import Allies.TaskAgent;
import Contests.CalcPermutation;
import Contests.nCk;
import Dictionary.MachineDictionary;
import Machine.Machine;
import Reflector.Reflector;
import Rotor.Rotor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SingleAlliesManager {
    private SingleUBoatEntry UBoatManager;
    private String alliesName;
    private int sizeTask;
    private List<SingleAgentManager> agents;
    private String level;
    private BlockingQueue<TaskAgent> taskQueue;
    private boolean isReady;
    private String strToDec;
    private boolean isStart;
    private int totalTasks;
    private int pushTasks;
    private int finishTasks;
    private boolean isPushTasks;
    private String isClear;

    public SingleAlliesManager(String name, int sizeTask) {
        this.alliesName = name;
        this.sizeTask = sizeTask;
        agents = new ArrayList<>();
        taskQueue = new ArrayBlockingQueue(1000);
        isReady=false;
        isStart=false;
        pushTasks=0;
        finishTasks=0;
        isPushTasks=false;
        isClear="false";
    }

    public SingleUBoatEntry getUBoatManager() {
        return UBoatManager;
    }

    public boolean isPushTasks() {
        return isPushTasks;
    }

    public void setPushTasks(boolean pushTasks) {
        isPushTasks = pushTasks;
    }

    public synchronized void setFinishTasks(int finishTasks) {
        this.finishTasks = finishTasks;
    }

    public int getFinishTasks() {
        return finishTasks;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public boolean isReady() {
        return isReady;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setStrToDec(String strToDec) {
        this.strToDec = strToDec;
    }

    public String getStrToDec() {
        return strToDec;
    }

    public SingleUBoatEntry getUboat() {
        return UBoatManager;
    }

    public void addAgent(SingleAgentManager agent) {
        agents.add(agent);
    }

    public List<SingleAgentManager> getAgentLst() {
        return agents;
    }

    public String getAlliesName() {
        return alliesName;
    }

    public void setSizeTask(int sizeTask) {
        this.sizeTask = sizeTask;
    }

    public int getSizeTask() {
        return sizeTask;
    }

    public void StartContest() throws InterruptedException {
        if (level.equals("Easy")) {
            level1(UBoatManager.getMainEngine().getDictionary());
        } else if (level.equals("Medium")) {
            level2(UBoatManager.getMainEngine().getDictionary(), UBoatManager.getMainEngine().getlstOfReflector());
        } else if (level.equals("Hard")) {
            level3(UBoatManager.getMainEngine().getDictionary(),UBoatManager.getMainEngine().getlstOfReflector());
        } else if (level.equals("Insane")) {
            level4(UBoatManager.getMainEngine().getDictionary(),UBoatManager.getMainEngine().getlstOfReflector());
        }

    }


    public void setUBoatManager(SingleUBoatEntry UBoatManager) {
        this.UBoatManager = UBoatManager;
    }

    public void level1(MachineDictionary dictionary) throws InterruptedException {
        Machine original = UBoatManager.getMainEngine().getMachine();
         totalTasks = ((int) Math.pow(original.getKeyboardmap().getABC().length(), original.allrotors.size())) / sizeTask;
        //bruteForce.getStartBruteForce().setTotalTask(totalTasks);
        pushTasks=0;
int count=0;
        String currentPosition = setStartPosition();
while(count<totalTasks){

       // while(taskQueue.size()<1000) {
            // for (int i = 0; i < totalTasks; i++) {//changetototalTasks
            Machine copy = new Machine(original);
            count++;
            taskQueue.put(new TaskAgent(copy, dictionary, strToDec, currentPosition, sizeTask, totalTasks));
            pushTasks++;

            currentPosition = nextRotorPos(original.getKeyboardmap().getABC(), currentPosition, original.allrotors.size(), sizeTask);
      //  }
           /* if (value.get() % 10 == 0) {
                bruteForce.getStartBruteForce().update(value.get());

            }

            value.setValue(value.getValue() + 1);
        }
        bruteForce.getStartBruteForce().update(value.get());*/
        }
    }

    public void level2(MachineDictionary dictionary, List<Reflector>lsrOfRef) throws InterruptedException {
        Machine original = UBoatManager.getMainEngine().getMachine();
         int totalTasksRotor = ((int) Math.pow(original.getKeyboardmap().getABC().length(), original.allrotors.size()))/ sizeTask;
        totalTasks=totalTasksRotor*lsrOfRef.size();
                pushTasks=0;
        String currentPosition = setStartPosition();

        for (int j = 0; j < lsrOfRef.size(); j++) {
            for (int i = 0; i < totalTasksRotor; i++) {

                Machine copy = new Machine(original);
                copy.setReflectorM(copy.CopyReflector(lsrOfRef.get(j)));
                taskQueue.put(new TaskAgent(copy, dictionary, strToDec, currentPosition, sizeTask, totalTasks));
                pushTasks++;
                currentPosition = nextRotorPos(original.getKeyboardmap().getABC(), currentPosition, original.allrotors.size(), sizeTask);

            }
        }}

    public void level3(MachineDictionary dictionary, List<Reflector>lsrOfRef) throws InterruptedException {
        Machine original = UBoatManager.getMainEngine().getMachine();
        pushTasks=0;
        int [] rotorsArr=converteListRotorToArray(original.allrotors);
        CalcPermutation Perm=new CalcPermutation();
        Perm.heapPermutation(rotorsArr,rotorsArr.length,rotorsArr.length);
        List<int[]>ListArrRotorsPerm=Perm.getLst();


        int totalTasksRotors = ((int) Math.pow(original.getKeyboardmap().getABC().length(), original.allrotors.size())) / sizeTask;
        totalTasks= totalTasksRotors*(lsrOfRef.size())*Factorial(UBoatManager.getMainEngine().getUseRotors());
        String currentPosition = setStartPosition();


        for (int k = 0; k < ListArrRotorsPerm.size(); k++) {

            for (int j = 0; j < lsrOfRef.size(); j++) {

                for (int i = 0; i < totalTasksRotors; i++) {
                    Machine copy = new Machine(original);
                    copy.setRotor(copy.Copyrotor(convertArrToListRotor(ListArrRotorsPerm.get(k))));
                    copy.setReflectorM(copy.CopyReflector(lsrOfRef.get(j)));
                    taskQueue.put(new TaskAgent(copy, dictionary, strToDec, currentPosition, sizeTask, totalTasks));
                    pushTasks++;
                    currentPosition = nextRotorPos(original.getKeyboardmap().getABC(), currentPosition, original.allrotors.size(), sizeTask);


                }
            }
        }

    }

    public void level4(MachineDictionary dictionary,  List<Reflector>lsrOfRef) throws InterruptedException {

        Machine original = UBoatManager.getMainEngine().getMachine();
        pushTasks=0;
        nCk nck=new nCk();
        List<int[]> rotorsArr = nck.generate(UBoatManager.getMainEngine().getLstOfRotors().size(), UBoatManager.getMainEngine().getUseRotors());


        int totalTasksRotors = ((int) Math.pow(original.getKeyboardmap().getABC().length(), original.allrotors.size())) / sizeTask;
      totalTasks=totalTasksRotors*(lsrOfRef.size())*Factorial(UBoatManager.getMainEngine().getUseRotors())*(Factorial(UBoatManager.getMainEngine().getLstOfRotors().size())/(Factorial(UBoatManager.getMainEngine().getUseRotors())*Factorial(UBoatManager.getMainEngine().getLstOfRotors().size()-UBoatManager.getMainEngine().getUseRotors())));
        String currentPosition = setStartPosition();

        for (int q = 0; q < rotorsArr.size(); q++) {
            CalcPermutation Perm=new CalcPermutation();
            Perm.heapPermutation(rotorsArr.get(q),rotorsArr.get(q).length,rotorsArr.get(q).length);
            List<int[]>ListArrRotorsPerm=Perm.getLst();
            for (int k = 0; k < ListArrRotorsPerm.size(); k++) {

                for (int j = 0; j < lsrOfRef.size(); j++) {

                    for (int i = 0; i < totalTasksRotors; i++) {

                        Machine copy = new Machine(original);
                        copy.setRotor(copy.Copyrotor(convertArrToListRotor(ListArrRotorsPerm.get(k))));
                        copy.setReflectorM(copy.CopyReflector(lsrOfRef.get(j)));
                        taskQueue.put(new TaskAgent(copy, dictionary, strToDec, currentPosition, sizeTask, totalTasks));
                        pushTasks++;
                        currentPosition = nextRotorPos(original.getKeyboardmap().getABC(), currentPosition, original.allrotors.size(), sizeTask);


                    }

                }
            }
        }

    }
    public String setStartPosition() {
        char firstChar = UBoatManager.getMainEngine().getKeyboard().charAt(0);
        String str = String.valueOf(firstChar);
        for (int i = 1; i < UBoatManager.getMainEngine().getUseRotors(); i++) {
            str = str + firstChar;
        }
        return str;
    }

    public String nextRotorPos(String abc, String str, int numRotors, int advanceNum) {
        for (int j = 0; j < advanceNum; j++) {
            int sizeAbc = abc.length();
            int sizeStr = str.length();
            int i = 1;
            while (i <= numRotors) {
                if (str.charAt(sizeStr - i) == abc.charAt(sizeAbc - 1)) {
                    str = str.substring(0, sizeStr - i) + abc.charAt(0) + str.substring(sizeStr - i + 1);

                    i++;
                } else {
                    int ind = sizeStr - i;
                    Character newch = abc.charAt(abc.indexOf(str.charAt(sizeStr - i)) + 1);
                    str = str.substring(0, ind) + newch + str.substring(ind + 1);
                    i = numRotors + 1;
                }
            }
        }
        return str;
    }

    public TaskAgent getTaskFromQueue(){
            return taskQueue.poll();
    }

    public int getSizeQueue() {
        return taskQueue.size();
    }

    public SingleAgentManager getAgentByName(String name) {
        for (int i = 0; i < agents.size(); i++) {
            if (agents.get(i).getName().equals(name)) {
                return agents.get(i);
            }
        }
        return null;
    }
    public int Factorial(int n){
        int result=1;
        while(n>1){
            result=result*n;
            n=n-1;
        }
        return result;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public int getPushTasks() {
        return pushTasks;
    }
    public int[] converteListRotorToArray(List<Rotor>lst){
        int[]arr=new int[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            arr[i]=lst.get(i).getRotorId();
        }
        return arr;
    }
    public List<Rotor>convertArrToListRotor(int[] arr){
        List<Rotor> lstofrotors=new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            lstofrotors.add(i,UBoatManager.getMainEngine().getLstOfRotors().get(arr[i]-1));
        }
        return lstofrotors;
    }

    public void setIsClear(String isClear) {
        this.isClear = isClear;
    }

    public String getIsClear() {
        return isClear;
    }
    public void clearData(){
        sizeTask=0;
        taskQueue.clear();
        isReady=false;
        strToDec="";
        isStart=false;
        totalTasks=0;
        pushTasks=0;
        finishTasks=0;
        isPushTasks=false;
        UBoatManager=null;


    }
}
