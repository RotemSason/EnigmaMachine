package Engine;

import Keyboard.Keyboard;
import Reflector.Reflector;
import Rotor.Rotor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EngineUI {
    private final List<Reflector> lstOfReflector;
    private final int UseRotors;
    private final List<Rotor> lstOfRotors;
    private final Keyboard Keyboard;
    private final CurrCode Code;
    private final int CommandCount;

    public EngineUI(List<Reflector> lstOfReflector,int UseRotors,List<Rotor> lstOfRotors,Keyboard Keyboard,CurrCode Code,int CommandCount){
        this.lstOfRotors=new ArrayList<>(lstOfRotors);
        this.lstOfReflector=new ArrayList<>(lstOfReflector);
        this.UseRotors=UseRotors;
        this.Keyboard=Keyboard;
        this.Code=Code;
        this.CommandCount=CommandCount;
    }

    public CurrCode getCode() {
        return Code;
    }

    public int getCommandCount() {
        return CommandCount;
    }

    public int getUseRotors() {
        return UseRotors;
    }

    public Keyboard getKeyboard() {
        return Keyboard;
    }

    public List<Reflector> getLstOfReflector() {
        return lstOfReflector;
    }

    public List<Rotor> getLstOfRotors() {
        return lstOfRotors;
    }

    public Map<Integer, Integer> FindRotorNotch() {
        Map<Integer, Integer> notchmap = new HashMap<>();
        for (int i = 0; i < lstOfRotors.size(); i++) {
            Rotor currR = lstOfRotors.get(i);
            notchmap.put(currR.getRotorId(), currR.getNotch());
        }
        return notchmap;
    }
    public Map<Character, Character> FindPlags() {
        Map<Character, Character> allPlags = new HashMap<>();
        allPlags = Code.getSelctesPlugs().getPlugboard();
        return allPlags;
    }
    public int GetNotchFromLst(Integer id){
        return lstOfRotors.get(id-1).getNotch();
    }
    public Character GetPosFromLst(Integer id){
        return lstOfRotors.get(id-1).rightRotor.get(0);
    }

}
