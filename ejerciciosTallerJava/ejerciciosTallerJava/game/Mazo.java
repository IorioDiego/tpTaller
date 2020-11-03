package game;

import java.util.ArrayList;
import java.util.Collections;

import cartas.Baron;
import cartas.Condesa;
import cartas.Guardia;
import cartas.Mucama;
import cartas.Princesa;
import cartas.Principe;
import cartas.Rey;
import cartas.Sacerdote;

public class Mazo extends Observable {

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


	public void mezclar() {
		Collections.shuffle(mazo);
		Collections.shuffle(mazo);
	}

	public void darCarta(Jugador jugador) 
	{	
			if(getCantCartas() != 0 )
			{
				jugador.tomarCarta(mazo.remove(0));
			}else
				this.notificarFinMazo();
	}

	public int getCantCartas() {
		return mazo.size();
	}

}
