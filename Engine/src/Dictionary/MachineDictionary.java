package Dictionary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MachineDictionary {
    private List<Character> SpecialChars;
    private Set<String>Dictionary;

    public MachineDictionary(){
        SpecialChars=new ArrayList<>();
        Dictionary= new HashSet<>();
    }

    public void setDictionary(String strFromFile) {
        String listToString= SpecialChars.toString();
        for (String str : strFromFile.split(" ")) {
            str=str.replaceAll(listToString,"");
            str=str.toUpperCase();
            Dictionary.add(str);
        }
    }
    public void setSpecialChars(String strFromFile){
        for (int i = 0; i < strFromFile.length(); i++) {
            SpecialChars.add(strFromFile.charAt(i));
        }
    }
    public Set<String> getDictionary(){
        return  Dictionary;
    }
    public List<Character>getSpecialChars(){return SpecialChars;}
}


