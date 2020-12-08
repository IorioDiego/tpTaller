package cartas;

import java.io.IOException;

import javax.swing.JDialog;

import InterfaceGrafica.Tablero;
import game.Jugador;
import game.Partida;
import servidor.Paquete;
import servidor.Servidor;

public class Sacerdote extends Carta {

	private Partida partida;

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
			
			for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
				if (paqueteCliente.getCliente().isConnected()&& paquete.getNick().equals(oponente.getNombre())) {
					paqueteCliente.getSalida().writeObject(oponente.getMano(0));
				}
			}

			oponente.mostrarMano();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// lista.setVisible(true);
//		partida.getJugadores().get(Tablero.getJugadorElegido()).mostrarMano();
	}
}
