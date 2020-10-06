package letterLove;

import game.Jugador;
import game.Partida;

public class Principe extends Carta{
	
	public Principe() {
		super(5, "Principe", " elige otro jugador (incluso a sí mismo) para descartar su mano y robar una "
				+ "carta nueva. Si la Princesa es descartada de esta manera, el jugador que la descartó "
				+ "es eliminado de la ronda");
		
	}
	
	public int getCantidadCartasPersonaje() {
		return 2;
	}

	@Override
	public int getFuerzaCarta() {
		return 5;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida) {
		Jugador oponente = jugador.seleccionarJugador(partida);
		oponente.descartar(oponente.sacarCartaDeMano(0)); 
		partida.entregarCarta(jugador);
	}

}
