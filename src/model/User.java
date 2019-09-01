package model;

public class User {

    private int id;
    private String email;

    public User(int id, String email) {
        this.id = id;
        this.email = email;
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
