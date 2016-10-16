package mz_kolko_i_krzyzyk;

/**
 * Klasa dziedzicząca po klasie Gracz
 * Człowiek
 */
public class Czlowiek extends Gracz {
	public void wykonajRuch(Plansza _p) {
		_p.ustawGracza(this);				//ustawiamy na planszy odpowiedniego gracza
	}
}
