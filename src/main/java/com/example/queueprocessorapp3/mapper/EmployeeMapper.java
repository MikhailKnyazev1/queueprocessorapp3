package com.example.queueprocessorapp3.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.example.queueprocessorapp3.entity.Employee;
import com.example.queueprocessorapp3.entity.Message;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "profession", target = "profession")
    @Mapping(source = "company", target = "company")
    @Mapping(source = "effectiveDate", target = "effectiveDate")
    Message employeeToMessage(Employee employee);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "profession", target = "profession")
    @Mapping(source = "company", target = "company")
    @Mapping(source = "effectiveDate", target = "effectiveDate")
    Employee messageToEmployee(Message message);
}
