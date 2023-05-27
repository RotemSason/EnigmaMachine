package UIException;

public class NotXmlFile extends RuntimeException{
    private String Xmlpath;
    private final String EXCEPTION_MESSAGE="The ending of the file path : %s is not .xml";
    public NotXmlFile(String Xmlpath){
        this.Xmlpath=Xmlpath;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,Xmlpath);
    }
}
