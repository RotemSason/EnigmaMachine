package Exceptions;

public class RotorMappingFailException extends RuntimeException{

    private int RotorID;
   private Character ch;
   private String side;
   private int amount;

    private final String EXCEPTION_MESSAGE="In rotor %d on the %s side the rotor mapping is wrong,the Char %c is mapping to %d entries !!!:(";
    public RotorMappingFailException(int RotorID,Character ch,String side,int amount){
        this.RotorID=RotorID;
        this.ch=ch;
        this.side=side;
        this.amount=amount;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,RotorID,side,ch,amount);
    }
}
