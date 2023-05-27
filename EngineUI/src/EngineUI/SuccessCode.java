package EngineUI;

import javafx.beans.property.SimpleStringProperty;

public class SuccessCode {
    private  SimpleStringProperty  rotors;
    private  SimpleStringProperty  positions;
    private SimpleStringProperty  reflector;
    private SimpleStringProperty  strCandidate;
    private SimpleStringProperty  nameAllies;



    public SuccessCode(String rotors, String positions, String reflector, String strCandidate,String nameAllies ){
        this.reflector= new SimpleStringProperty(reflector);
        this.rotors= new SimpleStringProperty(rotors);
        this.positions= new SimpleStringProperty(positions);
        this.strCandidate=new SimpleStringProperty(strCandidate);
        this.nameAllies=new SimpleStringProperty(nameAllies);
    }

    public void setNameAllies(String nameAllies) {
        this.nameAllies.set(nameAllies);
    }
public String getNameAllies(){return nameAllies.get();}
    public String getRotors() {
        return rotors.get();
    }

    public void setStrCandidate(String strCandidate) {
        this.strCandidate.set(strCandidate);
    }

    public String getStrCandidate() {
        return strCandidate.get();
    }

    public void setName(String fName) {
        rotors.set(fName);
    }

    public String getPositions() {
        return positions.get();
    }
    public void setPositions(String fName) {
        positions.set(fName);
    }

    public String getReflector() {
        return reflector.get();
    }
    public void setReflector(String fName) {
        reflector.set(fName);
    }

}
