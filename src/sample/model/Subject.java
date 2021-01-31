package sample.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Subject {

    private IntegerProperty subject_id;
    private StringProperty subject_name;
    private StringProperty specialty;
    private StringProperty semester;

    //Constructor
    public Subject() {
        this.subject_id = new SimpleIntegerProperty();
        this.subject_name = new SimpleStringProperty();
        this.specialty = new SimpleStringProperty();
        this.semester = new SimpleStringProperty();

    }

    //subject_id
    public int getSubjectId() {
        return subject_id.get();
    }

    public void setSubjectId(int subjectId){
        this.subject_id.set(subjectId);
    }

    public IntegerProperty subjectIdProperty(){
        return subject_id;
    }

    //subject_name
    public String getSubjectName () {
        return subject_name.get();
    }

    public void setSubjectName(String subjectName){
        this.subject_name.set(subjectName);
    }

    public StringProperty subjectNameProperty() {
        return subject_name;
    }

    public String getSpecialty () {
        return specialty.get();
    }

    public void setSpecialty(String specialty){
        this.specialty.set(specialty);
    }

    public StringProperty specialtyProperty() {
        return specialty;
    }

    //semester
    public String getSemester () {
        return semester.get();
    }

    public void setSemester(String semester){
        this.semester.set(semester);
    }

    public StringProperty semesterProperty() {
        return semester;
    }
    
}
