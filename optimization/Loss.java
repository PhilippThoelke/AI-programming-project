package optimization;

import java.util.HashSet;

import frame.Warehouse;

/*
 *	The Loss class implements the loss function used to determine how "good" a certain state is.
 *	It takes into account how many of the ordered items are provided by the current state and
 *	how many PSUs the state requires. Loss should be maximized (maximization problem) to achieve
 *	the best working result requiring the least amount of PSUs while maintaining coverage
 *	of all ordered items. The components (item coverage and PSU count) are normalized so that
 *	the loss function is applicable to different warehouse configurations without adjustment.
 *	Furthermore some local search parameters are tuned to the specific loss implementation which
 *	is another reason to normalize the components since optimization performance depends
 *	on the size of the loss values.
 */

public class Loss {

	public static float loss(boolean[] currentState) {
		// calculate how many of the ordered items are provided by the selected PSUs in currentState
		// and normalize to the range [0, 1]
		float covered = (float) numItemsCovered(currentState) / Warehouse.orderSize();
		// calculate how many PSUs are being used in the currentState
		// and normalize to the range [0, 1]
		float usedPSUs = (float) numPSUsUsed(currentState) / Warehouse.psuCount();
		return covered - usedPSUs;
	}

	public static int numItemsCovered(boolean[] state) {
		// get all the items thate are being carried by the selected PSUs in state
		HashSet<Integer> itemsCarried = Warehouse.maskedItems(state);
		// get the current order
		int[] currentOrder = Warehouse.currentOrder();

		int numItemsCovered = 0;
		// count how many of the items in the current order are among the carried items
		for (int itemIndex : currentOrder) {
			if (itemsCarried.contains(itemIndex)) {
				numItemsCovered++;
			}
		}
		return numItemsCovered;
	}

	public static int numPSUsUsed(boolean[] state) {
		int numPSUsUsed = 0;
		// count how many PSUs are being used -> count how many true values are in state
		for (int i = 0; i < state.length; i++) {
			numPSUsUsed += state[i] ? 1 : 0;
		}
		return numPSUsUsed;
	}

}