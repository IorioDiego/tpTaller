package cartas;

import javax.swing.JDialog;

import InterfaceGrafica.Tablero;
import game.Jugador;
import game.Partida;

public class Baron extends Carta {

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
	public void activarEfecto(Jugador jugador, Partida partida,JDialog lista,JDialog listaCartas) {
		lista.setVisible(true);
		//Jugador oponente = jugador.seleccionarJugador(partida);
		Jugador oponente = partida.getJugadores().get(Tablero.getJugadorElegido());
		int resultado = jugador.compararMano( oponente );
		if( resultado >0  ) {
				oponente.descartar(oponente.getMano(0));
				oponente.seJugoBaron();
				partida.setHuboEliminacion(true);
				}
		else if( resultado < 0) {
			
			if(jugador.getMano(0).equals(new Baron()))
				jugador.descartar(oponente.getMano(1));
			else
				jugador.descartar(oponente.getMano(0));
			jugador.seJugoBaron();
			partida.setHuboEliminacion(true);
		}
	
	}
	
}
