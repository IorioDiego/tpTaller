package ejerciciosTallerJava;

import java.util.ArrayList;

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
		for (Jugador jugador : jugadores) {
			jugador.tomarCartaDelMazo(mazo);
		}
		Carta primCarta=mazo.eliminarPrimeraCarta();
		
		///POR CADA RONDA TIENE QUE PASAR ESTO--->TAL VEZ PUEDE IR EN TABLERO
	}
	
	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}
}
