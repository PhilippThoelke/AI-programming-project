package optimization;

import java.util.LinkedList;
import java.util.ListIterator;

public class Optimizers {

	public static boolean[] hillClimbing(int psuCount) {
		boolean[] current = State.randomState(psuCount);

		LinkedList<boolean[]> neighbourhood;
		boolean[] newState;
		ListIterator it;

		boolean foundBetter = true;
		while (foundBetter) {
			foundBetter = false;
			neighbourhood = State.unvisitedNeighbourhood(current);
			it = neighbourhood.listIterator();
			while (it.hasNext()) {
				newState = (boolean[]) it.next();
				if (Loss.loss(current) < Loss.loss(newState)) {
					current = newState;
					foundBetter = true;
				}
			}
		}
		return current;
	}

	public static boolean[] firstChoiceHillClimbing(int psuCount) {
		boolean[] current = State.randomState(psuCount);

		LinkedList<boolean[]> neighbourhood;
		boolean[] newState;
		ListIterator it;

		boolean foundBetter = true;
		while (foundBetter) {
			foundBetter = false;
			neighbourhood = State.unvisitedNeighbourhood(current);
			it = neighbourhood.listIterator();
			while (it.hasNext()) {
				newState = (boolean[]) it.next();
				if (Loss.loss(current) < Loss.loss(newState)) {
					current = newState;
					foundBetter = true;
					break;
				}
			}
		}
		return current;
	}

}