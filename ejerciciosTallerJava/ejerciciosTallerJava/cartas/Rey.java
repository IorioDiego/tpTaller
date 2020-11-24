package cartas;

import javax.swing.JDialog;

import InterfaceGrafica.Tablero;
import game.Jugador;
import game.Partida;

public class Rey extends Carta{
	
	public Rey() {
		super(6, "Rey", "descripciones/reyDescrip.png");
		
	}
	
	public int getCantidadCartasPersonaje() {
		return 1;
	}

	@Override
	public int getFuerzaCarta() {
		return 6;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida, JDialog lista,JDialog listaCartas) {
		lista.setVisible(true);
		jugador.intercabiarMano(partida.getJugadores().get(Tablero.getJugadorElegido()));
	}
}
