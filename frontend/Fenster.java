package frontend;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Fenster extends JFrame {
	JLabel warehouseTxt;
	JLabel orderTxt;
	JLabel stateNumTxt;
	JLabel algo_Type;

	JButton start = new JButton("Start");

	private JTextField stateNum;
	// private JTextField warehouseName;
	// private JTextField orderName;

	JFileChooser wareChoose = new JFileChooser();
	JFileChooser orderChoose = new JFileChooser();

	FileNameExtensionFilter txtfilter = new FileNameExtensionFilter("TXT Text-Files", "txt");


	public Fenster() {
		super("Programing project Methods of AI");

		// warehouseName = new JTextField(50);
		// orderName = new JTextField(50);
		stateNum = new JTextField(50);

		String[] algorithm_choice = { "Hill-Climbing", "First-Choice Hill-Climbing", "Random Restart", "Simulated Annealing"};

		final JComboBox<String> dropdown = new JComboBox<String>(algorithm_choice);

		wareChoose.setFileFilter(txtfilter);
		orderChoose.setFileFilter(txtfilter);

		/*int returnVal = wareChoose.showOpenDialog(parent);
		   if (returnVal == JFileChooser.APPROVE_OPTION) {
		        System.out.println("You chose to open this files: " +
		            wareChoose.getSelectedFile().getName());
		   }

		   returnVal = orderChoose.showOpenDialog(parent);
		   if (returnVal == JFileChooser.APPROVE_OPTION) {
		        System.out.println("You chose to open this files: " +
		            orderChoose.getSelectedFile().getName());
		   }*/


		warehouseTxt = new JLabel("Warehouse file:");
		orderTxt = new JLabel("Order file:");
		stateNumTxt = new JLabel("Number of States");
		algo_Type = new JLabel("Algorithm");

		Font font = new Font("Arial", Font.PLAIN + Font.BOLD, 30);
		warehouseTxt.setFont(font);
		orderTxt.setFont(font);
		algo_Type.setFont(font);
		stateNumTxt.setFont(font);

		add(warehouseTxt);
		//add(warehouseName);
		add(wareChoose);
		add(orderTxt);
		//add(orderName);
		add(orderChoose);
		add(stateNumTxt);
		add(stateNum);
		add(algo_Type);
		add(dropdown);
		add(start);


		setLayout(new GridLayout(5, 2));

		Haendler handler = new Haendler();
		stateNum.addActionListener(handler);
		// warehouseName.addActionListener(handler);
		// orderName.addActionListener(handler);

	}

	private class Haendler implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			String string = "";

			if (event.getSource() == stateNum)
				string = String.format("Number of States: %s", event.getActionCommand());
			/*else if(event.getSource()==warehouseName)
			        string=String.format("warehouseName: %s", event.getActionCommand());
			   else if(event.getSource()==orderName)
			        string=String.format("orderName: %s", event.getActionCommand()); */

			JOptionPane.showMessageDialog(null, string);
		}

	}
}
