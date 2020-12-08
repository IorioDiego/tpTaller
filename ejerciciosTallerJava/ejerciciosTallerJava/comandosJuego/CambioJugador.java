package comandosJuego;

import cartas.Carta;
import game.Jugador;
import game.Partida;
import servidor.Paquete;
import servidor.Servidor;

public class CambioJugador implements ComandosJuego {

	private ComandosJuego siguiente;

	public void establecerSiguiente(ComandosJuego siguiente) {
		this.siguiente = siguiente;

	}

	public String procesar(Paquete paquete, String msj, Partida partida) {
		String resp = "?";
		if (msj.equals("5")) {
			try {
				Jugador proximoJ = null;
				for (Jugador j : partida.getJugadores()) {
					if (j.getNombre().equals(paquete.getNick()))
						proximoJ = partida.proximoTurnoJugador(j);
				}

				for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
					if (paqueteCliente.getNick().equals(proximoJ.getNombre())) {
						paqueteCliente.getSalida().writeObject("tuTurno");
					
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		} else
			return siguiente.procesar(paquete, msj, partida);
	}

}
