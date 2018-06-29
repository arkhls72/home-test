package com.home.processor.job.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Student_Target")
public class Student {
    
    @Id
    @Column(name = "id")
    private String id;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "name")
    private String name;

    public Student() {
        
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}