package com.example.queueprocessorapp3.entity;

import java.time.Instant;

public class Message {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String profession;
    private Instant handledTimestamp;
    private String status;

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Instant getHandledTimestamp() {
        return handledTimestamp;
    }

    public void setHandledTimestamp(Instant handledTimestamp) {
        this.handledTimestamp = handledTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Методы для type, если они вам нужны, можно раскомментировать
    // public String getType() {
    //     return type;
    // }
    //
    // public void setType(String type) {
    //     this.type = type;
    // }
}
