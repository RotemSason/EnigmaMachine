package Screens.MainScreens;

import EngineUI.CurrCode;
import Engine.EngineImpl;

import EngineUI.EngineUI;
/*
import Screens.BruteForceProgress.BruthForceProgressController;
import Screens.CodeConfiguration.CodeConfigurationController;
import Screens.CurrentCode.CurrentCodeController;
import Screens.DMoperational.DMoperationalController;
import Screens.EncryptDecrypt.EncryptDecryptController;
import Screens.MachineDetails.MachineDetailsController;
import UIException.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.ListSelectionView;

import java.io.File;
import java.util.*;
/*
public class MainScreensController {
    @FXML
    private AnchorPane machineDetailsComponent;
    @FXML
    private HBox encryptDecryptComponent;
    @FXML
    private EncryptDecryptController encryptDecryptComponentController;
    @FXML
    private VBox dMoperationalComponent;
    @FXML
    private DMoperationalController dMoperationalComponentController;
    @FXML
    private FlowPane bruteForceProgressComponent;
    @FXML
    private BruthForceProgressController bruteForceProgressComponentController;
    @FXML
    private HBox codeConfigurationComponent;
    @FXML
    private VBox currentCodeComponent;
    @FXML
    private VBox vboxLoadFile;
    @FXML
    private VBox vboxMainScreens;
    @FXML
    private TabPane tabPane;
    @FXML
    private VBox currentCodeComponent3;
    @FXML
    private CurrentCodeController currentCodeComponent3Controller;
    @FXML
    private CurrentCodeController currentCodeComponentController;

    @FXML
    private MachineDetailsController machineDetailsComponentController;
    @FXML
    private CodeConfigurationController codeConfigurationComponentController;
    public EngineImpl MainEngine = new EngineImpl();
    @FXML
    private Button loadFileButton;
    @FXML
    private Label TheEnigmaMachine;
    @FXML
    private TextField filePath;
    @FXML
    private ScrollPane mainScrolll;


    final FileChooser fc = new FileChooser();
private List<Integer> lstnumrotors;
    private List<Character> lstpos;
    private Set<Character> plugSet;
    private int selcetedreflector;
    private   Map<Character, Character> plugBoard;



    @FXML
    public void initialize() {

        if (codeConfigurationComponentController != null) {
            codeConfigurationComponentController.setMainScreensController(this);
            codeConfigurationComponentController.getSepOne().setVisible(false);
            codeConfigurationComponentController.getSepTwo().setVisible(false);
            codeConfigurationComponentController.initializeButtons();


        }
        if (currentCodeComponentController != null) {
            currentCodeComponentController.setMainScreensController(this);
            currentCodeComponentController.getSepOne().setVisible(false);
            currentCodeComponentController.getSepTwo().setVisible(false);
            currentCodeComponentController.getOldCodeButton().setVisible(false);

        }
        if (currentCodeComponent3Controller != null) {
            currentCodeComponent3Controller.setMainScreensController(this);
            currentCodeComponent3Controller.getSepOne().setVisible(false);
            currentCodeComponent3Controller.getSepTwo().setVisible(false);
            currentCodeComponent3Controller.getOldCodeButton().setVisible(false);

        }
        if(encryptDecryptComponentController!=null){
           encryptDecryptComponentController.setMainScreensController(this);
        }
        if(dMoperationalComponentController!=null){
            dMoperationalComponentController.setMainScreensController(this);
        }
        if(bruteForceProgressComponentController!=null){
            bruteForceProgressComponentController.setMainScreensController(this);
            bruteForceProgressComponentController.initialize();
        }

       plugBoard = new HashMap<>();

    }

    @FXML
    void clickButtonLoadFileAction(ActionEvent event) {
        File file = fc.showOpenDialog(null);
        filePath.appendText(file.getAbsolutePath());

        try {
            if (!file.getAbsolutePath().endsWith(".xml")) {
                throw new NotXmlFile(file.getAbsolutePath());
            }
            //MainEngine.loadXmlFile(file.getAbsolutePath());
            plugSet=new HashSet<>();
            for (int i = 0; i < MainEngine.getKeyboard().length(); i++) {
                plugSet.add(MainEngine.getKeyboard().charAt(i));
            }
            codeConfigurationComponentController.getRandomCodeButton().setDisable(false);
            codeConfigurationComponentController.getSetRotors().setDisable(false);
            EngineUI returnObj = MainEngine.CreateReturnObj();
            //machineDetailsComponentController.setMachineDetails(returnObj);
            dMoperationalComponentController.initializeDMopertional();
            dMoperationalComponentController.setTrie();


        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }


    }
    public  DMoperationalController getDMoperationalController(){
        return dMoperationalComponentController;
    }

    public CurrCode Random() {
        int selectedreflrctor;
        List<Integer> lstnumrotors = new ArrayList<>();
        List<Character> lstpos = new ArrayList<>();
        Map<Character, Character> AutoPlugs = new HashMap<>();
        String MachineABC = MainEngine.getKeyboard();
        Random random = new Random();
        int numofrotors = MainEngine.getUseRotors();

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= MainEngine.getLstOfRotors().size(); i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i = 0; i < numofrotors; i++) {
            lstnumrotors.add(list.get(i));
        }
        for (int i = 0; i < numofrotors; i++) {
            Character startpos = (MachineABC.charAt(random.nextInt(MachineABC.length())));//set the start pos in each rotor
            lstpos.add(startpos);
        }
        if (MainEngine.getlstOfReflector().size() == 1) {
            selectedreflrctor = 1;
        } else {
            selectedreflrctor = random.nextInt(MainEngine.getlstOfReflector().size() - 1) + 1;
        }
        int numofplugs = random.nextInt((MachineABC.length()) / 2 - 0) + 0;
        List<Character> listofmachineabc = new ArrayList<Character>();
        for (int i = 0; i < MachineABC.length(); i++) {
            listofmachineabc.add(MachineABC.charAt(i));
        }
        Collections.shuffle(listofmachineabc);
        for (int i = 0; i < numofplugs; i += 2) {
            AutoPlugs.put(listofmachineabc.get(i), listofmachineabc.get(i + 1));
            AutoPlugs.put(listofmachineabc.get(i + 1), listofmachineabc.get(i));
        }
        CurrCode tmpcode = new CurrCode(lstnumrotors, null, lstpos, selectedreflrctor, AutoPlugs);
        MainEngine.InitialCode(tmpcode);
    currentCodeComponentController.showCodeConfiguration(tmpcode,currentCodeComponentController);

        currentCodeComponentController.showCodeConfiguration(tmpcode,currentCodeComponent3Controller);

        return tmpcode;

    }

    public void setRotors(){
        Dialog<CurrCode> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Please select "+MainEngine.getUseRotors()+"rotors id: ");
        ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);

        ListSelectionView rotorsId = new ListSelectionView();
        for (int i = 0; i < MainEngine.getLstOfRotors().size(); i++) {
            rotorsId.getSourceItems().add(i, i + 1);
        }
        Node loginButton = dialog.getDialogPane().lookupButton(applyButtonType);
        dialog.getDialogPane().setContent(rotorsId);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyButtonType) {
                lstnumrotors = new ArrayList<>();
                lstnumrotors = SetListRotors(rotorsId);
                if(lstpos!=null) {
                    lstpos.clear();
                }
                if(plugBoard!=null) {
                    plugBoard.clear();
                    plugSet.clear();
                    for (int i = 0; i < MainEngine.getKeyboard().length(); i++) {
                        plugSet.add(MainEngine.getKeyboard().charAt(i));
                    }
                }
                if (lstnumrotors == null) {
                    dialog.close();
                    return null;
                }

            }return null;});

        Optional<CurrCode> result = dialog.showAndWait();
        if(!lstnumrotors.isEmpty()){
            codeConfigurationComponentController.getSetPositions().setDisable(false);
        }
        }
    public void setPositions(){
        Dialog<CurrCode> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Please select rotors positions: ");
        ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(MainEngine.getUseRotors());
        grid.setVgap(2);

        for (int i = 0; i < lstnumrotors.size(); i++) {
            Label label=new Label();
            ChoiceBox abc = new ChoiceBox();
            label.setText("Rotor "+lstnumrotors.get(i));
            for (int j = 0; j < MainEngine.getKeyboard().length(); j++) {
                abc.getItems().add(MainEngine.getKeyboard().charAt(j));
            }
            grid.add(label, 0, i);
            grid.add(abc,1, i);
        }
        Node loginButton = dialog.getDialogPane().lookupButton(applyButtonType);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyButtonType) {
                lstpos = new ArrayList<>(SetListPos(grid, lstnumrotors.size()*2));
            }return null;});

        Optional<CurrCode> result = dialog.showAndWait();
        if(!lstpos.isEmpty()){
            codeConfigurationComponentController.getSetReflector().setDisable(false);
        }
    }
    public void setReflector(){
        Dialog<CurrCode> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Please select reflector id: ");
        ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);
        ChoiceBox reflector = new ChoiceBox();

        for (int i = 0; i < MainEngine.getlstOfReflector().size(); i++) {

            reflector.getItems().add(IntToRom(i + 1));

        }

        Node loginButton = dialog.getDialogPane().lookupButton(applyButtonType);
        dialog.getDialogPane().setContent(reflector);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyButtonType) {
                 selcetedreflector = RomToInt((String) reflector.getValue());

            }return null;});

        Optional<CurrCode> result = dialog.showAndWait();
        codeConfigurationComponentController.getSetPlugs().setDisable(false);
        codeConfigurationComponentController.getSetManualCodeButton().setDisable(false);
    }
    public void setPlugs(){

        Dialog<CurrCode> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Please select 1 pair of plugs: ");
        ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);

        CheckComboBox plugsbox=new CheckComboBox<>();

        for (Character x:plugSet) {
            plugsbox.getItems().add(x);
        }

        Node loginButton = dialog.getDialogPane().lookupButton(applyButtonType);
        dialog.getDialogPane().setContent(plugsbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyButtonType) {
              SetMapPlugs(plugsbox);

            }return null;});

        Optional<CurrCode> result = dialog.showAndWait();

    }
    public CurrCode setManualCode() {
        CurrCode tmpcode = new CurrCode(lstnumrotors, null, lstpos, selcetedreflector, plugBoard);
        MainEngine.InitialCode(tmpcode);
        currentCodeComponentController.showCodeConfiguration(tmpcode, currentCodeComponentController);
        currentCodeComponentController.showCodeConfiguration(tmpcode, codeConfigurationComponentController.getCurrentCodeComponentController());
        currentCodeComponentController.showCodeConfiguration(tmpcode,currentCodeComponent3Controller);
    codeConfigurationComponentController.setButtonsInDisable();
        return MainEngine.getCurrCode();

    }


    public List<Integer> SetListRotors(ListSelectionView rotorsId) {
        try {
            if(rotorsId.getTargetItems().size()!=MainEngine.getUseRotors()){
                throw new NumOfRotorWrongException(rotorsId.getTargetItems().size());
            }
            List<Integer> lstnumrotors = new ArrayList<>();
            for (int i = 0; i < rotorsId.getTargetItems().size(); i++) {
                lstnumrotors.add(Integer.parseInt((rotorsId.getTargetItems().get(i)).toString()));

            }

            Collections.reverse(lstnumrotors);
            return lstnumrotors;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
     return null;
    }

    public List<Character> SetListPos(GridPane grid , int size) {
        List<Character> lstpos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if(i%2!=0) {
                ChoiceBox k = (ChoiceBox) grid.getChildren().get(i);
                Character selcetedpos = (Character) k.getValue();
                lstpos.add(selcetedpos);
            }
        }
        return lstpos;

    }

    public MachineDetailsController getMachineDetailsComponentController() {
        return machineDetailsComponentController;
    }

    public void SetMapPlugs(CheckComboBox plug) {
        try {

            if (plug.getCheckModel().getCheckedItems().size() == 2) {
                Character ch = (Character) plug.getCheckModel().getCheckedItems().get(0);
                Character ch1 = (Character) plug.getCheckModel().getCheckedItems().get(1);
                plugBoard.put(ch, ch1);
                plugBoard.put(ch1, ch);
                plugSet.remove(ch);
                plugSet.remove(ch1);
            }
            else{
                throw new PlugsNotPair();
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }


    public String CaseInsensitive(String str,Character x,int ind) {
        if (Character.isLetter(x)) {
            if (Character.isUpperCase(x)) {
                Character tmp = Character.toLowerCase(x);
                if (!(MainEngine.getMapKeyboard().containsKey(tmp))) {
                    throw new NotInABCException(x);
                }
                str = ConvertStr(tmp, ind, str);
            }
            if (Character.isLowerCase(x)) {
                Character tmp = Character.toUpperCase(x);
                if (!(MainEngine.getMapKeyboard().containsKey(tmp))) {
                    throw new NotInABCException(x);
                }
                str = ConvertStr(tmp, ind, str);
            }
            return str;
        } else {
            throw new NotInABCException(x);
        }
    }
    public String ConvertStr(char ch,int ind,String str){
        String newName = str.substring(0,ind)+ ch+ str.substring(ind+1);
        return newName;
    }

    public CodeConfigurationController getCodeConfigurationComponentController() {
        return codeConfigurationComponentController;
    }
    public CurrentCodeController getCurrentCodeComponentController(){
      return currentCodeComponentController;
    }

    public CurrentCodeController getCurrentCodeComponent3Controller(){
        return currentCodeComponent3Controller;
    }
public String EncryptTextManual(String Encryptchar){
        String DecryptStr=MainEngine.DecodeStrManual(Encryptchar);
        return DecryptStr;
}
public String EncryptTextAuto(String EncryptStr){
    long Startime=System.nanoTime();
        StringBuilder code=new StringBuilder(currentCodeComponentController.getSpecialCode());
        String DecryptStr=MainEngine.DecodeStr(EncryptStr);
    long Endtime=System.nanoTime();
    machineDetailsComponentController.setNumCommands(MainEngine.getCommandCount());
        encryptDecryptComponentController.getHistoryAndStatistics().setText(encryptDecryptComponentController.getHistoryAndStatistics().getText() + MainEngine.setHistorylst(code, EncryptStr, DecryptStr, Endtime - Startime) + "\n");

    return DecryptStr;
}
public void presentHistoryManual(String Encode,String Decode,long Startime,long Endtime){
    StringBuilder code=new StringBuilder(currentCodeComponentController.getSpecialCode());
    encryptDecryptComponentController.getHistoryAndStatistics().setText(encryptDecryptComponentController.getHistoryAndStatistics().getText()+MainEngine.setHistorylst(code,Encode,Decode,Endtime-Startime)+"\n");
}
public VBox getCurrentCodeComponent(){
        return currentCodeComponent;
}

public DMoperationalController getdMoperationalComponentController(){return dMoperationalComponentController;}
    public String IntToRom(int num) {
        switch (num) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            default:
                return "wrong!";
        }
    }
    public int RomToInt(String RomNum) {
        switch (RomNum) {
            case "I":
                return 1;
            case "II":
                return 2;
            case "III":
                return 3;
            case "IV":
                return 4;
            case "V":
                return 5;
            default:
                return 0;
        }
    }

}*/