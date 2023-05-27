package Exceptions;

public class MissingLetterInRotor extends RuntimeException{
    private int id;
    private final String EXCEPTION_MESSAGE="In rotor %d not all the ABC exist!!! :( ";
    public MissingLetterInRotor(int id){
 this.id=id;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,id);
    }
}

