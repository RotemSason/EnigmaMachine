package Exceptions;

public class TheRotorIdNotUnique extends RuntimeException{
    private final String EXCEPTION_MESSAGE="There are a few rotors with the same id!!!";

    @Override
    public String getMessage(){
        return  EXCEPTION_MESSAGE;
    }
}
