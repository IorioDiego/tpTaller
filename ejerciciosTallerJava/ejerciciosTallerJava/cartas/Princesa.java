package cartas;



import game.Carta;
import game.Jugador;
import game.Partida;

public class Princesa extends Carta {

	public Princesa() {
		super(8, "Princesa", "Si un jugador juega o descarta esta carta por cualquier motivo, "
				+ "ese jugador es eliminado de la ronda");

	}

	public int getCantidadCartasPersonaje() {
		return 1;
	}

	@Override
	public int getFuerzaCarta() {
		return 8;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida) {
		jugador.seJugoPrincesa();
	}

}
