package frame;

import java.util.HashMap;
import java.util.HashSet;

/*
 *	The PSU class on the one hand handles the mapping between an item name and its corresponding ID.
 *	Therefore it saves names and ID integers in two maps which allow a bidirectional mapping. Both
 *	item name and item ID are unique so a perfect mapping in both maps is guaranteed. This
 *	functionality is provided by static operations without the need of a PSU object.
 *	On the other hand a PSU object contains a set of all IDs of the items that are carried
 *	by this PSU. It also provides operations to retrieve the item information again.
 */

public class PSU {

	// global mapping of item names to integers and reverse
	private static HashMap<String, Integer> itemMapping = new HashMap<>();
	private static HashMap<Integer, String> reverseItemMapping = new HashMap<>();

	// IDs of the items stored by this PSU
	private HashSet<Integer> items;

	public PSU(String[] itemArr) {
		items = new HashSet<>();
		// initialize this PSU's item set by mapping the item name to an integer
		for (int i = 0; i < itemArr.length; i++) {
			items.add(itemMapping.get(itemArr[i]));
		}
	}

	public String itemsToString() {
		String result = "";
		// collect names of all items in a string separated by commas
		for (Integer index : items) {
			result += reverseItemMapping.get(index) + ", ";
		}

		if (result.isEmpty()) {
			// string is empty (this PSU has no items) -> no unnecessary comma at the end
			return result;
		} else {
			// remove the last comma if the string is non-empty
			return result.substring(0, result.length() - 2);
		}
	}

	public HashSet<Integer> getItems() {
		return items;
	}

	public int itemCount() {
		return items.size();
	}

	// ----------------- static operations ----------------- \\

	public static int[] indices(String[] itemNames) {
		int[] indices = new int[itemNames.length];
		// find the corresponding index for each item name in the array
		for (int i = 0; i < itemNames.length; i++) {
			if (itemMapping.containsKey(itemNames[i])) {
				indices[i] = itemMapping.get(itemNames[i]);
			} else {
				return null;
			}
		}
		return indices;
	}

	public static void addItemMapping(String name, int index) {
		// add a new item with corresponding ID into the maps
		itemMapping.put(name, index);
		reverseItemMapping.put(index, name);
	}

	public static void resetItemMapping() {
		// remove all entries from both maps
		itemMapping.clear();
		reverseItemMapping.clear();
	}

}