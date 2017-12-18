package test.com.tjava.Scanner;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tjava.Scanner.Utilities.CommonUtilities;

public class CommonUtilitiesTest {

	@Before
	public void setup() throws IOException {
		Path path = Paths.get("D:\\Temp");
		// if directory exists?
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				// fail to create directory
				e.printStackTrace();
			}
		}
	}

	@Test
	public void scanFileTest() throws IOException {

		File file = new File("D:\\Temp\\tempText.txt");
		if (file.createNewFile()) {
			System.out.println("File is created!");
		} else {
			System.out.println("File already exists.");
		}

		PrintWriter writer = new PrintWriter(file.getAbsoluteFile(), "UTF-8");
		writer.println("1234%^");
		writer.close();

		int[] metadata = CommonUtilities.scanFile(file);
		file = null;
		Assert.assertEquals(1, metadata[0]);
	}

	@Test
	public void scanMTDFileTest() throws IOException {

		File file = new File("D:\\Temp\\tempText.mtd");
		if (file.createNewFile()) {
			System.out.println("File is created!");
		} else {
			System.out.println("File already exists.");
		}

		PrintWriter writer = new PrintWriter(file.getAbsoluteFile(), "UTF-8");

		writer.println("Words : 4");
		writer.println("Letters : 28");
		writer.println("Vowels : 4");
		writer.println("Special Characters : 5");
		writer.close();

		int[] metadata = CommonUtilities.scanMTDFile(file);
		Assert.assertEquals(4, metadata[0]);

	}

	/*
	 * @After public void tearDown() throws IOException {
	 * 
	 * FileUtils.deleteDirectory(new File("D:\\Temp")); }
	 */
}
