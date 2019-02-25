package optimization;

public class Loss {

	public static float loss(boolean[] currentState) {
		int numberPSUs = 0;
		for (int i = 0; i < currentState.length; i++) {
			numberPSUs += currentState[i] ? 1 : 0;
		}
		// TODO: calculate loss of the current state

		return numberPSUs;
	}

}