package Exceptions;

public class TheRotorsIdNotInARow extends RuntimeException{
    private final String EXCEPTION_MESSAGE="The Rotor's Id not in a row start from 1!!!";

    @Override
    public String getMessage(){
        return EXCEPTION_MESSAGE;
    }
}

