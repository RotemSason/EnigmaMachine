package Exceptions;

public class UseRotorsBiggerThenNumRotorException extends RuntimeException {
    private int userotors;
    private int allrotorsamount;

    private final String EXCEPTION_MESSAGE="The amount of rotors that the machine needs (%d) is bigger then the total amount of rotors (%d) !!!:(";
public UseRotorsBiggerThenNumRotorException(int userotors,int allrotorsamount){
    this.userotors=userotors;
    this.allrotorsamount=allrotorsamount;
}
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,userotors,allrotorsamount);
    }
}

