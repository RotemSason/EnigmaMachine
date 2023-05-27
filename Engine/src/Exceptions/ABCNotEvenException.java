package Exceptions;

public class ABCNotEvenException extends RuntimeException {
    private final String EXCEPTION_MESSAGE="The ABC is not an even number!!!";

    @Override
    public String getMessage(){
        return EXCEPTION_MESSAGE;
    }
}
