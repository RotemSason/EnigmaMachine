package Screens.MachineDetails;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import Engine.EngineUI;
import javafx.scene.layout.AnchorPane;

public class MachineDetailsController {
    @FXML
    private Label MachineDetails;

    @FXML
    private Label numRotors;

    @FXML
    private Label numReflectors;

    @FXML
    private Label numCommands;

    @FXML
    private AnchorPane anchorPane;

    public void setMachineDetails(EngineUI returnObj){
        numRotors.setText("The Machine use:" + returnObj.getUseRotors() + "/" + returnObj.getLstOfRotors().size());
        numReflectors.setText("The number of reflectors:" + returnObj.getLstOfReflector().size());
        numCommands.setText("The number of commands processed is: " + returnObj.getCommandCount());
    }
    public void setNumCommands(int numCommands){
        this.numCommands.setText(" The number of commands processed is: " + numCommands);
    }


}
