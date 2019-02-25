package frame;

public class Warehouse {

	public static PSU[] psus;

	public static void main(String[] args) {
		psus = Parser.parseWarehouse("problem_files\\problem1.txt");
		int[] order = Parser.parseOrder("problem_files\\order11.txt");
	}

}