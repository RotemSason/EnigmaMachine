package UBoatException;

public class PlugsNotPair extends RuntimeException{
    private final String EXCEPTION_MESSAGE="The number of plugs is not valid, you need to enter 2 positions!";

    @Override
    public String getMessage(){
        return EXCEPTION_MESSAGE;
    }
}
