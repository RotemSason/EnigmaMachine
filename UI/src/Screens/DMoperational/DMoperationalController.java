package Screens.DMoperational;

import Engine.CurrCode;
//import Screens.MainScreens.MainScreensController;
import UIException.NotInDictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

import static java.lang.Integer.parseInt;
/*
public class DMoperationalController implements Initializable {

    @FXML
    private Label Decrypt;
    @FXML
    private Label labelTask;


    @FXML
    private TextField Encrypt;
    @FXML
    private Button Process;

    @FXML
    private Slider sliderAgent;

    @FXML
    private ComboBox<String> levelComboBox;

    @FXML
    private TextField sizeTask;
    private MainScreensController mainScreensController;
    private Trie trie;
    @FXML
    private VBox VboxSkin;


    @FXML
    void clickDictionaryOptions(ActionEvent event) {
        Dialog<CurrCode> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Your dictionary options are: ");
        ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);
        ListView<String> Dictionarylst = new ListView<>();
        if (Encrypt.getText().isEmpty()) {
            for (String str : mainScreensController.MainEngine.getDictionary().getDictionary()) {
                Dictionarylst.getItems().add(str);
            }
        }
        else {
            List<String> lst = new ArrayList<>();

            lst = trie.findAllWordsForPrefix(Encrypt.getText().toUpperCase(), trie.getRoot());
            for (int i = 0; i < lst.size(); i++) {
                Dictionarylst.getItems().add(lst.get(i));
            }}
            Node loginButton = dialog.getDialogPane().lookupButton(applyButtonType);
            dialog.getDialogPane().setContent(Dictionarylst);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyButtonType) {
               Encrypt.setText(Dictionarylst.getSelectionModel().getSelectedItem());
                }

            return null;});
            Optional<CurrCode> result = dialog.showAndWait();

    }
    @FXML
    void clickShowTotalTask(ActionEvent event){
        if(sizeTask.getText().isEmpty()){
            sizeTask.setText("0");
        }
        else{
            if(levelComboBox.getValue().equals("Easy")){
                labelTask.setText(""+((int) Math.pow(mainScreensController.MainEngine.getABCsize(),mainScreensController.MainEngine.getNumOfRotorsDM()) / parseInt(sizeTask.getText())));
            }
            else if (levelComboBox.getValue().equals("Medium")) {
                labelTask.setText(""+(((int) Math.pow(mainScreensController.MainEngine.getABCsize(),mainScreensController.MainEngine.getNumOfRotorsDM()) / parseInt(sizeTask.getText()))*(mainScreensController.MainEngine.getNumOfRefDM())));
            }
            else if (levelComboBox.getValue().equals("Hard")) {
                labelTask.setText(""+(((int) Math.pow(mainScreensController.MainEngine.getABCsize(),mainScreensController.MainEngine.getNumOfRotorsDM()) / parseInt(sizeTask.getText()))*(mainScreensController.MainEngine.getNumOfRefDM())*Factorial(mainScreensController.MainEngine.getUseRotors())));
            }
            else {
                labelTask.setText(""+(((int) Math.pow(mainScreensController.MainEngine.getABCsize(),mainScreensController.MainEngine.getNumOfRotorsDM()) / parseInt(sizeTask.getText()))*(mainScreensController.MainEngine.getNumOfRefDM())*Factorial(mainScreensController.MainEngine.getUseRotors())*(Factorial(mainScreensController.MainEngine.getLstOfRotors().size())/(Factorial(mainScreensController.MainEngine.getUseRotors())*Factorial(mainScreensController.MainEngine.getLstOfRotors().size()-mainScreensController.MainEngine.getUseRotors())))));
            }
        }
    }
    @FXML
    void clickProcessButtonAction(ActionEvent event) {
        try {
            removeSpecialChars(Encrypt.getText(), mainScreensController.MainEngine.getDictionary().getSpecialChars());
            if ((searchInDictionary(Encrypt.getText().toUpperCase(), mainScreensController.MainEngine.getDictionary().getDictionary())) ||(searchInDictionary(Encrypt.getText().toLowerCase(), mainScreensController.MainEngine.getDictionary().getDictionary()))) {
                Decrypt.setText(mainScreensController.MainEngine.DecodeStr(Encrypt.getText().toUpperCase()));

                mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(mainScreensController.MainEngine.getCurrentCode(), mainScreensController.getCurrentCodeComponentController());
                mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(mainScreensController.MainEngine.getCurrentCode(), mainScreensController.getCurrentCodeComponent3Controller());
                mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(mainScreensController.MainEngine.getCurrentCode(), mainScreensController.getCodeConfigurationComponentController().getCurrentCodeComponentController());
            } else {
                throw new NotInDictionary();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void clearAll() {
        Encrypt.clear();
        Decrypt.setText("");
        levelComboBox.getEditor().clear();
        sliderAgent.setValue(2);
        sizeTask.clear();
    }


    public void setMainScreensController(MainScreensController mainScreensController) {
        this.mainScreensController = mainScreensController;
    }

    public void initializeDMopertional() {
        sliderAgent.setMin(2);
        sliderAgent.setMax(mainScreensController.MainEngine.getAgentsNumber());
        sliderAgent.setSnapToTicks(true);

    }

    @FXML
    void clickSliderAgent(MouseEvent event) {
        int numCurentAgent = (int) (sliderAgent.getValue());
    }

    public void initialize(URL location, ResourceBundle resources) {
        levelComboBox.getItems().add(0, "Easy");
        levelComboBox.getItems().add(1, "Medium");
        levelComboBox.getItems().add(2, "Hard");
        levelComboBox.getItems().add(3, "Expert");

    }

    public int getSizeTask() {
        return parseInt(sizeTask.getText());
    }

    public String getStrToDec() {
        return Decrypt.getText();
    }

    public int getCurrentAgent() {
        return (int) (sliderAgent.getValue());
    }

    public String getLevel(){
        return levelComboBox.getValue();

    }

    public void setTrie() {
        trie = new Trie();
        for (String x : mainScreensController.MainEngine.getDictionary().getDictionary()) {
            trie.add(x);
        }
    }


        public boolean searchInDictionary(String checkInDictionary, Set<String> Dictionary) {
            boolean entertofor = false;
            for (String str : checkInDictionary.split(" ")) {
                entertofor = true;
                if (!(Dictionary.contains(str) || Dictionary.contains(str.toLowerCase()))) {
                    return false;
                }
            }
            if (!entertofor) {
                return false;
            }
            return true;
        }
    public void removeSpecialChars(String encrypt, List<Character> specialChars){
        for (int i = 0; i < encrypt.length(); i++) {
            if(specialChars.contains(encrypt.charAt(i))){
              Encrypt.setText( encrypt.substring(0,i)+  encrypt.substring(i+1));
            }
        }


    }
    public int Factorial(int n){
        int result=1;
        while(n>1){
            result=result*n;
            n=n-1;
        }
        return result;
    }

}

 */