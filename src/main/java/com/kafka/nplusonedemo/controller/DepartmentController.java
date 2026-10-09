package com.kafka.nplusonedemo.controller;

import com.kafka.nplusonedemo.entity.Department;
import com.kafka.nplusonedemo.repository.DepartmentRepository;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentController {
    private final DepartmentRepository departmentRepository;
    private final EntityManagerFactory entityManagerFactory;

    public DepartmentController(
            DepartmentRepository departmentRepository, EntityManagerFactory entityManagerFactory) {
        this.departmentRepository = departmentRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    // Original implementation: can trigger N+1
    @GetMapping("/departments/n-plus-one")
    @Transactional(readOnly = true)
    public List<String> getDepartmentsNPlusOne() {

        return departmentRepository.findAll()
                .stream()
                .map(d -> d.getName()
                        + " - Employees: "
                        + d.getEmployees().size())
                .toList();
    }

    // Fixed using JOIN FETCH
    @GetMapping("/departments/join-fetch")
    @Transactional(readOnly = true)
    public List<String> getDepartmentsJoinFetch() {

        return toResponse(
                departmentRepository.findAllWithEmployees()
        );
    }

    // Fixed using @EntityGraph
    @GetMapping("/departments/entity-graph")
    @Transactional(readOnly = true)
    public List<String> getDepartmentsEntityGraph() {

        return toResponse(
                departmentRepository.findAllUsingEntityGraph()
        );
    }

    private List<String> toResponse(
            List<Department> departments) {

        return departments.stream()
                .map(d -> d.getName()
                        + " - Employees: "
                        + d.getEmployees().size())
                .toList();
    }

    @GetMapping("/departments/query-count")
    @Transactional(readOnly = true)
    public long getQueryCount() {

        SessionFactory sessionFactory =
                entityManagerFactory.unwrap(SessionFactory.class);

        var statistics = sessionFactory.getStatistics();
        statistics.clear();

        List<Department> departments =
                departmentRepository.findAllWithEmployees();

        departments.forEach(d -> d.getEmployees().size());

        return statistics.getPrepareStatementCount();
    }
}
