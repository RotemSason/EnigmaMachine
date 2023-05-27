package UIException;

public class NotEnterStr extends RuntimeException{
    private final String EXCEPTION_MESSAGE="You can't press process without enter a string!!!";
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE);
    }
}





