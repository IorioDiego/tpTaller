package cartas;

import java.io.IOException;

import javax.swing.JDialog;

import InterfaceGrafica.Tablero;
import game.Jugador;
import game.Partida;
import servidor.Paquete;
import servidor.Servidor;

public class Guardia extends Carta {

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
				partida.setHuboEliminacion(true);//////////// enviar mensaje
			} else
				for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
					paqueteCliente.getSalida().writeObject("NoAcierto");
				}

		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		lista.setVisible(true);
//
//		listaCartas.setVisible(true);
//		
//		Jugador oponente = partida.elegirJugador(Tablero.getJugadorElegido());
//		if (oponente.tengoLaCarta(partida.seleccionarCarta(Tablero.getCartaElegida()))) {
//			oponente.descartar(oponente.getMano(0));
//			oponente.seJugoGuardia();
//			partida.setHuboEliminacion(true);
//		}

	}
}
