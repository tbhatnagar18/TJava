package com.tjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tjava.model.Student;

public interface StudentRepository extends JpaRepository<Student, String> {

}
