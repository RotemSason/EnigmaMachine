package Engine;

import EngineUI.EngineUI;
import EngineUI.CurrCode;
import java.io.InputStream;

public interface Engine {

    public void loadXmlFile(InputStream inputStream);
    public EngineUI CreateReturnObj();
    //public void InitialCode(CurrCode code);
    //public String DecodeStr(String str,StringBuilder CodeConf);
    public String DecodeStr(String str);
    public String DecodeStrManual(String str);
    public void ResetMachine();
    public StringBuilder HistoryAndStatistics(HistoryCode tmp);



}
