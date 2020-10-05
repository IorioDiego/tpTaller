package letterLove;

import game.Jugador;
import game.Partida;

public class Baron extends Carta{
	
	public Baron() {
		super(3, "Baron", "Elige otro jugador y se revelan las cartas de forma privada. "
				+ "El jugador que posee la carta con menos fuerza es eliminado de la ronda");
	}
	
	public int getCantidadCartasPersonaje() {
		return 2;
	}

	@Override
	public int getFuerzaCarta() {
		return 3;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida) {
		// TODO Auto-generated method stub
		
	}
}
