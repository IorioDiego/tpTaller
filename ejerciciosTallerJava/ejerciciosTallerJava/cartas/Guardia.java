package cartas;

import javax.swing.JDialog;

import InterfaceGrafica.Tablero;
import game.Jugador;
import game.Partida;

public class Guardia extends Carta {

	public Guardia() {
		super(1, "Guardia", "Elige otro jugador oponente y nombra un tipo de carta (excepto \"Guardia\").\n "
				+ "Si el oponente tiene en su mano una carta de ese tipo, \n " + "el oponente es eliminado de la ronda");

	}

	public int getCantidadCartasPersonaje() {
		return 5;
	}

	@Override
	public int getFuerzaCarta() {
		return 1;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida, JDialog lista, JDialog listaCartas) {
		lista.setVisible(true);

		listaCartas.setVisible(true);
		Jugador oponente = partida.elegirJugador(Tablero.getJugadorElegido());
		if (oponente.tengoLaCarta(partida.seleccionarCarta(Tablero.getCartaElegida()))) {
			oponente.descartar(oponente.getMano(0));
			oponente.seJugoGuardia();
			partida.setHuboEliminacion(true);
		}

	}
}
