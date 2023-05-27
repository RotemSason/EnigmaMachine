/*package Agent;

import EngineUI.CurrCode;

import static java.lang.Integer.parseInt;

public class AgentCurrCode {
    private String ID;
   private CurrCode successCode;

    public AgentCurrCode(String id, CurrCode successCode){
        ID=id;
        this.successCode=successCode;

    }
    public CurrCode getSuccessCode(){
        return successCode;
    }
    public int getID(){
        return parseInt(ID);
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

}*/
