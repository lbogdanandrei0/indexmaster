package indexmaster;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class EntryFirma extends JPanel{
	
	private Firma firma;
	
	private JPanel info;
	
	private JLabel name;
	private JLabel cui;
	private JTextField[] dec; 
	
	private String[] nameList;

	public EntryFirma(Firma firma, String[] nameList, DocumentListener d)
	{
		super();
		
		this.firma = firma;
		this.nameList = nameList;
		
		this.setLayout(new FlowLayout());
		//this.setPreferredSize(new Dimension(900, 50));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		initComponents();
		setDecs(d);
	}
	
	public EntryFirma(Firma firma, String[] indexList, String[] decsNameList)
	{
		super();
		
		this.firma = firma;
		this.nameList = decsNameList;
		
		this.setLayout(new FlowLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		initComponents();
		fillDecs(indexList);
	}
	
	public Firma getFirma()
	{
		return this.firma;
	}
	
	public JTextField[] getDecs()
	{
		return this.dec;
	}
	
	private void initComponents()
	{
		info = new JPanel();
		name = new JLabel();
		cui = new JLabel();
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		cui.setAlignmentX(Component.CENTER_ALIGNMENT);
		cui.setFont(new Font("TimesRoman", Font.PLAIN, 13));
		//info.setBorder(BorderFactory.createLineBorder(Color.black));
		info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
		info.setPreferredSize(new Dimension(100,40));
		info.setMaximumSize(new Dimension(100,50));
		name.setText(firma.getName());
		if(firma.hasRo())
			cui.setText("RO "+firma.getCui());
		else
			cui.setText(firma.getCui());
		name.setToolTipText(name.getText());
		info.add(name);
		info.add(cui);
		this.add(info);
	}
	
	private void setDecs(DocumentListener d)
	{
		int i;
		dec = new JTextField[nameList.length];
		for(i=0; i<nameList.length; i++)
		{
			dec[i] = new JTextField(8);
			dec[i].setToolTipText(nameList[i]);
			if(MainController.man.hasDec(firma.getDecs(), nameList[i]))
			{
				dec[i].setBorder(BorderFactory.createLineBorder(Color.green));
			}
			else
				dec[i].setBorder(BorderFactory.createLineBorder(Color.yellow));
			dec[i].getDocument().addDocumentListener(d);
			this.add(dec[i]);
		}
	}
	
	public void setListener(ActionListener a)
	{
		for(JTextField aux:dec)
		{
			aux.addActionListener(a);
		}
	}
	
	private void fillDecs(String[] indexList)
	{
		int i;
		dec = new JTextField[nameList.length];
		for(i=0; i<nameList.length; i++)
		{
			dec[i] = new JTextField(8);
			dec[i].setToolTipText(nameList[i]);
			dec[i].setText(indexList[i]);
			if(MainController.man.hasDec(firma.getDecs(), nameList[i]))
			{
				dec[i].setBorder(BorderFactory.createLineBorder(Color.green));
			}
			else
				dec[i].setBorder(BorderFactory.createLineBorder(Color.yellow));
			this.add(dec[i]);
		}
	}
	
}
