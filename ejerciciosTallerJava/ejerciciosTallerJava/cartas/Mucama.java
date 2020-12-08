package cartas;

import javax.swing.JDialog;

import game.Jugador;
import game.Partida;
import servidor.Paquete;


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
	public void activarEfecto(Jugador jugador, Partida partida,Paquete paquete) {
		jugador.seJugoMucama();
	}
}
