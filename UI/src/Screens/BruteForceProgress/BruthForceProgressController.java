package Screens.BruteForceProgress;
//import Agent.DM;
/*

import UIException.NotValidSizeTask;
import javafx.animation.ScaleTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class BruthForceProgressController {
    @FXML
    private Label avglabel;
    @FXML
    private Button bruteForceButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button resumeButton;
    @FXML
    private Button resetButton;



    private MainScreensUBoatController mainScreensController;
   @FXML
    private TableView<SuccessCode> TableSuccessCodes;
    @FXML
    private TableColumn<SuccessCode, Integer> idColumn;

    @FXML
    private TableColumn<SuccessCode, String> rotorsColumn;

    @FXML
    private TableColumn<SuccessCode, String> positionsColumn;

    @FXML
    private TableColumn<SuccessCode, Character> reflectorColumn;
    @FXML
    ProgressBar taskProgressBar;
    private boolean paused;
private long startTime;
private long endTime;


    @FXML
    private Label progressPercentLabel;
    @FXML
    private Label totalTime;
    private SimpleIntegerProperty totalProcessedTasks;
    private DM dm;
    private CollectDataTask startBruteForce;
    public void initialize(){
        stopButton.setDisable(true);
        clearButton.setDisable(true);
        pauseButton.setDisable(true);
        resumeButton.setDisable(true);
    }
    @FXML
    void clickPauseAction(ActionEvent event) throws InterruptedException {
        paused=true;
        dm.setPause(true);
    }

    @FXML
    void clickResetAction(ActionEvent event) {
        mainScreensController.MainEngine.ResetMachine();
       // mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(mainScreensController.MainEngine.getCurrCode(), mainScreensController.getCurrentCodeComponentController());
       // mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(mainScreensController.MainEngine.getCurrCode(), mainScreensController.getCurrentCodeComponent3Controller());
      //  mainScreensController.getCurrentCodeComponentController().showCodeConfiguration(mainScreensController.MainEngine.getCurrCode(), mainScreensController.getCodeConfigurationComponentController().getCurrentCodeComponentController());
    }
    @FXML
    void clickResumeAction(ActionEvent event) {
        dm.setPause(false);
        synchronized (dm.getThreadPool()){
            dm.getThreadPool().notify();
        }
    }
    @FXML
    void clickStopAction(ActionEvent event) {
       startBruteForce.cancel();
        endTime=System.nanoTime();
        totalTime.setText(""+(endTime-startTime));
    }
    @FXML
    void clickClearAction(ActionEvent event) {
        this.progressPercentLabel.textProperty().unbind();
        this.taskProgressBar.progressProperty().unbind();
        TableSuccessCodes.getItems().clear();
        taskProgressBar.setProgress(0);
        progressPercentLabel.setText("");
        totalTime.setText("");
        mainScreensController.getDMoperationalController().clearAll();
    }
    public synchronized void addToTable(SuccessCode code){
if(TableSuccessCodes.getItems().size()==0){
    idColumn.setCellValueFactory(new PropertyValueFactory<SuccessCode, Integer>("id"));

    rotorsColumn.setCellValueFactory(new PropertyValueFactory<SuccessCode, String>("rotors"));

    positionsColumn.setCellValueFactory(new PropertyValueFactory<SuccessCode, String>("positions"));

    reflectorColumn.setCellValueFactory(new PropertyValueFactory<SuccessCode, Character>("reflector"));
}
        final ObservableList<SuccessCode> data =
                FXCollections.observableArrayList(code);
        TableSuccessCodes.setEditable(true);
        TableSuccessCodes.getItems().add(code);

    }

    public void setMainScreensController(MainScreensController mainScreensController) {
        this.mainScreensController = mainScreensController;
    }

    @FXML
    void clickBruteForceButton(ActionEvent event) {
        try {
            if (mainScreensController.getCodeConfigurationComponentController().getIsAnimations().isSelected()) {
                ScaleTransition st = new ScaleTransition(Duration.millis(2000), bruteForceButton);
                st.setFromX(1);
                st.setFromY(1);
                st.setToX(1.2);
                st.setToY(1.2);
                st.setCycleCount(4);
                st.setAutoReverse(true);
                st.play();
            }
            stopButton.setDisable(false);
            clearButton.setDisable(false);
            pauseButton.setDisable(false);
            resumeButton.setDisable(false);
            UIAdapter uiAdapter = createUIAdapter();
            if (mainScreensController.getdMoperationalComponentController().getSizeTask() <= 0 || mainScreensController.getdMoperationalComponentController().getSizeTask() % 1 != 0) {
                throw new NotValidSizeTask();
            }
            dm = new DM(mainScreensController.MainEngine, mainScreensController.getdMoperationalComponentController().getSizeTask(), mainScreensController.getdMoperationalComponentController().getStrToDec(), mainScreensController.getdMoperationalComponentController().getCurrentAgent(), this, uiAdapter);
            setDM(dm);

            startTime = System.nanoTime();
            startBruteForce = new CollectDataTask(mainScreensController.MainEngine, uiAdapter, dm, mainScreensController.getdMoperationalComponentController().getLevel());

            bindTaskToUIComponents(startBruteForce, () -> toggleTaskButtons(false));
            new Thread(startBruteForce).start();

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }


    }
    public CollectDataTask getStartBruteForce(){return startBruteForce;}

    public void bindTaskToUIComponents(Task<Boolean> aTask, Runnable onFinish) {

        // task progress bar
        taskProgressBar.progressProperty().bind(aTask.progressProperty());

        // task percent label
        progressPercentLabel.textProperty().bind(
                Bindings.concat(
                        Bindings.format(
                                "%.0f",
                                Bindings.multiply(
                                        aTask.progressProperty(),
                                        100)),
                        " %"));

        // task cleanup upon finish
        aTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            onTaskFinished(Optional.ofNullable(onFinish));
        });
    }

    public void onTaskFinished(Optional<Runnable> onFinish) {
        this.progressPercentLabel.textProperty().unbind();
        this.taskProgressBar.progressProperty().unbind();
        onFinish.ifPresent(Runnable::run);
    }

    private UIAdapter createUIAdapter() {
        return new UIAdapter(
                successCode -> {
                    addToTable(successCode);
                },
                (delta) -> {
                    totalProcessedTasks.set(totalProcessedTasks.get() + delta);
                }
        );
    }

    private void toggleTaskButtons(boolean isActive) {
        endTime=System.nanoTime();
        totalTime.setText(""+(endTime-startTime));
        avglabel.setText(""+dm.getAverageTotalTask());

    }
    public void setDM(DM dm) {
        this.dm = dm;
    }
    public MainScreensController getMainScreensController(){
        return mainScreensController;
    }

}*/
