import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.security.Key;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Test {

	public static void main(String args[]) throws IOException, InterruptedException {/*

		Path faxFolder = Paths.get("D:\\Ace");
		WatchService watchService = FileSystems.getDefault().newWatchService();
		faxFolder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

		boolean valid = true;
		do {
			WatchKey watchKey = watchService.take();

			for (WatchEvent event : watchKey.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
					
					ConcurrentMap<Key, Graph> graphs = new MapMaker()
							   .concurrencyLevel(4)
							   .softKeys()
							   .weakValues()
							   .maximumSize(10000)
							   .expiration(10, TimeUnit.MINUTES)
							   .makeComputingMap(
							       new Function<Key, Graph>() {
							         public Graph apply(Key key) {
							           return createExpensiveGraph(key);
							         }
							       });
					
				}
			}
			valid = watchKey.reset();

		} while (valid);
	*/}

	/*
	 * PrintWriter writer = new PrintWriter(absolute + ".mtd", "UTF-8");
	 * writer.println("Number of Words : "); writer.println("Number of Letters : ");
	 * writer.println("Number of Vowels : ");
	 * writer.println("Number of Words Special Characters (@, #, $, *) : ");
	 * writer.close();
	 */
}
