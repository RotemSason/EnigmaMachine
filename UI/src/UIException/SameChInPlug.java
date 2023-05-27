package UIException;

public class SameChInPlug extends RuntimeException{
    private Character ch;
    private final String EXCEPTION_MESSAGE="The char %c cannot be mapping to %c";
    public SameChInPlug(Character ch){
        this.ch=ch;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,ch,ch);
    }
}
