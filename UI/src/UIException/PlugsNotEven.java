package UIException;

public class PlugsNotEven extends RuntimeException{
    private final String EXCEPTION_MESSAGE="The number of plugs is not even!";

    @Override
    public String getMessage(){
        return EXCEPTION_MESSAGE;
    }
}
