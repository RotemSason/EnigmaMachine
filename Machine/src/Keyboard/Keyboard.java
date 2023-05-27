package Keyboard;

import java.util.HashMap;
import java.util.Map;


public class Keyboard {
        private String abc;
        public Map<Character,Integer> Keyboard;
        public Keyboard(){
            this.Keyboard=new HashMap<>();
        }
    public Keyboard(Keyboard keyboard){
            this.abc=keyboard.abc;
        this.Keyboard=new HashMap<>();
        this.Keyboard.putAll(keyboard.Keyboard);
    }
        public void setABC(String abc){
             this.abc=abc.toUpperCase();
        }
        public String getABC(){
            return abc;
        }
        public void setKeyboard() {
            for (int i = 0; i <abc.length(); i++) {
                this.Keyboard.put(abc.charAt(i),i);
            }
        }
        public Integer findEntry(Character in){
            return Keyboard.get(in);
        }
        public Character findCh(Integer val){
            {
                for (Map.Entry<Character,Integer> entry:Keyboard.entrySet()) {
                    if (entry.getValue().equals(val)) {
                        return entry.getKey();
                    }
                }
                return null;
            }
        }
    }


