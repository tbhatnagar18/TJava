package com.tjava.DAO;

import java.util.List;
import java.util.Set;

import com.tjava.model.Student;
import com.tjava.model.Subject;

public interface StudentDAO {

	public void persistStudents(List<Student> student);

	public void persistStudent(Student student);

	public List<Student> getStudentsList();

	public String setStatus(Set<Subject> subjects);

	public int calculateTotalMarks(Set<Subject> subjects);

	public void calculateRankOfStudents();
	
}
