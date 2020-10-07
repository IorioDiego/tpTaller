package game;

import java.util.ArrayList;

import letterLove.Baron;
import letterLove.Condesa;
import letterLove.Mucama;
import letterLove.Princesa;
import letterLove.Rey;
import letterLove.Principe;
import letterLove.Sacerdote;
import letterLove.ultimaCartaException;
import letterLove.Carta;
import letterLove.Mazo;

public class Partida {
	private int afecto;
	private int cantJugadores;
	private ArrayList<Jugador> jugadores;
	private ArrayList<Carta> listaCartas = new  ArrayList<Carta>(); 
	private Mazo mazo;
	
	public Partida(int afecto, int cantJugadores, ArrayList<Jugador> jugadores) {
		this.afecto = afecto;
		this.cantJugadores = cantJugadores;
		this.jugadores = jugadores;

		///////////////////////
		listaCartas.add(new Sacerdote());
		listaCartas.add(new Baron());
		listaCartas.add(new Mucama());
		listaCartas.add(new Principe());
		listaCartas.add(new Rey());
		listaCartas.add(new Condesa());
		listaCartas.add(new Princesa());

	}

	public  Mazo getMazo() {
		return mazo;
	}

	public void setMazo(Mazo mazo) {
		this.mazo = mazo;
	}

	public void iniciarPartida() {
		Mazo mazo = new Mazo();
		mazo.mezclar();
		mazo.eliminarPrimeraCarta();

		for (Jugador jugador : jugadores) {
			mazo.darCarta(jugador);
		}
		/// POR CADA RONDA TIENE QUE PASAR ESTO--->TAL VEZ PUEDE IR EN TABLERO
	}
	
	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}

	public Jugador elegirJugador(int i) {
		return jugadores.get(i);
	}
	
	public void mostrarLista() {
		int i=0;
		for (Carta carta : listaCartas) {
			System.out.println("Opcion: " + i + "-->" + carta.getNombre());
			i++;
		}
	}
	
	public Carta seleccionarCarta(int index) {
	//TODO evento de seleecion
		//int i = 0;
		return listaCartas.get(index);
	}
		
	

}
