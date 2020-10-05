package game;

import java.util.ArrayList;

import letterLove.Carta;
import letterLove.Mazo;

public class Partida {
	private int afecto;
	private int cantJugadores;
	private ArrayList<Jugador> jugadores;
	
	public Partida(int afecto,int cantJugadores,ArrayList<Jugador> jugadores)
	{
		this.afecto=afecto;
		this.cantJugadores=cantJugadores;
		this.jugadores=jugadores;
	}
	
	public void iniciarPartida()
	{
		Mazo mazo = new Mazo();
		mazo.mezclar();
		Carta primCarta=mazo.eliminarPrimeraCarta();
		
		for (Jugador jugador : jugadores) {
			mazo.darCarta(jugador);
		}
		
	
		
		///POR CADA RONDA TIENE QUE PASAR ESTO--->TAL VEZ PUEDE IR EN TABLERO
	}
	
	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}
	
	
	
}
