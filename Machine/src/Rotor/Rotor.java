package Rotor;

import java.util.ArrayList;
import java.util.List;

public class Rotor {
    public int rotorId;
    public int notch;
    private int size;
    public List<Character> rightRotor;
    public List<Character> leftRotor;

    public Rotor(int id,int notch,int size) {
        this.rotorId=id;
        this.notch=notch;
        this.size=size;
        this.leftRotor=new ArrayList<>();
        this.rightRotor=new ArrayList<>();
    }
   public int getRotorId(){return rotorId;}
    public int getSize(){return size;}
    public int getNotch(){return notch;}
    public void setNotch(int notch) {
        this.notch =notch;
    }
    public void setRotorpos(Character right,Character left,int index){
        leftRotor.add(index,left);
        rightRotor.add(index,right);
    }
    public void setRotorId(int rotorId){
        this.rotorId=rotorId;
    }
    public void advencedNotch(){
        if(this.leftRotor.size()==0 || this.rightRotor.size()==0){
            System.out.println("help");
        }
        Character firstL=this.leftRotor.remove(0);//remove the first element
        this.leftRotor.add(this.getSize()-1,firstL);
        Character firstR=this.rightRotor.remove(0);//remove the first element
        this.rightRotor.add(this.getSize()-1,firstR);
        if(notch==1){
            this.setNotch(this.getSize());
        }

        else{
            this.setNotch(--notch);
        }
    }
    public int translateRotor(char dir,Character input){
        int index;
        if(dir=='R'){
             index=leftRotor.indexOf(input);//if the direction is from l to r we need to find the entry in the left rotor
        }
        else {
             index=rightRotor.indexOf(input);
        }
        return index;
    }

}




