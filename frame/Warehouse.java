package frame;

import java.util.HashSet;

import optimization.Optimizers;
import optimization.Loss;

/*
 *	The Warehouse class contains the current warehouse configuration with all the PSU information
 *	and the currently ordered items. It also allows to retrieve a set of all items that are
 *	carried by PSUs specified in a given state and calculate how many items these PSUs
 *	carry in total. Furthermore getter operations for the current PSU configuration and
 *	order are implemented. These are all static so a warehouse object is not necessary. The class
 *	contains only static attributes and operations because only a single set of PSUs and a single
 *	order can be active at one time and this also enables access to this data from every
 *	other class which is important because the PSU configuration and current order are essential
 *	information during the optimization.
 */

public class Warehouse {

	// currently loaded PSUs and order
	private static PSU[] psus;
	private static int[] order;

	public static boolean readWarehouseFile(String path) {
		// parse a new warehouse file
		psus = Parser.parseWarehouse(path);
		return psus != null;
	}

	public static boolean readOrderFile(String path) {
		// parse a new order file
		order = Parser.parseOrder(path);
		return order != null;
	}

	public static HashSet<Integer> maskedItems(boolean[] mask) {
		HashSet<Integer> items = new HashSet<>();
		// return all items that are being carried by the PSUs selected in the boolean mask
		for (int i = 0; i < psus.length; i++) {
			if (mask[i]) {
				items.addAll(psus[i].getItems());
			}
		}
		return items;
	}

	public static int numItemsCarried(boolean[] mask) {
		int numItemsCarried = 0;
		// return how many items the PSUs selected by the boolean mask carry
		for (int i = 0; i < psus.length; i++) {
			if (mask[i]) {
				numItemsCarried += psus[i].itemCount();
			}
		}
		return numItemsCarried;
	}

	public static PSU getPSU(int index) {
		return psus[index];
	}

	public static int[] currentOrder() {
		return order;
	}

	public static int orderSize() {
		return order.length;
	}

	public static int psuCount() {
		return psus.length;
	}

}