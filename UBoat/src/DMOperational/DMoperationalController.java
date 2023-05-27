package DMOperational;

import EngineUI.CurrCode;
import MainScreensUBoat.ContestUBoatComponentController;
import MainScreensUBoat.MainScreensUBoatController;
import UBoatException.NotInDictionary;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import okhttp3.*;

import java.util.*;

import static Configuration.Configuration.*;
import static java.lang.Integer.parseInt;

public class DMoperationalController {

    @FXML
    private Label Decrypt;

    @FXML
    private TextField Encrypt;
    @FXML
    private Button Process;


    private ContestUBoatComponentController mainScreensController;
    private Trie trie;
    @FXML
    private VBox VboxSkin;


    @FXML
    void clickDictionaryOptions(ActionEvent event) {
        Dialog<CurrCode> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Your dictionary options are: ");
        ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);
        ListView<String> Dictionarylst = new ListView<>();
        if (Encrypt.getText().isEmpty()) {
            for (String str : mainScreensController.getMainScreensUBoatController().getReturnObj().getDictionary().getDictionary()) {
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
    void clickProcessButtonAction(ActionEvent event) {
        try {
            removeSpecialChars(Encrypt.getText(), mainScreensController.getMainScreensUBoatController().getReturnObj().getDictionary().getSpecialChars());
            if ((searchInDictionary(Encrypt.getText().toUpperCase(), mainScreensController.getMainScreensUBoatController().getReturnObj().getDictionary().getDictionary())) ||(searchInDictionary(Encrypt.getText().toLowerCase(), mainScreensController.getMainScreensUBoatController().getReturnObj().getDictionary().getDictionary()))) {
                String RESOURCE = "/encode-string";
                Gson gson=new Gson();
                String body="encode="+gson.toJson(Encrypt.getText().toUpperCase());

                String finalUrl = HttpUrl
                        .parse(BASE_URL+RESOURCE)
                        .newBuilder()
                        .addQueryParameter(CONTEST_NAME, mainScreensController.getContestName())
                        .build()
                        .toString();

                Request request = new Request.Builder()
                        .url(finalUrl)
                        // .addHeader("Content-Type", "text/plain")
                        .post(RequestBody.create(body.getBytes()))
                        .build();

                Call call = HTTP_CLIENT.newCall(request);

                Response response = call.execute();
                Gson gson2=new Gson();
                Properties prop = new Properties();
                prop.load(response.body().byteStream());


                String currCode=prop.getProperty("currCode");
                String decode = prop.getProperty("decode");
                CurrCode dto=gson2.fromJson(currCode, CurrCode.class);
                Decrypt.setText(decode);

                mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(dto, mainScreensController.getCurrentCodeComponentController());
               // mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(dto, mainScreensController.getCurrentCodeComponent3Controller());
               // mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(dto, mainScreensController.getCodeConfigurationComponentController().getCurrentCodeComponentController());
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
    }


    public void setMainScreensController(ContestUBoatComponentController mainScreensController) {
        this.mainScreensController = mainScreensController;
    }

    public String getStrToDec() {
        return Decrypt.getText();
    }



    public void setTrie() {
        trie = new Trie();
        for (String x : mainScreensController.getMainScreensUBoatController().returnObj.getDictionary().getDictionary()) {
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
    public void clearEncrypyDecrypt(){
        Encrypt.setText("");
        Decrypt.setText("");
    }

}