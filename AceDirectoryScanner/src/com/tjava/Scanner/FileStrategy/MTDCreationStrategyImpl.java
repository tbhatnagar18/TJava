package com.tjava.Scanner.FileStrategy;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tjava.Scanner.FileTypes.CreateMTD;
import com.tjava.Scanner.Utilities.SearchUtility;

public class MTDCreationStrategyImpl implements FileCreationStrategy {

	String fileType;
	File defaultDir;

	public MTDCreationStrategyImpl(String fileType, File defaultDir) {

		this.fileType = fileType;
		this.defaultDir = defaultDir;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tjava.Scanner.FileStrategy.FileCreationStrategy#createFile()
	 * 
	 * count no. of words, alphabets, etc. in a file and write metadata to .mtd
	 * file for each directory and file
	 */
	@Override
	public void createFile() throws IOException, InterruptedException {

		List<File> files = SearchUtility.fileFinder(defaultDir);

		if (files.size() > 0) {

			int count = 0;
			for (File file : files) {
				count++;
			}

			ExecutorService exec = Executors.newFixedThreadPool(count);
			CountDownLatch latch = new CountDownLatch(count);

			for (File file : files) {
				exec.execute(new CreateMTD(file, latch));
			}

			latch.await();
			exec.shutdown();
		}

	}

}
