package com.example.queueprocessorapp3.entity;

import java.time.LocalDate;
import java.util.List;

public class EmployeeBulkRequest {

    private String company;
    private LocalDate effectiveDate;
    private List<EmployeeInfo> staff;

    // Геттеры и сеттеры
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public List<EmployeeInfo> getStaff() {
        return staff;
    }

    public void setStaff(List<EmployeeInfo> staff) {
        this.staff = staff;
    }

    // Внутренний класс для сотрудников
    public static class EmployeeInfo {
        private String firstName;
        private String lastName;
        private int age;
        private String profession;

        // Геттеры и сеттеры
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
    }
}
