package com.tjava.DAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tjava.model.Student;
import com.tjava.model.Subject;
import com.tjava.repository.StudentRepository;

@Service
public class StudentDAOImpl implements StudentDAO {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public void persistStudents(List<Student> student) {

		for (Student s1 : student) {
			persistStudent(s1);
		}

	}

	@Override
	public void persistStudent(Student student) {

		studentRepository.save(student);
	}

	@Override
	public List<Student> getStudentsList() {
		List<Student> students = new ArrayList<>();
		studentRepository.findAll().forEach(students::add);
		return students;
	}

	@Override
	public String setStatus(Set<Subject> subjects) {
		Stream<Subject> subject = subjects.stream().filter(t -> t.getSubject_marks() < 35);
		if (subject.count() > 0)
			return "FAIL";
		else
			return "PASS";
	}

	@Override
	public int calculateTotalMarks(Set<Subject> subjects) {
		int total = subjects.stream().mapToInt(t -> t.getSubject_marks()).sum();
		return total;
	}

	@Override
	public void calculateRankOfStudents() {

		Collections.sort(getStudentsList());

		// counting number of students
		int counter = 0;
		for (Student s1 : getStudentsList()) {
			counter++;
		}

		// setting rank for each student
		for (Student s1 : getStudentsList()) {
			s1.setRank(counter);
			counter--;
			persistStudent(s1);
		}

	}

}
