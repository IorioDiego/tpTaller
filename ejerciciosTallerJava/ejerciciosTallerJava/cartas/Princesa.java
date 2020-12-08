package cartas;



import javax.swing.JDialog;

import game.Jugador;
import game.Partida;
import servidor.Paquete;

public class Princesa extends Carta {

	public Princesa() {
		super(8, "Princesa", "descripciones/princesaDescrip.png");

	}

	public int getCantidadCartasPersonaje() {
		return 1;
	}

	@Override
	public int getFuerzaCarta() {
		return 8;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida,Paquete paquete) {
		jugador.seJugoPrincesa();
		
	}

}
