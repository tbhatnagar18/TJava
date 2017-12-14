package com.tjava.service;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.tjava.DAO.StudentDAO;
import com.tjava.model.Student;
import com.tjava.model.Students;
import com.tjava.utilities.GeneralUtitlities;
import com.tjava.utilities.StudentUtilities;

@Service
public class StudentService {

	@Autowired
	private StudentDAO studentDAOimpl;

	public void uploadingDataAndGeneratingReport(MultipartFile file)
			throws JAXBException, JsonGenerationException, JsonMappingException, IOException {

		// code to marshal xml file to Model objects
		Students students = GeneralUtitlities.marshalDataToModel(file);

		// code to persist data
		studentDAOimpl.persistStudents(students.getStudents());

		// code to delete the existing json files from resource folder
		StudentUtilities.deleteOldJSONFiles();

		// code to generate JSON report
		// iterating over student list
		for (Student s1 : studentDAOimpl.getStudentsList()) {

			// setting status of each student based on subject marks
			s1.setSTATUS(studentDAOimpl.setStatus(s1.getSubject()));

			// calculating total marks for each student
			s1.setTotal_marks(studentDAOimpl.calculateTotalMarks(s1.getSubject()));

			// persisting the updated objects in database
			studentDAOimpl.persistStudent(s1);
		}

		// calculating rank for each student
		studentDAOimpl.calculateRankOfStudents();

		for (Student s1 : studentDAOimpl.getStudentsList()) {
			// generating JSON report
			StudentUtilities.generateJsonReports(s1);

		}

	}

}

