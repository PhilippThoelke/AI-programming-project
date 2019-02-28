package frontend;

import frame.Warehouse;

import optimization.Loss;
import optimization.State;
import optimization.Optimizers;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.BadLocationException;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;

import java.lang.Thread;
import java.lang.Runnable;

import java.text.DecimalFormat;

public class Window {

	// ------------- GUI ATTRIBUTES ------------- \\
	private static final int SPACING = 5;

	private static final Color ORANGE = new Color(255, 153, 0);
	private static final Color GREEN = new Color(0, 200, 0);

	private static final String COLOR_STYLE = "color style";

	private static final String NORTH = SpringLayout.NORTH;
	private static final String EAST = SpringLayout.EAST;
	private static final String SOUTH = SpringLayout.SOUTH;
	private static final String WEST = SpringLayout.WEST;

	private static final int WAREHOUSE = 0;
	private static final int ORDER = 1;

	private static DecimalFormat decimalFormat = new DecimalFormat("#.###");

	// ------------- OPTIMIZER ATTRIBUTES ------------- \\
	private static final String[] optimizerNames = {
		"Hill climbing",
		"First choice hill climbing",
		"Local beam search",
		"Parallel hill climbing",
		"Simulated annealing"
	};

	// indices of the optimizers in the optimizerNames array that require an extra state parameter
	private static final int[] stateCountRequiredIndices = {2, 3};

	// ------------- LAYOUT COMPONENTS ------------- \\
	private JFrame frame;

	private JTextField warehouseFileTxt;
	private JTextField orderFileTxt;
	private JComboBox<String> optimizerBox;
	private JTextField stateCountTxt;
	private JTextPane outputPane;
	private JButton startBtn;
	private JButton openWarehouseBtn;
	private JButton openOrderBtn;

	public Window() {
		frame = new JFrame("AI Programming Project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// initialize content pane with a spring layout
		Container contentPane = frame.getContentPane();
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);

		// vvvvvvvvvvv INITIALIZING AND ADDING ALL LAYOUT COMPONENTS vvvvvvvvvvv \\
		// ----------------------- WAREHOUSE FILE SECTION -----------------------
		JLabel warehouseFileLbl = new JLabel("Warehouse file");

		warehouseFileTxt = new JTextField(25);
		warehouseFileTxt.setEditable(false);

		openWarehouseBtn = new JButton("Open");
		openWarehouseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			        openFile(WAREHOUSE);
			}
		});

		// add components to the layout
		contentPane.add(warehouseFileLbl);
		contentPane.add(warehouseFileTxt);
		contentPane.add(openWarehouseBtn);

		// ----------------------- ORDER FILE SECTION -----------------------

		JLabel orderFileLbl = new JLabel("Order file");

		orderFileTxt = new JTextField();
		orderFileTxt.setEditable(false);

		openOrderBtn = new JButton("Open");
		openOrderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			        openFile(ORDER);
			}
		});

		// add components to the layout
		contentPane.add(orderFileLbl);
		contentPane.add(orderFileTxt);
		contentPane.add(openOrderBtn);

		// ----------------------- OPTIMIZER SECTION -----------------------

		JLabel optimizerLbl = new JLabel("Algorithm");

		optimizerBox = new JComboBox<>(optimizerNames);
		optimizerBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			        updateStateCountTxt();
			}
		});

		// add components to the layout
		contentPane.add(optimizerLbl);
		contentPane.add(optimizerBox);

		// ----------------------- STATE COUNT SECTION -----------------------

		JLabel stateCountLbl = new JLabel("Number of states");

		stateCountTxt = new JTextField();

		// add components to the layout
		contentPane.add(stateCountLbl);
		contentPane.add(stateCountTxt);

		// ----------------------- START SECTION -----------------------

		startBtn = new JButton("Start");
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			        startOptimizer();
			}
		});

		// add the component to the layout
		contentPane.add(startBtn);

		// ----------------------- OUTPUT SECTION -----------------------

		outputPane = new JTextPane();
		outputPane.setEditable(false);
		outputPane.addStyle(COLOR_STYLE, null);
		outputPane.setPreferredSize(new Dimension(600, 600));

		JScrollPane scrollPane = new JScrollPane(outputPane);

		// add the component to the layout
		contentPane.add(scrollPane);

		// vvvvvvvvvvv ADDING ALL LAYOUT CONSTRAINTS vvvvvvvvvvv \\
		// -------------------- WAREHOUSE FILE SECTION CONSTRAINTS -------------------- \\

		layout.putConstraint(WEST, warehouseFileLbl, SPACING, WEST, contentPane);
		layout.putConstraint(NORTH, warehouseFileLbl, SPACING, NORTH, contentPane);
		layout.putConstraint(EAST, warehouseFileLbl, 0, EAST, openWarehouseBtn);

		layout.putConstraint(NORTH, warehouseFileTxt, SPACING, SOUTH, warehouseFileLbl);
		layout.putConstraint(WEST, warehouseFileTxt, 0, WEST, warehouseFileLbl);

		layout.putConstraint(WEST, openWarehouseBtn, SPACING, EAST, warehouseFileTxt);
		layout.putConstraint(EAST, openWarehouseBtn, 0, EAST, openOrderBtn);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, openWarehouseBtn, 0, SpringLayout.VERTICAL_CENTER, warehouseFileTxt);

		// -------------------- ORDER FILE SECTION CONSTRAINTS -------------------- \\

		layout.putConstraint(WEST, orderFileLbl, 0, WEST, warehouseFileLbl);
		layout.putConstraint(NORTH, orderFileLbl, SPACING, SOUTH, warehouseFileTxt);
		layout.putConstraint(EAST, orderFileLbl, 0, EAST, openWarehouseBtn);

		layout.putConstraint(NORTH, orderFileTxt, SPACING, SOUTH, orderFileLbl);
		layout.putConstraint(WEST, orderFileTxt, 0, WEST, orderFileLbl);
		layout.putConstraint(EAST, orderFileTxt, 0, EAST, warehouseFileTxt);

		layout.putConstraint(WEST, openOrderBtn, SPACING, EAST, orderFileTxt);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, openOrderBtn, 0, SpringLayout.VERTICAL_CENTER, orderFileTxt);

		// -------------------- OPTIMIZER SECTION CONSTRAINTS -------------------- \\

		layout.putConstraint(WEST, optimizerLbl, 0, WEST, warehouseFileLbl);
		layout.putConstraint(NORTH, optimizerLbl, SPACING, SOUTH, orderFileTxt);
		layout.putConstraint(EAST, optimizerLbl, 0, EAST, openWarehouseBtn);

		layout.putConstraint(NORTH, optimizerBox, SPACING, SOUTH, optimizerLbl);
		layout.putConstraint(WEST, optimizerBox, 0, WEST, optimizerLbl);
		layout.putConstraint(EAST, optimizerBox, 0, EAST, openWarehouseBtn);

		// -------------------- STATE COUNT SECTION CONSTRAINTS -------------------- \\

		layout.putConstraint(WEST, stateCountLbl, 0, WEST, warehouseFileLbl);
		layout.putConstraint(NORTH, stateCountLbl, SPACING, SOUTH, optimizerBox);
		layout.putConstraint(EAST, stateCountLbl, 0, EAST, openWarehouseBtn);

		layout.putConstraint(WEST, stateCountTxt, 0, WEST, warehouseFileLbl);
		layout.putConstraint(NORTH, stateCountTxt, SPACING, SOUTH, stateCountLbl);
		layout.putConstraint(EAST, stateCountTxt, 0, EAST, openWarehouseBtn);

		// -------------------- START SECTION CONSTRAINTS -------------------- \\

		layout.putConstraint(NORTH, startBtn, SPACING, SOUTH, stateCountTxt);
		layout.putConstraint(WEST, startBtn, 0, WEST, optimizerLbl);
		layout.putConstraint(EAST, startBtn, 0, EAST, openWarehouseBtn);

		// -------------------- OUTPUT SECTION CONSTRAINTS -------------------- \\

		layout.putConstraint(NORTH, scrollPane, 0, NORTH, warehouseFileLbl);
		layout.putConstraint(WEST, scrollPane, SPACING * 2, EAST, openWarehouseBtn);

		// -------------------- CONTENT PANE CONSTRAINTS -------------------- \\

		layout.putConstraint(EAST, contentPane, SPACING, EAST, scrollPane);
		layout.putConstraint(SOUTH, contentPane, SPACING, SOUTH, scrollPane);
		// _____________________ END OF LAYOUT CONSTRAINTS _____________________ \\

		// choose if the state count text field should be enabled
		updateStateCountTxt();

		// finalize the JFrame and make it visible
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void openFile(int type) {
		// open a file chooser starting in the local project directory
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		// allow only selection of .txt files
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));

		// show file chooser and check response
		if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			String filePath = chooser.getSelectedFile().getAbsolutePath();

			// proceed depending on the type of file that should be opened
			switch (type) {
				case WAREHOUSE:
					if (Warehouse.readWarehouseFile(filePath)) {
						// a warehouse file was parsed without errors
						warehouseFileTxt.setText(filePath);
					} else {
						// an error occured while parsing a warehouse file
						parsingError(type);
						warehouseFileTxt.setText("");
					}
					break;

				case ORDER:
					if (Warehouse.readOrderFile(filePath)) {
						// an order file was parsed without errors
						orderFileTxt.setText(filePath);
					} else {
						// an error occured while parsing an order file
						parsingError(type);
						orderFileTxt.setText("");
					}
					break;
			}
		}
	}

	private void parsingError(int fileType) {
		// get the type of selected file as a string
		String fileName = fileType == WAREHOUSE ? "warehouse" : "order";
		// print an error message to the output pane depending on the file type
		print("ERROR: The selected " + fileName + " file could not be parsed", Color.red);
		if (fileType == ORDER) {
			print(" (a warehouse file must be selected before selecting the order file)", Color.red);
		}
		print("\n");
	}

	private void updateStateCountTxt() {
		// enable state count text field if the selected optimizer has a state parameter
		stateCountTxt.setEnabled(optimizerNeedsStateCount());
	}

	public void println(String str) {
		// overloaded convenience method to print black text to the output pane followed by a new line
		print(str + "\n", Color.black);
	}

	private void println(String str, Color col) {
		// overloaded convenience method to print colored text to the output pane followed by a new line
		print(str + "\n", col);
	}

	public void print(String str) {
		// overloaded convenience method to print black text to the output pane
		print(str, Color.black);
	}

	private void print(String str, Color col) {
		// prepare for printing onto the output pane
		StyledDocument doc = outputPane.getStyledDocument();
		Style style = outputPane.getStyle(COLOR_STYLE);

		// set the selected color
		StyleConstants.setForeground(style, col);

		try {
			// add the passed string to the end of the output pane
			doc.insertString(doc.getLength(), str, style);
			// scroll to the end of the output pane by selecting an empty string at the end of the pane
			outputPane.select(doc.getLength(), doc.getLength());
		} catch (BadLocationException e) {
			System.err.println(e.getMessage());
		}
	}

	private boolean optimizerNeedsStateCount() {
		String selected = (String) optimizerBox.getSelectedItem();
		// go through the indices of optimizers which require an additional state parameter
		for (int i = 0; i < stateCountRequiredIndices.length; i++) {
			// check if the selected optimizer is one state parameter requiring optimizers
			if (selected.equals(optimizerNames[stateCountRequiredIndices[i]])) {
				return true;
			}
		}
		// none of the optimizers that require a state parameter match the selected one
		return false;
	}

	private void startOptimizer() {
		// insert a new line to separate the optimizer's information from previous Text
		// if the output pane is not empty
		if (!outputPane.getText().isEmpty()) {
			print("\n");
		}
		// check if all input fields
		if (checkInputFields()) {
			// start the optimization process in a new thread so the UI thread is not blocked
			new Thread(new Runnable() {
				public void run() {
				        optimize();
				}
			}).start();
		}
	}

	private void optimize() {
		// disable user interaction of controls that can influence the optimizer
		startBtn.setEnabled(false);
		openWarehouseBtn.setEnabled(false);
		openOrderBtn.setEnabled(false);

		// get the name of the selected optimizer
		String selected = (String) optimizerBox.getSelectedItem();
		// parse the selected state count if possible
		int stateCount = -1;
		if (!stateCountTxt.getText().isEmpty()) {
			stateCount = Integer.parseInt(stateCountTxt.getText());
		}

		// output for the user
		print("<<------------------ ");
		print("Starting optimization", ORANGE);
		println(" ------------------>>");
		println("Selected optimizer: " + selected);

		// save the start time before running the optimizer
		long startTime = System.nanoTime();

		boolean[] optimized = null;
		// run the selected optimizer
		if (selected.equals(optimizerNames[0])) {
			// hill climbing
			optimized = Optimizers.hillClimbing(Warehouse.psuCount());
		} else if (selected.equals(optimizerNames[1])) {
			// first choice hill climbing
			optimized = Optimizers.firstChoiceHillClimbing(Warehouse.psuCount());
		} else if (selected.equals(optimizerNames[2])) {
			// local beam search
			// TODO: implement Local beam search
			println("ERROR: Not yet implemented", Color.red);
		} else if (selected.equals(optimizerNames[3])) {
			// parallel hill climbing
			optimized = Optimizers.parallelHillClimbing(Warehouse.psuCount(), stateCount);
		} else if (selected.equals(optimizerNames[4])) {
			// simulated annealing
			optimized = Optimizers.simulatedAnnealing(Warehouse.psuCount());
		}

		// enable all previously disabled controls
		startBtn.setEnabled(true);
		openWarehouseBtn.setEnabled(true);
		openOrderBtn.setEnabled(true);

		// check if the optimizer finished without failing
		if (optimized != null) {
			print("Optimization finished ", ORANGE);
			// claculate the time since the start of the optimizer to provide the runtime
			float deltaTime = (System.nanoTime() - startTime) / 1e9f;
			println("(" + decimalFormat.format(deltaTime) + " seconds)");

			// output how many PSUs were used
			print("\nNumber of used PSUs: ");
			println(Integer.toString(Loss.numPSUsUsed(optimized)), GREEN);

			// output how many items the PSUs carried and how many of them are individuals
			print("Number of carried items: ");
			print(Integer.toString(Warehouse.numItemsCarried(optimized)), GREEN);
			print(" (individual: ");
			print(Integer.toString(Warehouse.maskedItems(optimized).size()), GREEN);
			println(")");

			// output the loss of the optimized state
			print("Loss: ");
			println(decimalFormat.format(Loss.loss(optimized)), GREEN);

			print("\n");

			// output all PSU identifiers followed by the items carried by this PSU
			for (int i = 0; i < optimized.length; i++) {
				if (optimized[i]) {
					print("PSU identifier: ");
					println(Integer.toString(i), Color.blue);
					print("Items: ");
					println(Warehouse.getPSU(i).itemsToString(), Color.gray);
				}
			}
		} else {
			// an error occured while optimizing
			println("ERROR: Optimizer returned null", Color.red);
		}
	}

	private boolean checkInputFields() {
		if (warehouseFileTxt.getText().isEmpty()) {
			// no warehouse file selected
			println("Please select a warehouse file", Color.red);
			return false;
		} else if (orderFileTxt.getText().isEmpty()) {
			// no order file selected
			println("Please select an order file", Color.red);
			return false;
		} else if (optimizerNeedsStateCount() && stateCountTxt.getText().isEmpty()) {
			// a state count is necessary but not provided
			println("Please choose the state count for " + (String) optimizerBox.getSelectedItem(), Color.red);
			return false;
		} else if (optimizerNeedsStateCount() && !stateCountTxt.getText().matches("[1-9]\\d*")) {
			// an invalid state count was selected
			println("Please select an integer greater than 0", Color.red);
			return false;
		}
		// no errors found while checking the input fields
		return true;
	}

}