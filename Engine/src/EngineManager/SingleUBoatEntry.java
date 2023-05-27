package EngineManager;

import Agent.AgentCurrCode;
import Contests.DetailsUBoat;
import Engine.EngineImpl;

import java.util.ArrayList;
import java.util.List;

public class SingleUBoatEntry {
    private final EngineImpl MainEngine;
    private final String contestname;
    private final String battle;
    private final List<SingleAlliesManager> allies;
    private boolean isFull;
    private boolean readyButton=false;
    private String StrDec;
    private String Status;
    private String strEncode;
private String winAllies;
private List<AgentCurrCode>AllsuccessCode;


    public SingleUBoatEntry(EngineImpl MainEngine, String contestname,String battle) {
        this.MainEngine = MainEngine;
        this.contestname = contestname;
        this.battle=battle;
        this.allies=new ArrayList<>();
        isFull=false;
        this.Status="Waiting";
        winAllies="";
        AllsuccessCode=new ArrayList<>();

    }

    public synchronized void addToAllSuccessLst(AgentCurrCode allsuccessCode) {
        this.AllsuccessCode.add(allsuccessCode);
    }

    public synchronized List<AgentCurrCode> getAllsuccessCode() {
        return AllsuccessCode;
    }

    public String getStrDec() {
        return StrDec;
    }
public String getStatus(){return Status; }
    public void setStatus(String status){this.Status=status;}
    public void setStrDec(String strDec) {
        StrDec = strDec;
    }
    public void setReadyButton(boolean readyButton) {
        this.readyButton = readyButton;
    }

    public boolean isReadyButton() {
        return readyButton;
    }

    public DetailsUBoat getDetailsUBoat(){
        String battle= MainEngine.getBattle().getBattleName();
        String uboatName=contestname;
        String staus=Status;
        String level=MainEngine.getBattle().getLevel();
        String allies="/"+MainEngine.getBattle().getNumOfAllies();
        DetailsUBoat tmp=new DetailsUBoat(battle,uboatName,staus,level,allies,isFull);
        return tmp;
    }
public boolean getIsFull(){
        return isFull;
}
public void setIsFull(){
        isFull=true;
}
    public EngineImpl getMainEngine() {
        return MainEngine;
    }


    public String getContestname() {
        return contestname;
    }
    public void addAlliesToList(SingleAlliesManager tmp){
        allies.add(tmp);
    }

    public String getBattle() {
        return battle;
    }

    public int getAlliesSize() {
        return allies.size();
    }
    public void setFullUBoat(boolean b){
        isFull=b;
    }

    public List<SingleAlliesManager> getAllies() {
        return allies;
    }

    public String getStrEncode() {
        return strEncode;
    }

    public void setStrEncode(String strEncode) {
        this.strEncode = strEncode;
    }

    public void setWinAllies(String winAllies) {
        this.winAllies = winAllies;
    }

    public String getWinAllies() {
        return winAllies;
    }
    public void clearAll(){
        isFull=false;
        setStatus("Waiting");
        winAllies="";
        AllsuccessCode.clear();
        allies.clear();
    }
}