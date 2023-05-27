package Exceptions;

public class UseRotorSmallerThenTwoException extends RuntimeException {
    private int userotors;

    private final String EXCEPTION_MESSAGE="The amount of rotors that the machine needs (%d) is not valid, it needs to be between 2-99 !!! :(";
    public UseRotorSmallerThenTwoException(int userotors){
        this.userotors=userotors;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,userotors);
    }
}
