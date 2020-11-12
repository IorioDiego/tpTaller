package cartas;

import javax.swing.JDialog;

import InterfaceGrafica.Tablero;
import game.Jugador;
import game.Partida;

public class Principe extends Carta {

	public Principe() {
		super(5, "Principe",
				" elige otro jugador (incluso a sí mismo) para descartar su mano y robar una "
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
	public void activarEfecto(Jugador jugador, Partida partida, JDialog lista, JDialog listaCartas) {
		lista.setVisible(true);
		// Jugador oponente = jugador.seleccionarJugador(partida);
		Jugador oponente = partida.elegirJugador(Tablero.getJugadorElegido());
//		if (oponente.getMano(0).equals(new Princesa())) {
//			oponente.seJugoPrincesa();
//		} else
//		{
//			oponente.descartar(oponente.sacarCartaDeMano(0));
//			partida.getMazo().darCarta(oponente);
//		}

		Carta jugada = oponente.descartar(oponente.sacarCartaDeMano(0));
		
		if (jugada.equals(new Princesa())) {
			oponente.seJugoPrincesa();
			oponente.descartar(jugada);
		}else
			partida.getMazo().darCarta(oponente);
		

	}

}
