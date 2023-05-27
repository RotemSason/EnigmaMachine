package Exceptions;

public class RefEntriesNumber extends RuntimeException{
    private int currnum;
    private int abcnum;
    private int refid;
    private final String EXCEPTION_MESSAGE="In reflector %d there are %d entries and it should be %d entries!!!:(";
    public RefEntriesNumber(int currnum,int abcnum,int refid){
        this.currnum=currnum;
        this.abcnum=abcnum;
        this.refid=refid;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,refid,currnum,abcnum);
    }
}

