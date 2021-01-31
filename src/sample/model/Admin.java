package sample.model;

import javafx.beans.property.*;

public class Admin {

    private IntegerProperty admin_id;
    private StringProperty first_name;
    private StringProperty last_name;
    private StringProperty password;
    private StringProperty nickname;


    //Constructor
    public Admin() {
        this.admin_id = new SimpleIntegerProperty();
        this.first_name = new SimpleStringProperty();
        this.last_name = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.nickname = new SimpleStringProperty();

    }

    //admin_id
    public int getAdminId() {
        return admin_id.get();
    }

    public void setAdminId(int adminId){
        this.admin_id.set(adminId);
    }

    public IntegerProperty adminIdProperty(){
        return admin_id;
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

    //password
    public String getPassword () {
        return password.get();
    }

    public void setPassword (String password){
        this.password.set(password);
    }

    public StringProperty emailProperty() {
        return password;
    }

    //nickname
    public String getNickname () {
        return nickname.get();
    }

    public void setNickname (String nickname){
        this.nickname.set(nickname);
    }

    public StringProperty nicknameProperty() {
        return nickname;
    }

}
