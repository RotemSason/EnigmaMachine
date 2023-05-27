package SuccessCodeDetails;
import EngineUI.AgentCurrCode;
import EngineUI.SuccessCode;
import MainScreensUBoat.ContestUBoatComponentController;
import MainScreensUBoat.MainScreensUBoatController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class SuccessCodeDetailsController {
    @FXML
    private TableView<SuccessCode> AllSuccessCodeTable;

    @FXML
    private TableColumn<SuccessCode, String> AlliesNameColumn;

    @FXML
    private TableColumn<SuccessCode, String> CandidateStringColumn;

    @FXML
    private TableColumn<SuccessCode, String> RotorsColumn;

    @FXML
    private TableColumn<SuccessCode, String> PositionsColumn;

    @FXML
    private TableColumn<SuccessCode, Character> ReflectorColumn;
    private ContestUBoatComponentController mainScreensUBoattController;
    private List<AgentCurrCode> SuccessCode;

    public SuccessCodeDetailsController() {
        SuccessCode = new ArrayList<>();
    }

    public void setMainScreensUBoattController(ContestUBoatComponentController mainScreensUBoatController) {
        this.mainScreensUBoattController = mainScreensUBoatController;
    }
    public synchronized void addToAllSuccessTable(SuccessCode code){

        if(AllSuccessCodeTable.getItems().size()==0){

            AlliesNameColumn.setCellValueFactory(new PropertyValueFactory<SuccessCode, String>("nameAllies"));
            CandidateStringColumn.setCellValueFactory(new PropertyValueFactory<SuccessCode, String>("strCandidate"));

            RotorsColumn.setCellValueFactory(new PropertyValueFactory<SuccessCode, String>("rotors"));

            PositionsColumn.setCellValueFactory(new PropertyValueFactory<SuccessCode, String>("positions"));

            ReflectorColumn.setCellValueFactory(new PropertyValueFactory<SuccessCode, Character>("reflector"));
        }
        final ObservableList<SuccessCode> data =
                FXCollections.observableArrayList(code);
        AllSuccessCodeTable.setEditable(true);
        AllSuccessCodeTable.getItems().add(code);

    }
    public synchronized TableView<SuccessCode>getAllSuccessCodeTable(){
        return AllSuccessCodeTable;
    }

}