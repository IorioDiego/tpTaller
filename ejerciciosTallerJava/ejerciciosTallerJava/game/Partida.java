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

public class Partida extends Observer {
	private int afecto;
	private int cantJugadores;
	private ArrayList<Jugador> jugadores;
	private ArrayList<Carta> listaCartas = new ArrayList<Carta>();
	private Mazo mazo;

	public Partida() {

	}

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

	@Override
	public void notificarse() {
		int fuerza = 0, ganador,empate=0;
		int [] jugEmpatados = new int[jugadores.size()];
		for (int i = 0; i < jugadores.size(); i++) {
			if (!jugadores.get(0).equals(new Eliminado())) {
				if (jugadores.get(0).getMano().getFuerza() > fuerza) {
					fuerza = jugadores.get(0).getMano().getFuerza();
					empate=0;
				}if(jugadores.get(0).getMano().getFuerza() == fuerza)
				{	
					jugEmpatados[i]=i;
					empate=1;
				}
				
			}
		}	
	}
	

	public Mazo getMazo() {
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

	public Carta seleccionarCarta(int index) {
		// TODO evento de seleecion
		// int i = 0;
		return listaCartas.get(index);
	}

}
