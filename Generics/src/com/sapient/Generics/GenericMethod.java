package com.sapient.Generics;

import java.util.ArrayList;
import java.util.List;

public class GenericMethod {

	public static void main(String[] args) {
		List<String> l = returnAsList("Timon", "Pumba");
		System.out.println(l);
	}

	private static <T> List<T> returnAsList(T... arr) {

		int len = arr.length;

		List<T> list = new ArrayList<T>();
		for (int i = 0; i < len; i++) {
			list.add(arr[i]);
		}

		return list;
	}

}
