package com.tjava.Scanner.FileStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FilenameUtils;

import com.tjava.Scanner.FileTypes.CreateSMTD;
import com.tjava.Scanner.Utilities.SearchUtility;

public class SMTDCreationStrategyImpl implements FileCreationStrategy {

	String fileType;
	File defaultDir;

	public SMTDCreationStrategyImpl(String fileType, File defaultDir) {

		this.fileType = fileType;
		this.defaultDir = defaultDir;

	}

	@Override
	public void createFile() throws InterruptedException, IOException {

		List<File> files = SearchUtility.fileFinder(defaultDir);

		if (files.size() > 0) {

			List<String> directories = SearchUtility.dirFinder(defaultDir);

			int count = 0;
			for (String dir : directories) {
				count++;
			}

			ExecutorService exec = Executors.newFixedThreadPool(count);
			CountDownLatch latch = new CountDownLatch(count);

			String sortingCriteria = findSortingCriteria(defaultDir);

			for (String dir : directories) {

				exec.execute((new CreateSMTD(dir, latch, sortingCriteria)));
			}

			latch.await();
			exec.shutdown();
		}

	}

	/**
	 * Method to find the sorting criteria from the peoperty files.
	 * 
	 * @param dir2
	 * @return
	 * @throws FileNotFoundException
	 */
	private static String findSortingCriteria(File dir2) throws FileNotFoundException {

		File file = null;
		int index;
		for (File fi : dir2.listFiles()) {

			String ext1 = FilenameUtils.getExtension(fi.getAbsolutePath());

			if ((ext1).equalsIgnoreCase("properties")) {
				file = fi;
				break;

			}
		}

		if (null != file) {
			Scanner in = new Scanner(file);
			String line = in.nextLine();
			int len = line.length();

			return line.substring(8, len);
		} else {
			throw new FileNotFoundException("No property file found while creating SMDT file");
		}
	}

}
