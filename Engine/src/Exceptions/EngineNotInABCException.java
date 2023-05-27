package Exceptions;

public class EngineNotInABCException extends RuntimeException{
    private Character ch;
    private final String EXCEPTION_MESSAGE="The Char %c ,is not part of the machine ABC ! ";
    public EngineNotInABCException(Character ch){
        this.ch=ch;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,ch);
    }
}
