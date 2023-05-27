package Reflector;

import java.util.HashMap;
import java.util.Map;

public class Reflector {
    public int id;
    public Map<Integer,Integer> reflctor;

    public Reflector(){this.reflctor=new HashMap<>();}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setReflctor(Integer in,Integer out){
        reflctor.put(in,out);
        reflctor.put(out,in);
    }
    public Integer getEntry(Integer in){
       return reflctor.get(in);
    }
    public int RomToInt(String RomNum) {
            switch (RomNum) {
                case "I":
                    return 1;
                case "II":
                    return 2;
                case "III":
                    return 3;
                case "IV":
                    return 4;
                case "V":
                    return 5;
                default:
                    return 0;//fix!
            }
        }
}
