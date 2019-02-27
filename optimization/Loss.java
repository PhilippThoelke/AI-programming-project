package optimization;

import java.util.HashSet;

import frame.Warehouse;

public class Loss {

	public static float loss(boolean[] currentState) {
		float covered = (float) numItemsCovered(currentState) / Warehouse.orderSize();
		float usedPSUs = (float) numPSUsUsed(currentState) / Warehouse.psuCount();
		return covered - usedPSUs;
	}

	public static int numItemsCarried(boolean[] state) {
		return Warehouse.numItemsCarried(state);
	}

	public static int numItemsCovered(boolean[] state) {
		HashSet<Integer> itemsCarried = Warehouse.maskedItems(state);
		int[] currentOrder = Warehouse.currentOrder();

		int numItemsCovered = 0;

		for (int itemIndex : currentOrder) {
			if (itemsCarried.contains(itemIndex)) {
				numItemsCovered++;
			}
		}
		return numItemsCovered;
	}

	public static int numPSUsUsed(boolean[] state) {
		int numPSUsUsed = 0;
		for (int i = 0; i < state.length; i++) {
			numPSUsUsed += state[i] ? 1 : 0;
		}
		return numPSUsUsed;
	}

}