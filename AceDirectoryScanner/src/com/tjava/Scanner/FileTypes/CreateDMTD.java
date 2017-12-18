package com.tjava.Scanner.FileTypes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import com.tjava.Scanner.Utilities.CommonUtilities;
import com.tjava.Scanner.Utilities.SearchUtility;

/**
 * Thread Create metadata to create .txt and .csv files
 * 
 * @author tbhatn
 *
 */
public class CreateDMTD implements Runnable {

	String directory;
	CountDownLatch latch;

	public CreateDMTD(String directory, CountDownLatch latch) {
		super();
		this.directory = directory;
		this.latch = latch;
	}

	@Override
	public void run() {

		int[] metadataArr;
		try {
			metadataArr = calculateMetadataForDirectory(directory);
			createDMTDFile(directory, metadataArr);

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			latch.countDown();
		}

	}

	/**
	 * Method to generate metadata for each .txt and .csv files.
	 * 
	 * @param directory2
	 * @return
	 * @throws FileNotFoundException
	 */
	private int[] calculateMetadataForDirectory(String directory2) throws FileNotFoundException {
		int words = 0, letters = 0, vowel = 0, specialCharCount = 0;
		int[] metadataArr = new int[4];

		List<File> files = SearchUtility.getAllMTDFiles(directory);

		for (File file : files) {

			words = words + CommonUtilities.calculateTotalWords(file);
			letters = letters + CommonUtilities.calculateTotalLetters(file);
			vowel = vowel + CommonUtilities.calculateTotalVowel(file);
			specialCharCount = specialCharCount + CommonUtilities.calculateTotalSpecialCharCount(file);

		}
		metadataArr[0] = words;
		metadataArr[1] = letters;
		metadataArr[2] = vowel;
		metadataArr[3] = specialCharCount;

		return metadataArr;
	}

	/**
	 * method to create dmtd files based on mtd files
	 * 
	 * @param directory2
	 * @param metadataArr
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	private void createDMTDFile(String directory2, int[] metadataArr)
			throws FileNotFoundException, UnsupportedEncodingException {

		directory2 = directory2 + directory2.substring(directory2.lastIndexOf("\\"));

		PrintWriter writer = new PrintWriter(directory2 + ".dmtd", "UTF-8");
		writer.println("Words : " + metadataArr[0]);
		writer.println("Letters : " + metadataArr[1]);
		writer.println("Vowels : " + metadataArr[2]);
		writer.println("Special Characters" + metadataArr[3]);
		writer.close();

	}

}
