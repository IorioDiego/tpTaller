package cartas;

import java.io.IOException;
import game.Jugador;
import game.Partida;
import servidor.Paquete;
import servidor.Servidor;

public class Princesa extends Carta {

	private static final long serialVersionUID = 1L;

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
	public void activarEfecto(Jugador jugador, Partida partida, Paquete paquete) {
		try {
			for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
				paqueteCliente.getSalida().writeObject(jugador.getNombre());
				paqueteCliente.getSalida().writeObject(jugador.getMano(0));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		jugador.seJugoPrincesa();
	}

}
