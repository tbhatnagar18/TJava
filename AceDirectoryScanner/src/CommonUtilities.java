import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CommonUtilities {

	public static final String FINAL_CHAR_REGEX = "[!@#$%^&*()[\\\\]|;',./{}\\\\\\\\:\\\"<>?]";

	public static List<File> getAllMTDFiles(String dir) {

		List<File> mtdFile = new ArrayList<File>();

		File directory = new File(dir);

		// get all the files from a directory
		File[] fList = directory.listFiles();

		for (File file : fList) {
			if (file.isFile()) {
				if (file.getAbsolutePath().endsWith(".mtd")) {
					mtdFile.add(file);
				}
			}
		}

		return mtdFile;

	}

	// count no. of words, alphabets, etc. in a file
	public static int[] scanFile(File file) throws FileNotFoundException, UnsupportedEncodingException {
		int words = 0, letters = 0, vowel = 0, specialCharCount = 0;

		// array will contain number of word at position 0, letters at position 1 and so
		// on.
		int[] metadataArr = new int[4];

		Scanner in = new Scanner(file);

		in = new Scanner(file);

		while (in.hasNext()) {
			in.next();
			words++;
		}

		metadataArr[0] = words;

		in = new Scanner(file);
		while (in.hasNextLine()) {
			String str = in.nextLine();
			for (int i = 0; i < str.length(); i++) {
				if (Character.isUpperCase(str.charAt(i))) {
					letters++;
					if (isVowel(str.charAt(i))) {
						vowel++;
					}
				}
				if (Character.isLowerCase(str.charAt(i))) {
					letters++;
					if (isVowel(str.charAt(i))) {
						vowel++;
					}
				}
			}
		}

		metadataArr[1] = letters;
		metadataArr[2] = vowel;

		in = new Scanner(file);
		while (in.hasNextLine()) {
			specialCharCount = specialCharCount + in.nextLine().split(FINAL_CHAR_REGEX, -1).length - 1;
		}

		metadataArr[3] = specialCharCount;

		// write metadata to .mtd file for each directory and file

		return metadataArr;

	}

	private static boolean isVowel(char ch) {
		if ((ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u' || ch == 'A' || ch == 'E' || ch == 'I'
				|| ch == 'O' || ch == 'U')) {
			return true;
		}
		return false;
	}

	public static int[] scanMTDFile(File file) throws FileNotFoundException {

		int[] metadata = new int[4];

		Scanner sc = new Scanner(file);

		while (sc.hasNext()) {
			String str = sc.nextLine();
			String[] str1 = str.split(":");
			if ((str1[0].substring(0, str1[0].length() - 1).equalsIgnoreCase("Words"))) {
				metadata[0] = Integer.parseInt(str1[1].substring(1));
			}
			if ((str1[0].substring(0, str1[0].length() - 1).equalsIgnoreCase("Letters"))) {
				metadata[1] = Integer.parseInt(str1[1].substring(1));
			}
			if ((str1[0].substring(0, str1[0].length() - 1).equalsIgnoreCase("Vowels"))) {
				metadata[2] = Integer.parseInt(str1[1].substring(1));
			} else {
				if ((str1[0].substring(0, str1[0].length() - 1).equalsIgnoreCase("Special Characters"))) {
					metadata[3] = Integer.parseInt(str1[1].substring(1));
				}
			}

		}

		return metadata;
	}

	// method to delete cache is expired certain seconds
	public static void deleteCacheIfExpired(Date currentDate, int seconds) {

		int timeDiff = (int) ((new Date().getTime() - currentDate.getTime()) / 1000);

		if (timeDiff > seconds) {
			FileCache.clearFileCache();
		}
	}

}
