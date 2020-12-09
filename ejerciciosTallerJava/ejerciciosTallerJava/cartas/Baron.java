package cartas;

import java.io.IOException;

import javax.swing.JDialog;

import InterfaceGrafica.Tablero;
import game.Jugador;
import game.Partida;
import servidor.Paquete;
import servidor.Servidor;

public class Baron extends Carta {

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

			Paquete paquetOp = null;
			Jugador oponente = partida.getJugadores().get(jElegido);
			int resultado = jugador.compararMano(oponente);

			///// ESTA ES PARA MOSTRAR

			for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
				paqueteCliente.getSalida().writeObject(oponente.getNombre());
				paqueteCliente.getSalida().writeObject(oponente.getMano(0));

				if (jugador.getMano(0).equals(new Baron())) {

					paqueteCliente.getSalida().writeObject(jugador.getMano(1));
				} else {

					paqueteCliente.getSalida().writeObject(jugador.getMano(0));
				}

			}

//			paquete.getSalida().writeObject(oponente.getMano(0));

			////////////////////////////////////////////

			if (resultado > 0) {
				for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
					paqueteCliente.getSalida().writeObject("PerdioOponente");
					paqueteCliente.getSalida().writeObject(oponente.getMano(0));

				}

				oponente.descartar(oponente.getMano(0));
				oponente.seJugoBaron();
				partida.setHuboEliminacion(true);/////////////////// enviarMensaje
//					partida.setEliminoOpBaron(true);
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
				partida.setHuboEliminacion(true);///////////////// enviarMensaje
				partida.setEliminoActBaron(true);//////////////// enviarMensaje
			} else {
				for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
					paqueteCliente.getSalida().writeObject("Empate");

				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		lista.setVisible(true);
//		Jugador oponente = partida.getJugadores().get(Tablero.getJugadorElegido());
//		int resultado = jugador.compararMano( oponente );
//		if( resultado >0  ) {
//				oponente.descartar(oponente.getMano(0));
//				oponente.seJugoBaron();
//				partida.setHuboEliminacion(true);
////				partida.setEliminoOpBaron(true);
//				}
//		else if( resultado < 0) {
//			
//			if(jugador.getMano(0).equals(new Baron()))
//				jugador.descartar(oponente.getMano(1));
//			else
//				jugador.descartar(oponente.getMano(0));
//			jugador.seJugoBaron();
//			partida.setHuboEliminacion(true);
//			partida.setEliminoActBaron(true);
//		}

	}

}
