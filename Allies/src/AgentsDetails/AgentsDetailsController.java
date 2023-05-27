package AgentsDetails;
import MainScreensAllies.ContestAlliesComponentController;
import MainScreensAllies.DetailsAgent;
import MainScreensAllies.DetailsUBoat;
import MainScreensAllies.MainScreensAlliesController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class AgentsDetailsController {

        @FXML
        private TableView<DetailsAgent> TableSuccessCodes1;

        @FXML
        private TableColumn<DetailsAgent,String> AgentColumn;

        @FXML
        private TableColumn<DetailsAgent,String> ThreadsColumn;

        @FXML
        private TableColumn<DetailsAgent,String>TasksSizeColumn;
        @FXML
        private ContestAlliesComponentController mainScreensAlliesController;

        public void setMainScreensController(ContestAlliesComponentController mainScreensController){
            this.mainScreensAlliesController=mainScreensController;
        }
        public TableView<DetailsAgent> getTable(){return TableSuccessCodes1;}

    public TableColumn<DetailsAgent, String> getAgentColumn() {
        return AgentColumn;
    }

    public TableColumn<DetailsAgent, String> getTasksSizeColumn() {
        return TasksSizeColumn;
    }

    public TableColumn<DetailsAgent, String> getThreadsColumn() {
        return ThreadsColumn;
    }


    public synchronized void addToAgentTable(List<DetailsAgent> lst) {
        if (TableSuccessCodes1.getItems().size() == 0) {
            AgentColumn.setCellValueFactory(new PropertyValueFactory<DetailsAgent, String>("Agent"));
            TasksSizeColumn.setCellValueFactory(new PropertyValueFactory<DetailsAgent, String>("TasksSize"));
            ThreadsColumn.setCellValueFactory(new PropertyValueFactory<DetailsAgent, String>("Threads"));
        }
        // final ObservableList<DetailsUBoat> data =
        //       FXCollections.observableArrayList(lst);
        // TableSuccessCodes1.setEditable(true);
        //TableSuccessCodes1.getItems().clear();
        for (int i = 0; i < lst.size(); i++) {

            TableSuccessCodes1.getItems().add(lst.get(i));
        }


    }
    public void clearTable(){
        TableSuccessCodes1.getItems().clear();
    }
    }

