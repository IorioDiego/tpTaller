package comandos;

import java.io.IOException;
import java.util.ArrayList;

import game.Jugador;
import game.Partida;
import servidor.Paquete;
import servidor.Servidor;
import servidor.SettingsPartida;

public class ComenzarPartida implements ComandosServer {

	private ComandosServer siguiente;

	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;

	}

	public String procesar(Paquete paquete, String msj) {
		String comenzarPartida = "--ComenzoJuego";
		if (msj.equals("15")) {
			try {
				SettingsPartida configPart = Servidor.darConfigSalas(paquete.getSala());
				String orden = (String) paquete.getEntrada().readObject();
				String jInicial = (String) paquete.getEntrada().readObject();
				configPart.setOrden(orden);
				configPart.setJugadorIncial(jInicial);
				Partida partida = generarPartida(configPart, paquete);
				for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
					if (paqueteCliente.getCliente().isConnected()) {
						paqueteCliente.getSalida().writeObject(comenzarPartida);
						paqueteCliente.getSalida().writeObject(partida);
					}
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			return comenzarPartida;
		} else
			return siguiente.procesar(paquete, msj);
	}

	public Partida generarPartida(SettingsPartida config, Paquete cliente) {
		ArrayList<Jugador> jugadores = new ArrayList<>();
		for (Paquete paqueteCliente : Servidor.darClientesDeSala(cliente.getSala())) {
			jugadores.add(new Jugador(paqueteCliente.getNick()));
		}
		Partida nuevaPartida = new Partida(config.getPrendasAmor(), jugadores.size(), jugadores,
				config.getJugadorIncial(), config.getOrden());
		return nuevaPartida;
	}
}
