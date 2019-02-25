package frame;

import java.util.HashMap;
import java.util.Arrays;

public class PSU {

	public static HashMap<String, Integer> itemMapping;

	private int[] items;

	public PSU(String[] itemArr) {
		items = new int[itemArr.length];
		for (int i = 0; i < itemArr.length; i++) {
			items[i] = itemMapping.get(itemArr[i]);
		}
		Arrays.sort(items);
	}

	public static int[] indices(String[] itemNames) {
		int[] indices = new int[itemNames.length];
		for (int i = 0; i < itemNames.length; i++) {
			indices[i] = itemMapping.get(itemNames[i]);
		}
		return indices;
	}

}