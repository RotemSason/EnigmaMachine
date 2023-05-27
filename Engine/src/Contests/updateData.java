package Contests;

import javafx.beans.property.SimpleStringProperty;

public class updateData {

    private SimpleStringProperty pushTasks;
    private SimpleStringProperty finishTasks;

    public updateData(String pushTasks, String finishTasks){
        this.pushTasks= new SimpleStringProperty(pushTasks);
        this.finishTasks= new SimpleStringProperty(finishTasks);

    }

    public void setFinishTasks(String finishTasks) {
        this.finishTasks.set(finishTasks);
    }

    public void setPushTasks(String pushTasks) {
        this.pushTasks.set(pushTasks);
    }

    public String getFinishTasks() {
        return finishTasks.get();
    }

    public String getPushTasks() {
        return pushTasks.get();
    }
}

