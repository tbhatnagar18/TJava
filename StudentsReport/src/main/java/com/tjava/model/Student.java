package com.tjava.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "Student")
public class Student implements Comparable<Student>{

	@Id
	@Column(name = "student_id")
	@XmlElement(name = "ID")
	private int student_id;

	@XmlElement(name = "Name")
	private String student_name;

	@Transient
	private int total_marks;

	@Transient
	private String STATUS;

	@Transient
	private int rank;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@XmlElementWrapper(name = "Subjects")
	@XmlElement(name = "subject")
	private Set<Subject> listOfsubjects;

	public int getId() {
		return student_id;
	}

	public void setId(int id) {
		this.student_id = id;
	}

	public String getName() {
		return student_name;
	}

	public void setName(String name) {
		this.student_name = name;
	}

	public int getTotal_marks() {
		return total_marks;
	}

	public void setTotal_marks(int total_marks) {
		this.total_marks = total_marks;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public Set<Subject> getSubject() {
		return listOfsubjects;
	}

	public void setSubject(Set<Subject> subject) {
		this.listOfsubjects = subject;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public int compareTo(Student o) {
		 if (o == null) {
		      return 0;
		    }
		    int c = Integer.valueOf(total_marks).compareTo(o.total_marks);
		    if (c != 0) {
		      return c;
		    }
		return 0;
	}
}
