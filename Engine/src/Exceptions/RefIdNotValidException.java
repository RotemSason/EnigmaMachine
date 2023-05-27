package Exceptions;

public class RefIdNotValidException extends RuntimeException{
    private String Id;
    private final String EXCEPTION_MESSAGE="The reflector id %s is not valid !!!:(";
    public RefIdNotValidException(String Id){
       this.Id=Id;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,Id);
    }
}

