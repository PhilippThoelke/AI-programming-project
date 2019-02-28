package frame;

import java.util.HashSet;

import optimization.Optimizers;
import optimization.Loss;

import frontend.Window;

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