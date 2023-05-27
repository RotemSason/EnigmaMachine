package UIException;

public class NumOfRotorWrongException extends RuntimeException{

    private int numrotorselected;
    private final String EXCEPTION_MESSAGE="You selected %d rotors and this is not the amount of rotors that the machine needs ! ";
    public NumOfRotorWrongException(int numrotorselected){
        this.numrotorselected=numrotorselected;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,numrotorselected);
    }
}
