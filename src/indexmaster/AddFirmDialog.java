package indexmaster;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

public class AddFirmDialog extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	public static final int STATUS_SAVED = 1;
	public static final int STATUS_CLOSED = 2;
	public static final int STATUS_ERROR = 3;
	
	private int status = 0;
	
	private String[] decsList;
	
	private JCheckBox roCheck;
	private JLabel cui;
	private JLabel name;
	
	private JTextField cuiField;
	private JTextField nameField;
	
	private JPanel info;
	private JPanel cuiPanel;
	private JPanel namePanel;
	private JPanel decsPanel;
	private JPanel buttonPanel;
	
	private JCheckBox[] decsCheck;
	
	private JButton saveButton;
	private JButton closeButton;
	
	public AddFirmDialog(String[] decsList, JFrame parent)
	{
		super(parent, "Adauga firma");
		this.setLayout(new BorderLayout());
		this.setSize(700, 300);
		this.setLocationRelativeTo(parent);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.decsList = decsList;
		initComponents();
		this.add(info, BorderLayout.NORTH);
		this.add(decsPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	private void initComponents()
	{
		roCheck = new JCheckBox("RO");
		cui = new JLabel("CUI");
		name = new JLabel("Denumire");
		cuiField = new JTextField(8);
		nameField = new JTextField(10);
		info = new JPanel();
		cuiPanel = new JPanel();
		namePanel = new JPanel();
		buttonPanel = new JPanel();
		cuiPanel.setLayout(new FlowLayout());
		namePanel.setLayout(new FlowLayout());
		buttonPanel.setLayout(new GridLayout(1,2));
		cuiPanel.add(cui);
		cuiPanel.add(cuiField);
		namePanel.add(name);
		namePanel.add(nameField);
		info.setLayout(new GridLayout(1,3));
		roCheck.setSelected(true);
		info.add(roCheck);
		info.add(cuiPanel);
		info.add(namePanel);
		
		saveButton = new JButton("Salveaza");
		closeButton = new JButton("Inchide");
		saveButton.addActionListener(this);
		closeButton.addActionListener(this);
		buttonPanel.add(saveButton);
		buttonPanel.add(closeButton);
		
		decsPanel = new JPanel();
		decsPanel.setLayout(new FlowLayout());
		decsCheck = new JCheckBox[decsList.length];
		int i=0;
		for(String decName : decsList)
		{
			decsCheck[i] = new JCheckBox(decName);
			decsCheck[i].setSelected(true);
			decsPanel.add(decsCheck[i]);
			i++;
		}
	}
	
	public boolean hasRo()
	{
		return roCheck.isSelected();
	}
	
	public String getCui()
	{
		return this.cuiField.getText();
	}
	
	public String getName()
	{
		return this.nameField.getText();
	}
	
	public String[] getDecs()
	{
		int i=0;
		for(JCheckBox aux : decsCheck)
		{
			if(!aux.isSelected())
				decsList[i] = null;
			i++;
		}
		return decsList;
	}
	
	public int getStatus()
	{
		return status;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == saveButton)
		{
			status = STATUS_SAVED;
		}
		else if(e.getSource() == closeButton)
		{
			status = STATUS_CLOSED;
		}
		this.dispose();
	}

}
