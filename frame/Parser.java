package frame;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.ArrayList;

public class Parser {

	public static PSU[] parseWarehouse(String path) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));

			// assign indices to all items an save in PSU class
			String[] itemArr = reader.readLine().split(" ");
			PSU.itemMapping = new HashMap<>();
			for (int i = 0; i < itemArr.length; i++) {
				PSU.itemMapping.put(itemArr[i], i);
			}

			// skip empty line
			reader.readLine();

			// load PSU data
			ArrayList<PSU> psus = new ArrayList<>();
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] items = line.split(" ");
				psus.add(new PSU(items));
			}
			reader.close();

			PSU[] psuArr = new PSU[psus.size()];
			return psus.toArray(psuArr);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public static int[] parseOrder(String path) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String[] itemArr = reader.readLine().split(" ");
			reader.close();

			return PSU.indices(itemArr);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

}