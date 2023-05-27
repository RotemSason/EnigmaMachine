package UBoatException;

public class NotInDictionary extends RuntimeException{
    private final String EXCEPTION_MESSAGE="You enter a word that doesn't exist in the dictionary!!!";
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE);
    }
}
