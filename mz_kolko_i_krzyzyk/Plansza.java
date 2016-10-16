package mz_kolko_i_krzyzyk;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Klasa Plansza odpowiada za wyświetlenie planszy na ekranie, 
 * zawiera m.in. tablicę obiektów typu Znak, które są polami planszy. 
 * Jest to dziewięć przycisków za pomocą których możliwe jest wykonanie ruchów przez graczy. 
 * Klasa ta zawiera w sobie inna klasę – BL – która pomaga sprawnie zarządzać przyciskami zgodnie z tym 
 * jaki ruch wykonali gracze (m.in. wyświetlać znak X lub O).
 */
public class Plansza extends JApplet{
	private static final long serialVersionUID = 1L;
	private Gra g;
	private Gracz player;
	private Znak[] pole = new Znak[9];				//Tablica znaków
	private JButton[] b = new JButton[9];			//Tablica przycisków
	private BL[] bl = new BL[9];
	public JTextField t = new JTextField(40);		//Pole na komunikaty
	
	/*
	 * Konstruktor klasy
	 */
	public Plansza(Gra _g){
		g = _g;
		
		for(int i=0; i<pole.length; i++)
			pole[i] = new Znak();
	}
	
	/*
	 * Metoda za pomocą której ustawiamy gracza, który wykonuje ruch na planszy
	 */
	public void ustawGracza(Gracz _player){
		player = _player;
	}
	
	/*
	 * Metoda za pomocą której możemy uzyskać obiekt Gracz, który aktualnie wykonuje ruch na planszy
	 */
	public Gracz getGracz(){
		return player;
	}
	
	/*
	 * Metoda za pomoca której zaznaczamy wybrany znak na planszy
	 * Pierwszt argument jest liczbą oznaczającą pole, które zostanie zaznaczone (od 0-8)
	 * Drugim argumentem jest znak, który zostanie "postawiony" w danym polu ("X" lub "O")
	 */
	private void zaznaczPole(int _pole, String _znak){
		pole[_pole].ustaw(_znak);
	}
	
	/*
	 * Metoda zwracająca tablicę dziewięciu obiektów typu Znak
	 * z których składa się plansza
	 */
	public Znak[] getPola(){
		return pole;
	}
	
	/*
	 * Metoda pomocnicza za pomocą której sprawdzam w konsoli jakie pola są zaznaczone
	 */
	public void pokazPola(){
		for(int i=0; i<pole.length; i++)
			System.out.println("Pole " + i + ": " + pole[i].rysuj());
	}
	
	/*
	 * Klasa za pomocą której możliwe jest sterowanie polami na planszy
	 */
	class BL implements ActionListener{
		private int pole; 
		/*
		 * Konstruktor klasy w którym rejestrujemy
		 * jaki przycisk (jakie pole) został kliknięty (zaznaczony)
		 */
		BL(int _pole){
			pole = _pole;
		}
		public void actionPerformed(ActionEvent e) {
			System.out.println("zaznaczone pole: " + pole); 				//pomoc
			zaznaczPole(pole,player.getZnak());								//zaznaczenie wybranego pola					
			((AbstractButton)e.getSource()).setText(player.getZnak());		//ustawiamy na przycisku tekst odpowiadający znakowi gracza X lub O
			((AbstractButton)e.getSource()).removeActionListener(this);		//usuwamy aktywność przycisku - od teraz po kliknięciu nic się nie oznaczy
			g.ruch();														//wywołujemy kolejny ruch 
			
			pokazPola();	//pomoc
		}
	}
	
	/*
	 * metoda za pomocą której wyświetlamy planszę w postaci buttonów
	 */
	public Container pokazPlansze(){
		//panel z informacja jaki gracz wykonuje ruch
		//JPanel usr = new JPanel();
		//usr.setLayout(new BoxLayout(usr, BoxLayout.X_AXIS));
		//usr.add(u);
		
		//panel z informacjami
		JPanel info = new JPanel();
		info.setLayout(new BoxLayout(info, BoxLayout.X_AXIS));
		t.setEditable(false);										//pole jest nieaktywne - nie można edytować komunikatu
		info.add(t);
		
		//panel z przyciskami
		JPanel btns = new JPanel();
		btns.setLayout(new GridLayout(3,3));	//plansza 3x3
		
		
		//rysowanie przycisków
		for(int i = 0; i < pole.length; i++){
			b[i] = new JButton(pole[i].rysuj());
			bl[i] = new BL(i);
			b[i].addActionListener(bl[i]);
			btns.add(b[i]);
		}
		
		Container cp = getContentPane();
		//cp.add(BorderLayout.NORTH,usr);
		cp.add(BorderLayout.SOUTH,info);
		cp.add(BorderLayout.CENTER,btns);
		return cp;
	}
	
	/*
	 * inicjalizujemy aplet
	 */
	public void init(){
		pokazPlansze();
	}
	
	/*
	 * Metoda za pomocą której wykonujemy zdarzenie (zaznaczamy pole na planszy)
	 */
	public void wykonajZdarzenie(int _i){
		BL test = new BL(_i);
		test.actionPerformed(
				new ActionEvent(b[_i], 1, ""));
		b[_i].removeActionListener(bl[_i]);
	}
	
	/*
	 * Metoda za pomocą której usuwamy wszystkie zdarzenia - wszystkie przyciski (pola planszy) stają się nieaktywne
	 */
	public void usunZdarzenia(){
		for(int i=0; i<b.length; i++)
			b[i].removeActionListener(bl[i]);
	}
}
