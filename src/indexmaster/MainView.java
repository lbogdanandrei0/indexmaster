package indexmaster;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class MainView extends JFrame {
	
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 500;
	
	private JMenuBar jmb;
	
	private JMenu options;
	private JMenu firmaOptions;
	private JMenu decsOptions;
	private JMenu importItem;
	private JMenu exportItem;
	
	private JMenuItem newItem;
	private JMenuItem importEditItem;
	private JMenuItem importViewItem;
	private JMenuItem exportTxtItem;
	private JMenuItem exportXmlItem;
	private JMenuItem closeItem;
	
	private JMenuItem addFirma;
	private JMenuItem saveFirma;
	private JMenuItem listFirma;
	
	private JMenuItem addDecs;
	private JMenuItem saveDecs;
	private JMenuItem listDecs;
	
	private JPanel viewContainer;
	private JScrollPane scroll;

	private ImageIcon logo;
	
	public MainView()
	{
		super("Index Master");
		this.setSize(WIDTH, HEIGHT);
		
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initComponents();
		
		this.setJMenuBar(jmb);
	}
	
	private void initComponents()
	{
		jmb = new JMenuBar();
		options = new JMenu("Optiuni");
		firmaOptions = new JMenu("Firme");
		decsOptions = new JMenu("Declaratii");
		importItem = new JMenu("Import");
		exportItem = new JMenu("Export");
		newItem = new JMenuItem("New");
		importEditItem = new JMenuItem("Edit");
		importViewItem = new JMenuItem("View");
		exportTxtItem = new JMenuItem(".txt");
		exportXmlItem = new JMenuItem(".xml");
		closeItem = new JMenuItem("Inchide documentul");
		addFirma = new JMenuItem("Adauga Firma");
		saveFirma = new JMenuItem("Salveaza Firme");
		listFirma = new JMenuItem("Lista Firme");
		addDecs = new JMenuItem("Adauga Declaratii");
		saveDecs = new JMenuItem("Salveaza Declaratiile Curente");
		listDecs = new JMenuItem("Lista Declaratii Curente");

		logo = new ImageIcon("Assets\\logo.png");
		
		importItem.add(importViewItem);
		importItem.add(importEditItem);
		exportItem.add(exportTxtItem);
		exportItem.add(exportXmlItem);
		
		options.add(newItem);
		options.add(importItem);
		options.add(exportItem);
		options.add(closeItem);
		
		firmaOptions.add(addFirma);
		firmaOptions.add(saveFirma);
		firmaOptions.add(listFirma);
		
		decsOptions.add(addDecs);
		decsOptions.add(saveDecs);
		decsOptions.add(listDecs);
		
		jmb.add(options);
		jmb.add(firmaOptions);
		jmb.add(decsOptions);
		
		viewContainer = new JPanel();
		scroll = new JScrollPane(viewContainer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		viewContainer.setLayout(new BoxLayout(viewContainer, BoxLayout.Y_AXIS));
		this.setIconImage(logo.getImage());
	}
	
	public void addListener(ActionListener a)
	{
		newItem.addActionListener(a);
		importEditItem.addActionListener(a);
		importViewItem.addActionListener(a);
		exportTxtItem.addActionListener(a);
		exportXmlItem.addActionListener(a);
		closeItem.addActionListener(a);
		addFirma.addActionListener(a);
		saveFirma.addActionListener(a);
		listFirma.addActionListener(a);
		addDecs.addActionListener(a);
		saveDecs.addActionListener(a);
		listDecs.addActionListener(a);
	}
	
	public void addListenerToWindow(WindowListener w)
	{
		this.addWindowListener(w);
	}
	
	public void setDocHeader(String[] decsList)
	{
		this.add(new HeaderEntry(decsList), BorderLayout.NORTH);
	}
	
	public void addEntry(JPanel toAdd)
	{
		viewContainer.add(toAdd);
	}
	
	public void finish()
	{
		this.getContentPane().add(scroll, BorderLayout.CENTER);
		this.getContentPane().revalidate();
	}
	
	public Component[] getEntryList()
	{
		return viewContainer.getComponents();
	}
	
	public void clearScreen()
	{
		viewContainer.removeAll();
		this.getContentPane().removeAll();
		this.repaint();
	}
}
