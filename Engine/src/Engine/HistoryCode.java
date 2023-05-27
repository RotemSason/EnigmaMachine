package Engine;

public class HistoryCode {
    private StringBuilder CodeConf;
   private String encodeStr;
    private String decodeStr;
   private long time;
    public HistoryCode(StringBuilder CodeConf,String encodeStr,String decodeStr,long time){
        this.CodeConf=CodeConf;
        this.encodeStr=encodeStr;
        this.decodeStr=decodeStr;
        this.time=time;

    }
    public String getEncodeStr(){return encodeStr;}
    public String getDecodeStr(){return decodeStr;}
    public StringBuilder getCodeConf(){return CodeConf;}
    public long getTime(){return time;}

}


