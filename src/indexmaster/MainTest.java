package indexmaster;

import java.util.TreeSet;

public class MainTest {

	public static void main(String[] args) {	
		MainView view = new MainView();
		TreeSet<Firma> firmaList = new TreeSet<Firma>();
		
		MainController m = new MainController(view, firmaList);
		m.loadFirms();
		}

}
