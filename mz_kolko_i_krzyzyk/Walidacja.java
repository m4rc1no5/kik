package mz_kolko_i_krzyzyk;
import java.util.Random;

/**
 * Klasa Walidacja sprawdza czy jest zwycięzca pojedynku oraz czy są wolne pola na planszy i możliwe
 * jest kontunuowanie gry. 
 * Klasa daje również informacje graczowi wykonującemu ruch, które pole może dać mu zwycięstwo, które pole może dać
 * zwycięstwo przeciwnikowi oraz jakie jest jeszcze możliwe rozwiązanie (jeśli jest).  
 */
public class Walidacja {
	private static Random rand = new Random();			//Tworzymy obiek klasy Random()
	private int rozwiazania[][] = Gra.rozwiazania;		//możliwe rozwiązania (zwycięskie) gry
	private Znak[] pola;								//pola planszy
	
	/*
	 * Konstruktor klasy
	 * W konstruktorze pobieramy pola planszy i przypisujemy je do naszej prywatnej zmiennej o nazwie pola
	 */
	public Walidacja(Znak[] _pola){
		pola = _pola;
	}
	
	/*
	 * Metoda sprawdzająca czy gracz (komputer lub człowiek) osiągnął jedno z ośmiu możliwych rozwiązań
	 * 
	 * Metoda pobiera jeden argument - obiekt typu Gracz oraz zwraca
	 * true (jeśli gracz osiągnął jedno z ośmiu rozwiązań) lub false (jeśli nie osiągnął rozwiązania)
	 */
	public boolean czyJestWygrany(Gracz g){
		String znak = g.getZnak(); //znak gracza: X lub O
		
		for(int i=0; i<rozwiazania.length; i++){
			if(pola[rozwiazania[i][0]].rysuj()==znak && pola[rozwiazania[i][1]].rysuj()==znak && pola[rozwiazania[i][2]].rysuj()==znak)
				return true;
		}
		return false;
	}
	
	/*
	 * Metoda sprawdza czy plansza składająca się z dziewięciu pól posiada 
	 * jeszcze conajmniej jedno wolne (nie oznaczone 'O' lub 'X') pole
	 * 
	 * Metoda zwraca true (jeśli istnieje conajmniej jedno wolne pole) lub false (jeśli nie ma już wolnych pól)
	 */
	public boolean czyJestWolnePole(){
		for(int i=0; i<pola.length; i++){
			if(pola[i].rysuj()==null)
				return true;			//jest wolne pole!
		}
		return false;
	}
	
	/*
	 * Metoda zwracająca pole, które jest zwycięskie dla danego gracza
	 * 
	 * Metoda pobiera jeden argument - obiekt typu Gracz (może to być np. Komputer lub Człowiek)
	 * Metoda zwraca liczbę określającą pole, które gracz powinien zakreślić żeby wygrać pojedynek
	 * Jeśli metoda zwróci liczbę od 0 do 8 to oznacza to, że istnieje zwycięski ruch dzięki któremu
	 * gracz może wygrać w tym ruchu pojedynek. Jeśli natomiast metoda zwróci liczbę -1 to oznacza to, że
	 * nie istnieje w tym momencie możliwość zwycięskiego zakończenia pojedynku przez gracza.
	 */
	public int podajZwycieskiRuch(Gracz g){
		String znak = g.getZnak();
		for(int i=0; i<rozwiazania.length; i++){
			if(pola[rozwiazania[i][0]].rysuj()==znak && pola[rozwiazania[i][1]].rysuj()==znak && pola[rozwiazania[i][2]].rysuj()==null)
				return rozwiazania[i][2];
			else if(pola[rozwiazania[i][0]].rysuj()==znak && pola[rozwiazania[i][1]].rysuj()==null && pola[rozwiazania[i][2]].rysuj()==znak)
				return rozwiazania[i][1];
			else if(pola[rozwiazania[i][0]].rysuj()==null && pola[rozwiazania[i][1]].rysuj()==znak && pola[rozwiazania[i][2]].rysuj()==znak)
				return rozwiazania[i][0];
		}
		return -1; //komputer nie ma mozliwosci wygrania w nastepnej rundzie
	}
	
	/*
	 * Metoda zwracająca pole, które może być zwycięskie dla przeciwnika w następnym ruchu
	 * 
	 * Metoda pobiera jeden argument - obiekt typu Gracz (może to być np. Komputer lub Człowiek), który aktualnie wykonuje ruch.
	 * Metoda zwraca liczbę określającą pole, które może być zwycięskie dla przeciwnika w następnym ruchu.
	 * Jeśli metoda zwróci liczbę od 0 do 8 to oznacza to, że istnieje zwycięski ruch dzięki któremu
	 * przeciwnik może wygrać pojedynek. Jeśli natomiast metoda zwróci liczbę -1 to oznacza to, że
	 * nie istnieje w następnym ruchu możliwość zwycięskiego zakończenia pojedynku przez przeciwnika.
	 */
	public int podajZwycieskiRuchPrzeciwnika(Gracz g){
		String znak = g.getZnak(); //znak gracza
		
		//szuakmy znaku przeciwnika
		if(znak=="X")
			znak = "0";
		else
			znak = "X";
		
		for(int i=0; i<rozwiazania.length; i++){
			if(pola[rozwiazania[i][0]].rysuj()==znak && pola[rozwiazania[i][1]].rysuj()==znak && pola[rozwiazania[i][2]].rysuj()==null)
				return rozwiazania[i][2];
			else if(pola[rozwiazania[i][0]].rysuj()==znak && pola[rozwiazania[i][1]].rysuj()==null && pola[rozwiazania[i][2]].rysuj()==znak)
				return rozwiazania[i][1];
			else if(pola[rozwiazania[i][0]].rysuj()==null && pola[rozwiazania[i][1]].rysuj()==znak && pola[rozwiazania[i][2]].rysuj()==znak)
				return rozwiazania[i][0];
		}
		return -1; //czlowiek nie ma mozliwosci wygrania w nastepnej rundzie
	}
	
	/*
	 * Metoda zwracająca pole, dzięki którem gracz będzie miał możliwość zwycięstwa w następnym ruchu
	 * 
	 * Metoda pobiera jeden argument - obiekt typu Gracz (może to być np. Komputer lub Człowiek), który aktualnie wykonuje ruch.
	 * Metoda zwraca liczbę określającą pole, które może być zwycięskie dla gracza w następnym ruchu.
	 * Jeśli metoda zwróci liczbę od 0 do 8 to oznacza to, że w następnym ruchu gracz będzie miał możliowość wygrania pojedynku (jeśli
	 * tylko zaznaczy to pole i przeciwnik mu nie przeszkodzi). 
	 * Jeśli natomiast metoda zwróci liczbę -1 to oznacza to, że nie ma zaczętych rozwiązań, które gracz mógłby osiągnąć i wygrać.
	 */
	public int podajNajlepszyRuch(Gracz g){
		String znak = g.getZnak();
		for(int i=0; i<rozwiazania.length; i++){
			if(pola[rozwiazania[i][0]].rysuj()==znak && pola[rozwiazania[i][1]].rysuj()==null && pola[rozwiazania[i][2]].rysuj()==null){
				int j = rand.nextInt(2)+1; //1, 2
				return rozwiazania[i][j];
			} else if(pola[rozwiazania[i][0]].rysuj()==null && pola[rozwiazania[i][1]].rysuj()==znak && pola[rozwiazania[i][2]].rysuj()==null){
				int j = rand.nextInt(2); //0, 2
				if(j==1)
					j=2;
				return rozwiazania[i][j];
			} else if(pola[rozwiazania[i][0]].rysuj()==null && pola[rozwiazania[i][1]].rysuj()==null && pola[rozwiazania[i][2]].rysuj()==znak){
				int j = rand.nextInt(2); //0, 1
				return rozwiazania[i][j];
			}
		}
		return -1; //nie ma dobrego ruchu (takiego, który mógłby przynieść zwycięstwo w następnym ruchu)
	}
	
	/*
	 * Metoda zwracająca pole, dzięki którem gracz będzie miał możliwość zwycięstwa w grze w następnych ruchach - osiągnięcia jednego z ośmiu rozwiązań.
	 * 
	 * Metoda pobiera jeden argument - obiekt typu Gracz (może to być np. Komputer lub Człowiek), który aktualnie wykonuje ruch.
	 * Metoda zwraca liczbę określającą pole, które może być zwycięskie dla gracza w następnych ruchach.
	 * Jeśli metoda zwróci liczbę od 0 do 8 to oznacza to, że jest możliwość wygrania pojedynku w następnych ruchach (jeśli
	 * tylko zaznaczy to pole i przeciwnik mu nie przeszkodzi). 
	 * Jeśli natomiast metoda zwróci liczbę -1 to oznacza to, że nie ma już możliwych rozwiązań dla danego gracza i nie może on zwyciężyć już w tym pojedynku.
	 */
	public int podajMozliwyDoWygraniaRuch(Gracz g){
		//String znak = g.getZnak();
		for(int i=0; i<rozwiazania.length; i++){
			if(pola[rozwiazania[i][0]].rysuj()==null && pola[rozwiazania[i][1]].rysuj()==null && pola[rozwiazania[i][2]].rysuj()==null){
				int j = rand.nextInt(3); //0, 1, 2
				//System.out.println("Losowanko" + j);  //pomocnicza zmienna
				return rozwiazania[i][j];
			}
		}
		return -1; //nie ma dobrego ruchu (takiego, który mógłby przynieść zwycięstwo graczowi)
	}
}
