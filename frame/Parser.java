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

			// ------------ LOAD ITEMS ------------ \\
			// assign indices to all items and save in PSU class
			String[] itemArr = reader.readLine().split(" ");
			// remove previous mappings from item names to IDs
			PSU.resetItemMapping();
			// build new mapping for each item found
			for (int i = 0; i < itemArr.length; i++) {
				PSU.addItemMapping(itemArr[i], i);
			}

			// skip empty line, fail if no further lines are present (file does not contain PSU data)
			if (reader.readLine() == null) {
				return null;
			}

			// ------------ LOAD PSUS ------------ \\
			ArrayList<PSU> psus = new ArrayList<>();
			String line = null;
			while ((line = reader.readLine()) != null) {
				// seperate all items in one PSU
				String[] items = line.split(" ");
				// add a new PSU to the list
				psus.add(new PSU(items));
			}
			reader.close();

			// check if any PSUs were found
			if (psus.size() == 0) {
				return null;
			}

			// transform PSU list into array and return
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

			// separate all items in the line and save names in an array
			String[] itemArr = reader.readLine().split(" ");
			reader.close();

			// check how many items we found, fail if none were found
			if (itemArr.length == 0) {
				return null;
			}

			// return item IDs provided by the name-index mapping
			return PSU.indices(itemArr);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

}