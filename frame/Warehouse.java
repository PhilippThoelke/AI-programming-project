package frame;

import optimization.Optimizers;

public class Warehouse {

	private static PSU[] psus;
	private static int[] order;

	public static void main(String[] args) {
		psus = Parser.parseWarehouse("problem_files\\problem1.txt");
		order = Parser.parseOrder("problem_files\\order11.txt");

		Optimizers.hillClimbing(10);
	}

}