package comandosJuego;

import cartas.Carta;
import game.Jugador;
import game.Partida;
import servidor.Paquete;
import servidor.Servidor;

public class TomarCartas implements ComandosJuego {
	private ComandosJuego siguiente;

	@Override
	public void establecerSiguiente(ComandosJuego siguiente) {
		this.siguiente = siguiente;

	}

	@Override
	public String procesar(Paquete paquete, String msj, Partida partida) {
		String resp = "?";
		if (msj.equals("3")) {
			try {
				partida.setPaqueteActivo(paquete);
				int cantC = partida.getMazo().getCantCartas();
				Jugador jugadorActivo = null;
				for (Jugador j : partida.getJugadores()) {
					if (j.getNombre().equals(paquete.getNick())) {
						jugadorActivo = j;
					}
				}
				Carta cartaTomada = partida.getMazo().darCarta(jugadorActivo);
				if (cantC == 0) {
					Servidor.darConfigSalas(paquete.getSala()).setReinicioRonda("NoReinicio");
				} else {
					paquete.getSalida().writeObject(cartaTomada);
					paquete.getSalida().writeObject("noReinicio");
					paquete.getSalida().writeObject("noFinPartida");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		} else
			return siguiente.procesar(paquete, msj, partida);
	}

}
