package Agent;

import EngineUI.CurrCode;

import static java.lang.Integer.parseInt;

public class AgentCurrCode {
    private String nameAllies;
   private CurrCode successCode;
    private String StrCandidate;

    public AgentCurrCode(String nameAllies, CurrCode successCode,String StrCandidate){
        this.nameAllies=nameAllies;
        this.successCode=successCode;
        this.StrCandidate=StrCandidate;

    }
    public CurrCode getSuccessCode(){
        return successCode;
    }

    public String getNameAllies() {
        return nameAllies;
    }

    public String getRotors(){
        String str="";
        for (int i = 0; i < successCode.getSelectedRotors().size(); i++) {
            str=str+successCode.getSelectedRotors().get(i);
        }
        return str;
    }
    public String getPositions(){
        String str="";
        for (int i = 0; i < successCode.getSelectedPos().size(); i++) {
            str=str+successCode.getSelectedPos().get(i);
        }
        return str;
    }
    public String getReflector(){
        return successCode.IntToRom(successCode.getSelectedReflector());
    }
    public String getStrCandidate(){return StrCandidate;}
}
