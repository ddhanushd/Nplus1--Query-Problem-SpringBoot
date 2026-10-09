package com.kafka.nplusonedemo.repository;

import com.kafka.nplusonedemo.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
