package com.kafka.nplusonedemo.controller;

import com.kafka.nplusonedemo.entity.Department;
import com.kafka.nplusonedemo.repository.DepartmentRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentController {
    private final DepartmentRepository departmentRepository;

    public DepartmentController(
            DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/departments/n-plus-one")
    @Transactional(readOnly = true)
    public List<String> getDepartments() {

        List<Department> departments =
                departmentRepository.findAll();

        return departments.stream()
                .map(department ->
                        department.getName()
                                + " - Employees: "
                                + department.getEmployees().size())
                .toList();
    }
}
