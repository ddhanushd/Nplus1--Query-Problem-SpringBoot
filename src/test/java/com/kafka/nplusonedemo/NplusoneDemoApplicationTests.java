package com.kafka.nplusonedemo;

import com.kafka.nplusonedemo.entity.Department;
import com.kafka.nplusonedemo.repository.DepartmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class NplusoneDemoApplicationTests {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private Statistics statistics;

    @BeforeEach
    void setUp() {
        SessionFactory sessionFactory =
                entityManagerFactory.unwrap(SessionFactory.class);

        statistics = sessionFactory.getStatistics();

        // Detach previously managed entities
        entityManager.clear();

        // Reset Hibernate statistics before each measurement
        statistics.clear();
    }

    @Test
    void shouldDemonstrateNPlusOneProblem() {

        List<Department> departments =
                departmentRepository.findAll();

        // Access each lazy collection
        int totalEmployees = departments.stream()
                .mapToInt(d -> d.getEmployees().size())
                .sum();

        assertEquals(3, departments.size());
        assertEquals(6, totalEmployees);

        // 1 department SELECT + 3 employee SELECTs
        assertEquals(4, statistics.getPrepareStatementCount());
    }

    @Test
    void shouldFixNPlusOneWithJoinFetch() {

        List<Department> departments =
                departmentRepository.findAllWithEmployees();

        int totalEmployees = departments.stream()
                .mapToInt(d -> d.getEmployees().size())
                .sum();

        assertEquals(3, departments.size());
        assertEquals(6, totalEmployees);

        // One joined SELECT
        assertEquals(1, statistics.getPrepareStatementCount());
    }

    @Test
    void shouldFixNPlusOneWithEntityGraph() {

        List<Department> departments =
                departmentRepository.findAllUsingEntityGraph();

        int totalEmployees = departments.stream()
                .mapToInt(d -> d.getEmployees().size())
                .sum();

        assertEquals(3, departments.size());
        assertEquals(6, totalEmployees);

        // One SELECT for this Hibernate fetch plan
        assertEquals(1, statistics.getPrepareStatementCount());
    }

}
