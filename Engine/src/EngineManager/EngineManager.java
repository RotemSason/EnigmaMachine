package EngineManager;

import Contests.DetailsUBoat;
import Engine.EngineImpl;

import java.util.ArrayList;
import java.util.List;

public class EngineManager {
    private final List<SingleUBoatEntry> contestsList;
private final List<SingleAlliesManager>alliesList;
    public EngineManager() {

        contestsList = new ArrayList<>();
        alliesList = new ArrayList<>();
    }

    public List<SingleUBoatEntry> getContestsList() {
        return contestsList;
    }
    public List<SingleAlliesManager> getAlliesList() {
        return alliesList;
    }

    public List<String>getNamesOfAllie(){
        List<String> names=new ArrayList<>();
        for (int i = 0; i < alliesList.size(); i++) {
            names.add(alliesList.get(i).getAlliesName());
        }
        return names;
    }

    public synchronized void addContest(EngineImpl MainEngine, String contestname,String battle) {
        contestsList.add(new SingleUBoatEntry(MainEngine, contestname,battle));
    }
    public synchronized void addAllies( String name) {
        alliesList.add(new SingleAlliesManager(name,-1));
    }

    public synchronized List<SingleUBoatEntry> getChatEntries(int fromIndex){
        if (fromIndex < 0 || fromIndex > contestsList.size()) {
            fromIndex = 0;
        }
        return contestsList.subList(fromIndex, contestsList.size());
    }

    public int getVersion() {
        return contestsList.size();
    }

    public EngineImpl getMainEngine(String name){
        for (int i = 0; i < contestsList.size(); i++) {
            if(contestsList.get(i).getContestname().equals(name)){
                return contestsList.get(i).getMainEngine();
            }
        }
        return null;
    }
    public SingleUBoatEntry getBattleField(String battle){
        for (int i = 0; i < contestsList.size(); i++) {
            if(contestsList.get(i).getBattle().equals(battle)){
                return contestsList.get(i);
            }
        }
        return null;
    }
    public SingleUBoatEntry getContest(String name){
        for (int i = 0; i < contestsList.size(); i++) {
            if(contestsList.get(i).getContestname().equals(name)){
                return contestsList.get(i);
            }
        }
        return null;
    }
    public SingleAlliesManager getSingleAllies(String name){
        for (int i = 0; i < alliesList.size(); i++) {
            if(alliesList.get(i).getAlliesName().equals(name)){
                return alliesList.get(i);
            }
        }
        return null;
    }
    public void setFullUBoat(String uboatName){
        for (int i = 0; i < contestsList.size(); i++) {
            if(contestsList.get(i).getContestname().equals(uboatName)){
                contestsList.get(i).setFullUBoat(true);
            }
        }
    }
    public SingleUBoatEntry getUBoatByAlliesName(String name){
        for (int i = 0; i < alliesList.size(); i++) {
            if(alliesList.get(i).getAlliesName().equals(name)){
                return alliesList.get(i).getUboat();
            }

        }
        return null;
    }
}
