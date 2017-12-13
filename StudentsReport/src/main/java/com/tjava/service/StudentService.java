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
import com.tjava.utilities.MultiThreadedStudentJSON;
import com.tjava.utilities.StudentUtilities;

@Service
public class StudentService {

	@Autowired
	private StudentDAO studentDAOimpl;

	@Autowired
	MultiThreadedStudentJSON multiThreadedStudentJSON;

	/*
	 * public void save(List<Student> student) {
	 * 
	 * for (Student s1 : student) { studentRepository.save(s1); } }
	 */

	/*
	 * public Student getById(String id) { return studentRepository.findOne(id); }
	 */

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
			StudentUtilities.generateJsonReports(multiThreadedStudentJSON, s1);

		}

	}

}

/*
 * public void generateJsonReports(Student students) {
 * 
 * ObjectMapper mapper = new ObjectMapper();
 * 
 * try { mapper.writerWithDefaultPrettyPrinter() .writeValue(new
 * File("reports\\" + students.getId() + "_student.json"), students); } catch
 * (JsonGenerationException e) {
 * 
 * } catch (JsonMappingException e) {
 * 
 * } catch (IOException e) {
 * 
 * } }
 */

/*
 * public List<Student> getStudents() { List<Student> students = new
 * ArrayList<>(); studentRepository.findAll().forEach(students::add); return
 * students; }
 */

/*
 * public int calculateTotal(Set<Subject> subjects) { int total =
 * subjects.stream().mapToInt(t -> t.getSubject_marks()).sum(); return total; }
 */

/*
 * public String setStatus(Set<Subject> subjects) {
 * 
 * Iterator<Subject> itr = subjects.iterator(); while(itr.hasNext()) { Subject
 * sub = itr.next(); if(sub.getSubject_marks() < 35) { return "FAIL"; } else
 * return "PASS"; } return "PASS";
 * 
 * Stream<Subject> subject = subjects.stream().filter(t -> t.getSubject_marks()
 * < 35); if (subject.count() > 0) return "FAIL"; else return "PASS";
 * 
 * }
 */

/*
 * public Students marshalDataToModel(MultipartFile file) throws JAXBException {
 * 
 * File file1 = new File(StudentConstants.UPLOADED_FOLDER +
 * file.getOriginalFilename()); JAXBContext jaxbContext =
 * JAXBContext.newInstance(Students.class); Unmarshaller jaxbUnmarshaller =
 * jaxbContext.createUnmarshaller(); Students students = (Students)
 * jaxbUnmarshaller.unmarshal(file1);
 * 
 * return students; }
 */

/*
 * @Override public void calculateRank(List<Student> students) {
 * students.stream().filter(t -> t.getSTATUS() ==
 * "PASS").collect(Collectors.toList()); students.stream().sorted();
 * students.stream().findFirst().get().setRank(1); }
 */
/*
 * @Override public void setStatus(Student student) { if
 * (student.getMarks_chemistry() < 35 || student.getMarks_english() < 35 ||
 * student.getMarks_german() < 35 || student.getMarks_maths() < 35 ||
 * student.getMarks_physics() < 35 ) { student.setSTATUS("FAIL"); } else
 * student.setSTATUS("PASS"); }
 */
