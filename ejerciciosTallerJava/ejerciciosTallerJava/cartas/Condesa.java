package cartas;

import game.Carta;
import game.Jugador;
import game.Partida;

public class Condesa extends Carta{
	
	public Condesa() {
		super(7, "Condesa", "Si un jugador tiene esta carta y el Rey o el Pr�ncipe, "
				+ "esta carta debe ser jugada inmediatamente");
		
	}
	
	public int getCantidadCartasPersonaje() {
		return 1;
	}

	@Override
	public int getFuerzaCarta() {
		return 7;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida) {
		//TODO
		
	}
}