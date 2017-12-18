package test.com.tjava.Scanner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tjava.Scanner.Utilities.SearchUtility;

public class SearchUtilityTest {

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

		File file = new File("D:\\Temp\\temp.txt");
		if (file.createNewFile()) {
			System.out.println("File is created!");
		} else {
			System.out.println("File already exists.");
		}
	}

	@After
	public void tearDown() throws IOException {
		FileUtils.deleteDirectory(new File("D:\\Temp"));
	}

	@Test
	public void fileFinderTest() throws IOException {

		File dir = new File("D:\\Temp");
		List<File> files = SearchUtility.fileFinder(dir);
		Assert.assertEquals(1, files.size());
	}

	@Test
	public void dirFinderTest() throws IOException {

		File dir = new File("D:\\Temp");
		List<String> directoryNames = SearchUtility.dirFinder(dir);
		Assert.assertEquals(1, directoryNames.size());
	}

	@Test
	public void getAllMTDFilesTest() throws IOException {

		File file = new File("D:\\Temp\\temp.mtd");
		file.createNewFile();
		List<File> fileNames = SearchUtility.getAllMTDFiles("D:\\Temp");
		Assert.assertNotNull(fileNames);
	}

}
