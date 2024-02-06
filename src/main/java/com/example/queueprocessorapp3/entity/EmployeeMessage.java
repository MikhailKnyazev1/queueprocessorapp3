package com.example.queueprocessorapp3.entity;

import java.time.LocalDate;
import java.util.List;

public class EmployeeMessage {
    private String company;
    private LocalDate effectiveDate; // Изменено на LocalDate
    private List<Employee> staff;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDate getEffectiveDate() { // Возвращаемый тип изменен на LocalDate
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) { // Тип параметра изменен на LocalDate
        this.effectiveDate = effectiveDate;
    }

    public List<Employee> getStaff() {
        return staff;
    }

    public void setStaff(List<Employee> staff) {
        this.staff = staff;
    }
}
