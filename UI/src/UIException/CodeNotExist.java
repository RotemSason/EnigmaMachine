package UIException;

public class CodeNotExist extends RuntimeException{
    private final String EXCEPTION_MESSAGE="You can't encode a string without an available code in the machine! please press 3 or 4 in the menu  ";

    @Override
    public String getMessage(){
        return EXCEPTION_MESSAGE;
    }
}
