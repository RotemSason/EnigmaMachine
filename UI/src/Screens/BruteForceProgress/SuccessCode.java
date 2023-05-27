package Screens.BruteForceProgress;

import com.sun.org.apache.bcel.internal.generic.PUSH;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SuccessCode {
    private  final SimpleIntegerProperty  id;
    private  SimpleStringProperty  rotors;
    private  SimpleStringProperty  positions;
    private SimpleStringProperty  reflector;

    public SuccessCode(Integer id,String rotors, String positions, String reflector){
        this.id= new SimpleIntegerProperty(id);
        this.reflector= new SimpleStringProperty(reflector);
        this.rotors= new SimpleStringProperty(rotors);
        this.positions= new SimpleStringProperty(positions);

    }
    public String getRotors() {
        return rotors.get();
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

    public Integer getId(){return id.get();}
    public void setId(Integer fName) {
        id.set(fName);
    }

    public String getReflector() {
        return reflector.get();
    }
    public void setReflector(String fName) {
        reflector.set(fName);
    }

}
