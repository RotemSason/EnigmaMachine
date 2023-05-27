package Exceptions;

public class NotchNotRangeOfABCException extends RuntimeException{
    private int notch;
    private int RotorID;
    private int sizeABC;

    private final String EXCEPTION_MESSAGE="The notch's number %d of rotor %d is not in the range of the rotor size 1-%d  !!!:(";
    public NotchNotRangeOfABCException(int notch,int RotorID,int sizeABC){
        this.notch=notch;
        this.RotorID=RotorID;
        this.sizeABC=sizeABC;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,notch,RotorID,sizeABC);
    }
}
