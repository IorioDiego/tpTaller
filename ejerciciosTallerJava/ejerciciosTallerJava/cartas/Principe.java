package cartas;

import javax.swing.JDialog;

import InterfaceGrafica.Tablero;
import game.Jugador;
import game.Partida;

public class Principe extends Carta {

	public Principe() {
		super(5, "Principe",
				" elige otro jugador (incluso a sí mismo) para descartar su mano y robar una \n "
						+ "carta nueva. Si la Princesa es descartada de esta manera, \n "
						+ "el jugador que la descartó es eliminado de la ronda");

	}

	public int getCantidadCartasPersonaje() {
		return 2;
	}

	@Override
	public int getFuerzaCarta() {
		return 5;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida, JDialog lista, JDialog listaCartas) {
		lista.setVisible(true);
		
		Jugador oponente = partida.elegirJugador(Tablero.getJugadorElegido());


		Carta jugada = oponente.descartar(oponente.sacarCartaDeMano(0));
		
		if (jugada.equals(new Princesa())) {
			oponente.seJugoPrincesa();
			oponente.descartar(jugada);
		}else
			partida.getMazo().darCarta(oponente);
		
		if(oponente.getManoCompleta().isEmpty() && !oponente.isBlockedOrDelete()) {
			oponente.tomarCarta(partida.getCartaEliminda());
		}
		
		

	}

}
