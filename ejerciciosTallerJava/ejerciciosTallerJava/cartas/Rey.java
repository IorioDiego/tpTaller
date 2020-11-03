package cartas;

import game.Carta;
import game.Jugador;
import game.Partida;

public class Rey extends Carta{
	
	public Rey() {
		super(6, "Rey", "Elige otro jugador e intercambian sus manos");
		
	}
	
	public int getCantidadCartasPersonaje() {
		return 1;
	}

	@Override
	public int getFuerzaCarta() {
		return 6;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida) {
		jugador.intercabiarMano(jugador.seleccionarJugador(partida));	
		
	}
}
