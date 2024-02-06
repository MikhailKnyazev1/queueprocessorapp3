package com.example.queueprocessorapp3.entity;

import java.time.LocalDate;

public class Message {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String profession;
    private String company;         // Добавлено поле company
    private LocalDate effectiveDate; // Добавлено поле effectiveDate

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

    public String getCompany() { // Добавлен геттер для company
        return company;
    }

    public void setCompany(String company) { // Добавлен сеттер для company
        this.company = company;
    }

    public LocalDate getEffectiveDate() { // Добавлен геттер для effectiveDate
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) { // Добавлен сеттер для effectiveDate
        this.effectiveDate = effectiveDate;
    }
}
