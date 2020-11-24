package cartas;

import javax.swing.JDialog;

import InterfaceGrafica.Tablero;
import game.Jugador;
import game.Partida;

public class Sacerdote extends Carta{
	
	public Sacerdote() {
		super(2, "Sacerdote", "descripciones/sacerdoteDescrip.png");
		
	}
	
	public int getCantidadCartasPersonaje() {
		return 2;
	}

	@Override
	public int getFuerzaCarta() {
		return 2;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida, JDialog lista,JDialog listaCartas) {
		lista.setVisible(true);
		partida.getJugadores().get(Tablero.getJugadorElegido()).mostrarMano();
	}
}
