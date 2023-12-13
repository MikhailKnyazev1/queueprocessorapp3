package com.example.queueprocessorapp3.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.Instant;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя не может быть пустым")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Column(name = "last_name")
    private String lastName;

    @Min(value = 18, message = "Возраст должен быть не менее 18 лет")
    @Max(value = 65, message = "Возраст должен быть не более 65 лет")
    @Column(name = "age")
    private int age;

    @NotBlank(message = "Профессия не может быть пустой")
    @Column(name = "profession")
    private String profession;

    @Column(name = "handled_timestamp")
    private Instant handledTimestamp;

    @Column(name = "status")
    private String status;

    // Getters and setters

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
}
