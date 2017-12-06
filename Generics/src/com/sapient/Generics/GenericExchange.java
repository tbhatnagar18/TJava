package com.sapient.Generics;

import java.util.Arrays;
import java.util.List;

// Write a generic method to exchange the positions of two different elements in an array.

public class GenericExchange {

	public static void main(String[] args) {
		Object arr[] = { "one", "two", "three" };
		exchange(arr[0], arr[2], arr);

	}

	private static <T extends Object> void exchange(T x, T y, T... arr) {

		T temp;
		int xIndex = 0, yIndex = 0;

		for (int i = 0; i < arr.length; i++) {

			if (arr[i].equals(x)) {

				xIndex = i;
			}

			if (arr[i].equals(y)) {

				yIndex = i;

			}
		}

		temp = arr[xIndex];
		arr[xIndex] = arr[yIndex];
		arr[yIndex] = temp;

		System.out.println(Arrays.asList(arr));
	}

	public static void print(List<? extends Number> list) {
		for (Number n : list)
			System.out.print(n + " ");
		System.out.println();
	}

	void maximal(List<? extends Number> list) {

		int l= list.size();
		int c=0;
		Number max=0;
		for (Number n: list) {
			
			if((n.doubleValue()>max.doubleValue())&&(c<l)) {
				max=n;
			}
			c++;
		}
	}

}
