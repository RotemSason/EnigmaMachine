package Exceptions;

public class AgentsNumNotValidException extends RuntimeException{
    private int number;
    private final String EXCEPTION_MESSAGE="The number of agents is: %d it's not between 2 - 50";
    public AgentsNumNotValidException(int number){
        this.number=number;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,number);
    }
}

