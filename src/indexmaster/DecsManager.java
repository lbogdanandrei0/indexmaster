package indexmaster;

public class DecsManager {
	private DecsConstants helper = new DecsConstants();
	
	public String[] listAllDecs()
	{
		return helper.getDecsName();
	}
	
	public int setDecs(String[] decs)
	{
		int toReturn=0;
		int bitToSet;
		for(String aux:decs)
		{
			bitToSet = helper.getConstant(aux);
			if(bitToSet != 0)
				toReturn = toReturn | (1<<(bitToSet-1));
		}
		return toReturn;
	}
	
	public int removeDec(int oldDec, String decName)
	{
		int bitToSet = helper.getConstant(decName);
		if(bitToSet != 0)
			return oldDec & (~1<<(bitToSet-1));
		return oldDec;
	}
	
	public int addDec(int oldDec, String decName)
	{
		int bitToSet = helper.getConstant(decName);
		if(bitToSet != 0)
			return oldDec | (1<<(bitToSet-1));
		return oldDec;
	}
	
	public int nrOfDecs(int dec)
	{
		int i;
		int cnt;
		cnt = 0;
		for(i=1; i<=Integer.BYTES*8; i++)
		{
			if(dec == (dec | (1<<(i-1))))
					cnt++;
		}
		return cnt;
	}
	
	public boolean hasDec(int dec, String decName)
	{
		if(helper.getConstant(decName) != 0)
			return addDec(dec, decName)==dec;
		return false;
	}
	
	public String[] listDec(int dec)
	{
		String[] toReturn = new String[helper.getMaxSize()];
		String[] names;
		int i=-1;
		names = helper.getDecsName();
		for(String aux:names)
		{
			if(hasDec(dec, aux))
				toReturn[++i]=aux;
		}
		return toReturn;
	}
	
	public int getMaxSize()
	{
		return helper.getMaxSize();
	}
}
