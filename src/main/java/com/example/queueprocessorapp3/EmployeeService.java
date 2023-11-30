package com.example.queueprocessorapp3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Long addOrUpdateEmployee(Employee employee) {
        // Проверка на существование сотрудника по имени и фамилии
        Optional<Employee> existing = employeeRepository.findByFirstNameAndLastName(
                employee.getFirstName(), employee.getLastName()
        );

        Employee toSave;
        if (existing.isPresent()) {
            // Обновление существующего сотрудника
            toSave = existing.get();
            toSave.setAge(employee.getAge());
            toSave.setProfession(employee.getProfession());
        } else {
            // Добавление нового сотрудника
            toSave = employee;
            toSave.setStatus("Active");
        }
        toSave.setHandledTimestamp(Instant.now());
        Employee saved = employeeRepository.save(toSave);
        return saved.getId();
    }

    public Employee getEmployeeById(Long employeeId) {
        // Получение сотрудника по ID, возвращение Optional<Employee>
        return employeeRepository.findById(employeeId).orElse(null); // Или обработка отсутствия сотрудника
    }
}
