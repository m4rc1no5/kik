package mz_kolko_i_krzyzyk;

/**
 * Klasa dziedzicząca po klasie Gracz
 * Komputer
 */
public class Komputer extends Gracz {
	/*
	 * Metoda odpowiedzialna za wykonanie ruchu przez komputer
	 */
	public void wykonajRuch(Plansza _p) {
		_p.ustawGracza(this);
		
		Walidacja w = new Walidacja(_p.getPola());
		
		//sprawdzamy czy możliwe jest zwycięstwo w najbliższym ruchu
		int zwycieski_ruch = w.podajZwycieskiRuch(this);
		int x = 0;
		if(zwycieski_ruch>=0){	//jesli mamy zwycieski ruch to jest on rowny badz wiekszy od 0
			x = zwycieski_ruch;
		} else { //jesli nie mamy zwycieskiego pola to lecimy dalej
			//sprawdzamy czy czlowiek moze wygrac - jesli tak to mu przeszkadzamy
			int zwycieski_ruch_czlowieka = w.podajZwycieskiRuchPrzeciwnika(this);
			if(zwycieski_ruch_czlowieka>=0){
				x = zwycieski_ruch_czlowieka;
			} else {
				//sprawdzamy czy istnieje mozliwosc zrealizowania jednego
				//z osmiu rozwiazan - jesli tak to staramy sie to zrealizowac
				//(jedno z pol jest zakreslone a reszta pozostałe dwa są wolne)
				int najlepszy_ruch = w.podajNajlepszyRuch(this);
				if(najlepszy_ruch>=0){
					x = najlepszy_ruch;
				} else {
					//sprawdzamy czy jest jakieś 'wolne' rozwiązanie
					//możliwe do zrealizowania i nie oznaczone znakami przeciwnika
					int mozliwy_do_wygrania_ruch = w.podajMozliwyDoWygraniaRuch(this);
					if(mozliwy_do_wygrania_ruch>=0){
						x = mozliwy_do_wygrania_ruch;
					} else {
						//wyciągamy pierwsze wolne pole i zaznaczamy
						Znak[] z = _p.getPola();
						for(int i=0; i<z.length; i++){
							if(z[i].rysuj()==null){
								x = i;
								break;
							}
						}
					}
				}
			}
		}
		//System.out.println("Skreslone przez komp pole: " + x); //pomocniczy komunikat
		_p.wykonajZdarzenie(x);
	}
}
