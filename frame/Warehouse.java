package frame;

import java.util.HashSet;

import optimization.Optimizers;
import optimization.Loss;

public class Warehouse {

	private static PSU[] psus;
	private static int[] order;

	public static void main(String[] args) {
		psus = Parser.parseWarehouse("problem_files\\problem1.txt");
		order = Parser.parseOrder("problem_files\\order12.txt");

		boolean[] optimalMask = Optimizers.hillClimbing(psus.length);

		System.out.print("Items covered: " + Loss.numItemsCovered(optimalMask));
		System.out.println("/" + order.length);
		System.out.println("Items carried: " + numItemsCarried(optimalMask));
		System.out.println("Individual items carried: " + maskedItems(optimalMask).size());
		System.out.print("PSUs used: " + Loss.numPSUsUsed(optimalMask));
		System.out.println("/" + psus.length);
	}

	public static HashSet<Integer> maskedItems(boolean[] mask) {
		HashSet<Integer> items = new HashSet<>();
		for (int i = 0; i < psus.length; i++) {
			if (mask[i]) {
				items.addAll(psus[i].getItems());
			}
		}
		return items;
	}

	public static int numItemsCarried(boolean[] mask) {
		int numItemsCarried = 0;
		for (int i = 0; i < psus.length; i++) {
			if (mask[i]) {
				numItemsCarried += psus[i].itemCount();
			}
		}
		return numItemsCarried;
	}

	public static int[] currentOrder() {
		return order;
	}

}