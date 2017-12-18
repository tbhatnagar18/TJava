package com.tjava.Scanner.Cache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

// Singleton FileCache class
public class FileCache {

	private static ConcurrentHashMap<String, File> fileCache;

	private FileCache() {
	}

	// static block initialization for exception handling
	static {
		try {

			fileCache = getInstance();
		} catch (Exception e) {
			throw new RuntimeException("Exception occured in creating singleton instance");
		}
	}

	/**
	 * initializing the cache is object is null, otherwise returns the old
	 * instance.
	 * 
	 * @return fileCache
	 */
	public static ConcurrentHashMap<String, File> getInstance() {

		if (null == fileCache) {
			fileCache = new ConcurrentHashMap<String, File>();
		}
		return fileCache;
	}

	/**
	 * method to find files with extension as txt or csv
	 * 
	 * @param filePath
	 * @param file
	 * @return true if file is present in the Cache
	 * @throws IOException
	 */
	public static boolean checkIfPresentInFileCache(String filePath, File file) throws IOException {

		if (!(fileCache.keySet().contains(filePath))) {
			// fileCache.put(filePath, file);
			return false;
		}

		return true;

	}

	/**
	 * method to return instance of File present in Cache for absolute file path
	 * 
	 * @param filePath
	 * @return File
	 */
	public static File getFileFromFileCache(String filePath) {
		return fileCache.get(filePath);
	}

	// method to clear cache contents in case of time expiry
	public static void clearFileCache() {
		fileCache.clear();
	}

	/**
	 * Add files to the cache after all dmtd and smtd file creation.
	 * 
	 * @param files
	 * @throws IOException
	 */
	public static void addProcessedFilesToCache(List<File> files) throws IOException {

		List<File> unprocessedFileList = new ArrayList<File>();
		for (File file : files) {

			if (!(FileCache.checkIfPresentInFileCache(file.getAbsolutePath(), file))) {
				unprocessedFileList.add(file);
			}
		}

	}

}
