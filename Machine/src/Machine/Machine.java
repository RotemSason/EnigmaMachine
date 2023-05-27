package Machine;

import Keyboard.Keyboard;
import Plugs.Plugs;
import Reflector.Reflector;
import Rotor.Rotor;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.String;

public class Machine {
    public Plugs plug;
    public Reflector reflectorM;
    public List<Rotor> allrotors;
    private Keyboard Keyboardmap;

    public Machine() {
    }

    ;
public Machine(Machine machine){
    this.plug= CopyPlugs(machine.plug);
    this.reflectorM=CopyReflector(machine.reflectorM);
    this.allrotors=Copyrotor(machine.allrotors);
    this.Keyboardmap=CopyKeyBoard(machine.Keyboardmap);
}
    public  Reflector CopyReflector(Reflector r){
        Reflector CurrRef=new Reflector();
        CurrRef.id=r.id;
        CurrRef.reflctor.putAll(r.reflctor);
        return CurrRef;
    }
    public Keyboard CopyKeyBoard(Keyboard keyboard){
    Keyboard CurrKey=new Keyboard();
        CurrKey.setABC(keyboard.getABC());
        CurrKey.Keyboard.putAll(keyboard.Keyboard);
        return CurrKey;
    }
    public Plugs CopyPlugs(Plugs p){
        Plugs CurrPlugs=new Plugs();
        CurrPlugs.plugboard.putAll(p.plugboard);
        return CurrPlugs;
    }
public List<Rotor>Copyrotor(List<Rotor>machineRotor) {
    List<Rotor> copyRotor = new ArrayList<Rotor>();

    for (int i = 0; i < machineRotor.size(); i++) {
        Rotor CurrRotor = new Rotor(machineRotor.get(i).rotorId, machineRotor.get(i).notch, machineRotor.get(i).getSize());
        for (int j = 0; j < CurrRotor.getSize(); j++) {
            CurrRotor.setRotorpos(machineRotor.get(i).rightRotor.get(j), machineRotor.get(i).leftRotor.get(j), j);
        }
        copyRotor.add(CurrRotor);
    }
    return copyRotor;
}




    public void SetMachine(Plugs plug, Reflector reflctor, Keyboard keyboard1, List<Rotor> allrotors) {
        this.plug = plug;
        this.reflectorM = reflctor;
        this.Keyboardmap = keyboard1;
        this.allrotors = new ArrayList<>(allrotors);
    }

    public void SetFirstPos(List<Character> lstposition) {
        for (int i = 0; i < lstposition.size(); i++) {
            int ind = allrotors.get(i).rightRotor.indexOf(lstposition.get(i));
            for (int j = 0; j < ind; j++) {
                allrotors.get(i).advencedNotch();
            }
        }
    }

    public Plugs getPlug() {
        return plug;
    }

    public void moveallrotors() {
        Rotor firstrotor = this.allrotors.get(0);
        firstrotor.advencedNotch();
        for (int i = 0; i < allrotors.size() - 1; i++) {
            Rotor currotor = allrotors.get(i);
            if (currotor.getNotch() == 1) {//שינינו מ0
                allrotors.get(i + 1).advencedNotch();
            }
        }
    }

    public Integer RtoLpath(Character x) {
        Character letter = x;
        int currInd = 0;
        letter = plug.translate(letter);

        moveallrotors();
        Integer in = Keyboardmap.findEntry(letter);

        letter = allrotors.get(0).rightRotor.get(in);
        for (int j = 0; j < allrotors.size(); j++) {
            Rotor currotor = allrotors.get(j);//get the current rotor
            currInd = currotor.translateRotor('R', letter);
            if (j != allrotors.size() - 1) {
                letter = allrotors.get(j + 1).rightRotor.get(currInd);
            }
        }

        return reflectorM.getEntry(currInd);
    }

    public Character LtoR(Integer x) {

        int currInd = 0;
        Rotor currotor = allrotors.get(allrotors.size() - 1);
        Character l = currotor.leftRotor.get(x);
        for (int j = allrotors.size() - 1; j >= 0; j--) {

            currotor = allrotors.get(j);//get the current rotor
            currInd = currotor.translateRotor('l', l);
            if (j != 0) {
                l = allrotors.get(j - 1).leftRotor.get(currInd);
            }
        }
        currInd = currotor.rightRotor.indexOf(l);
        l = Keyboardmap.findCh(currInd);
        return plug.translate(l);
    }

    public String encode(String input) {
        String str = new String();
            for (int i = 0; i < input.length(); i++) {
                Character letter = input.charAt(i);
                Integer en1 = RtoLpath(input.charAt(i));
                Character encodeLetter = LtoR(en1);
                str = str + encodeLetter;
            }
            return str;
        }


    public Keyboard getKeyboardmap() {
        return Keyboardmap;
    }
public Reflector getReflectorM(){
        return reflectorM;
}
    public List<Integer> getNotches() {
        List<Integer> listNotch = new ArrayList<>();
        for (int i = 0; i < allrotors.size(); i++) {
            listNotch.add(i, allrotors.get(i).getNotch());
        }
        return listNotch;
    }

    public List<Integer> getIDrotors() {
        List<Integer> listrotors = new ArrayList<>();
        for (int i = 0; i < allrotors.size(); i++) {
            listrotors.add(i, allrotors.get(i).getRotorId());
        }
        return listrotors;
    }
    public void setReflectorM(Reflector r){
        this.reflectorM=r;
    }
    public void setRotor(List<Rotor> r){
        this.allrotors=r;
    }
}









