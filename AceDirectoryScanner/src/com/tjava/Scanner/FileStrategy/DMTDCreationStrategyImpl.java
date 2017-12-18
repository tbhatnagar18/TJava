package com.tjava.Scanner.FileStrategy;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tjava.Scanner.FileTypes.CreateDMTD;
import com.tjava.Scanner.Utilities.SearchUtility;

public class DMTDCreationStrategyImpl implements FileCreationStrategy {

	String fileType;
	File defaultDir;

	public DMTDCreationStrategyImpl(String fileType, File defaultDir) {

		this.fileType = fileType;
		this.defaultDir = defaultDir;

	}

	@Override
	public void createFile() throws IOException, InterruptedException {

		List<File> files = SearchUtility.fileFinder(defaultDir);
		
		if(files.size()>0){

		List<String> directories = SearchUtility.dirFinder(defaultDir);

		int count = 0;
		for (String dir : directories) {
			count++;
		}

		ExecutorService exec = Executors.newFixedThreadPool(count);
		CountDownLatch latch = new CountDownLatch(count);

		for (String dir : directories) {

			exec.execute((new CreateDMTD(dir, latch)));
		}

		latch.await();
		exec.shutdown();}

	}

}
