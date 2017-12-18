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
import java.util.Date;
import java.util.Scanner;

import com.tjava.Scanner.Cache.FileCache;
import com.tjava.Scanner.FileStrategy.DMTDCreationStrategyImpl;
import com.tjava.Scanner.FileStrategy.FileCreationStrategy;
import com.tjava.Scanner.FileStrategy.MTDCreationStrategyImpl;
import com.tjava.Scanner.FileStrategy.SMTDCreationStrategyImpl;
import com.tjava.Scanner.Utilities.CommonUtilities;
import com.tjava.Scanner.Utilities.SearchUtility;

public class CustomerScanner {

	public static void main(String... args) {

		Date currentDate = null;

		File dir = new File("D:\\Ace");

		// inputting number of seconds from the user
		System.out.println("Please enter seconds after which cache needs to be cleared");
		Scanner sc = new Scanner(System.in);
		int seconds = Integer.parseInt(sc.nextLine());
		System.out.println("Please add files to " + dir + " directory to be processed");

		try {

			Path faxFolder = Paths.get("D:\\Ace");

			// initiating watcher for continuous polling of the program
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

						FileCreationStrategy fileCreationStrategy1 = new MTDCreationStrategyImpl(".mtd", dir);
						fileCreationStrategy1.createFile();

						FileCreationStrategy fileCreationStrategy2 = new DMTDCreationStrategyImpl(".dmtd", dir);
						fileCreationStrategy2.createFile();

						FileCreationStrategy fileCreationStrategy3 = new SMTDCreationStrategyImpl(".smtd", dir);
						fileCreationStrategy3.createFile();

						// add files to the cache after all dmtd and smtd file
						// creation.
						FileCache.addProcessedFilesToCache(SearchUtility.fileFinder(dir));
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
		} finally {
			dir = null;
		}

		// keep polling over a location for the new files to find new files. If
		// found
		// put it cache.

		// write .dmtd file sontaining agrrgated values of .mtd files

		// write .smtd containing sorted file names on parameter

		// cache refresh happens using property file values

		//

	}

}
