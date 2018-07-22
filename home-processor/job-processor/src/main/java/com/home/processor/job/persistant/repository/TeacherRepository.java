package com.home.processor.job.persistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.home.processor.job.persistant.domain.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer>{

}
