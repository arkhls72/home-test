package com.home.processor.job.persistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.home.processor.job.persistant.domain.Student;
import com.home.processor.job.persistant.domain.Teacher;

@Repository
public interface StudentRepository  extends JpaRepository<Student, Integer>{

}
