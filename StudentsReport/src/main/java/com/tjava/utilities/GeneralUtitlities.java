package com.tjava.utilities;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.web.multipart.MultipartFile;

import com.tjava.constants.StudentConstants;
import com.tjava.model.Students;

public class GeneralUtitlities {

	public static Students marshalDataToModel(MultipartFile file) throws JAXBException {

		File file1 = new File(StudentConstants.UPLOADED_FOLDER + file.getOriginalFilename());
		JAXBContext jaxbContext = JAXBContext.newInstance(Students.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Students students = (Students) jaxbUnmarshaller.unmarshal(file1);

		return students;
	}

}
