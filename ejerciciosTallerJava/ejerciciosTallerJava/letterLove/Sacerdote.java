package letterLove;

import game.Jugador;
import game.Partida;

public class Sacerdote extends Carta{
	
	public Sacerdote() {
		super(2, "Sacerdote", "Elige otro jugador para ver la cartas en su mano");
		
	}
	
	public int getCantidadCartasPersonaje() {
		return 2;
	}

	@Override
	public int getFuerzaCarta() {
		return 2;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida) {
		// TODO Auto-generated method stub
		
	}
}
