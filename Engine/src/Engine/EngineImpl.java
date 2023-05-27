package Engine;

import Agent.AgentCurrCode;
//import Agent.DM;
import EngineUI.EngineUI;
import EngineUI.CurrCode;

import Exceptions.*;
import Machine.Machine;
import jaxb.CTEEnigma;
import jaxb.CTEReflector;
import jaxb.CTERotor;

import Keyboard.Keyboard;
import Reflector.Reflector;
import Rotor.Rotor;
import Dictionary.MachineDictionary;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import static java.lang.Integer.parseInt;

public class EngineImpl implements Engine {

    private List<Reflector> lstOfReflector;
    private int UseRotors;
    private List<Rotor> lstOfRotors;
    private Keyboard Keyboard;
    private CurrCode originalCode;


    private CurrCode CurrentCode;
    private int CommandCount;
    private boolean OriginalCodeFlag;
    private boolean XmlFlag;
    private boolean CurrentCodeFlag;
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "jaxb";
    private Machine Machine;

    private List<HistoryCode> Historylst;
    private MachineDictionary Dictionary;
    //private DM dm;
    private BattleField battle;

    public EngineImpl() {
        this.lstOfRotors = new ArrayList<>();
        this.lstOfReflector = new ArrayList<>();
        this.Keyboard = new Keyboard();
        this.OriginalCodeFlag = false;
        this.CurrentCodeFlag = false;
        this.Machine = new Machine();
        this.Historylst = new ArrayList<>();
        this.Dictionary = new MachineDictionary();
        this.battle=new BattleField();
    }

    public void setCurrentCodeFlag() {
        CurrentCodeFlag = true;
    }

    public void setCommandCount() {
        CommandCount++;
    }

    public String getKeyboard() {
        return Keyboard.getABC();
    }

    public Map<Character, Integer> getMapKeyboard() {
        return Keyboard.Keyboard;
    }

    public int getUseRotors() {
        return UseRotors;
    }

    public List<Reflector> getlstOfReflector() {
        return lstOfReflector;
    }

    public CurrCode getCurrCode() {
        return originalCode;
    }

    public CurrCode getCurrentCode() {
        return CurrentCode;
    }

    public List<Rotor> getLstOfRotors() {
        return lstOfRotors;
    }

    public boolean getOriginalCodeFlag() {
        return OriginalCodeFlag;
    }

    public boolean getCurrentCodeFlag() {
        return CurrentCodeFlag;
    }

    public boolean getXmlFlag() {
        return XmlFlag;
    }
    public int getCommandCount(){return CommandCount;}

    Queue<List<AgentCurrCode>> successCodes = new LinkedList<>();

    public Queue<List<AgentCurrCode>> getQueue() {
        return successCodes;
    }

    public void loadXmlFile(String inputStream) {
    }

    public void loadXmlFile(InputStream inputStream) {
        try {
          /*  File checkexist = new File(fullPathXml);
            if (!checkexist.exists()) {
                throw new XmlFileDoesntExist();
            }*/
            EngineImpl copyfile = new EngineImpl();
            //InputStream inputStream = new FileInputStream(new File(fullPathXml));
            CTEEnigma EnigmaMachine = deserializeFrom(inputStream);
            if (EnigmaMachine.getCTEMachine().getABC().trim().length() % 2 != 0) {
                throw new ABCNotEvenException();
            }
           copyfile.Keyboard.setABC(EnigmaMachine.getCTEMachine().getABC().trim());
          copyfile.Keyboard.setKeyboard();
            copyfile.Dictionary.setSpecialChars(EnigmaMachine.getCTEDecipher().getCTEDictionary().getExcludeChars());
            copyfile.Dictionary.setDictionary(EnigmaMachine.getCTEDecipher().getCTEDictionary().getWords());
            copyfile.battle.setBattleName(EnigmaMachine.getCTEBattlefield().getBattleName());
            copyfile.battle.setNumOfAllies(EnigmaMachine.getCTEBattlefield().getAllies());
            copyfile.battle.setLevel(EnigmaMachine.getCTEBattlefield().getLevel());


            copyfile.UseRotors = EnigmaMachine.getCTEMachine().getRotorsCount();
            List<CTERotor> FileRotors = EnigmaMachine.getCTEMachine().getCTERotors().getCTERotor();
            if (copyfile.UseRotors > FileRotors.size()) {
                throw new UseRotorsBiggerThenNumRotorException(copyfile.UseRotors, FileRotors.size());
            }
            if ((copyfile.getUseRotors() < 2) || (copyfile.getUseRotors() > 99)) {
                throw new UseRotorSmallerThenTwoException(copyfile.UseRotors);
            }
            for (int i = 0; i < FileRotors.size(); i++) {
                if (FileRotors.get(i).getNotch() < 1 || FileRotors.get(i).getNotch() > EnigmaMachine.getCTEMachine().getABC().trim().length()) {
                    throw new NotchNotRangeOfABCException(FileRotors.get(i).getNotch(), FileRotors.get(i).getId(), EnigmaMachine.getCTEMachine().getABC().trim().length());
                }
                Rotor currRotor = new Rotor(FileRotors.get(i).getId(), FileRotors.get(i).getNotch(), FileRotors.get(i).getCTEPositioning().size());
                for (int j = 0; j < FileRotors.get(i).getCTEPositioning().size(); j++) {
                    char chright = FileRotors.get(i).getCTEPositioning().get(j).getRight().charAt(0);
                    char chleft = FileRotors.get(i).getCTEPositioning().get(j).getLeft().charAt(0);

                    if (!(copyfile.getMapKeyboard().containsKey(FileRotors.get(i).getCTEPositioning().get(j).getRight().charAt(0)))) {
                        chright = caseinsensitive(FileRotors.get(i).getCTEPositioning().get(j).getRight().charAt(0), copyfile.getMapKeyboard());
                    }
                    if (!(copyfile.getMapKeyboard().containsKey(FileRotors.get(i).getCTEPositioning().get(j).getLeft().charAt(0)))) {
                        chleft = caseinsensitive(FileRotors.get(i).getCTEPositioning().get(j).getLeft().charAt(0), copyfile.getMapKeyboard());
                    }
                    currRotor.setRotorpos(chright, chleft, j);

                }
                if (currRotor.rightRotor.size() < copyfile.getKeyboard().length() || currRotor.leftRotor.size() < copyfile.getKeyboard().length()) {
                    throw new MissingLetterInRotor(currRotor.rotorId);
                }
                copyfile.lstOfRotors.add(i, currRotor);
            }
            RotorMapping(copyfile);

            Collections.sort(copyfile.lstOfRotors, new Comparator<Rotor>() {
                public int compare(Rotor x, Rotor y) {
                    if (x.getRotorId() == y.getRotorId())
                        return 0;
                    return x.getRotorId() < y.getRotorId() ? -1 : 1;
                }
            });
            for (int i = 0; i < copyfile.lstOfRotors.size(); i++) {
                if (copyfile.lstOfRotors.get(i).getRotorId() < 1 || copyfile.lstOfRotors.get(i).getRotorId() > copyfile.lstOfRotors.size()) {
                    throw new TheRotorsIdNotInARow();
                }
                if (i + 1 != copyfile.lstOfRotors.get(i).getRotorId()) {
                    throw new TheRotorIdNotUnique();
                }

            }
            List<CTEReflector> FileReflectors = EnigmaMachine.getCTEMachine().getCTEReflectors().getCTEReflector();
            for (int i = 0; i < FileReflectors.size(); i++) {
                Reflector currRef = new Reflector();
                String RefId = FileReflectors.get(i).getId();
                if (currRef.RomToInt(RefId) == 0) {
                    throw new RefIdNotValidException(RefId);
                }
                currRef.setId(currRef.RomToInt(RefId));
                for (int j = 0; j < FileReflectors.get(i).getCTEReflect().size(); j++) {

                    if ((currRef.reflctor.containsKey(FileReflectors.get(i).getCTEReflect().get(j).getInput() - 1)) || (currRef.reflctor.containsValue(FileReflectors.get(i).getCTEReflect().get(j).getInput() - 1))) {
                        throw new DuplicateRefEntry(currRef.getId(), FileReflectors.get(i).getCTEReflect().get(j).getInput());
                    }
                    if ((currRef.reflctor.containsKey(FileReflectors.get(i).getCTEReflect().get(j).getOutput() - 1)) || (currRef.reflctor.containsValue(FileReflectors.get(i).getCTEReflect().get(j).getOutput() - 1))) {
                        throw new DuplicateRefEntry(currRef.getId(), FileReflectors.get(i).getCTEReflect().get(j).getOutput());
                    }
                    if ((FileReflectors.get(i).getCTEReflect().get(j).getInput() < 1) || (FileReflectors.get(i).getCTEReflect().get(j).getInput() > copyfile.getKeyboard().length())) {
                        throw new RefMappingNotInsize(FileReflectors.get(i).getCTEReflect().get(j).getInput(), currRef.id);
                    }
                    if ((FileReflectors.get(i).getCTEReflect().get(j).getOutput() < 1) || (FileReflectors.get(i).getCTEReflect().get(j).getOutput() > copyfile.getKeyboard().length())) {
                        throw new RefMappingNotInsize(FileReflectors.get(i).getCTEReflect().get(j).getOutput(), currRef.id);
                    }

                    if (FileReflectors.get(i).getCTEReflect().get(j).getInput() - 1 == FileReflectors.get(i).getCTEReflect().get(j).getOutput() - 1) {
                        throw new RefMappingFailException(RefId, FileReflectors.get(i).getCTEReflect().get(j).getInput());
                    }
                    currRef.setReflctor(FileReflectors.get(i).getCTEReflect().get(j).getInput() - 1, FileReflectors.get(i).getCTEReflect().get(j).getOutput() - 1);
                }
                if ((currRef.reflctor.size() / 2) != (copyfile.getKeyboard().length() / 2)) {
                    throw new RefEntriesNumber(currRef.reflctor.size() / 2, copyfile.getKeyboard().length() / 2, currRef.getId());
                }
                copyfile.lstOfReflector.add(i, currRef);
            }
            Collections.sort(copyfile.lstOfReflector, new Comparator<Reflector>() {
                public int compare(Reflector x, Reflector y) {
                    if (x.getId() == y.getId())
                        return 0;
                    return x.getId() < y.getId() ? -1 : 1;
                }
            });
            for (int i = 0; i < copyfile.lstOfReflector.size(); i++) {
                if (i + 1 != copyfile.lstOfReflector.get(i).getId()) {
                    throw new TheRefIdNotInARowExcetion();
                }
            }

            XmlFlag = true;
            CommandCount = 0;
            CopyXmlFile(copyfile);
            CurrentCodeFlag = false;
            OriginalCodeFlag = false;
            Historylst.clear();
            inputStream.close();

        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //public void loadXmlFile(InputStream inputStream){



    public char caseinsensitive(char ch, Map<Character, Integer> ABC) {
        Character tmp = null;
        if (Character.isLetter(ch)) {
            if (Character.isUpperCase(ch)) {
                tmp = Character.toLowerCase(ch);
                if (!(ABC.containsKey(tmp))) {
                    throw new EngineNotInABCException(ch);
                }
            }
            if (Character.isLowerCase(ch)) {
                tmp = Character.toUpperCase(ch);
                if (!(ABC.containsKey(tmp))) {
                    throw new EngineNotInABCException(ch);
                }
            }
            return tmp;
        } else {
            throw new EngineNotInABCException(ch);
        }
    }


    public void CopyXmlFile(EngineImpl copyfile) {
        this.lstOfReflector = copyfile.lstOfReflector;
        this.UseRotors = copyfile.UseRotors;
        this.lstOfRotors = copyfile.lstOfRotors;
        this.Keyboard = copyfile.Keyboard;
        this.Dictionary = copyfile.Dictionary;
        this.battle=copyfile.battle;
    }

    private static CTEEnigma deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (CTEEnigma) u.unmarshal(in);
    }

@Override
    public EngineUI CreateReturnObj() {
        EngineUI ReturnObj;
        if (!OriginalCodeFlag) {
            ReturnObj = new EngineUI(lstOfReflector, UseRotors, lstOfRotors, Keyboard, null, CommandCount,Dictionary);
        } else {
            ReturnObj = new EngineUI(lstOfReflector, UseRotors, lstOfRotors, Keyboard, originalCode, CommandCount,Dictionary);
        }
        return ReturnObj;
    }

    public void InitialCode(CurrCode code) {
        originalCode = code;
        this.CurrentCode = new CurrCode(code);
        List<Rotor> rotormachine = new ArrayList<>();
        for (int i = 0; i < code.getSelectedRotors().size(); i++) {
            rotormachine.add(lstOfRotors.get(code.getSelectedRotors().get(i) - 1));
        }

        this.Machine.SetMachine(originalCode.getSelctesPlugs(), lstOfReflector.get(originalCode.getSelectedReflector() - 1), Keyboard, rotormachine);
        this.Machine.SetFirstPos(originalCode.getSelectedPos());
        for (int i = 0; i < code.getSelectedRotors().size(); i++) {
            originalCode.getCurrNotch().add(i, lstOfRotors.get(code.getSelectedRotors().get(i) - 1).getNotch());
        }
        OriginalCodeFlag = true;
    }

    @Override
    public String DecodeStr(String str) {
        String strAfterDec = Machine.encode(str);
        CurrentCodeFlag = true;
        setCurrentCode();
        CommandCount++;
        return strAfterDec;
    }

    @Override
    public String DecodeStrManual(String str) {

        String strAfterDec = Machine.encode(str);
        setCurrentCode();
        return strAfterDec;
    }

    public void RotorMapping(EngineImpl copy) {

        for (int i = 0; i < copy.lstOfRotors.size(); i++) {
            int[] arright = new int[copy.lstOfRotors.get(i).rightRotor.size()];
            int[] arrleft = new int[copy.lstOfRotors.get(i).leftRotor.size()];
            for (int j = 0; j < copy.lstOfRotors.get(i).leftRotor.size(); j++) {
                int numentryR = copy.Keyboard.findEntry(copy.lstOfRotors.get(i).rightRotor.get(j));
                arright[numentryR]++;
                int numentryL = copy.Keyboard.findEntry(copy.lstOfRotors.get(i).leftRotor.get(j));
                arrleft[numentryL]++;
            }
            for (int j = 0; j < copy.Keyboard.getABC().length(); j++) {
                if (arright[j] > 1) {
                    throw new RotorMappingFailException(copy.lstOfRotors.get(i).getRotorId(), copy.getKeyboard().charAt(j), "right", arright[j]);
                }
                if (arrleft[j] > 1) {
                    throw new RotorMappingFailException(copy.lstOfRotors.get(i).getRotorId(), copy.getKeyboard().charAt(j), "left", arrleft[j]);
                }
            }
        }
    }

    @Override
    public void ResetMachine() {
        this.Machine.SetFirstPos(originalCode.getSelectedPos());
        for (int i = 0; i < getUseRotors(); i++) {

            this.Machine.allrotors.get(i).setNotch(originalCode.getCurrNotch().get(i));
        }
        setCurrentCode();
    }

    public StringBuilder setHistorylst(StringBuilder CodeConf, String encodeStr, String decodeStr, long time) {
        HistoryCode tmp = new HistoryCode(CodeConf, encodeStr, decodeStr, time);
        return HistoryAndStatistics(tmp);
    }

    @Override
    public StringBuilder HistoryAndStatistics(HistoryCode tmp) {
        StringBuilder S = new StringBuilder();
        S.append("For code configuration: ");
        S.append(tmp.getCodeConf());
        S.append(" The history and statstics is: ");
        S.append("<");
        S.append(tmp.getEncodeStr());
        S.append(">--><");
        S.append(tmp.getDecodeStr());
        S.append(">(");
        S.append(tmp.getTime());
        S.append(')');
        return S;
    }

    public void setCurrentCode() {

        CurrentCode.setSelectedRotors(originalCode.getSelectedRotors());
        CurrentCode.setSelectedPlugs(originalCode.getSelctesPlugs());
        CurrentCode.setSelectedReflector(originalCode.getSelectedReflector());
        List<Integer> CurrNotchLst = new ArrayList<>();
        List<Character> CurrPosLst = new ArrayList<>();
        for (int i = 0; i < originalCode.getSelectedRotors().size(); i++) {
            Integer rotorid = originalCode.getSelectedRotors().get(i);
            CurrNotchLst.add(lstOfRotors.get(rotorid - 1).getNotch());
            CurrPosLst.add(lstOfRotors.get(rotorid - 1).rightRotor.get(0));
        }
        CurrentCode.setCurrNotch(CurrNotchLst);
        CurrentCode.setSelectedPos(CurrPosLst);

    }

    public Machine getMachine() {
        return Machine;
    }

    public MachineDictionary getDictionary() {
        return Dictionary;
    }

    /*public void startBruteForce(DM dm,String level){

        try {
            if(level.equals("Easy")) {
                dm.level1(getDictionary(), () -> toggleTaskButtons(false));
            }
            else if (level.equals("Medium")) {
                dm.level2(getDictionary(), () -> toggleTaskButtons(false), lstOfReflector);
            }
            else if (level.equals("Hard")) {
                dm.level3(getDictionary(), () -> toggleTaskButtons(false), lstOfReflector);
            }
            else {
                dm.level4(getDictionary(), () -> toggleTaskButtons(false), lstOfReflector);
            }


        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public void setDM(DM dm) {
        this.dm = dm;
    }
*/
    private void toggleTaskButtons(boolean isActive) {

    }
public int getABCsize(){return Machine.getKeyboardmap().getABC().length();}
    public int  getNumOfRotorsDM(){return Machine.allrotors.size();}
    public int getNumOfRefDM(){return lstOfReflector.size();}

    public void copyDataToMainEngine(EngineUI dto){
        this.lstOfRotors=dto.getLstOfRotors();
        this.UseRotors=dto.getUseRotors();
        this.Keyboard=dto.getKeyboard();
        this.CurrentCode=dto.getCode();
        this.CommandCount=dto.getCommandCount();

    }

    public BattleField getBattle() {
        return battle;
    }
}













        


