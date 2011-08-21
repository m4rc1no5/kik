package mz_kolko_i_krzyzyk;
/**
 * Abstrakcyjna klasa Gracz
 * 
 * Zdecydowałem się na abstrakcyjną klasę a nie interfejs ponieważ jedynie metoda wykonajRuch() jest różna dla klas potomnych.
 *
 */
public abstract class Gracz {
	private String nazwa_gracza;	//nazwa gracza
	private String znak;			//znak - kółko 'O' lub krzyżyk 'X'
	
	/*
	 * Metoda za pomocą której ustawiamy nazwę gracza
	 */
	public void setNazwaGracza(String _nazwa_gracza){
		nazwa_gracza = _nazwa_gracza;
	}
	
	/*
	 * Metoda za pomocą której otrzymujemy informację jaka jest nazwa gracza
	 */
	public String getNazwaGracza(){
		return nazwa_gracza;
	}
	
	/*
	 * Metoda za pomocą której ustawiamy znak gracza
	 */
	public void setZnak(String _znak) throws ZlyZnakException{
		//jeśli mamy inne znaki niż O lub X to wyrzucamy wyjątek ZlyZnakException
		if(_znak == "O" || _znak == "X"){} else {
			throw new ZlyZnakException("Zły znak (" + _znak + ")");
		}
		
		znak = _znak;
	}
	
	/*
	 * Metoda za pomocą której otrzymujemy informację jaki jest znak gracza (O lub X)
	 */
	public String getZnak(){
		return znak;
	}
	
	/*
	 * Abstrakcyjna metoda, za pomocą której gracz wykonuje ruch
	 */
	public abstract void wykonajRuch(Plansza _p);
}
