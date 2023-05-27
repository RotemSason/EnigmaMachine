package Exceptions;

public class TheRefIdNotInARowExcetion extends RuntimeException{
    private final String EXCEPTION_MESSAGE="The Reflector's Id not in a row start from 1!!!";

    @Override
    public String getMessage(){
        return EXCEPTION_MESSAGE;
    }

}

