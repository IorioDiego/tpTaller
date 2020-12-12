package cartas;

import java.io.IOException;
import game.Jugador;
import game.Partida;
import servidor.Paquete;
import servidor.Servidor;

public class Guardia extends Carta {

	private static final long serialVersionUID = 1L;

	public Guardia() {
		super(1, "Guardia", "descripciones/guardiaDescrip.png");

	}

	public int getCantidadCartasPersonaje() {
		return 5;
	}

	@Override
	public int getFuerzaCarta() {
		return 1;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida, Paquete paquete) {
		try {
			int jElegido = (int) paquete.getEntrada().readObject();
			int cElegido = (int) paquete.getEntrada().readObject();

			Jugador oponente = partida.elegirJugador(jElegido);
			for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
				paqueteCliente.getSalida().writeObject(partida.seleccionarCarta(cElegido));
				paqueteCliente.getSalida().writeObject(oponente.getNombre());
			}
			
			if (oponente.tengoLaCarta(partida.seleccionarCarta(cElegido))) {
				for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
					paqueteCliente.getSalida().writeObject("Acierto");
					paqueteCliente.getSalida().writeObject(oponente.getMano(0));
				}
				oponente.descartar(oponente.getMano(0));
				oponente.seJugoGuardia();
			} else
				for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
					paqueteCliente.getSalida().writeObject("NoAcierto");
				}

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
}
