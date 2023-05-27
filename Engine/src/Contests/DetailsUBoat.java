package Contests;

import javafx.beans.property.SimpleStringProperty;

public class DetailsUBoat {
    private SimpleStringProperty battle;
    private SimpleStringProperty uboatName;
    private SimpleStringProperty status;
    private SimpleStringProperty level;
    private SimpleStringProperty allies;
    private boolean isFull;

    public DetailsUBoat(String battle,String uboatName, String status, String level, String allies,boolean isFull){
        this.battle= new SimpleStringProperty(battle);
        this.uboatName= new SimpleStringProperty(uboatName);
        this.status= new SimpleStringProperty(status);
        this.level= new SimpleStringProperty(level);
        this.allies= new SimpleStringProperty(allies);
        this.isFull=isFull;
    }

    public void setLevel(String level) {
        this.level.set(level);
    }

    public void setAllies(String allies) {
        this.allies.set(allies);
    }

    public void setBattle(String battle) {
        this.battle.set(battle);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setUboatName(String uboatName) {
        this.uboatName.set(uboatName);
    }

    public String getAllies() {
        return allies.get();
    }

    public String getBattle() {
        return battle.get();
    }

    public String getLevel() {
        return level.get();
    }

    public String getStatus() {
        return status.get();
    }

    public String getUboatName() {
        return uboatName.get();
    }

}
