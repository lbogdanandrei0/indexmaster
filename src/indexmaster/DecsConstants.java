package indexmaster;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

public final class DecsConstants {
	
	private static TreeMap<String, Integer> decsIndex = new TreeMap<String, Integer>(); 
	private static int currentIndex = 0;
	
	public DecsConstants()
	{
		initDecsIndex();
	}
	
	private void initDecsIndex()
	{
		String[] decsList;
		decsList = FileManager.getDecsList(MainController.dirToImport, new File(MainController.decsFile));
		for(String aux : decsList)
		{
			decsIndex.put(aux, ++currentIndex);
		}
	}
	
	public int getMaxSize()
	{
		return decsIndex.size();
	}
	
	public int getConstant (String dec)
	{
		int toReturn;
		try {
		toReturn = decsIndex.get(dec).intValue();
		}
		catch(Exception e)
		{
			return 0;
		}
		return toReturn;
	}
	
	public String[] getDecsName()
	{
		String[] toReturn = new String[decsIndex.size()];
		int i=-1;
		for(Map.Entry<String, Integer> entry : decsIndex.entrySet())
		{
			toReturn[++i] = entry.getKey();
		}
		return toReturn;
	}
	
	public static void addDec(String decName)
	{
		decsIndex.put(decName, ++currentIndex);
	}
	
	public static void removeDec(String decName)
	{
		decsIndex.remove(decName);
	}
	
}
