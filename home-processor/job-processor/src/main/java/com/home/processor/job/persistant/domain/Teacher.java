package com.home.processor.job.persistant.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TB_TEACHER",schema="niko")
public class Teacher {

	@Id
	@GeneratedValue(generator = "prog_id_seq")
    @SequenceGenerator(name = "prog_id_seq", sequenceName = "prog_id_seq", schema = "niko", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	
	@Column(name = "teachername") 
	private String teacherName;

	@ManyToOne
	@JoinColumn(name = "student_id")
	private StudentTarget studentTarget;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public StudentTarget getStudentTarget() {
		return studentTarget;
	}

	public void setStudentTarget(StudentTarget studentTarget) {
		this.studentTarget = studentTarget;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((studentTarget == null) ? 0 : studentTarget.hashCode());
		result = prime * result + ((teacherName == null) ? 0 : teacherName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teacher other = (Teacher) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (studentTarget == null) {
			if (other.studentTarget != null)
				return false;
		} else if (!studentTarget.equals(other.studentTarget))
			return false;
		if (teacherName == null) {
			if (other.teacherName != null)
				return false;
		} else if (!teacherName.equals(other.teacherName))
			return false;
		return true;
	}
}
