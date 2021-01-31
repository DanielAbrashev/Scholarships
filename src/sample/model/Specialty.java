package sample.model;

import javafx.beans.property.*;

public class Specialty {

    private IntegerProperty specialty_id;
    private StringProperty specialty_name;

    //Constructor
    public Specialty() {
        this.specialty_id = new SimpleIntegerProperty();
        this.specialty_name = new SimpleStringProperty();


    }

    //specialty_id
    public int getSpecialtyId() {
        return specialty_id.get();
    }

    public void setSpecialtyId(int specialtyId){
        this.specialty_id.set(specialtyId);
    }

    public IntegerProperty specialtyIdProperty(){
        return specialty_id;
    }

    //specialty_name
    public String getSpecialtyName () {
        return specialty_name.get();
    }

    public void setSpecialtyName(String specialtyName){
        this.specialty_name.set(specialtyName);
    }

    public StringProperty specialtyNameProperty() {
        return specialty_name;
    }


}
