import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class CreateSMTD implements Runnable {

	String directory;
	CountDownLatch latch;
	String sortingCriteria;

	public CreateSMTD(String directory, CountDownLatch latch, String sortingCriteria) {
		super();
		this.directory = directory;
		this.latch = latch;
		this.sortingCriteria = sortingCriteria;
	}

	@Override
	public void run() {

		List<File> files = CommonUtilities.getAllMTDFiles(directory);

		List<String> fileNameList = generateFileListBasedOnCriteria(files);

		try {
			createAndWriteToSMTDFile(fileNameList, directory);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {

			latch.countDown();
		}
	}

	private void createAndWriteToSMTDFile(List<String> fileNameList, String directory2)
			throws FileNotFoundException, UnsupportedEncodingException {

		directory2 = directory2 + directory2.substring(directory2.lastIndexOf("\\"));

		PrintWriter writer = new PrintWriter(directory2 + ".smtd", "UTF-8");

		for (String fileName : fileNameList) {
			writer.println(fileName);

		}
		writer.close();

	}

	// generating list of sorted file names based on criteria
	private List<String> generateFileListBasedOnCriteria(List<File> files) {

		List<String> list = new ArrayList<String>();

		// creating map for Filename and metadata value to be sorted on.
		Map<String, Integer> fileMap = new HashMap<String, Integer>();
		try {
			for (File file : files) {

				int[] metadata = CommonUtilities.scanMTDFile(file);
				int value = findMetadataForSortingCriteria(sortingCriteria, metadata);
				fileMap.put(file.getAbsolutePath(), value);

			}

			System.out.println("Current File Map: " + fileMap);

			Iterator<String> it = MapUtil.sortByValue(fileMap).keySet().iterator();

			while (it.hasNext()) {
				list.add(it.next());
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}

	private int findMetadataForSortingCriteria(String criteria, int[] metadata) {

		int metadataValue;
		if (criteria.equalsIgnoreCase("Words")) {
			metadataValue = metadata[0];
		} else if (criteria.equalsIgnoreCase("Letters")) {
			metadataValue = metadata[1];
		} else if (criteria.equalsIgnoreCase("Vowels")) {
			metadataValue = metadata[2];
		} else {
			metadataValue = metadata[3];
		}

		return metadataValue;
	}

}
