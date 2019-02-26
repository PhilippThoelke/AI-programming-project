package frame;

import java.util.HashMap;
import java.util.HashSet;

public class PSU {

	private static HashMap<String, Integer> itemMapping = new HashMap<>();
	private static HashMap<Integer, String> reverseItemMapping = new HashMap<>();

	private HashSet<Integer> items;

	public PSU(String[] itemArr) {
		items = new HashSet<>();
		for (int i = 0; i < itemArr.length; i++) {
			items.add(itemMapping.get(itemArr[i]));
		}
	}

	public HashSet<Integer> getItems() {
		return items;
	}

	public int itemCount() {
		return items.size();
	}

	public static int[] indices(String[] itemNames) {
		int[] indices = new int[itemNames.length];
		for (int i = 0; i < itemNames.length; i++) {
			indices[i] = itemMapping.get(itemNames[i]);
		}
		return indices;
	}

	public static void addItemMapping(String name, int index) {
		itemMapping.put(name, index);
		reverseItemMapping.put(index, name);
	}

	public static void resetItemMapping() {
		itemMapping = new HashMap<>();
		reverseItemMapping = new HashMap<>();
	}

}