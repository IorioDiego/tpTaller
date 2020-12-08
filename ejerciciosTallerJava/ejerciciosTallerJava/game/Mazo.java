package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import cartas.Baron;
import cartas.Carta;
import cartas.Condesa;
import cartas.Guardia;
import cartas.Mucama;
import cartas.Princesa;
import cartas.Principe;
import cartas.Rey;
import cartas.Sacerdote;

public class Mazo extends Observable implements Serializable {

	private ArrayList<Carta> mazo = new ArrayList<Carta>();

	public Mazo() {
		
		

		mazo.add(new Guardia());
		mazo.add(new Principe());
		mazo.add(new Guardia());
		mazo.add(new Principe());
		mazo.add(new Guardia());
		mazo.add(new Guardia());
		mazo.add(new Guardia());

		mazo.add(new Sacerdote());
		mazo.add(new Sacerdote());

		mazo.add(new Baron());
		mazo.add(new Baron());

		mazo.add(new Mucama());
		mazo.add(new Mucama());

		

		mazo.add(new Rey());

		mazo.add(new Condesa());

		 mazo.add(new Princesa());
		
//		mazo.add(new Guardia());
//		mazo.add(new Guardia());
//		mazo.add(new Guardia());
//		mazo.add(new Guardia());
//		mazo.add(new Guardia());
//
//		mazo.add(new Sacerdote());
//		mazo.add(new Sacerdote());
//
//		mazo.add(new Baron());
//		mazo.add(new Baron());
//
//		mazo.add(new Mucama());
//		mazo.add(new Mucama());
//
//		mazo.add(new Principe());
//		mazo.add(new Principe());
//
//		mazo.add(new Rey());
//
//		mazo.add(new Condesa());
//
//		 mazo.add(new Princesa());
	}

	public Carta eliminarPrimeraCarta() {
		 return mazo.remove(0);
	}

	public void mezclar() {
//		Collections.shuffle(mazo);
	}

	public Carta darCarta(Jugador jugador) {
		Carta robada = null;
		if (getCantCartas() != 0) {
			robada = jugador.tomarCarta(mazo.remove(0));
		} else
			this.notificarFinMazo();
		return robada;
	}

	public int getCantCartas() {
		return mazo.size();
	}

}
