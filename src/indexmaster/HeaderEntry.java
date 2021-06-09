package indexmaster;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class HeaderEntry extends JPanel{

	private JPanel leftOffset;
	
	private JTextField[] decName; 
	
	private String[] nameList;

	public HeaderEntry(String[] nameList)
	{
		super();

		this.nameList = nameList;
		
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(900, 50));
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		initComponents();
		setDecs();
	}
	
	private void initComponents()
	{
		leftOffset = new JPanel();
		//info.setBorder(BorderFactory.createLineBorder(Color.black));
		leftOffset.setLayout(new BoxLayout(leftOffset, BoxLayout.Y_AXIS));
		leftOffset.setPreferredSize(new Dimension(85,60));
		leftOffset.setMaximumSize(new Dimension(85,60));
		this.add(leftOffset);
	}
	
	private void setDecs()
	{
		int i;
		decName = new JTextField[nameList.length];
		for(i=0; i<nameList.length; i++)
		{
			decName[i] = new JTextField(8);
			decName[i].setText(nameList[i]);
			decName[i].setAlignmentY(BOTTOM_ALIGNMENT);
			decName[i].setHorizontalAlignment(JTextField.CENTER);;
			decName[i].setEditable(false);
			this.add(decName[i]);
		}
	}
	
	public void setListener(ActionListener a)
	{
		for(JTextField aux:decName)
		{
			aux.addActionListener(a);
		}
	}
	
}
