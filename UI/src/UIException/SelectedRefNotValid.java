package UIException;

public class SelectedRefNotValid extends RuntimeException{
    private int refid;
    private final String EXCEPTION_MESSAGE="The selected reflector id %d is not one of the options!";
    public SelectedRefNotValid(int refid){
        this.refid=refid;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,refid);
    }
}
