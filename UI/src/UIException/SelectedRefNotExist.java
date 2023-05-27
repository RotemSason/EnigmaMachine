package UIException;

public class SelectedRefNotExist extends RuntimeException{
    private int refid;
    private int size;
    private final String EXCEPTION_MESSAGE="There is no reflector with id %d,you should enter number of reflector id between 1 to %d";
    public SelectedRefNotExist(int refid, int size){
        this.refid=refid;
        this.size=size;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,refid,size);
    }
}
