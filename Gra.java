package mz_kolko_i_krzyzyk;
import java.util.Random;
import javax.swing.*;

/**
 * Klasa Gra - jest to główna klasa, która steruje grą
 * W tej klasie inicjalizujemy planszę i dwóch graczy - człowieka i komputer - którzy będą ze sobą
 * toczyć pojedynek. 
 */
public class Gra extends JApplet {
	Plansza p = new Plansza(this);				//inicjalizacja obiektu klasy Plansza
	Gracz c = new Czlowiek();		//inicjalizacja gracza - człowiek o imieniu Marcin, który podczas gry będzie posługiwał się znakami 'X'
	Gracz k = new Komputer();			//inicjalizacja gracza - komputer o imieni Ania, który podczas gry będzie posługiwał się znakami 'O'
	
	//rozwiązania gry
	public static int rozwiazania[][] = {
			{0,1,2},
			{3,4,5},
			{6,7,8},
			{0,3,6},
			{1,4,7},
			{2,5,8},
			{0,4,8},
			{2,4,6},
	};
		
	/*
	 * metoda mieszająca rozwiązania - tak aby przypadkiem komputer nie dążył do celu zawsze w takiej samej kolejności
	 */
	private void pomieszajRozwiazania(){
		Random rand = new Random();
		int pomieszane_rozwiazania[][] = new int[8][3];
		
		//w pętli poniżej tworzymy ośmioelementową tablicę r[]
		//która będzie zawierała w sobie niepowtarzalne liczby od 1 do 8
		//tablica ta odpowiada ilości rozwiązań - jest ich osiem jak wiadomo
		int[] r = new int[8];									//tworzymy osmioelementową tablicę r[]
		int count = 0;											//pomocniczy licznik
		boolean found = false;									//pomocnicza zmienna
		while(count < 8){										//pętla która będzie wypełniać tablicę r[] liczbami od 1 do 8 w losowej kolejności
			int losowa_liczba = rand.nextInt(8);				//losujemy liczbę z zakresu od 0-7
			losowa_liczba++;									//ponieważ mamy rozwiązania od 1-8 to zwiększamy cyfre o 1 tak aby odpowiadała danemu rozwiązaniu
			
			//pętla w której badamy czy wylosowana liczba
			//jest już przypisana do tablicy r[] - jesli tak to przerywamy i losujemy nowa liczbe
			for(int i=0; i<r.length; i++){
				if(r[i]==losowa_liczba){		//element tablicy r[] posiada juz wartosc odpowiadajaca wylosowanej liczbie
					found = true;				//oznaczamy ze znaleziono taka liczbe w elemencie tablicy
					break;						//przerywamy petle i losujemy dalej
				} else {
					found = false;				//oznaczamy ze takiej liczby nie ma w tym elemencie tablicy
				}
			}
			
			//jesli nie znaleziono liczby w tablicy r[] to mozna ja dopisac
			if(found==false){
				r[count] = losowa_liczba;
				count++;
			}
		} //element zapętlony aż do momentu przypisania do r[] osmiu unikalnych liczb od 1-8
		
		//taka mała walidacja w konsoli
		//for(int x=0; x<r.length; x++)
		//	System.out.println("r[" + x + "] " + r[x]);
		
		
		for(int y=0; y<rozwiazania.length; y++){
			pomieszane_rozwiazania[y] = rozwiazania[r[y]-1];
		}
		
		rozwiazania = pomieszane_rozwiazania;
	}
	
	/*
	 * metoda uruchamiająca grę
	 * nie zwraca żadnej wartości
	 */
	public void startujemy() throws ZlyZnakException{
		k.setNazwaGracza("Anna");
		c.setNazwaGracza("Marcin");
		try {
			k.setZnak("O");
			c.setZnak("X");
		} catch (ZlyZnakException e) {
			throw e;
		}
		
		Console.run(p,300,300);			//uruchomienie okienka z planszą (wysokość 300px na szerokość 300px)
		p.ustawGracza(c);				//ustawienie początkowego stanu (gracza) - ustawiony tutaj gracz nie bedzie pierwszy
		pomieszajRozwiazania();			//mieszamy rozwiązania wg których ruch wykonuje komputer - robimy tak aby komputer był bardziej nieprzewidywalny
		ruch();
	}
	
	/*
	 * metoda, w której następuje ruch gracza
	 * ruch gracza odbywa się "na zmianę" - raz komputer, raz czlowiek
	 */
	public void ruch(){
		//inicjalizacja i utworzenie obiektu klasy Walidacja 
		Walidacja w = new Walidacja(p.getPola()); 	
		
		//sprawdzamy czy zostało osiągnięte rozwiązanie
		if(w.czyJestWygrany(k)){
			//System.out.println("Komputer wygrał!");
			//ustawiamy w polu tekstowym na planszy (pod planszą) informację o tym, że komputer zwyciężył
			p.t.setText("Komputer o imieniu " + p.getGracz().getNazwaGracza() + " wygrał pojedynek!"); 
			p.usunZdarzenia();
			return;
		} else if(w.czyJestWygrany(c)){
			//System.out.println("Człowiek wygrał!");
			//ustawiamy w polu tekstowym na planszy (pod planszą) informację o tym, że człowiek zwyciężył
			p.t.setText("Człowiek o imieniu " + p.getGracz().getNazwaGracza() + " wygrał!");
			p.usunZdarzenia();
			return;
		}
		
		//sprawdzamy czy jest conajmniej jedno wolne pole
		//czy istnieje możliwość ruchu
		if(w.czyJestWolnePole()==false){
			//System.out.println("Brak możliwości ruchu - remis");
			p.t.setText("Brak możliwości ruchu - remis");
			p.usunZdarzenia();
			return;
		}
		
		//ruch wykonuje przeciwnik
		//wyciągnięcie info kto był ostatnim graczem
		Gracz player = p.getGracz();
		if(player.getClass().getName().indexOf("Czlowiek")>0) //graczem jest czlowiek (RTTI)
			k.wykonajRuch(p);
		else 
			c.wykonajRuch(p);
	}
	
	public static void main(String[] args) {	
		Gra g = new Gra();							//tworzymy obiekt Gra	
		try {
			g.startujemy();							//startujemy!
		} catch (ZlyZnakException e) {
			System.err.println(e.getMessage() + ": można ustawić graczowi jedynie X lub O");
		}									
	}
	

	/*
	 * Metoda za pomocą której odpalamy grę z poziomu apletu na stronie www
	 * @see java.applet.Applet#start()
	 */
	public void start() {
		Gra g = new Gra();							//tworzymy obiekt Gra	
		try {
			g.startujemy();							//startujemy!
		} catch (ZlyZnakException e) {
			System.err.println(e.getMessage() + ": można ustawić graczowi jedynie X lub O");
		}	
	}
}
