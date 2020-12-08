package comandosJuego;

import cartas.Carta;
import game.Jugador;
import game.Mazo;
import game.Partida;
import servidor.Paquete;
import servidor.Servidor;

public class TomarCartas implements ComandosJuego {
	private ComandosJuego siguiente;

	public void establecerSiguiente(ComandosJuego siguiente) {
		this.siguiente = siguiente;

	}

	public String procesar(Paquete paquete, String msj, Partida partida) {
		String resp = "?";
		if (msj.equals("3")) {
			try {

				Jugador jugadorActivo = null;
				for (Jugador j : partida.getJugadores()) {
					if (j.getNombre().equals(paquete.getNick())) {
						jugadorActivo = j;
					}
				}
				Carta cartaTomada = partida.getMazo().darCarta(jugadorActivo);
				paquete.getSalida().writeObject(cartaTomada);
				paquete.getSalida().writeObject(paquete.getNick());
				paquete.getSalida().writeObject(jugadorActivo.getUltimaDescartada());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		} else
			return siguiente.procesar(paquete, msj, partida);
	}

}
