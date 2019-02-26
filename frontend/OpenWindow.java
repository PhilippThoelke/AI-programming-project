package frontend;

import javax.swing.JFrame;

public class OpenWindow {

	public static void main(String[] args) {
		Fenster unserFenster = new Fenster();
		unserFenster.setVisible(true);
		unserFenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		unserFenster.setSize(1600, 800);
	}

}
