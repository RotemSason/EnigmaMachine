package Exceptions;

public class DuplicateRefEntry extends RuntimeException{
    private int input;
    private int id;

    private final String EXCEPTION_MESSAGE="In reflector %d ,The entry %d has duplicate mapping!!! :(";
    public DuplicateRefEntry(int id,int input){
        this.input=input;
        this.id=id;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,id,input);
    }
}
