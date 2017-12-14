package com.tjava.Scanner.FileTypes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import com.tjava.Scanner.Utilities.CommonUtilities;

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
			latch.countDown();

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			latch.countDown();
		}

	}

	private int[] calculateMetadataForDirectory(String directory2) throws FileNotFoundException {
		int words = 0, letters = 0, vowel = 0, specialCharCount = 0;
		int[] metadataArr = new int[4];

		List<File> files = CommonUtilities.getAllMTDFiles(directory);

		for (File file : files) {

			words = words + calculateTotalWords(file);
			letters = letters + calculateTotalLetters(file);
			vowel = vowel + calculateTotalVowel(file);
			specialCharCount = specialCharCount + calculateTotalSpecialCharCount(file);

		}
		metadataArr[0] = words;
		metadataArr[1] = letters;
		metadataArr[2] = vowel;
		metadataArr[3] = specialCharCount;

		return metadataArr;
	}

	private void createDMTDFile(String directory2, int[] metadataArr)
			throws FileNotFoundException, UnsupportedEncodingException {

		directory2 = directory2 + directory2.substring(directory2.lastIndexOf("\\"));

		PrintWriter writer = new PrintWriter(directory2 + ".dmtd", "UTF-8");
		writer.println("Number of Words : " + metadataArr[0]);
		writer.println("Number of Letters : " + metadataArr[1]);
		writer.println("Number of Vowels : " + metadataArr[2]);
		writer.println("Number of Words Special Characters (@, #, $, *) : " + metadataArr[3]);
		writer.close();

	}

	private int calculateTotalSpecialCharCount(File file) throws FileNotFoundException {

		Scanner in = new Scanner(file);
		in.nextLine();
		in.nextLine();
		in.nextLine();
		String str = in.nextLine();
		int index = str.lastIndexOf(": ");

		return Integer.parseInt(str.substring(index + 2));

	}

	private int calculateTotalVowel(File file) throws FileNotFoundException {
		Scanner in = new Scanner(file);
		in.nextLine();
		in.nextLine();
		String str = in.nextLine();
		int index = str.lastIndexOf(": ");

		return Integer.parseInt(str.substring(index + 2));

	}

	private int calculateTotalLetters(File file) throws FileNotFoundException {
		Scanner in = new Scanner(file);
		in.nextLine();
		String str = in.nextLine();
		int index = str.lastIndexOf(": ");

		return Integer.parseInt(str.substring(index + 2));

	}

	private int calculateTotalWords(File file) throws FileNotFoundException {
		Scanner in = new Scanner(file);
		String str = in.nextLine();
		int index = str.lastIndexOf(": ");


		return Integer.parseInt(str.substring(index + 2));

	}

}
