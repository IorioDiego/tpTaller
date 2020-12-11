package comandosJuego;

import java.io.IOException;

import game.Jugador;
import game.Partida;
import servidor.Paquete;
import servidor.Servidor;

public class VolverPantallaInicial implements ComandosJuego {

	private ComandosJuego siguiente;

	@Override
	public void establecerSiguiente(ComandosJuego siguiente) {
		this.siguiente = siguiente;

	}

	@Override
	public String procesar(Paquete paquete, String msj, Partida partida) {
		if (msj.equals("2")) {
			try {
				String sala = paquete.getSala();
				String enGame = Servidor.darConfigSalas(sala).getFinalizoPartida();
				if (!enGame.equals("finPartida")) {
					if (partida.getJugadores().size() > 2) {
						Jugador player = null;		///-----> mas de 2 jugadores
						for (Jugador players : partida.getJugadores()) {
							if (paquete.getNick().equals(players.getNombre()))
								player = players;
						}
						partida.getJugadores().remove(player);
						//partida.observarJugadores();
					} else
						for (Paquete paqueteClient : Servidor.darClientesDeSala(sala)) {
							if (!paqueteClient.getNick().equals(paquete.getNick())) {
								paqueteClient.getSalida().writeObject("finPartida");
								paqueteClient.getSalida().writeObject(paqueteClient.getNick());
							}
						}
				}
				paquete.dejarSala(sala);
				Servidor.eliminarClienteDeSala(paquete, sala);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "--Salir";
		} else
			return siguiente.procesar(paquete, msj, partida);
	}

}
