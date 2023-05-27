package Exceptions;

public class RefMappingNotInsize extends RuntimeException{
    private int num;
    private int id;
    private final String EXCEPTION_MESSAGE="In reflector id %d there is an entry to %d, and this is not in the machine ABC bounds !!!:(";
    public RefMappingNotInsize(int num,int id){
        this.num=num;
        this.id=id;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,id,num);
    }
}
