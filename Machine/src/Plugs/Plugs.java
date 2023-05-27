package Plugs;

import com.sun.org.apache.bcel.internal.generic.PUSH;

import java.util.HashMap;
import java.util.Map;

public class Plugs {
    public Map<Character,Character> plugboard;
    public Plugs(){this.plugboard=new HashMap<>();}
    public Plugs(Map<Character,Character>maplugs){this.plugboard =new HashMap<>(maplugs);}
    public Plugs(Plugs p){
        this.plugboard=new HashMap<>();
        this.plugboard.putAll(p.plugboard);
    }
    public Map<Character,Character>getPlugboard(){
        return plugboard;
    }

    public void set(Character x,Character y){//לוודא שאין שקע מעצמו,ואין חזרות
        plugboard.put(x,y);
        plugboard.put(y,x);
    }
    public Character translate(Character input){
        if(plugboard.containsKey(input)){
            return plugboard.get(input);
        }
        else{//check
            return input;
        }
    }

    void clearAllPlugs(){
        plugboard.clear();
    }

}
