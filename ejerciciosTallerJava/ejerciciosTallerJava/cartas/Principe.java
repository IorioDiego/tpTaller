package cartas;

import java.io.IOException;

import javax.swing.JDialog;

import InterfaceGrafica.Tablero;
import game.Jugador;
import game.Partida;
import servidor.Paquete;
import servidor.Servidor;

public class Principe extends Carta {

	public Principe() {
		super(5, "Principe", "descripciones/principeDescrip.png");

	}

	public int getCantidadCartasPersonaje() {
		return 2;
	}

	@Override
	public int getFuerzaCarta() {
		return 5;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida, Paquete paquete) {

		try {
			int jElegido = (int) paquete.getEntrada().readObject();

			Jugador oponente = partida.elegirJugador(jElegido);
			Paquete paqueteOp = null;

			Carta jugada = oponente.descartar(oponente.sacarCartaDeMano(0));

			for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
				if (paqueteCliente.getCliente().isConnected()) {
					if(oponente.getNombre().equals(paqueteCliente.getNick())) {
						paqueteOp=paqueteCliente;	
					}
					
				
					paqueteCliente.getSalida().writeObject(oponente.getNombre());
					paqueteCliente.getSalida().writeObject(jugada);
				}
			}
			if (jugada.equals(new Princesa())) {
				oponente.seJugoPrincesa();
			} else {
				if (partida.getMazo().getCantCartas() != 0)
					paqueteOp.getSalida().writeObject(partida.getMazo().darCarta(oponente));
			}

			if (oponente.getManoCompleta().isEmpty() && !oponente.isBlockedOrDelete()) {
				if (partida.getMazo().getCantCartas() == 0) {
					paqueteOp.getSalida().writeObject(oponente.tomarCarta(partida.getCartaEliminda()));
				}
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// lista.setVisible(true);
//
//		Jugador oponente = partida.elegirJugador(Tablero.getJugadorElegido());
//
//		Carta jugada = oponente.descartar(oponente.sacarCartaDeMano(0));
//
//		if (jugada.equals(new Princesa())) {
//			oponente.seJugoPrincesa();
//			oponente.descartar(jugada);
//		} else {
//			if(partida.getMazo().getCantCartas() != 0)
//				partida.getMazo().darCarta(oponente);
//		}
//
//		if (oponente.getManoCompleta().isEmpty() && !oponente.isBlockedOrDelete()) {
//			if (partida.getMazo().getCantCartas() == 0) {
//				jugador.tomarCarta(partida.getCartaEliminda());
//			} else {
//				oponente.tomarCarta(partida.getCartaEliminda());
//			}
//		}

	}

}
