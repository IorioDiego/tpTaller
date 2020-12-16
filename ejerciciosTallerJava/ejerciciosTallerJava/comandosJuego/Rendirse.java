package comandosJuego;

import java.io.IOException;

import estados.Eliminado;
import game.Jugador;
import game.Partida;
import servidor.Paquete;
import servidor.Servidor;

public class Rendirse implements ComandosJuego {

	private ComandosJuego siguiente;

	@Override
	public void establecerSiguiente(ComandosJuego siguiente) {
		this.siguiente = siguiente;

	}

	@Override
	public String procesar(Paquete paquete, String msj, Partida partida) {
		if (msj.equals("9")) {
			try {
				String sala = paquete.getSala();
				String nuevoJinicial = "";
				for (Paquete paqueteClient : Servidor.darClientesDeSala(sala)) {
					if (!paqueteClient.getNick().equals(paquete.getNick())) {
						paqueteClient.getSalida().writeObject("Abandono");
						paqueteClient.getSalida().writeObject(paquete.getNick());
						nuevoJinicial = paqueteClient.getNick();
					}
				}
				if (partida.getjInicial().equals(paquete.getNick()))
					partida.setjInicial(nuevoJinicial);

				int cantPlayers = partida.getJugadores().size();
				if (cantPlayers > 2) {
					rendirseConFinRonda(cantPlayers, partida, paquete);
				}

				partida.setCantJugadores(partida.getCantJugadores() - 1);
				partida.setJugadoresActivos(partida.getJugadoresActivos() - 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "y";
		} else
			return siguiente.procesar(paquete, msj, partida);
	}

	public void rendirseConFinRonda(int cantPlayers, Partida partida, Paquete paquete) {
		int eliminados = 0;
		Jugador ganador = null;
		try {
			for (Jugador players : partida.getJugadores()) {
				if (players.getEstado().equals(new Eliminado()))
					eliminados++;
				else if(!players.getNombre().equals(paquete.getNick()))
					ganador = players;
			}
			if (cantPlayers == 3 && eliminados == 1 || cantPlayers == 4 && eliminados == 2) {
				paquete.getSalida().writeObject("noDarTurno");
				Servidor.darConfigSalas(paquete.getSala()).setGanador(ganador);
			} else
				paquete.getSalida().writeObject("DarTurno");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
