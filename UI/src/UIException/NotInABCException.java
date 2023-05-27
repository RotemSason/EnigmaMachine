package UIException;

public class NotInABCException extends RuntimeException{
    private Character ch;
    private final String EXCEPTION_MESSAGE="You entered %c ,this char is not part of the machine ABC ! ";
    public NotInABCException(Character ch){
        this.ch=ch;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,ch);
    }
}
