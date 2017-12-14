package com.tjava.utilities;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjava.model.Student;
import com.tjava.repository.StudentRepository;

@Service
public class MultiThreadedStudentJSON implements Callable<String> {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public String call() throws Exception {

		List<Student> students = studentRepository.findAll();

		ObjectMapper objectMapper = new ObjectMapper();

		students.forEach(student -> {
			Student stu = (Student) student;
			try {
				/*objectMapper.writeValue(new File("reports\\" + stu.getId() + "_student.json"), student);*/
				objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("reports\\" + stu.getId() + "_student.json"),
						students);
				
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		return "path";
	}

	void getStudentDBData() {
	}
}
