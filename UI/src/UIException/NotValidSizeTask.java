package UIException;

public class NotValidSizeTask extends RuntimeException{
    private final String EXCEPTION_MESSAGE="You enter wrong number of task size!!!";
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE);
    }
}


