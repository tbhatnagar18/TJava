import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class MainClass {

	public static void main(String... args) {

		// count no. of words, alphabets, etc. in a file.

		// keep polling over a location for the new files.
		
		// write metadata to .mtd file for each directory and file	
		
		// write .dmtd file sontaining agrrgated values of .mtd files
		
		// write .smtd containing sorted file names on parameter
		
		// cache refresh happens, sorting mechanism in property file
		
		// 

		File dir = new File("D:/");
		List<File> files = null;
		try {
			files = MainClass.finder(dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// method to find files with extension as txt or csv
	public static List<File> finder(File dirName) throws IOException {

		String[] extensions = new String[] { "txt", "csv" };
		System.out.println("Getting all .txt and .jsp files in " + dirName.getCanonicalPath()
				+ " including those in subdirectories");

		List<File> files = (List<File>) FileUtils.listFiles(dirName, extensions, true);
		for (File file : files) {
			System.out.println("file: " + file.getCanonicalPath());
		}

		return files;
	}

}
