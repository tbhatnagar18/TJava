package com.tjava.utilities;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjava.model.Student;

public class StudentUtilities {

	public static void deleteOldJSONFiles() {

		File files = new File("reports\\");
		System.out.println("deleting old files " + files.toString());
		if (files.isDirectory()) {
			if (files.listFiles().length > 0) {
				File[] f = files.listFiles();
				int n = f.length;
				for (int i = 0; i <= n - 1; i++) {
					f[i].delete();
				}

			}
		}

	}

	public static void generateJsonReports(MultiThreadedStudentJSON studentJSon, Student students)
			throws JsonGenerationException, JsonMappingException, IOException {

		/*
		 * ExecutorService executorService = Executors.newFixedThreadPool(1);
		 * executorService.submit(studentJSon); executorService.shutdown();
		 */

		ObjectMapper mapper = new ObjectMapper();

		mapper.writerWithDefaultPrettyPrinter().writeValue(new File("reports\\" + students.getId() + "_student.json"),
				students);
	}

}
