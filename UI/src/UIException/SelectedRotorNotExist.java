package UIException;

public class SelectedRotorNotExist extends RuntimeException{
    private int rotorid;
    private int size;
    private final String EXCEPTION_MESSAGE="There is no rotor with id %d,you should enter number of rotor id between 1 to %d";
    public SelectedRotorNotExist(int rotorid, int size){
        this.rotorid=rotorid;
        this.size=size;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,rotorid,size);
    }
}

