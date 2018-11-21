package com.example.rajneeshshukla.letsconnect.model;

/**
 *  Class is used user profile pojo
 */
public class User {

    private String name;
    private String username;
    private String country;
    private String dob;
    private String status;
    private String gender;
    private String relationship;

    public User(String name, String username, String country, String dob, String status, String gender, String relationship) {
        this.name = name;
        this.username = username;
        this.country = country;
        this.dob = dob;
        this.status = status;
        this.gender = gender;
        this.relationship = relationship;
    }

    public User(String username, String name, String country) {
        this.name = name;
        this.username = username;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", country='" + country + '\'' +
                ", dob='" + dob + '\'' +
                ", status='" + status + '\'' +
                ", gender='" + gender + '\'' +
                ", relationship='" + relationship + '\'' +
                '}';
    }
}
