package optimization;

import java.util.LinkedList;
import java.util.ListIterator;

public class Optimizers {

	public static boolean[] hillClimbing(int psuCount) {
		boolean[] current = State.randomState(psuCount);
		float currentLoss = Loss.loss(current);

		LinkedList<boolean[]> neighbourhood;
		boolean[] newState;
		ListIterator it;
		float newLoss;

		boolean foundBetter = true;
		while (foundBetter) {
			foundBetter = false;
			neighbourhood = State.unvisitedNeighbourhood(current);

			it = neighbourhood.listIterator();
			while (it.hasNext()) {
				newState = (boolean[]) it.next();
				newLoss = Loss.loss(newState);
				if (currentLoss < newLoss) {
					current = newState;
					currentLoss = newLoss;
					foundBetter = true;
				}
			}
		}
		return current;
	}

	public static boolean[] firstChoiceHillClimbing(int psuCount) {
		boolean[] current = State.randomState(psuCount);
		float currentLoss = Loss.loss(current);

		LinkedList<boolean[]> neighbourhood;
		boolean[] newState;
		ListIterator it;
		float newLoss;

		boolean foundBetter = true;
		while (foundBetter) {
			foundBetter = false;
			neighbourhood = State.unvisitedNeighbourhood(current);

			it = neighbourhood.listIterator();
			while (it.hasNext() && !foundBetter) {
				newState = (boolean[]) it.next();
				newLoss = Loss.loss(newState);
				if (currentLoss < newLoss) {
					current = newState;
					currentLoss = newLoss;
					foundBetter = true;
				}
			}
		}
		return current;
	}

}