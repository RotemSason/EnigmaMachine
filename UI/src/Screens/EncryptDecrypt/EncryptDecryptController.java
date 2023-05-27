package Screens.EncryptDecrypt;
//import Agent.DM;
//import Screens.MainScreens.MainScreensController;
import UIException.CodeNotExist;
import UIException.NotEnterStr;
import UIException.NotInABCException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
/*
public class EncryptDecryptController implements Initializable {
    @FXML
    private CheckBox EncodeStringManual;
    @FXML
    private HBox hboxAll;

    @FXML
    private CheckBox EncodeStringAuto;
    @FXML
    private Button ProcessButton;
    private MainScreensController mainScreensController;
    @FXML
    private TextField EncryptText;
    @FXML
    private Button ClickClearButton;

    @FXML
    private TextArea DecryptText;

    @FXML
    private Button DoneButton;

    @FXML
    private TextField EncryptAuto;

    @FXML
    private TextArea DecryptAuto;

    @FXML
    private Button ResetButton;
    @FXML
    private TextArea HistoryAndStatistics;

    private long Startime;
    private long Endtime;

    @FXML
    private ChoiceBox<String> ChoiceBoxEncode;





    @FXML
    void clickDoneButtonAction(ActionEvent event) {
         Endtime=System.nanoTime();
         mainScreensController.presentHistoryManual(EncryptText.getText(),DecryptText.getText(),Startime,Endtime);
         EncryptText.clear();
         DecryptText.setText("");
mainScreensController.MainEngine.setCommandCount();
mainScreensController.MainEngine.setCurrentCodeFlag();
        mainScreensController.getMachineDetailsComponentController().setNumCommands(mainScreensController.MainEngine.getCommandCount());
    }
    @FXML
    void clickResetButton(ActionEvent event){
        EncryptText.setText("");
        EncryptAuto.setText("");
        DecryptText.clear();
        DecryptAuto.clear();
        mainScreensController.MainEngine.ResetMachine();
        mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(mainScreensController.MainEngine.getCurrCode(),mainScreensController.getCurrentCodeComponentController());
        mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(mainScreensController.MainEngine.getCurrCode(),mainScreensController.getCurrentCodeComponent3Controller());
        mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(mainScreensController.MainEngine.getCurrCode(),mainScreensController.getCodeConfigurationComponentController().getCurrentCodeComponentController());
    }
    @FXML
    void clickProcessButtonAction(ActionEvent event) {
        try{
            if(EncryptAuto.getText().isEmpty()){
                throw new NotEnterStr();
            }
        for (int i = 0; i < EncryptAuto.getText().length(); i++) {
            if (!mainScreensController.MainEngine.getMapKeyboard().containsKey(EncryptAuto.getText().charAt(i))) {
                EncryptAuto.setText(CaseInsensitive(EncryptAuto.getText(),EncryptAuto.getText().charAt(i),i));
            }
        }
DecryptAuto.setText(mainScreensController.EncryptTextAuto(EncryptAuto.getText()));
        mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(mainScreensController.MainEngine.getCurrentCode(),mainScreensController.getCurrentCodeComponentController());
        mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(mainScreensController.MainEngine.getCurrentCode(),mainScreensController.getCurrentCodeComponent3Controller());
        mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(mainScreensController.MainEngine.getCurrentCode(),mainScreensController.getCodeConfigurationComponentController().getCurrentCodeComponentController());

    } catch (Exception e) {
            EncryptAuto.clear();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(e.getMessage());


        alert.showAndWait();
    }}
    @FXML
    void setKeyReleased(KeyEvent event) {
        try {

            DoneButton.setDisable(true);
            String s = EncryptText.getText();
            int len = s.length();
            if (!mainScreensController.MainEngine.getMapKeyboard().containsKey(s.charAt(len-1))) {
                if(len==1){
                    s=CaseInsensitive(EncryptText.getText(), s.charAt(0),len-1);
                }
                else {
                   s=CaseInsensitive(EncryptText.getText(), s.charAt(len - 1), len - 1);
                }

            }

            if (s.length() == 1) {
                Startime = System.nanoTime();

            }
            DoneButton.setDisable(false);
                DecryptText.setText(DecryptText.getText() + mainScreensController.EncryptTextManual(String.valueOf(s.charAt(len - 1))));
                mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(mainScreensController.MainEngine.getCurrentCode(), mainScreensController.getCurrentCodeComponentController());
                mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(mainScreensController.MainEngine.getCurrentCode(), mainScreensController.getCurrentCodeComponent3Controller());
            mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(mainScreensController.MainEngine.getCurrentCode(),mainScreensController.getCodeConfigurationComponentController().getCurrentCodeComponentController());

        } catch (Exception e) {
            EncryptText.clear();
            DecryptText.setText("");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(e.getMessage());


            alert.showAndWait();
        }
    }
    public void setMainScreensController(MainScreensController mainScreensController) {
        this.mainScreensController = mainScreensController;
    }
    public TextArea getHistoryAndStatistics(){
        return HistoryAndStatistics;
    }
    @FXML
    void clickClearButton(ActionEvent event){
        EncryptAuto.clear();
        DecryptAuto.setText("");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ChoiceBoxEncode.getItems().add(0,"Manual");
        ChoiceBoxEncode.getItems().add(1,"Auto");
  ChoiceBoxEncode.valueProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
          if(ChoiceBoxEncode.getValue().equals("Manual")){
EncryptText.setDisable(false);
//DoneButton.setDisable(false);
EncryptAuto.setDisable(true);
ProcessButton.setDisable(true);
ClickClearButton.setDisable(true);
          }
          else {
              EncryptText.setDisable(true);
              DoneButton.setDisable(true);
              EncryptAuto.setDisable(false);
              ProcessButton.setDisable(false);
              ClickClearButton.setDisable(false);
          }
      }
  });


            }

    public String CaseInsensitive(String str,Character x,int ind) {
        if (Character.isLetter(x)) {
            if (Character.isUpperCase(x)) {
                Character tmp = Character.toLowerCase(x);
                if (!(mainScreensController.MainEngine.getMapKeyboard().containsKey(tmp))) {
                    throw new NotInABCException(x);
                }
                str = ConvertStr(tmp, ind, str);
            }
            if (Character.isLowerCase(x)) {
                Character tmp = Character.toUpperCase(x);
                if (!(mainScreensController.MainEngine.getMapKeyboard().containsKey(tmp))) {
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
        };

*/


