package com.example.queueprocessorapp3.converter;

import com.example.queueprocessorapp3.entity.Employee;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.time.Instant;

public interface EmployeeConverter {

    default Employee convert(JsonNode jsonNode) {
        if (jsonNode == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setFirstName(getStringValue(jsonNode, "firstName"));
        employee.setLastName(getStringValue(jsonNode, "lastName"));
        employee.setAge(getIntValue(jsonNode, "age"));
        employee.setProfession(getStringValue(jsonNode, "profession"));
        employee.setHandledTimestamp(Instant.now());
        employee.setStatus("Complete");

        return employee;
    }

    private static String getStringValue(JsonNode jsonNode, String fieldName) {
        return jsonNode.has(fieldName) ? jsonNode.get(fieldName).asText() : null;
    }

    private static Integer getIntValue(JsonNode jsonNode, String fieldName) {
        return jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull() ? jsonNode.get(fieldName).asInt() : null;
    }

    @Component
    class EmployeeConverterImpl implements EmployeeConverter {

        @Override
        public Employee convert(JsonNode jsonNode) {
            if (jsonNode == null) {
                return null;
            }

            Employee employee = new Employee();
            employee.setFirstName(getStringValue(jsonNode, "firstName"));
            employee.setLastName(getStringValue(jsonNode, "lastName"));
            employee.setAge(getIntValue(jsonNode, "age"));
            employee.setProfession(getStringValue(jsonNode, "profession"));
            employee.setHandledTimestamp(Instant.now());
            employee.setStatus("Complete");

            return employee;
        }

        private String getStringValue(JsonNode jsonNode, String fieldName) {
            return jsonNode.has(fieldName) ? jsonNode.get(fieldName).asText() : null;
        }

        private Integer getIntValue(JsonNode jsonNode, String fieldName) {
            return jsonNode.has(fieldName) && !jsonNode.get(fieldName).isNull() ? jsonNode.get(fieldName).asInt() : null;
        }
    }
}
