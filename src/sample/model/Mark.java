package sample.model;

import javafx.beans.property.*;

public class Mark {
    private IntegerProperty mark_id;
    private IntegerProperty mark_value;
    private StringProperty subject_name;
    private StringProperty semester;
    private StringProperty faculty_number;

    //Constructor
    public Mark() {
        this.mark_id = new SimpleIntegerProperty();
        this.mark_value = new SimpleIntegerProperty();
        this.subject_name = new SimpleStringProperty();
        this.semester = new SimpleStringProperty();
        this.faculty_number = new SimpleStringProperty();

    }

    //mark_id
    public int getMarkId() {
        return mark_id.get();
    }

    public void setMarkId(int markId){
        this.mark_id.set(markId);
    }

    public IntegerProperty markIdProperty(){
        return mark_id;
    }
    
    //mark_value
    public int getMarkValue() {
        return mark_value.get();
    }

    public void setMarkValue(int markValue){
        this.mark_value.set(markValue);
    }

    public IntegerProperty markValueProperty(){
        return mark_value;
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

    //Student_id
    public String getFacultyNumber () {
        return faculty_number.get();
    }

    public void setFacultyNumber(String facultyNumber){
        this.faculty_number.set(facultyNumber);
    }

    public StringProperty facultyNumberProperty() {
        return faculty_number;
    }
}
