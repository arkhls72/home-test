package com.home.processor.job.config;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.home.processor.job.dto.StudentDTO;

public class StudentMapper implements RowMapper<StudentDTO> {
     

        public StudentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            StudentDTO student=null;
            if (rs !=null) {
                student= new StudentDTO();
                student.setId(rs.getString("id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
            }
          

            return student;
        }
}
