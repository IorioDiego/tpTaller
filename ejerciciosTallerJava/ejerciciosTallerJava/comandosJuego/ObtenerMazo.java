package comandosJuego;

import java.io.IOException;

import comandos.ComandosServer;
import game.Mazo;
import servidor.Paquete;
import servidor.Servidor;
import servidor.SettingsPartida;

public class ObtenerMazo implements ComandosJuego {

	private ComandosJuego siguiente;

	public void establecerSiguiente(ComandosJuego siguiente) {
		this.siguiente = siguiente;

	}

	public String procesar(Paquete paquete, String msj) {
		String resp = "?";
		if (msj.equals("1")) {
			try {
				
				Mazo mazo = (Servidor.darConfigSalas(paquete.getSala())).getMazo();
				paquete.getSalida().writeObject(mazo);
//				for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
//					if (paqueteCliente.getCliente().isConnected()) {
//						paqueteCliente.getSalida().writeObject(nuevoMazo);
//					}
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		} else
			return siguiente.procesar(paquete, msj);
	}

}
