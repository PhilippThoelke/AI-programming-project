import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class Fenster extends JFrame{
	JLabel warehouseTxt;
	JLabel orderTxt;
	
	private JTextField warehouse;
	private JTextField order;
	private JTextField stateNum;
	private JTextField warehouseName;
	private JTextField orderName;
	
	
	
	public Fenster() {
		super("Programing project Methods of AI");
		setLayout(new FlowLayout());
		
		warehouseName = new JTextField(50);
		orderName = new JTextField(50);
		
		//warehouseName.setBounds(5, 5, 100, 20);
		
		warehouseTxt = new JLabel("Warehouse files:");
		//warehouseTxt.setBounds(5, 5, 400, 20);

		orderTxt = new JLabel("Order file:");
		//orderTxt.setBounds(5, 5, 400, 200);	
		
		Font font = new Font("Arial", Font.PLAIN + Font.BOLD, 30);
		warehouseTxt.setFont(font);
		orderTxt.setFont(font);
		
		add(orderTxt);
		add(warehouseTxt);
		add(warehouseName);
		add(orderName);
		
		Haendler handler = new Haendler();
		warehouse.addActionListener(handler);
		order.addActionListener(handler);
		stateNum.addActionListener(handler);
		warehouseName.addActionListener(handler);
		orderName.addActionListener(handler);
		
	}
	
	private class Haendler implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			String string ="";
			
			if(event.getSource()==warehouse)
				string=String.format("Warehouse: %s", event.getActionCommand());
			else if(event.getSource()==order)
				string=String.format("Order: %s", event.getActionCommand());
			else if(event.getSource()==stateNum)
				string=String.format("Number of States: %s", event.getActionCommand());
			else if(event.getSource()==warehouseName)
				string=String.format("warehouseName: %s", event.getActionCommand());
			else if(event.getSource()==orderName)
				string=String.format("orderName: %s", event.getActionCommand());
			
			JOptionPane.showMessageDialog(null, string);
		}
		
	}
}
