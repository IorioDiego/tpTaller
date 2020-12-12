package cartas;

import java.io.IOException;
import game.Jugador;
import game.Partida;
import servidor.Paquete;


public class Sacerdote extends Carta {

	private static final long serialVersionUID = 1L;

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
	public void activarEfecto(Jugador jugador, Partida partida, Paquete paquete) {
		int jElegido;
		try {
			jElegido = (int) paquete.getEntrada().readObject();
			Jugador oponente = partida.getJugadores().get(jElegido);
			
			paquete.getSalida().writeObject(oponente.getNombre());
			paquete.getSalida().writeObject(oponente.getMano(0));
						
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
}
