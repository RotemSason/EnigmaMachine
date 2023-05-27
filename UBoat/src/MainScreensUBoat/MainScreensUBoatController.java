package MainScreensUBoat;

import AlliesDetails.AlliesDetailsController;
import CurrentCode.CurrentCodeController;
import DMOperational.DMoperationalController;
import EngineUI.CurrCode;
import EngineUI.EngineUI;
import MachineDetailsUBoat.MachineDetailsUBoatController;
import SuccessCodeDetails.SuccessCodeDetailsController;
import codeConfiguration.codeConfigurationController;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import okhttp3.*;
import org.controlsfx.control.ListSelectionView;
import winContest.WinContestController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static Configuration.Configuration.*;

public class MainScreensUBoatController {

    @FXML
        private AnchorPane machineDetailsComponent;
        @FXML
        private HBox encryptDecryptComponent;


        @FXML
        private FlowPane bruteForceProgressComponent;

        @FXML
        private HBox codeConfigurationComponent;
        @FXML
        private VBox currentCodeComponent;
        @FXML
        private VBox vboxLoadFile;
        @FXML
        private VBox vboxMainScreens;
        @FXML
        private TabPane tapPane;
    @FXML
    private Label UBoatTitle;




        @FXML
        private MachineDetailsUBoatController machineDetailsComponentController;
        @FXML
        private codeConfigurationController codeConfigurationComponentController;
        public EngineUI returnObj;
        @FXML
        private Button loadFileButton;
        @FXML
        private Label TheEnigmaMachine;
        @FXML
        private TextField filePath;
        @FXML
        private ScrollPane mainScrolll;


    @FXML
    private TextField UBoatNameId;
    @FXML
    private HBox hboxLogin;
    @FXML
    private Label errorMessageLabel;
    @FXML private AnchorPane mainPanelUBoat;

private Parent ContestUBoatComponent;

    private ContestUBoatComponentController contestUBoatComponentController;
        final FileChooser fc = new FileChooser();
        private List<Integer> lstnumrotors;
        private List<Character> lstpos;
        private Set<Character> plugSet;
        private int selcetedreflector;
        private Map<Character, Character> plugBoard;
        private String contestName;
    private final StringProperty errorMessageProperty = new SimpleStringProperty();


        @FXML
        public void initialize() {
            errorMessageLabel.textProperty().bind(errorMessageProperty);
            loadContestPage();

            if (codeConfigurationComponentController != null) {
                codeConfigurationComponentController.setMainScreensController(this);
              //  codeConfigurationComponentController.getSepOne().setVisible(false);
               // codeConfigurationComponentController.getSepTwo().setVisible(false);
                codeConfigurationComponentController.initializeButtons();

            }


           // tapPane.setVisible(false);
            loadFileButton.setVisible(false);
            filePath.setVisible(false);
            plugBoard = new HashMap<>();

        }
        public TextField getfilePath(){
            return filePath;
        }

        @FXML
        void clickButtonLoadFileAction(ActionEvent event) {
            File file = fc.showOpenDialog(null);
            filePath.appendText(file.getAbsolutePath());

            try {
                /*if (!file.getAbsolutePath().endsWith(".xml")) {
                    throw new NotXmlFile(file.getAbsolutePath());
                }*/
                String RESOURCE = "/upload-file";

                String finalUrl = HttpUrl
                        .parse(BASE_URL+RESOURCE)
                        .newBuilder()
                        .addQueryParameter("contestname",contestName)
                        .build()
                        .toString();

                RequestBody body =
                        new MultipartBody.Builder()
                                .addFormDataPart("file1", file.getName(), RequestBody.create(file, MediaType.parse("text/plain")))
                                //.addFormDataPart("key1", "value1") // you can add multiple, different parts as needed
                                .build();

                Request request = new Request.Builder()
                        .url(finalUrl)
                        .post(body)
                        .build();
                Call call = HTTP_CLIENT.newCall(request);

                Response response = call.execute();

                Gson gson=new Gson();
                String dtoString=response.body().string();
                EngineUI dto=gson.fromJson(dtoString, EngineUI.class);
               returnObj=dto;
                plugSet=new HashSet<>();

                /*for (int i = 0; i <( returnObj.getKeyboard().getABC().length()); i++) {
                    plugSet.add(returnObj.getKeyboard().charAt(i));
                }*/
                codeConfigurationComponentController.getRandomCodeButton().setDisable(false);
                codeConfigurationComponentController.getSetRotors().setDisable(false);

                machineDetailsComponentController.setMachineDetails(dto);
                contestUBoatComponentController.getdMoperationalComponentController().setTrie();


            } catch (Exception e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText(e.getMessage());
                alert.showAndWait();
            }
contestUBoatComponentController.getLogoutButton().setVisible(false);

        }


    @FXML
    void clickButtonloginAction (ActionEvent event) throws IOException {
            contestName = UBoatNameId.getText();
        UBoatTitle.setText(contestName);
        contestUBoatComponentController.getUBoatTitle().setText("Welcome To Contest: "+contestName);
        if (contestName.isEmpty()) {
            //errorMessageProperty.set("User name is empty. You can't login with empty user name");
            return;
        }

        String finalUrl = HttpUrl
                .parse(BASE_URL+"/login")
                .newBuilder()
                .addQueryParameter("contestname", contestName)
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();
        Call call = HTTP_CLIENT.newCall(request);

        Response response = call.execute();

            if (response.code() != 200) {
                String responseBody = response.body().string();
                Platform.runLater(() ->
                        errorMessageProperty.set("Something went wrong: " + responseBody)
                );
            } else {
                Platform.runLater(() -> {

                    this.contestName=contestName;
                    loadFileButton.setVisible(true);
                    filePath.setVisible(true);
                    hboxLogin.setVisible(false);
                    errorMessageProperty.set("");
                    contestUBoatComponentController.getDetailsAlliesComponentController().startListRefresher();

                });
            }

        }




        public  DMoperationalController getDMoperationalController(){
            return contestUBoatComponentController.getdMoperationalComponentController();
        }

        public void setRotors(){
            Dialog<CurrCode> dialog = new javafx.scene.control.Dialog<>();
            dialog.setTitle("Login Dialog");
            dialog.setHeaderText("Please select "+returnObj.getUseRotors()+"rotors id: ");
            ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);

            ListSelectionView rotorsId = new ListSelectionView();
            for (int i = 0; i < returnObj.getLstOfRotors().size(); i++) {
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
                        for (int i = 0; i < returnObj.getKeyboardABC().length(); i++) {
                            plugSet.add(returnObj.getKeyboardABC().charAt(i));
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
            grid.setHgap(returnObj.getUseRotors());
            grid.setVgap(2);

            for (int i = 0; i < lstnumrotors.size(); i++) {
                Label label=new Label();
                ChoiceBox abc = new ChoiceBox();
                label.setText("Rotor "+lstnumrotors.get(i));
                for (int j = 0; j < returnObj.getKeyboardABC().length(); j++) {
                    abc.getItems().add(returnObj.getKeyboardABC().charAt(j));
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

            for (int i = 0; i < returnObj.getLstOfReflector().size(); i++) {

                reflector.getItems().add(IntToRom(i + 1));

            }

            Node loginButton = dialog.getDialogPane().lookupButton(applyButtonType);
            dialog.getDialogPane().setContent(reflector);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == applyButtonType) {
                    selcetedreflector = RomToInt((String) reflector.getValue());

                }return null;});

            Optional<CurrCode> result = dialog.showAndWait();
            //codeConfigurationComponentController.getSetPlugs().setDisable(false);
            codeConfigurationComponentController.getSetManualCodeButton().setDisable(false);
        }

        public CurrCode setManualCode() throws IOException {
            CurrCode tmpcode = new CurrCode(lstnumrotors, null, lstpos, selcetedreflector, plugBoard);
            String RESOURCE = "/manual-code";
            Gson gson=new Gson();
            String body="json="+gson.toJson(tmpcode);
            String finalUrl = HttpUrl
                    .parse(BASE_URL+RESOURCE)
                    .newBuilder()
                    .addQueryParameter(CONTEST_NAME, contestName)
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
            String dtoString=response.body().string();
            CurrCode dto=gson2.fromJson(dtoString, CurrCode.class);

            contestUBoatComponentController.getCurrentCodeComponentController().showCodeConfiguration(dto, contestUBoatComponentController.getCurrentCodeComponentController());
          //  currentCodeComponentController.showCodeConfiguration(tmpcode, codeConfigurationComponentController.getCurrentCodeComponentController());
          //  currentCodeComponentController.showCodeConfiguration(tmpcode,currentCodeComponent3Controller);
            codeConfigurationComponentController.setButtonsInDisable();
            return dto;

        }
        public List<Integer> SetListRotors(ListSelectionView rotorsId) {
            try {
                /*if(rotorsId.getTargetItems().size()!=MainEngine.getUseRotors()){
                    throw new NumOfRotorWrongException(rotorsId.getTargetItems().size());
                }*/
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

        public MachineDetailsUBoatController getMachineDetailsComponentController() {
            return machineDetailsComponentController;
        }

        public codeConfigurationController getCodeConfigurationComponentController() {
            return codeConfigurationComponentController;
        }
       public CurrentCodeController getCurrentCodeComponentController(){
            return contestUBoatComponentController.getCurrentCodeComponentController();
        }

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
        public EngineUI getReturnObj(){return returnObj;}
public String getContestName(){return contestName;}
public SuccessCodeDetailsController getSuccessCodeDetailsComponentController(){
            return contestUBoatComponentController.getSuccessCodeDetailsComponentController();
}


    public void switchToContest() {
        setMainPanelTo(ContestUBoatComponent);
       // contestUBoatComponentController.setActive();
    }

    public void setMainPanelTo(Parent pane) {
        mainPanelUBoat.getChildren().clear();
        mainPanelUBoat.getChildren().add(pane);
        AnchorPane.setBottomAnchor(pane, 1.0);
        AnchorPane.setTopAnchor(pane, 1.0);
        AnchorPane.setLeftAnchor(pane, 1.0);
        AnchorPane.setRightAnchor(pane, 1.0);
    }

    private void loadContestPage() {
        URL loginPageUrl = getClass().getResource(CONTEST_ROOM_FXML_RESOURCE_LOCATION);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            ContestUBoatComponent = fxmlLoader.load();
            contestUBoatComponentController = fxmlLoader.getController();
            contestUBoatComponentController.setMainScreenUBoatController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ScrollPane getMainScrolll() {
        return mainScrolll;
    }
}
