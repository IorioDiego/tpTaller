package cartas;

import java.io.IOException;

import game.Jugador;
import game.Partida;
import servidor.Paquete;
import servidor.Servidor;

public class Baron extends Carta {

	private static final long serialVersionUID = 1L;

	public Baron() {
		super(3, "Baron", "descripciones/baronDescrip.png");
	}

	public int getCantidadCartasPersonaje() {
		return 2;
	}

	@Override
	public int getFuerzaCarta() {
		return 3;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida, Paquete paquete) {

		try {
			int jElegido = (int) paquete.getEntrada().readObject();

			Jugador oponente = partida.getJugadores().get(jElegido);
			int resultado = jugador.compararMano(oponente);

			for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
				paqueteCliente.getSalida().writeObject(oponente.getNombre());
				paqueteCliente.getSalida().writeObject(oponente.getMano(0));
				paqueteCliente.getSalida().writeObject(jugador.getMano(0));
			}

			if (resultado > 0) {
				for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
					paqueteCliente.getSalida().writeObject("PerdioOponente");
				}
				oponente.descartar(oponente.getMano(0));
				oponente.seJugoBaron();
			} else if (resultado < 0) {

				if (jugador.getMano(0).equals(new Baron())) {

					for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
						paqueteCliente.getSalida().writeObject("PerdioJugador");

					}
					jugador.descartar(jugador.getMano(1));
				} else {

					for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
						paqueteCliente.getSalida().writeObject("PerdioJugador");
					}

					jugador.descartar(jugador.getMano(0));
				}
				jugador.seJugoBaron();
				partida.setEliminoActBaron(true);
			} else {
				for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
					paqueteCliente.getSalida().writeObject("Empate");

				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
