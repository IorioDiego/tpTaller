package comandosJuego;

import java.io.IOException;

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
				for (Paquete paqueteClient : Servidor.darClientesDeSala(sala)) {
					if (!paqueteClient.getNick().equals(paquete.getNick())) {
						paqueteClient.getSalida().writeObject("Abandono");
						paqueteClient.getSalida().writeObject(paquete.getNick());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "y";
		} else
			return siguiente.procesar(paquete, msj, partida);
	}
}
