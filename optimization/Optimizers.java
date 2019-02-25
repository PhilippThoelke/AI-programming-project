package optimization;

import java.util.LinkedList;
import java.util.ListIterator;

import java.util.Arrays;

public class Optimizers {

	public static void hillClimbing(int psuCount) {
		boolean[] current = State.randomState(psuCount);

		LinkedList<boolean[]> neighbourhood;
		ListIterator it;

		boolean foundBetter = true;
		while (foundBetter) {
			foundBetter = false;
			neighbourhood = State.unvisitedNeighbourhood(current);
			it = neighbourhood.listIterator();
			while (it.hasNext()) {
				boolean[] newState = (boolean[]) it.next();
				if (Loss.loss(current) < Loss.loss(newState)) {
					current = newState;
					foundBetter = true;
				}
			}
		}
	}

}