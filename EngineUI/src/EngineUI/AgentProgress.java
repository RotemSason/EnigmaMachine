package EngineUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class AgentProgress {
    private SimpleStringProperty NameAgent;
    private  SimpleStringProperty  TotalRecievedTasks;
    private SimpleStringProperty  TotalWaitingTasks;
    private SimpleStringProperty  TotalCandidateString;

   // @FXML
    //private TableColumn<AgentProgress, String> TotalCandidateStringColumn;
    public AgentProgress(String NameAgent, String TotalRecievedTasks,String TotalWaitingTasks,String TotalCandidateString){
        this.NameAgent= new SimpleStringProperty(NameAgent);
        this.TotalRecievedTasks= new SimpleStringProperty(TotalRecievedTasks);
        this.TotalWaitingTasks= new SimpleStringProperty(TotalWaitingTasks);
        this.TotalCandidateString= new SimpleStringProperty(TotalCandidateString);
    }
    public String getNameAgent() {
        return NameAgent.get();
    }

    public void setNameAgent(String fname) {NameAgent.set(fname);
    }

    public String getTotalRecievedTasks() {
        return TotalRecievedTasks.get();
    }

    public void setTotalRecievedTasks(String fName) {
        TotalRecievedTasks.set(fName);
    }

    public void setTotalWaitingTasks(String fName) {TotalWaitingTasks.set(fName);
    }
    public String getTotalCandidateString() {
        return TotalCandidateString.get();
    }
    public String getTotalWaitingTasks() {return TotalWaitingTasks.get();
    }
    public void setTotalCandidateString(String fName) {TotalCandidateString.set(fName);
}}
