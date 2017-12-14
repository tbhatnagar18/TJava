package com.tjava.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.tjava.Scanner.FileTypes.CreateDMTD;
import com.tjava.Scanner.FileTypes.CreateMTD;
import com.tjava.Scanner.FileTypes.CreateSMTD;

public class CustomerScanner {

	public static void main(String... args) {

		File dir = new File("D:\\Ace");

		System.out.println("Please enter seconds after which cache needs to be cleared");
		Scanner sc = new Scanner(System.in);
		int seconds = Integer.parseInt(sc.nextLine());

		Date currentDate = null;

		List<File> files = null;
		List<String> directories = null;

		try {

			Path faxFolder = Paths.get("D:\\Ace");
			WatchService watchService = FileSystems.getDefault().newWatchService();
			faxFolder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

			boolean valid = true;
			do {
				WatchKey watchKey = watchService.take();

				for (WatchEvent event : watchKey.pollEvents()) {
					WatchEvent.Kind kind = event.kind();
					if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {

						if (null != currentDate) {
							CommonUtilities.deleteCacheIfExpired(currentDate, seconds);
						}

						currentDate = new Date();

						// method to find files with extension as txt or csv
						files = CustomerScanner.fileFinder(dir);

						// method to find all the directories inside and including particular directory
						directories = CustomerScanner.dirFinder(dir);

						// count no. of words, alphabets, etc. in a file and write metadata to .mtd file
						// for each directory and file
						if (files.size() > 0) {
							countAndCreateMTD(files);
						}

						// create dmtd files once all mtd files are created
						if (files.size() > 0) {
							createDMTDAndSMTD(directories, dir);
						}
					}
				}
				valid = watchKey.reset();

			} while (valid);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// keep polling over a location for the new files to find new files. If found
		// put it cache.

		// write .dmtd file sontaining agrrgated values of .mtd files

		// write .smtd containing sorted file names on parameter

		// cache refresh happens using property file values

		//

	}

	private static void createSMTD(File dir) {

	}

	private static List<String> dirFinder(File dir) throws IOException {

		List<String> directories = new ArrayList<String>();

		directories.add(dir.getCanonicalPath().toString());
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				directories.add(file.getCanonicalFile().toString());
			}
		}

		System.out.println("Directories to be searched" + directories);
		return directories;
	}

	private static void createDMTDAndSMTD(List<String> directories, File dir2)
			throws InterruptedException, FileNotFoundException {

		int count = 0;
		for (String dir : directories) {
			count++;
		}

		ExecutorService exec = Executors.newFixedThreadPool(count);
		CountDownLatch latch = new CountDownLatch(count * 2);

		createDMTDFile(directories, exec, latch);
		createSMTDFile(directories, dir2, exec, latch);

		latch.await();
		exec.shutdown();

	}

	private static void createSMTDFile(List<String> directories, File dir2, ExecutorService exec, CountDownLatch latch)
			throws FileNotFoundException {

		String sortingCriteria = findSortingCriteria(dir2);

		for (String dir : directories) {

			exec.execute((new CreateSMTD(dir, latch, sortingCriteria)));
		}

	}

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

		Scanner in = new Scanner(file);
		String line = in.nextLine();
		int len = line.length();

		return line.substring(8, len);
	}

	private static void createDMTDFile(List<String> directories, ExecutorService exec, CountDownLatch latch) {

		for (String dir : directories) {

			exec.execute((new CreateDMTD(dir, latch)));
		}

	}

	private static void countAndCreateMTD(List<File> files)
			throws FileNotFoundException, UnsupportedEncodingException, InterruptedException {

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

	// method to find unprocessed files with extension as txt or csv which are not
	// present in Cache
	public static List<File> fileFinder(File dirName) throws IOException {

		String[] extensions = new String[] { "txt", "csv" };
		System.out.println("Getting all .txt and .csv files in " + dirName.getCanonicalPath()
				+ " including those in subdirectories");

		List<File> files = (List<File>) FileUtils.listFiles(dirName, extensions, true);
		List<File> unprocessedFileList = new ArrayList<File>();
		for (File file : files) {

			if (!(FileCache.checkIfPresentInFileCache(file.getAbsolutePath(), file))) {
				unprocessedFileList.add(file);
			}
		}

		return unprocessedFileList;
	}

}
