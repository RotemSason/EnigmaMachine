package CurrentCode;

import EngineUI.CurrCode;
import MainScreensUBoat.ContestUBoatComponentController;
import MainScreensUBoat.MainScreensUBoatController;
import javafx.animation.RotateTransition;
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
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;



    public class CurrentCodeController {
        @FXML
        private VBox vboxCurrentCode;
        private ContestUBoatComponentController mainScreensController;

        @FXML
        private HBox hboxGears;

        @FXML
        private Label reflector;

        @FXML
        private Label plugs;


        private String specialCode;
        @FXML
        private Button oldCodeButton;
        @FXML
        private Separator sepOne;
        @FXML
        private Separator sepTwo;

        @FXML
        void clickOldColdButton(ActionEvent event) {
            Dialog<CurrCode> dialog = new javafx.scene.control.Dialog<>();
            dialog.setTitle("Code Configuration Dialog");
            dialog.setHeaderText("Code console view: ");
            ButtonType closeButtonType = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(closeButtonType);
            Label l=new Label(specialCode);
            l.setStyle("-fx-font-weight: bold");
            dialog.getDialogPane().setContent(l);

            Optional<CurrCode> result = dialog.showAndWait();

        }

        public void setMainScreensController(ContestUBoatComponentController mainScreensController) {
            this.mainScreensController = mainScreensController;
        }

        public HBox getHboxGears(){
            return hboxGears;
        }

        public Label getPlugs() {
            return plugs;
        }

        public Label getReflector() {
            return reflector;
        }
        public String getspecialCode(){
            return specialCode;
        }

        public void setHboxGears(HBox hboxGears) {
            this.hboxGears = hboxGears;
        }

        public void setPlugs(Label plugs) {
            this.plugs = plugs;
        }

        public void setReflector(Label reflector) {
            this.reflector = reflector;
        }

        public void setSpecialCode(String specialCode) {
            this.specialCode = specialCode;
        }


        public void showCodeConfiguration(CurrCode code,CurrentCodeController tmpCurrentCode) {
            sepOne.setVisible(true);
            sepTwo.setVisible(true);
            oldCodeButton.setVisible(true);
            tmpCurrentCode.getHboxGears().getChildren().clear();
            Image Gear = new Image(getClass().getResourceAsStream("Gear3.png"));


            int size = code.getSelectedRotors().size();
            for (int i = size-1; i >=0; i--) {
                ImageView imageView = new ImageView(Gear);
                imageView.setImage(Gear);
                imageView.setFitHeight(75);
                imageView.setFitWidth(75);
                Text inftx = new Text("Id: " + code.getSelectedRotors().get(i) + "\n" + "Notch: " + code.getCurrNotch().get(i) + "\n" + "Pos: " + code.getSelectedPos().get(i));
                inftx.setStyle("-fx-font: 12 Ariel");
                StackPane pane = new StackPane();
                pane.getChildren().add(imageView);
                pane.getChildren().add(inftx);

                pane.setAlignment(Pos.CENTER);
                tmpCurrentCode.getHboxGears().getChildren().add(size-(i+1), pane);
            }
            tmpCurrentCode.getReflector().setText("Reflector: " + code.IntToRom(code.getSelectedReflector()));
            reflector.setStyle(" -fx-font-weight: bold");
            tmpCurrentCode.getPlugs().setText("Plugs: ");
            Map<Character, Character> tmpmap = new HashMap<>();
            tmpmap.putAll(code.getMapSelctesPlugs());
            int count = 0;
            for (Map.Entry<Character, Character> entry : tmpmap.entrySet()) {
                if (entry.getValue() != null) {
                    tmpCurrentCode.getPlugs().setText(tmpCurrentCode.getPlugs().getText() + entry.getKey() + "|" + entry.getValue() + " ");
                }
                tmpmap.replace(entry.getValue(), entry.getKey(), null);
            }
            plugs.setStyle(" -fx-font-weight: bold");
            StringBuilder CurrentCodeStr=BuildCodeConf(code.getSelectedRotors(), code.getCurrNotch(), code.getSelectedPos(),code.IntToRom(code.getSelectedReflector()), code.getMapSelctesPlugs());
            tmpCurrentCode.setSpecialCode(""+CurrentCodeStr);
            hboxGears.setSpacing(5);
            if(mainScreensController.getMainScreensUBoatController().getCodeConfigurationComponentController().getIsAnimations().isSelected()) {
                for (int i = 0; i < tmpCurrentCode.getHboxGears().getChildren().size(); i++) {

                    RotateTransition rt = new RotateTransition(Duration.millis(2000), tmpCurrentCode.getHboxGears().getChildren().get(i));
                    rt.setByAngle(360);
                    rt.setCycleCount(1);
                    rt.play();
                }
            }
        }
        public StringBuilder BuildCodeConf(List<Integer> SelectedRotors, List<Integer>currNotch, List<Character>SelectedPos, String SelectedReflector, Map<Character, Character> SelctesPlugs) {
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

        public String getSpecialCode(){
            return specialCode;
        }
        public Separator getSepOne(){return sepOne;}
        public  Separator getSepTwo(){return sepTwo;}

        public Button getOldCodeButton(){return oldCodeButton;}


}
