package cartas;

import java.io.IOException;
import game.Jugador;
import game.Partida;
import servidor.Paquete;
import servidor.Servidor;

public class Rey extends Carta {

	private static final long serialVersionUID = 1L;

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
	public void activarEfecto(Jugador jugador, Partida partida, Paquete paquete) {
		int jElegido = 0;
		try {
			jElegido = (int) paquete.getEntrada().readObject();
			Jugador oponente = partida.getJugadores().get(jElegido);

			Carta miMano = jugador.getMano(0);
			Carta opMano = oponente.getMano(0);

			
			for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
				paqueteCliente.getSalida().writeObject(oponente.getNombre());

				if (paqueteCliente.getNick().equals(oponente.getNombre())) {
					paqueteCliente.getSalida().writeObject(miMano);
				}
			}
			paquete.getSalida().writeObject(opMano);

			jugador.intercabiarMano(partida.getJugadores().get(jElegido));
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		lista.setVisible(true);
//		jugador.intercabiarMano(partida.getJugadores().get(Tablero.getJugadorElegido()));
	}
}
