package UIException;

public class FileNotLoaded extends RuntimeException{
    private final String EXCEPTION_MESSAGE="Xml file with the machine details not loaded!!! you cant set a machine code before press option 1 in the menu!";

    @Override
    public String getMessage(){
        return EXCEPTION_MESSAGE;
    }
}
