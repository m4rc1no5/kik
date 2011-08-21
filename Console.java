package mz_kolko_i_krzyzyk;
import javax.swing.*;

/**
 * Klasa - platforma wyświetlająca
 * Jest to narzędzie nie moje - skorzystałem tutaj z rozwiązania Pana B. Eckel'a z książki
 * Thinking in Java (str.632-633)
 */
public class Console {
	
	/*
	 * Metoda za pomocą której wydobywamy nazwę klasy (przy użyciu RTTI)
	 */
	public static String title(Object o){
		String t = o.getClass().toString();
		//Usuwamy slowo class
		if(t.indexOf("class") != -1)
			t = t.substring(6);
		return t;
	}
	
	public static void setupClosing(JFrame frame){
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void run(JApplet applet, int width, int height){
		JFrame frame = new JFrame(title(applet));
		setupClosing(frame);
		frame.getContentPane().add(applet);
		frame.setSize(width, height);
		applet.init();
		applet.start();
		frame.setVisible(true);
	}
}
