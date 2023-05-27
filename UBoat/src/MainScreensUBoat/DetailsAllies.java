package MainScreensUBoat;

import javafx.beans.property.SimpleStringProperty;

public class DetailsAllies {
    private SimpleStringProperty alliesName;
    private SimpleStringProperty agentsAmount;
    private SimpleStringProperty tasksSize;


    public DetailsAllies(String alliesName,String agentsAmount,String tasksSize){
        this.alliesName=new SimpleStringProperty(alliesName);
        this.agentsAmount=new SimpleStringProperty(agentsAmount);
        this.tasksSize=new SimpleStringProperty(tasksSize);
    }

    public void setAgentsAmount(String agentsAmount) {
        this.agentsAmount.set(agentsAmount);
    }

    public void setAlliesName(String alliesName) {
        this.alliesName.set(alliesName);
    }

    public void setTasksSize(String tasksSize) {
        this.tasksSize.set(tasksSize);
    }

    public String getAgentsAmount() {
        return agentsAmount.get();
    }

    public String getAlliesName() {
        return alliesName.get();
    }

    public String getTasksSize() {
        return tasksSize.get();
    }
}
