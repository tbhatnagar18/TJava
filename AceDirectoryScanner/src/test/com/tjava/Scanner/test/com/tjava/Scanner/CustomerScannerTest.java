/**
 * 
 */
package test.com.tjava.Scanner;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.tjava.Scanner.CustomerScanner;
import com.tjava.Scanner.Utilities.CommonUtilities;

/**
 * @author tbhatn
 *
 */

public class CustomerScannerTest {

	@Mock
	CommonUtilities commonUtilities;

	@Mock
	WatchKey watchKey;

	@Mock
	WatchService watchService;

	@Mock
	FileSystem fileSystems;

	@Before
	public void setUp() throws Exception {
		Mockito.when(FileSystems.getDefault()).thenReturn(fileSystems);
		Mockito.when(fileSystems.newWatchService()).thenReturn(watchService);
		Mockito.when(watchService.take()).thenReturn(watchKey);

	}

	@Test
	public void testMain() {

		CustomerScanner.main("test");
	}

}
