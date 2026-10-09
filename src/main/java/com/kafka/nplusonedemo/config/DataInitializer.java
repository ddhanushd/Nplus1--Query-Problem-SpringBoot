package com.kafka.nplusonedemo.config;

import com.kafka.nplusonedemo.entity.Department;
import com.kafka.nplusonedemo.entity.Employee;
import com.kafka.nplusonedemo.repository.DepartmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner loadData(
            DepartmentRepository departmentRepository) {

        return args -> {
            Department it = new Department("IT");
            it.addEmployee(new Employee("Rahul"));
            it.addEmployee(new Employee("Priya"));

            Department hr = new Department("HR");
            hr.addEmployee(new Employee("Amit"));
            hr.addEmployee(new Employee("Sneha"));

            Department finance = new Department("Finance");
            finance.addEmployee(new Employee("Vikram"));
            finance.addEmployee(new Employee("Anjali"));

            departmentRepository.save(it);
            departmentRepository.save(hr);
            departmentRepository.save(finance);

            System.out.println("Sample data inserted!");
        };
    }
}
