package indexmaster;

import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.TreeSet;

import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class FileManager {
	
	public static boolean createFile(String dirName, File f)
	{
		File dir = new File(dirName);
		try
		{
			dir.mkdir();
			try
			{
				f = new File(dirName+"\\"+f.getName());
				if(f.createNewFile())
					return true;
			}
			catch(Exception e)
			{
				System.out.println("Failed to create file " + f.getName() + " in " + dirName);
				return false;
			}
		}
		catch(Exception e)
		{
			System.out.println("Failed to create directory "+dirName);
			return false;
		}
		return false;
	}
	
	public static void saveFirmaList(TreeSet<Firma> firmaList, String dirName, File f)
	{
		f = new File(dirName+"\\"+f.getName());
		try
		{
			if(f.createNewFile())
				System.out.println("Created and saved firmaList");
			else
				System.out.println("Override firmaList");
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = builderFactory.newDocumentBuilder();
				Document document = builder.newDocument();
				
				Element root = document.createElement("firms");
				
				for(Firma aux : firmaList)
				{
					Element firma = document.createElement("firma");
					Attr cui = document.createAttribute("cui");
					Attr name = document.createAttribute("name");
					Attr dec = document.createAttribute("dec");
					Attr ro = document.createAttribute("ro");
					cui.setValue(aux.getCui());
					name.setValue(aux.getName());
					dec.setValue(aux.getDecs()+"");
					ro.setValue((aux.hasRo()==true?"true":"false"));
					firma.setAttributeNode(cui);
					firma.setAttributeNode(name);
					firma.setAttributeNode(dec);
					firma.setAttributeNode(ro);
					root.appendChild(firma);
				}
				document.appendChild(root);
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(document);
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.transform(source, new StreamResult(f));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void saveSession(TreeSet<Firma> firmaList, Component[] entryList, String dirName, File f)
	{
		try
		{
			if(createFile(dirName, f))
				System.out.println("Fisier salvare nou");
			else
				System.out.println("Override salvare");
			f = new File(dirName+"\\"+f.getName());
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder d = documentFactory.newDocumentBuilder();
			Document document = d.newDocument();
			document.setXmlStandalone(true);
			Element root = document.createElement("session");
			Attr nrOfEntry = document.createAttribute("nrOfEntry");
			nrOfEntry.setValue(firmaList.size()+"");
			root.setAttributeNode(nrOfEntry);
			Element id = document.createElement("date");
			id.appendChild(document.createTextNode(LocalDateTime.now().toString()));
			root.appendChild(id);
			int i=0;
			JTextField[] decs;
			for(Firma aux:firmaList)
			{
				Element el = document.createElement("firma");
				Attr ro = document.createAttribute("ro");
				Attr cui = document.createAttribute("cui");
				Attr name = document.createAttribute("name");
				Attr dec = document.createAttribute("dec");
				ro.setValue((aux.hasRo()==true?"true":"false"));
				cui.setValue(aux.getCui());
				name.setValue(aux.getName());
				dec.setValue(aux.getDecs()+"");
				el.setAttributeNode(ro);
				el.setAttributeNode(cui);
				el.setAttributeNode(name);
				el.setAttributeNode(dec);
				
				Element decsElement = document.createElement("decs");
				EntryFirma ef = (EntryFirma) entryList[i];
				decs = ef.getDecs();
				for(JTextField jtf:decs)
				{
					Element decElement = document.createElement("dec");
					Attr decName = document.createAttribute("decName");
					decName.setValue(jtf.getToolTipText());
					decElement.setAttributeNode(decName);
					decElement.appendChild(document.createTextNode(jtf.getText()));
					decsElement.appendChild(decElement);
				}
				i++;
				el.appendChild(decsElement);
				root.appendChild(el);
			}
			document.appendChild(root);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult streamResult = new StreamResult(f);
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, streamResult);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	
	public static void saveDecsList(String[] decsList, String dirName, File f)
	{
		f = new File(dirName+"\\"+f.getName());
		try
		{
			if(f.createNewFile())
			{
				System.out.println("Created new decList save");
			}
			else
			{
				System.out.println("Overriding decList save");
			}
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.newDocument();
			
			Element root = document.createElement("decs");
			Attr nrOfDecs = document.createAttribute("nrOfDecs");
			nrOfDecs.setValue(decsList.length+"");
			root.setAttributeNode(nrOfDecs);
			
			for(String aux : decsList)
			{
				Element dec = document.createElement("dec");
				Attr decName = document.createAttribute("name");
				decName.setValue(aux);
				dec.setAttributeNode(decName);
				root.appendChild(dec);
			}
			document.appendChild(root);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(document);
			
			transformer.transform(source, new StreamResult(f));
		}catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	
	public static String[] getDecsListFromFile(String dirName, File f)
	{
		String[] toReturn=null;
		f = new File(dirName+"\\"+f.getName());
		try
		{
				System.out.println("Citire din "+f.getName());
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = builderFactory.newDocumentBuilder();
				Document document = builder.parse(f);
				document.getDocumentElement().normalize();
				NodeList nList = document.getElementsByTagName("firma");
				Element firma = (Element) nList.item(0);
				Element dec = (Element)firma.getElementsByTagName("decs").item(0);
				NodeList decs = dec.getElementsByTagName("dec");
				toReturn = new String[decs.getLength()];
				for(int j=0; j<decs.getLength(); j++)
				{
					Element d = (Element) decs.item(j);
					toReturn[j] = d.getAttribute("decName");
				}
		}catch(Exception e)
		{
			return null;
		}
		return toReturn;
	}
	
	public static Firma[] getFirmsFrom(String dirName, File f)
	{
		Firma[] toReturn = null;
		f = new File(dirName+"\\"+f.getName());
		try
		{
		if(!f.createNewFile())
		{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(f);
			Element root = (Element)document.getDocumentElement();
			NodeList firms = root.getElementsByTagName("firma");
			toReturn = new Firma[firms.getLength()];
			boolean ro;
			String cui;
			String name;
			int dec;
			for(int i=0; i<firms.getLength(); i++)
			{
				Element firma = (Element)firms.item(i);
				cui = firma.getAttribute("cui");
				name = firma.getAttribute("name");
				ro = Boolean.parseBoolean(firma.getAttribute("ro"));
				dec = Integer.parseInt(firma.getAttribute("dec"));
				toReturn[i] = new Firma(cui, name, dec, ro);
			}
		}
		else
			f.delete();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return toReturn;
		}
		return toReturn;
	}
	
	public static Firma[] getFirmsFromFile(String dirName, File f)
	{
		Firma[] toReturn = null;
		f = new File(dirName+"\\"+f.getName());
		try {
		if(!f.createNewFile())
		{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(f);
			XPathFactory pathFactory = XPathFactory.newDefaultInstance();
			XPath xPath = pathFactory.newXPath();
			try
			{
				XPathExpression expr = xPath.compile("/session/firma");
				NodeList firms = (NodeList)expr.evaluate(document, XPathConstants.NODESET);
				toReturn = new Firma[firms.getLength()];
				Boolean ro;
				String cui;
				String name;
				int dec;
				for(int i=0; i<firms.getLength(); i++)
				{
					Element firma = (Element)firms.item(i);
					ro = Boolean.parseBoolean(firma.getAttribute("ro"));
					cui = firma.getAttribute("cui");
					name = firma.getAttribute("name");
					dec = Integer.parseInt(firma.getAttribute("dec"));
					toReturn[i] = new Firma(cui, name, dec, ro);
				}
			}catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}
		else
			f.delete();
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return toReturn;
	}
	
	public static String[] getDecsIndexFrom(String firmName, String dirName, File f)
	{
		String[] toReturn=null;
		f = new File(dirName+"\\"+f.getName());
		try
		{
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = builderFactory.newDocumentBuilder();
				Document document = builder.parse(f);
				XPathFactory pathFactory = XPathFactory.newDefaultInstance();
				XPath xPath = pathFactory.newXPath();
				try
				{
					XPathExpression expr = xPath.compile("session/firma[contains(@name, '"+firmName+"')]");
					NodeList firms = (NodeList)expr.evaluate(document, XPathConstants.NODESET);
					Element firma = (Element) firms.item(0);
					Element dec = (Element)firma.getElementsByTagName("decs").item(0);
					NodeList decs = dec.getElementsByTagName("dec");
					toReturn = new String[decs.getLength()];
					for(int i=0; i<decs.getLength(); i++)
					{
						Element d = (Element)decs.item(i);
						toReturn[i] = d.getTextContent();
					}
					
				}catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return toReturn;
	}
	
	public static String[] getDecsList(String dirName, File f)
	{
		String[] toReturn = null;
		if(!f.getName().endsWith(".xml"))
			f.renameTo(new File(f.getName().concat(".xml")));
		f = new File(dirName+"\\"+f.getName());
		if(createFile(dirName,f))
			System.out.println("Created new decList file");
		try
		{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(f);
			
			Element root = document.getDocumentElement();
			int nrOfDecs = Integer.parseInt(root.getAttribute("nrOfDecs"));
			NodeList decs = root.getElementsByTagName("dec");
			toReturn = new String[nrOfDecs];
			for(int i=0; i<nrOfDecs; i++)
			{
				Element dec = (Element)decs.item(i);
				toReturn[i] = dec.getAttribute("name");
				System.out.println(toReturn[i]);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return toReturn;
	}
	
	public static void exportToTxt(TreeSet<Firma> firmsList, Component[] entryList, String dirName, File f)
	{
		if(createFile(dirName, f))
			System.out.println("Created new .txt export file");
		else
			System.out.println("Overriding .txt file");
			try
			{
				f = new File(dirName+"\\"+f.getName());
				FileWriter fw = new FileWriter(f);
				BufferedWriter writer = new BufferedWriter(fw);
				int i=0;
				for(Firma aux : firmsList)
				{
					writer.write(aux.getName()+"\n\n");
					JTextField[] jtf;
					EntryFirma ef = (EntryFirma) entryList[i];
					jtf = ef.getDecs();
					for(JTextField txt : jtf)
					{
						writer.write(txt.getToolTipText()+":"+txt.getText()+"\n");
					}
					writer.write("\n");
					i++;
				}
				writer.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
	}
}
