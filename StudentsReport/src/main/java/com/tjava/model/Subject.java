package com.tjava.model;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.xml.bind.annotation.*;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Subject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement(name="SubjectID")
	private int subject_id;
	
	@XmlElement(name="SubjectName")
	private String subject_name;
	
	@XmlElement(name="SubbjectMarks")
	private int subject_marks;
	
	@ManyToOne
	@JoinColumn(name = "student_id")
	@JsonBackReference
	private Student student;

	public int getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public int getSubject_marks() {
		return subject_marks;
	}

	public void setSubject_marks(int subject_marks) {
		this.subject_marks = subject_marks;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
}
