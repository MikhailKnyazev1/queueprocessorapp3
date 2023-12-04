package com.example.queueprocessorapp3.entity;

public class Message {
    private String firstName;
    private String lastName;
    private int age;
    private String profession;
    private Long handledTimestamp;
    private String status;
//    private String type;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Long getHandledTimestamp() {
        return handledTimestamp;
    }

    public void setHandledTimestamp(Long handledTimestamp) {
        this.handledTimestamp = handledTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
}
