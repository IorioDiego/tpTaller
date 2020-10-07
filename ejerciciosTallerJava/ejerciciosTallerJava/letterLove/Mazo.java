package letterLove;

import java.util.ArrayList;
import java.util.Collections;

import game.Jugador;

public class Mazo {

	private int cantCartas = 16;
	private ArrayList<Carta> mazo = new ArrayList<Carta>();

	public Mazo() {
		
		mazo.add(new Guardia());
		mazo.add(new Guardia());
		mazo.add(new Guardia());
		mazo.add(new Guardia());
		mazo.add(new Guardia());

		mazo.add(new Sacerdote());
		mazo.add(new Sacerdote());

		mazo.add(new Baron());
		mazo.add(new Baron());

		mazo.add(new Mucama());
		mazo.add(new Mucama());

		mazo.add(new Principe());
		mazo.add(new Principe());

		mazo.add(new Rey());

		mazo.add(new Condesa());

		mazo.add(new Princesa());
	}

	public Carta eliminarPrimeraCarta() {
		return mazo.remove(0);	
	}

	public boolean mazoVacio() {
		return cantCartas == 0;
	}

	public void mezclar() {
		Collections.shuffle(mazo);
		Collections.shuffle(mazo);
	}

	public void darCarta(Jugador jugador) 
	{
		
		cantCartas--;
		jugador.tomarCarta(mazo.remove(0));
		
	}
	

//	public void mostrarMazo() {
//		
//		for (Carta x : mazo) {
//			x.mostrarCarta();
//		}	
//		
//	}

}
