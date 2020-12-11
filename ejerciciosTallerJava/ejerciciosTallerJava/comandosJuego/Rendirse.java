package comandosJuego;

import java.io.IOException;

import game.Jugador;
import game.Partida;
import javafx.collections.SetChangeListener;
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
				partida.setCantJugadores(partida.getCantJugadores() - 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "y";
		} else
			return siguiente.procesar(paquete, msj, partida);
	}
}
