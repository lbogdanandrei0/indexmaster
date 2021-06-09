package indexmaster;

public class Firma implements Comparable {
	private String cui;
	private String name;
	private int decs;
	private boolean ro;
	
	public Firma()
	{
		cui = null;
		name = "default-name";
		decs = 0;
		ro = true;
	}
	
	public Firma(String cui)
	{
		this();
		this.cui = cui;
	}
	
	public Firma(String cui, String name)
	{
		this();
		this.cui = cui;
		this.name = name;
	}
	
	public Firma(String cui, String name, int decs)
	{
		this();
		this.cui = cui;
		this.name = name;
		this.decs = decs;
	}
	
	public Firma(String cui, String name, int decs, boolean ro)
	{
		this();
		this.cui = cui;
		this.name = name;
		this.decs = decs;
		this.ro = ro;
	}
	
	public void setCui(String cui)
	{
		this.cui = cui;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setDecs(int decs)
	{
		this.decs = decs;
	}
	
	public void setRo(boolean ro)
	{
		this.ro = ro;
	}
	
	public String getCui()
	{
		return this.cui;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getDecs()
	{
		return this.decs;
	}
	
	public boolean hasRo()
	{
		return this.ro;
	}
	
	public int compareTo(Object o) 
	{
		Firma aux;
		aux = (Firma)o;
		return this.getName().compareTo(aux.getName());
	}
	
	public String toExport()
	{
		return (ro==true)?"true":"false"+"@"+this.cui+"@"+this.name+"@"+this.decs+"@";
	}
}
