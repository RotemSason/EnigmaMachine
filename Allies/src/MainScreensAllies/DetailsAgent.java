package MainScreensAllies;

import javafx.beans.property.SimpleStringProperty;

public class DetailsAgent {
    private SimpleStringProperty agent;
    private SimpleStringProperty threads;
    private SimpleStringProperty tasksSize;


    public DetailsAgent(String AgentColumn,String ThreadsColumn, String TasksSizeColumn){
        this.agent= new SimpleStringProperty(AgentColumn);
        this.threads= new SimpleStringProperty(ThreadsColumn);
        this.tasksSize= new SimpleStringProperty(TasksSizeColumn);


    }

    public void setTasksSize(String taskssize) {
        this.tasksSize.set(taskssize);
    }

    public void setAgent(String agentColumn) {
        this.agent.set(agentColumn);
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
