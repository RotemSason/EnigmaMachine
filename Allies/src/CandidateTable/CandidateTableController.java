package CandidateTable;

import EngineUI.SuccessCode;
import MainScreensAllies.ContestAlliesComponentController;
import MainScreensAllies.MainScreensAlliesController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CandidateTableController {

    @FXML
    private TableView<SuccessCode> TableSuccessCodes;
    @FXML
    private TableColumn<SuccessCode, String> CandidateStringColumn;

    @FXML
    private TableColumn<SuccessCode, String> rotorsColumn;

    @FXML
    private TableColumn<SuccessCode, String> positionsColumn;

    @FXML
    private TableColumn<SuccessCode, Character> reflectorColumn;
    ContestAlliesComponentController mainScreensAlliesController;
    public void setMainScreensController(ContestAlliesComponentController mainScreensController) {
        this.mainScreensAlliesController = mainScreensController;
    }
    public TableView<SuccessCode> getTableSuccessCodes(){
        return TableSuccessCodes;
    }
    public synchronized void addToTable(SuccessCode code){

        if(TableSuccessCodes.getItems().size()==0){
            CandidateStringColumn.setCellValueFactory(new PropertyValueFactory<SuccessCode, String>("strCandidate"));

            rotorsColumn.setCellValueFactory(new PropertyValueFactory<SuccessCode, String>("rotors"));

            positionsColumn.setCellValueFactory(new PropertyValueFactory<SuccessCode, String>("positions"));

            reflectorColumn.setCellValueFactory(new PropertyValueFactory<SuccessCode, Character>("reflector"));
        }
        final ObservableList<SuccessCode> data =
                FXCollections.observableArrayList(code);
        TableSuccessCodes.setEditable(true);
        TableSuccessCodes.getItems().add(code);

    }
}
