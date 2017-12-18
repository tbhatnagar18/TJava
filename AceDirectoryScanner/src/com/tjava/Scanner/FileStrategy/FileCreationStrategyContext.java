package com.tjava.Scanner.FileStrategy;

import java.io.IOException;

public class FileCreationStrategyContext {

	// Reference to the strategy.
	FileCreationStrategy fileCreationStrategy;

	// Register reference to strategy on construction.
	FileCreationStrategyContext(FileCreationStrategy fileCreationStrategy) {
		this.fileCreationStrategy = fileCreationStrategy;
	}

	public void fileCreationContetxt() throws IOException, InterruptedException {

		// Call strategy's method.
		fileCreationStrategy.createFile();

	}

}
