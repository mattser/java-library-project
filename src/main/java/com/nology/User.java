package com.nology;

public class User {

    private String name;
    private Boolean isAdmin;

    public User () {
        isAdmin = false;
    }

    public User (String name) {
        this.name = name;
        this.isAdmin = false;
    }

    public User(String name, Boolean isAdmin) {
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String toString() {
        return name + ((isAdmin) ? " is an admin" : " is a user");
    }
}
