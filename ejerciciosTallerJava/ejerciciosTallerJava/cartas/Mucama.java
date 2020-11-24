package cartas;

import javax.swing.JDialog;

import game.Jugador;
import game.Partida;


public class Mucama extends Carta{
	
	public Mucama() {
		super(4, "Mucama", "descripciones/mucamaDescrip.png");
		
	}
	
	public int getCantidadCartasPersonaje() {
		return 2;
	}

	@Override
	public int getFuerzaCarta() {
		return 4;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida,JDialog lista,JDialog listaCartas) {
		jugador.seJugoMucama();
	}
}
