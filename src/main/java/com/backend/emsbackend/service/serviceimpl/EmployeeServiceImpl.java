package com.backend.emsbackend.service.serviceimpl;

import com.backend.emsbackend.model.dto.EmployeeDto;
import com.backend.emsbackend.model.entity.Employee;
import com.backend.emsbackend.exceptions.ResourseNotFoundException;
import com.backend.emsbackend.mapper.EmployeeMapper;
import com.backend.emsbackend.repository.EmployeeRepository;
import com.backend.emsbackend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.INSTANCE.dtoToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.INSTANCE.toEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourseNotFoundException("Employee does not exist for the given id: " + employeeId));
        return EmployeeMapper.INSTANCE.toEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeMapper.INSTANCE::toEmployeeDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployees(Long employeeId, EmployeeDto updatedEmployee) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourseNotFoundException("Employee does not exist with the given id: " + employeeId)
        );
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());
        Employee updatedEmployeeObj = employeeRepository.save(employee);
        return EmployeeMapper.INSTANCE.toEmployeeDto(updatedEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourseNotFoundException("Employee does not exist: " + employeeId)
        );
        employeeRepository.deleteById(employeeId);

    }
}
