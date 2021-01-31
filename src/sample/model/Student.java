package sample.model;

import javafx.beans.property.*;
import java.sql.Date;

public class Student {
    //Declare Students Table Columns
    private IntegerProperty student_id;
    private StringProperty first_name;
    private StringProperty last_name;
    private StringProperty email;
    private StringProperty phone_number;
    private StringProperty faculty_number;
    private StringProperty specialty;
    private StringProperty semester;
    private DoubleProperty score;
    private DoubleProperty scholarship;
    private DoubleProperty scholarshipPerMonth;


    //Constructor
    public Student() {
        this.student_id = new SimpleIntegerProperty();
        this.first_name = new SimpleStringProperty();
        this.last_name = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.phone_number = new SimpleStringProperty();
        this.faculty_number = new SimpleStringProperty();
        this.specialty = new SimpleStringProperty();
        this.semester = new SimpleStringProperty();
        this.score = new SimpleDoubleProperty();
        this.scholarship = new SimpleDoubleProperty();
        this.scholarshipPerMonth = new SimpleDoubleProperty();

    }

    //student_id
    public int getStudentId() {
        return student_id.get();
    }

    public void setStudentId(int studentId){
        this.student_id.set(studentId);
    }

    public IntegerProperty studentIdProperty(){
        return student_id;
    }

    //first_name
    public String getFirstName () {
        return first_name.get();
    }

    public void setFirstName(String firstName){
        this.first_name.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return first_name;
    }

    //last_name
    public String getLastName () {
        return last_name.get();
    }

    public void setLastName(String lastName){
        this.last_name.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return last_name;
    }

    //email
    public String getEmail () {
        return email.get();
    }

    public void setEmail (String email){
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    //phone_number
    public String getPhoneNumber () {
        return phone_number.get();
    }

    public void setPhoneNumber (String phoneNumber){
        this.phone_number.set(phoneNumber);
    }

    public StringProperty phoneNumberProperty() {
        return phone_number;
    }


    //faculty_number
    public String getFacultyNumber () {
        return faculty_number.get();
    }

    public void setFacultyNumber (String facultyNumber){
        this.faculty_number.set(facultyNumber);
    }

    public StringProperty facultyNumberProperty() {
        return faculty_number;
    }

    //faculty_number
    public String getSpecialty () {
        return specialty.get();
    }

    public void setSpecialty (String specialty){
        this.specialty.set(specialty);
    }

    public StringProperty specialtyProperty() {
        return specialty;
    }

    //semester
    public String getSemester() {
        return semester.get();
    }

    public void setSemester(String semester){
        this.semester.set(semester);
    }

    public StringProperty semesterProperty(){
        return semester;
    }

    //score
    public double getScore() {
        return score.get();
    }

    public void setScore(double score){
        this.score.set(score);
    }

    public DoubleProperty scoreProperty(){
        return score;
    }

    //scholarship
    public double getScholarship() {
        return scholarship.get();
    }

    public void setScholarship(double scholarship){
        this.scholarship.set(scholarship);
    }

    public DoubleProperty scholarshipProperty(){
        return scholarship;
    }

    //scholarshipPerMonth
    public double getScholarshipPerMonth() {
        return scholarshipPerMonth.get();
    }

    public void setScholarshipPerMonth(double scholarshipPerMonth){
        this.scholarshipPerMonth.set(scholarshipPerMonth);
    }

    public DoubleProperty scholarshipPerMonthProperty(){
        return scholarshipPerMonth;
    }





}
