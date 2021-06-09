package indexmaster;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

public class EntryList extends JPanel{
	
	private JLabel dec;
	private JButton deleteDec;
	
	private JLabel name;
	private JLabel cui;
	private JButton deleteFirm;
	
	public EntryList(String decName)
	{
		super();
		this.setLayout(new GridLayout(1,2));
		
		dec = new JLabel(decName);
		deleteDec = new JButton("Elimina declaratia");
		deleteDec.setName(decName);
		
		this.add(dec);
		this.add(deleteDec);
	}
	
	public EntryList(Firma firma)
	{
		super();
		this.setLayout(new GridLayout(1, 3));
		
		name = new JLabel(firma.getName());
		cui = new JLabel(firma.getCui());
		deleteFirm = new JButton("Elimina firma");
		deleteFirm.setName(firma.getCui());
		
		this.add(name);
		this.add(cui);
		this.add(deleteFirm);
	}
	
	public void setListenerDec(ActionListener a)
	{
		deleteDec.addActionListener(a);
	}
	
	public void setListenerFirm(ActionListener a)
	{
		deleteFirm.addActionListener(a);
	}

}
