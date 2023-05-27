//package Agent;

import Dictionary.MachineDictionary;
import Engine.EngineImpl;
import Machine.Machine;
import Reflector.Reflector;
import Rotor.Rotor;
//import Screens.BruteForceProgress.BruthForceProgressController;
//import UI.UIAdapter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/*
public class DM {
    private  IntegerProperty value=new SimpleIntegerProperty();


    private static int currentTasksDone;
    private Task<Boolean> currentAgent;
    private int sizeTask;
    private String strToDec;
    private EngineImpl MainEngine = new EngineImpl();
    private int sizeAgent;
    private BruthForceProgressController bruteForce;
    private UIAdapter uiAdapter;
    private boolean pause;
    private long sumTime;

  private ThreadPoolExecutor s1;
    public DM(EngineImpl MainEngine, int sizeTask, String strToDec, int sizeAgent, BruthForceProgressController bruteForce,UIAdapter uiAdapter) {
        this.MainEngine = MainEngine;
        this.sizeTask = sizeTask;
        this.strToDec = strToDec;
        this.sizeAgent = sizeAgent;
        this.bruteForce = bruteForce;
        this.uiAdapter=uiAdapter;
        this.value.setValue(0);
        this.pause=false;
        sumTime=0;

    }
public void setPause(boolean pause){
        this.pause=pause;
}

public ThreadPoolExecutor getThreadPool(){
        return s1;
}

    public void level1(MachineDictionary Dictionary, Runnable onFinish) throws ExecutionException, InterruptedException {

        Machine original = MainEngine.getMachine();
        DefaultThreadFactory factory = new DefaultThreadFactory();
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue(1000);
        s1 = new ThreadPoolExecutor(sizeAgent, 50, 900, TimeUnit.SECONDS, blockingQueue, factory);

        int totalTasks = ((int) Math.pow(original.getKeyboardmap().getABC().length(), original.allrotors.size())) / sizeTask;
        bruteForce.getStartBruteForce().setTotalTask(totalTasks);

        String currentPosition = setStartPosition();
        s1.prestartAllCoreThreads();
        for (int i = 0; i < totalTasks; i++) {
            while(pause) {
                synchronized (s1) {
                    s1.wait();
                }
            }
                Machine copy = new Machine(original);
                blockingQueue.put(new TaskAgent(copy, Dictionary, strToDec, currentPosition, sizeTask, totalTasks, uiAdapter,this));
                currentPosition = nextRotorPos(original.getKeyboardmap().getABC(), currentPosition, original.allrotors.size(), sizeTask);


                if (value.get() % 10 == 0) {
                    bruteForce.getStartBruteForce().update(value.get());

                }

                value.setValue(value.getValue() + 1);
            }
            bruteForce.getStartBruteForce().update(value.get());
            s1.shutdown();
        }

      public void level2(MachineDictionary Dictionary, Runnable onFinish, List<Reflector>lsrOfRef) throws InterruptedException {

            Machine original = MainEngine.getMachine();
            DefaultThreadFactory factory = new DefaultThreadFactory();
            BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue(1000);
             s1 = new ThreadPoolExecutor(sizeAgent, 50, 900, TimeUnit.SECONDS, blockingQueue, factory);

            int totalTasks = ((int) Math.pow(original.getKeyboardmap().getABC().length(), original.allrotors.size()))/ sizeTask;
          //int dana=totalTasks*(lsrOfRef.size());


            String currentPosition = setStartPosition();
            s1.prestartAllCoreThreads();
            for (int j = 0; j < lsrOfRef.size(); j++) {
                for (int i = 0; i < totalTasks; i++) {
                    while(pause) {
                        synchronized (s1) {
                            s1.wait();
                        }
                    }

                    Machine copy = new Machine(original);
                    copy.setReflectorM(copy.CopyReflector(lsrOfRef.get(j)));
                    blockingQueue.put(new TaskAgent(copy, Dictionary, strToDec, currentPosition, sizeTask, totalTasks, uiAdapter,this));
                    currentPosition = nextRotorPos(original.getKeyboardmap().getABC(), currentPosition, original.allrotors.size(), sizeTask);


                    if (value.get() % 10 == 0) {
                        bruteForce.getStartBruteForce().update(value.get());
                    }
                    value.setValue(value.getValue() + 1);

                }
                bruteForce.getStartBruteForce().update(value.get());
            }
            s1.shutdown(); // shutdown worker threads
        }
        public void level3(MachineDictionary Dictionary, Runnable onFinish, List<Reflector>lsrOfRef) throws InterruptedException {

            Machine original = MainEngine.getMachine();

            int [] rotorsArr=converteListRotorToArray(original.allrotors);
            CalcPermutation Perm=new CalcPermutation();
            Perm.heapPermutation(rotorsArr,rotorsArr.length,rotorsArr.length);
            List<int[]>ListArrRotorsPerm=Perm.getLst();

            DefaultThreadFactory factory = new DefaultThreadFactory();
            BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue(1000);
            s1 = new ThreadPoolExecutor(sizeAgent, 50, 900, TimeUnit.SECONDS, blockingQueue, factory);

            int totalTasks = ((int) Math.pow(original.getKeyboardmap().getABC().length(), original.allrotors.size())) / sizeTask;
            bruteForce.getStartBruteForce().setTotalTask(totalTasks*(lsrOfRef.size())*Factorial(MainEngine.getUseRotors()));
            //bruteForce.getMainScreensController().getDMoperationalController().getLabelTask().setText(""+totalTasks*(lsrOfRef.size())*Factorial(MainEngine.getUseRotors()));
            s1.prestartAllCoreThreads();
            String currentPosition = setStartPosition();


            for (int k = 0; k < ListArrRotorsPerm.size(); k++) {

                for (int j = 0; j < lsrOfRef.size(); j++) {

                    for (int i = 0; i < totalTasks; i++) {
                        while(pause) {
                            synchronized (s1) {
                                s1.wait();
                            }
                        }
                        Machine copy = new Machine(original);
                        copy.setRotor(copy.Copyrotor(convertArrToListRotor(ListArrRotorsPerm.get(k))));
                        copy.setReflectorM(copy.CopyReflector(lsrOfRef.get(j)));
                        blockingQueue.put(new TaskAgent(copy, Dictionary, strToDec, currentPosition, sizeTask, totalTasks, uiAdapter,this));
                        currentPosition = nextRotorPos(original.getKeyboardmap().getABC(), currentPosition, original.allrotors.size(), sizeTask);


                        if (value.get() % 10 == 0) {
                            bruteForce.getStartBruteForce().update(value.get());
                        }
                        value.setValue(value.getValue() + 1);

                    }
                    bruteForce.getStartBruteForce().update(value.get());
                }
            }
            s1.shutdown(); // shutdown worker threads
        }
    public void level4(MachineDictionary Dictionary, Runnable onFinish, List<Reflector>lsrOfRef) throws InterruptedException {

        Machine original = MainEngine.getMachine();

        nCk nck=new nCk();
        List<int[]> rotorsArr = nck.generate(MainEngine.getLstOfRotors().size(), MainEngine.getUseRotors());

        DefaultThreadFactory factory = new DefaultThreadFactory();
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue(1000);
      s1 = new ThreadPoolExecutor(sizeAgent, 50, 900, TimeUnit.SECONDS, blockingQueue, factory);

        int totalTasks = ((int) Math.pow(original.getKeyboardmap().getABC().length(), original.allrotors.size())) / sizeTask;
        bruteForce.getStartBruteForce().setTotalTask(totalTasks*(lsrOfRef.size())*Factorial(MainEngine.getUseRotors())*(Factorial(MainEngine.getLstOfRotors().size())/(Factorial(MainEngine.getUseRotors())*Factorial(MainEngine.getLstOfRotors().size()-MainEngine.getUseRotors()))));
        //bruteForce.getMainScreensController().getDMoperationalController().getLabelTask().setText(""+totalTasks*(lsrOfRef.size())*Factorial(MainEngine.getUseRotors())*(Factorial(MainEngine.getLstOfRotors().size())/(Factorial(MainEngine.getUseRotors())*Factorial(MainEngine.getLstOfRotors().size()-MainEngine.getUseRotors()))));
        s1.prestartAllCoreThreads();
        String currentPosition = setStartPosition();

        for (int q = 0; q < rotorsArr.size(); q++) {
            CalcPermutation Perm=new CalcPermutation();
            Perm.heapPermutation(rotorsArr.get(q),rotorsArr.get(q).length,rotorsArr.get(q).length);
            List<int[]>ListArrRotorsPerm=Perm.getLst();
        for (int k = 0; k < ListArrRotorsPerm.size(); k++) {

            for (int j = 0; j < lsrOfRef.size(); j++) {

                for (int i = 0; i < totalTasks; i++) {
                    while(pause) {
                        synchronized (s1) {
                            s1.wait();
                        }
                    }
                    Machine copy = new Machine(original);
                    copy.setRotor(copy.Copyrotor(convertArrToListRotor(ListArrRotorsPerm.get(k))));
                    copy.setReflectorM(copy.CopyReflector(lsrOfRef.get(j)));
                    blockingQueue.put(new TaskAgent(copy, Dictionary, strToDec, currentPosition, sizeTask, totalTasks, uiAdapter,this));
                    currentPosition = nextRotorPos(original.getKeyboardmap().getABC(), currentPosition, original.allrotors.size(), sizeTask);


                    if (value.get() % 10 == 0) {
                        bruteForce.getStartBruteForce().update(value.get());
                    }
                    value.setValue(value.getValue() + 1);

                }
                bruteForce.getStartBruteForce().update(value.get());
            }
        }
        }
        s1.shutdown(); // shutdown worker threads
    }

    public String setStartPosition(){
        char firstChar=MainEngine.getKeyboard().charAt(0);
        String str=String.valueOf(firstChar);
        for (int i = 1; i < MainEngine.getUseRotors(); i++) {
            str=str+firstChar;
        }
        return str;
    }
    public String nextRotorPos(String abc, String str, int numRotors,int advanceNum) {
        for (int j = 0; j <advanceNum; j++) {
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
                    i=numRotors+1;
                }
            }
        }
        return str;
    }
    public IntegerProperty valueProperty(){return value;}
    public int getValue(){return value.get();}
    public void setValue(int i){value.setValue(i);}
    public int Factorial(int n){
        int result=1;
        while(n>1){
            result=result*n;
            n=n-1;
        }
        return result;
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
            lstofrotors.add(i,MainEngine.getLstOfRotors().get(arr[i]-1));
        }
        return lstofrotors;
    }
    public boolean getPause(){
        return pause;
    }

    public void setAvg(long time){
        sumTime+=time;
    }
    public long getAverageTotalTask(){
        return sumTime/value.get();
    }}
*/