package com.tjava.Scanner.FileTypes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;

import com.tjava.Scanner.Utilities.CommonUtilities;

/**
 * Thread class to create MTD file.
 * 
 * @author tbhatn
 *
 */
public class CreateMTD implements Runnable {

	File file;
	CountDownLatch latch;

	public CreateMTD(File file, CountDownLatch latch) {
		super();
		this.file = file;
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			createMTD(file, CommonUtilities.scanFile(file));
			latch.countDown();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create a file and write content to it.
	 * 
	 * @param file
	 * @param metadataArr
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	private static void createMTD(File file, int[] metadataArr)
			throws FileNotFoundException, UnsupportedEncodingException {

		int dotIndex = file.getAbsolutePath().lastIndexOf('.');
		String absolute = file.getAbsolutePath().substring(0, dotIndex);

		PrintWriter writer = new PrintWriter(absolute + ".mtd", "UTF-8");
		writer.println("Words : " + metadataArr[0]);
		writer.println("Letters : " + metadataArr[1]);
		writer.println("Vowels : " + metadataArr[2]);
		writer.println("Special Characters : " + metadataArr[3]);
		writer.close();

	}

}
