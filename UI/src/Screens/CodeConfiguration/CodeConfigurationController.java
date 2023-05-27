package Screens.CodeConfiguration;
import Engine.CurrCode;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.*;
/*
public class CodeConfigurationController {

        private MainScreensController mainScreensController;
        @FXML
        private VBox currentCodeComponent;
        @FXML
        private CurrentCodeController currentCodeComponentController;

        @FXML
        private Button RandomCodeButton;
        @FXML
        private Button oldCodeButton;
        @FXML
        private Button SetManualCodeButton;
        @FXML
        private Button setRotors;
        @FXML
        private Button setPositions;
        @FXML
        private Button setReflector;
        @FXML
        private Button setPlugs;
        @FXML
        private TableView<?> TableCode;
        @FXML
        private TableColumn<?, ?> cell;
        @FXML
        private ImageView imageGear;
        @FXML
        private HBox HboxCodeConf;


        @FXML
        private Label reflector;

        @FXML
        private Label plugs;


        private String oldcode;

        @FXML
        private HBox hboxGears;
        @FXML
        private Separator sepOne;
        @FXML
        private Separator sepTwo;
        @FXML
        private CheckBox isAnimations;


        public void initializeButtons(){
                oldCodeButton.setVisible(false);
                currentCodeComponentController.getSepOne().setVisible(false);
                currentCodeComponentController.getSepTwo().setVisible(false);
                currentCodeComponentController.getOldCodeButton().setVisible(false);
                RandomCodeButton.setDisable(true);
                SetManualCodeButton.setDisable(true);
                setRotors.setDisable(true);
                setReflector.setDisable(true);
                setPlugs.setDisable(true);
                setPositions.setDisable(true);


        }
public Separator getSepOne(){return sepOne;}
        public Separator getSepTwo(){return sepTwo;}
        public Button getOldCodeButton(){return oldCodeButton;}
        public void setMainScreensController(MainScreensController mainScreensController) {
                this.mainScreensController = mainScreensController;
        }

        @FXML
        void clickButtonManualCodeAction(ActionEvent event) {

                CurrCode code = mainScreensController.setManualCode();
                if(code!=null) {
                        showCodeConfiguration2(code);
                }
        }
        @FXML
        void clickCheckBocAnimations(ActionEvent event){

        }
        @FXML
        void clickSetRotorsButton(ActionEvent event) {
                mainScreensController.setRotors();

        }
        @FXML
        void clickSetReflectorButton(ActionEvent event) {
        mainScreensController.setReflector();
        }
        @FXML
        void cllicksetPositionsButton(ActionEvent event) {
                mainScreensController.setPositions();
        }
        @FXML
        void clickSetPlugsButton (ActionEvent event) {
        mainScreensController.setPlugs();
        }
        @FXML
        void clickButtunOldCode(ActionEvent event) {

                Dialog<CurrCode> dialog = new javafx.scene.control.Dialog<>();
                dialog.setTitle("Code Configuration Dialog");
                dialog.setHeaderText("Code console view: ");
                ButtonType closeButtonType = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(closeButtonType);
                Label l=new Label(oldcode);
                l.setStyle("-fx-font-weight: bold");
                dialog.getDialogPane().setContent(l);

                Optional<CurrCode> result = dialog.showAndWait();


        }




        @FXML
        void clickButtonRandomCodeAction(ActionEvent event) {
                CurrCode code = mainScreensController.Random();
                showCodeConfiguration2(code);

               mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(code,currentCodeComponentController);


        }

        public void showCodeConfiguration2(CurrCode code) {
        sepOne.setVisible(true);
        sepTwo.setVisible(true);
        oldCodeButton.setVisible(true);
        getCurrentCodeComponentController().getSepOne().setVisible(true);
        getCurrentCodeComponentController().getSepTwo().setVisible(true);
        getCurrentCodeComponentController().getOldCodeButton().setVisible(true);

                hboxGears.getChildren().clear();

                Image Gear = new Image(getClass().getResourceAsStream("Gear3.png"));


                int size = code.getSelectedRotors().size();
                for (int i = size-1; i >=0 ; i--) {
                        ImageView imageView = new ImageView(Gear);
                        imageView.setImage(Gear);
                        imageView.setFitHeight(80);
                        imageView.setFitWidth(80);

                        Text inftx = new Text("Id: " + code.getSelectedRotors().get(i) + "\n" + "Notch: " + code.getCurrNotch().get(i) + "\n" + "Pos: " + code.getSelectedPos().get(i));
                       inftx.setStyle("-fx-font: 12 Ariel");
                        StackPane pane = new StackPane();
                        pane.getChildren().add(imageView);
                        pane.getChildren().add(inftx);

                        pane.setAlignment(Pos.CENTER);
                        hboxGears.getChildren().add(size-(i+1), pane);
                }
                reflector.setText("Reflector: " + code.IntToRom(code.getSelectedReflector()));
                reflector.setStyle(" -fx-font-weight: bold");
                plugs.setText("Plugs: ");
                Map<Character, Character> tmpmap = new HashMap<>();
                tmpmap.putAll(code.getMapSelctesPlugs());
                int count = 0;
                for (Map.Entry<Character, Character> entry : tmpmap.entrySet()) {
                        if (entry.getValue() != null) {
                                plugs.setText(plugs.getText() + entry.getKey() + "|" + entry.getValue() + " ");
                        }
                        tmpmap.replace(entry.getValue(), entry.getKey(), null);
                }
                plugs.setStyle(" -fx-font-weight: bold");
                StringBuilder CurrentCodeStr=BuildCodeConf(code.getSelectedRotors(), code.getCurrNotch(), code.getSelectedPos(),code.IntToRom(code.getSelectedReflector()), code.getMapSelctesPlugs());
                oldcode="" +CurrentCodeStr;
hboxGears.setSpacing(5);
                getCurrentCodeComponentController().getHboxGears().setSpacing(5);
                mainScreensController.getCurrentCodeComponent3Controller().getHboxGears().setSpacing(5);

        }

        public StringBuilder BuildCodeConf(List<Integer>SelectedRotors,List<Integer>currNotch,List<Character>SelectedPos,String SelectedReflector,Map<Character, Character> SelctesPlugs) {
                StringBuilder codeStr = new StringBuilder();
                codeStr.append('<');
                for (int i = SelectedRotors.size()-1; i >=0; i--) {
                        Integer rotorid = SelectedRotors.get(i);
                        Integer notch = currNotch.get(i);
                        if (i !=  SelectedRotors.size()-1) {
                                codeStr.append(",");
                                codeStr.append(rotorid);
                                codeStr.append('(');
                                codeStr.append(notch);
                                codeStr.append(')');
                        } else {
                                codeStr.append(rotorid);
                                codeStr.append('(');
                                codeStr.append(notch);
                                codeStr.append(')');
                        }
                }
                codeStr.append('>');
                codeStr.append('<');
                for (int i =SelectedPos.size()-1; i >=0 ; i--) {
                        codeStr.append(SelectedPos.get(i));
                }
                codeStr.append('>');
                codeStr.append('<');
                codeStr.append(SelectedReflector);
                codeStr.append('>');
                if(SelctesPlugs.size()!=0) {
                        codeStr.append('<');
                        Map<Character, Character> tmpmap = new HashMap<>();
                        tmpmap.putAll(SelctesPlugs);
                        int count = 0;
                        for (Map.Entry<Character, Character> entry : tmpmap.entrySet()) {
                                if (entry.getValue() != null) {
                                        if (count != 0) {
                                                codeStr.append(',');
                                                codeStr.append(entry.getKey());
                                                codeStr.append('|');
                                                codeStr.append(entry.getValue());

                                        } else {
                                                codeStr.append(entry.getKey());
                                                codeStr.append('|');
                                                codeStr.append(entry.getValue());
                                        }
                                        tmpmap.replace(entry.getValue(), entry.getKey(), null);
                                        count++;
                                }

                        }
                        codeStr.append('>');
                }
                return codeStr;
        }

        public CurrentCodeController getCurrentCodeComponentController(){
                return currentCodeComponentController;
        }

        public Button getRandomCodeButton() {
                return RandomCodeButton;
        }

        public Button getSetManualCodeButton() {
                return SetManualCodeButton;
        }

        public Button getSetPlugs() {
                return setPlugs;
        }

        public Button getSetPositions() {
                return setPositions;
        }

        public Button getSetRotors() {
                return setRotors;
        }

        public Button getSetReflector() {
                return setReflector;
        }
        public void setButtonsInDisable(){
                setReflector.setDisable(true);
                setPlugs.setDisable(true);
                setPositions.setDisable(true);
        }
        public CheckBox getIsAnimations(){return isAnimations;}

}*/
