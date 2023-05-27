package Engine;

public class BattleField {
    private String battleName;
    private int numOfAllies;
    private String level;

    public void setBattleName(String name){
        this.battleName=name;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setNumOfAllies(int numOfAllies) {
        this.numOfAllies = numOfAllies;
    }

    public int getNumOfAllies() {
        return numOfAllies;
    }

    public String getBattleName() {
        return battleName;
    }

    public String getLevel() {
        return level;
    }
}