import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

// Singleton FileCache class
public class FileCache {

	private static ConcurrentHashMap<String, File> fileCache;

	private FileCache() {
	}

	// static block initialization for exception handling
	static {
		try {
			fileCache = new ConcurrentHashMap<String, File>();
		} catch (Exception e) {
			throw new RuntimeException("Exception occured in creating singleton instance");
		}
	}

	public static ConcurrentHashMap<String, File> getInstance() {
		return fileCache;
	}

	// method to find files with extension as txt or csv
	/**
	 * @param filePath
	 * @param file
	 * @return true if file is present in the Cache
	 * @throws IOException
	 */
	public static boolean checkIfPresentInFileCache(String filePath, File file) throws IOException {

		if (!(fileCache.keySet().contains(filePath))) {
			fileCache.put(filePath, file);
			return false;
		}

		return true;

	}

	// method to return instance of File present in Cache for absolute file path
	public static File getFileFromFileCache(String filePath) {
		return fileCache.get(filePath);
	}

	// method to clear cache contents in case of time expiry
	public static void clearFileCache() {
		fileCache.clear();
	}

}
