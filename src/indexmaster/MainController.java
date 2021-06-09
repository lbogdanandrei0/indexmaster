package indexmaster;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;

public class MainController implements ActionListener, WindowListener, DocumentListener{
	public static final String dirToExport = "Session";
	public static final String dirToExportTxt = "Export";
	public static final String dirToImport = "Source";
	public static final String firmsFile = "firmsData.xml";
	public static final String decsFile = "decsData.xml";
	
	static String fileName = null;
	static boolean isFileOpen = false;
	
	private TreeSet<Firma> firmaList = new TreeSet<Firma>();

	static DecsManager man = new DecsManager();
	private MainView view;
	
	public MainController(MainView v, TreeSet<Firma> firmaList)
	{	
		this.view = v;
		this.firmaList = firmaList;
		v.addListener(this);
		v.addListenerToWindow(this);
		this.view.setVisible(true);
	}
	
	public void loadFirms()
	{
		if(FileManager.createFile(dirToImport, new File(firmsFile)))
			JOptionPane.showMessageDialog(null, "A fost creeata baza de date a firmelor");
		else
		{
			Firma[] list = FileManager.getFirmsFrom(dirToImport, new File(firmsFile));
			for(Firma aux:list)
				firmaList.add(aux);
			JOptionPane.showMessageDialog(null, "Firme gasite:"+firmaList.size());
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().compareTo("New")==0)
			newItemPressed();
		else if(e.getActionCommand().equals("Edit"))
			importEditItemPressed();
		else if(e.getActionCommand().equals("View"))
			importViewItemPressed();
		else if(e.getActionCommand().equals(".txt"))
			exportTxtItemPressed();
		else if(e.getActionCommand().equals(".xml"))
			exportXmlItemPressed();
		else if(e.getActionCommand().equals("Inchide documentul"))
			closeItemPressed();
		else if(e.getActionCommand().equals("Adauga Firma"))
			addFirm();
		else if(e.getActionCommand().equals("Lista Firme"))
			listFirm();
		else if(e.getActionCommand().equals("Salveaza Firme"))
			saveFirms();
		else if(e.getActionCommand().equals("Elimina firma"))
			removeFirm(e);
		else if(e.getActionCommand().equals("Adauga Declaratii"))
			addDecs();
		else if(e.getActionCommand().equals("Salveaza Declaratiile Curente"))
			saveDecs();
		else if(e.getActionCommand().equals("Lista Declaratii Curente"))
			listDecs();
		else if(e.getActionCommand().equals("Elimina declaratia"))
			removeDec(e);
		else if(e.getSource() instanceof JTextField)
			System.out.println("Text");
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		FileManager.saveFirmaList(firmaList, dirToImport, new File(firmsFile));
		System.out.println("Saved");
	}

	private void newItemPressed()
	{
		if(!isFileOpen) {
			try{
				do {
					if(fileName == null)
						fileName = (String) JOptionPane.showInputDialog(view,
								"Nume fisier:", 
								"Document nou", 
								JOptionPane.PLAIN_MESSAGE,
								null,
								null,
								"Luna_"+LocalDate.now().getMonthValue()+"_"+LocalDate.now().getYear());
					else
						fileName = (String) JOptionPane.showInputDialog(view, 
								"Nume fisier:(Nu poate contine spatiu sau extensii)", 
								"Document nou", 
								JOptionPane.PLAIN_MESSAGE,
								null,
								null,
								"Luna_"+LocalDate.now().getMonthValue()+"_"+LocalDate.now().getYear());
				}while(fileName.contains(" ")||fileName.contains("."));
				}catch(Exception e)
				{
					e.printStackTrace();
					return;
				}
			isFileOpen = true;
			for(Firma aux:firmaList)
			{
				EntryFirma ef = new EntryFirma(aux, man.listAllDecs(), this);
				ef.setListener(this);
				view.addEntry(ef);
			}
			view.finish();
			view.setDocHeader(man.listAllDecs());
			view.getContentPane().revalidate();
			JOptionPane.showMessageDialog(null, "S-au adaugat "+firmaList.size()+" intrari");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Fisier deschis, este necesara inchiderea!");
		}
	}
	
	private void importEditItemPressed()
	{
		if(!isFileOpen)
		{
			JFileChooser chooseFile = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("xml files(*.xml)", "xml");
			chooseFile.setFileFilter(filter);
			chooseFile.setCurrentDirectory(new File(dirToExport+"\\"));
			int response = chooseFile.showOpenDialog(null);
			if(response == JFileChooser.APPROVE_OPTION)
			{
				fileName = chooseFile.getSelectedFile().getName();
				System.out.println(fileName);
				String[] decsNameList;
				Firma[] firmsList;
				String[] decsIndex;
				decsNameList = FileManager.getDecsListFromFile(dirToExport, new File(fileName));
				firmsList = FileManager.getFirmsFromFile(dirToExport, new File(fileName));
				for(int i=0; i<firmsList.length; i++)
				{
					decsIndex = FileManager.getDecsIndexFrom(firmsList[i].getName(), dirToExport, new File(fileName));
					EntryFirma ef = new EntryFirma(firmsList[i], decsIndex, decsNameList);
					view.addEntry(ef);
				}
				isFileOpen = true;
				view.finish();
				view.setDocHeader(decsNameList);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Fisier deschis, este necesara inchiderea!");
		}
	}
	
	private void importViewItemPressed()
	{
		
	}
	
	private void exportTxtItemPressed()
	{
		Component[] entryList = view.getEntryList();
		if(entryList.length < 1 || !(entryList[0] instanceof EntryFirma))
			JOptionPane.showMessageDialog(view, "Nu se poate salva documentul");
		else
			FileManager.exportToTxt(firmaList, view.getEntryList(), dirToExportTxt, new File(fileName+".txt"));
	}
	
	private void exportXmlItemPressed()
	{
		if(!(isFileOpen==false))
		{
			if(fileName.endsWith(".xml")==false)
				fileName = fileName.concat(".xml");
			if(JOptionPane.showConfirmDialog(null, "Export la:"+fileName, "Export", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			{
				FileManager.saveSession(firmaList, view.getEntryList(), dirToExport, new File(fileName));
			}
		}
		else
			JOptionPane.showMessageDialog(null, "Nu exista un document deschis");
	}
	
	private void closeItemPressed()
	{
		isFileOpen = false;
		fileName = null;
		this.view.clearScreen();
	}
	
	private void addFirm()
	{
		String[] decList = man.listAllDecs();
		AddFirmDialog diag = new AddFirmDialog(decList, view);
		if(diag.getStatus() == AddFirmDialog.STATUS_SAVED)
		{
			String cui;
			cui = diag.getCui();
			if(getFirmWithCui(cui) == null)
			{
				String name;
				name = diag.getName();
				if(!name.equals(""))
				{
					boolean hasRo;
					int decs=0;
					hasRo = diag.hasRo();
					decList = diag.getDecs();
					decs = man.setDecs(decList);
					firmaList.add(new Firma(cui, name, decs, hasRo));
				}
				else
					JOptionPane.showMessageDialog(view, "Numele nu poate sa fie null");
			}
			else
			{
				Firma f = getFirmWithCui(cui);
				JOptionPane.showMessageDialog(view, "Exista deja firma "+f.getName());
			}
		}
	}
	
	private void listFirm()
	{
		if(!isFileOpen)
		{System.out.println("true");
			for(Firma aux : firmaList)
			{
				System.out.println(aux.toString());
				EntryList el = new EntryList(aux);
				el.setListenerFirm(this);
				view.addEntry(el);
			}
			view.finish();
			isFileOpen = true;
		}
		else
			JOptionPane.showMessageDialog(view, "Inchide documentul curent");
	}
	
	private void saveFirms()
	{
		String firmsSave = (String) JOptionPane.showInputDialog(view, 
				"Fisier salvare:",
				"Salveaza firmele",
				JOptionPane.YES_NO_OPTION,
				null,
				null,
				firmsFile);
		FileManager.saveFirmaList(firmaList, dirToImport, new File(firmsSave));
	}
	
	private void removeFirm(ActionEvent e)
	{
		JButton aux = (JButton) e.getSource();
		if(JOptionPane.showConfirmDialog(view, "Elimina cui:"+aux.getName(),
				"Elimina firma",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			{
				firmaList.remove(getFirmWithCui(aux.getName()));
				view.clearScreen();
				isFileOpen = false;
				listFirm();
				view.finish();
			}
			else
				System.out.println("Nu elimina firma");
	}
	
	private Firma getFirmWithCui(String cui)
	{
		Iterator<Firma> it = firmaList.iterator();
		while(it.hasNext())
		{
			Firma aux = (Firma) it.next();
			if(aux.getCui().equals(cui))
				return aux;
		}
		return null;
	}
	
	private void addDecs()
	{
		String decToAdd = (String) JOptionPane.showInputDialog(view, 
				"Numele declaratiei:", 
				"Adauga Declaratie",
				JOptionPane.YES_NO_OPTION
				);
		if(decToAdd != null)
		{
			DecsConstants.addDec(decToAdd);
		}
	}
	
	private void saveDecs()
	{
		String fileDecs = (String) JOptionPane.showInputDialog(view,
				"Numele salvarii:",
				"Salveaza declaratiile",
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				decsFile);
		FileManager.saveDecsList(man.listAllDecs(), dirToImport, new File(fileDecs));
	}
	
	private void listDecs()
	{
		if(!isFileOpen)
		{
			String[] decList = man.listAllDecs();
			for(String dec : decList)
			{
				EntryList el = new EntryList(dec);
				el.setListenerDec(this);
				view.addEntry(el);
			}
			view.finish();
			isFileOpen = true;
		}
		else
			JOptionPane.showMessageDialog(view, "Inchideti documentul curent");
	}
	
	private void removeDec(ActionEvent e)
	{
		JButton aux = (JButton) e.getSource();
		if(JOptionPane.showConfirmDialog(view, "Eliminati declaratia "+aux.getName()+"?", "Elimina declaratia",
				JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
		{
			DecsConstants.removeDec(aux.getName());
			view.clearScreen();
			isFileOpen = false;
			listDecs();
			view.finish();
		}
		else
			System.out.println("Nu sterge "+aux.getName());
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		try
		{
			if(e.getDocument().getText(e.getDocument().getLength()-1, 1).compareTo("0")<0 ||
					e.getDocument().getText(e.getDocument().getLength()-1, 1).compareTo("9")>0) 
				System.out.println("litera");
			else
				System.out.println("cifra");
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		System.out.println("remove");
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		System.out.println("change");
	}
}
