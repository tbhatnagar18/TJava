package com.sapient.GenericsTest;

import java.util.ArrayList;
import java.util.List;

public class GenericsTest {

	public static void main(String[] args) {

		List<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i <= 10; i++) {

			ArrayList<Integer> row = new ArrayList<Integer>();

			for (int j = 0; j <= 10; j++) {
				row.add(i * j);
			}

			matrix.add(row);
		}

		for (int i = 0; i < matrix.size(); i++)
			System.out.println(matrix.get(i));
	}

}
