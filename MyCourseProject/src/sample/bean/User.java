package sample.bean;

import javafx.beans.property.SimpleStringProperty;

public class User {

    int id;
    private String login;
    private String password;
    private SimpleStringProperty name;
    private SimpleStringProperty surname;
    private SimpleStringProperty yearOfBirth;
    private SimpleStringProperty email;




    public User(int id, String name, String surname, String login, String password, String yearOfBirth, String email){
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.login = login;
        this.password = password;
        this.yearOfBirth = new SimpleStringProperty(yearOfBirth);
        this.email = new SimpleStringProperty(email);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getYearOfBirth() {
        return yearOfBirth.get();
    }

    public SimpleStringProperty yearOfBirthProperty() {
        return yearOfBirth;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth.set(yearOfBirth);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public String toString() {
        return "User{" +
                "name=" + name +
                ", surname=" + surname +
                ", yearOfBirth=" + yearOfBirth +
                ", email=" + email +
                '}';
    }
}
