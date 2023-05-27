package MachineDetailsUBoat;

import EngineUI.EngineUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MachineDetailsUBoatController {
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
public void clearalldetails(){
     numRotors.setText("");
     numReflectors.setText("");
     numCommands.setText("");
}
}
