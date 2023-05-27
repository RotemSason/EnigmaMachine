package UIException;

public class AmoutOfPosNotValid extends RuntimeException{
    private int numposFile;
    private int numposUser;
    private final String EXCEPTION_MESSAGE="Wrong amount of start positions.There are %d rotors and you entered %d positions ";
    public AmoutOfPosNotValid(int numposFile,int numposUser){
       this.numposFile=numposFile;
       this.numposUser=numposUser;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,numposFile,numposUser);
    }
}
