package com.kafka.nplusonedemo.repository;

import com.kafka.nplusonedemo.entity.Department;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Approach 1: Explicit JPQL fetch join
    @Query("""
        SELECT DISTINCT d
        FROM Department d
        LEFT JOIN FETCH d.employees
        """)
    List<Department> findAllWithEmployees();

    // Approach 2: Declarative entity graph
    @EntityGraph(attributePaths = {"employees"})
    @Query("SELECT d FROM Department d")
    List<Department> findAllUsingEntityGraph();
}
