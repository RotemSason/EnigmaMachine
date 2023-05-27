package EngineUI;

import Plugs.Plugs;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrCode {
    private List<Integer> SelectedRotors;
    private List<Integer>currNotch;
    private List<Character>SelectedPos;
    private int SelectedReflector;
    private Plugs SelctesPlugs;

    public CurrCode(List<Integer> SelectedRotors,List<Integer>currNotch, List<Character>SelectedPos, int SelectedReflector, Map<Character,Character> maplugs){
    this.SelectedRotors=SelectedRotors;
    this.currNotch=new ArrayList<>();
    this.SelectedPos=SelectedPos;
    this.SelectedReflector=SelectedReflector;
    if(maplugs!=null) {
        this.SelctesPlugs = new Plugs(maplugs);
    }
    if(currNotch!=null){
        this.currNotch=currNotch;
    }
}

    public CurrCode(CurrCode Code){
    this.SelctesPlugs=Code.SelctesPlugs;
    this.currNotch=Code.currNotch;
    this.SelectedReflector=Code.SelectedReflector;
    this.SelectedPos=Code.SelectedPos;
    this.SelectedRotors=Code.SelectedRotors;
}
    public Plugs getSelctesPlugs(){
    return SelctesPlugs;
}
    public List<Integer>getCurrNotch(){
    return currNotch;
    }
    public List<Character>getSelectedPos(){return SelectedPos;}
    public List<Integer>getSelectedRotors(){return SelectedRotors;}

    public int getSelectedReflector(){return SelectedReflector;}



    public void setSelectedRotors(List<Integer>SelectedRotors){
        this.SelectedRotors=SelectedRotors;
    }

    public void setCurrNotch(List<Integer>currNotch){
        this.currNotch=currNotch;
    }
    public void setSelectedPos(List<Character>SelectedPos){
        this.SelectedPos=SelectedPos;
    }
    public void setSelectedReflector(int SelctedReflector){
        this.SelectedReflector=SelctedReflector;
    }
    public void setSelectedPlugs(Plugs SelctesPlugs){
        this.SelctesPlugs=SelctesPlugs;
    }


    public String IntToRom(int num) {
        switch (num) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            default:
                return "wrong!";//fix!
        }
    }
    public Map<Character, Character> getMapSelctesPlugs(){
        return SelctesPlugs.plugboard;
    }
    }


