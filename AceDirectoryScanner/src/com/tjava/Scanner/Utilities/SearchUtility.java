package com.tjava.Scanner.Utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.tjava.Scanner.Cache.FileCache;

public class SearchUtility {

	/**
	 * method to find unprocessed files with extension as txt or csv including sub
	 * directories which are not present in cache.
	 * 
	 * @param dirName
	 * @return List<File>
	 * @throws IOException
	 */
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

	/**
	 * method to find all the directories inside and including particular
	 * directory
	 * 
	 * @param dir
	 * @return List<String>
	 * @throws IOException
	 */
	public static List<String> dirFinder(File dir) throws IOException {

		List<String> directories = new ArrayList<String>();

		directories.add(dir.getCanonicalPath().toString());
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				directories.add(file.getCanonicalFile().toString());
			}
		}

		return directories;
	}
	
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

}
