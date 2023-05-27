package Exceptions;

public class RefMappingFailException extends RuntimeException{
    private String refid;
    private int num;
    private final String EXCEPTION_MESSAGE="In reflector %s the mapping is wrong,the number %d is mapping to himself!!!:(";
    public RefMappingFailException(String refid,int num){
        this.refid=refid;
        this.num=num;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,refid,num);
    }
}

