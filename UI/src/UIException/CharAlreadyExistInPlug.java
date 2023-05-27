package UIException;

public class CharAlreadyExistInPlug extends RuntimeException{
    private Character ch;
    private final String EXCEPTION_MESSAGE="The char %c is already exist in another plug";
    public CharAlreadyExistInPlug(Character ch){
        this.ch=ch;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,ch);
    }
}
