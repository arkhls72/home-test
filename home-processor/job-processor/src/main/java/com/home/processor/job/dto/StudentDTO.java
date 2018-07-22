package com.home.processor.job.dto;

import java.io.Serializable;

public class StudentDTO implements Serializable {

    private Integer id;
    private String email;
    private String name;

    public StudentDTO() {
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}