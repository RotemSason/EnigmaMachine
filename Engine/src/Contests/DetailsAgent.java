package Contests;

import javafx.beans.property.SimpleStringProperty;

public class DetailsAgent {
    private SimpleStringProperty agent;
    private SimpleStringProperty threads;
    private SimpleStringProperty tasksSize;


    public DetailsAgent(String Agent,String Threads, String TasksSize){
        this.agent= new SimpleStringProperty(Agent);
        this.threads= new SimpleStringProperty(Threads);
        this.tasksSize= new SimpleStringProperty(TasksSize);


    }

    public void setAgent(String agentColumn) {
        this.agent.set(agentColumn);
    }

    public void setTasksSize(String taskssize) {
        this.tasksSize.set(taskssize);
    }

    public void setThreads(String threadsColumn) {
        this.threads.set(threadsColumn);
    }

    public String getAgent() {
        return agent.get();
    }
    public String getTasksSize() {
        return tasksSize.get();
    }
    public String getThreads() {
        return threads.get();
    }
}
