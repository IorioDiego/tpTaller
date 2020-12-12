package cartas;

import game.Jugador;
import game.Partida;
import servidor.Paquete;

public class Condesa extends Carta{
	
	private static final long serialVersionUID = 1L;

	public Condesa() {
		super(7, "Condesa", "descripciones/condesaDescrip.png");
		
	}
	
	public int getCantidadCartasPersonaje() {
		return 1;
	}

	@Override
	public int getFuerzaCarta() {
		return 7;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida,Paquete paquete) {
		//TODO
		
	}


}
