package comandos;

import servidor.Paquete;
import servidor.Servidor;
import servidor.SettingsPartida;

public class IngresarSala implements ComandosServer {

	private ComandosServer siguiente;

	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;

	}

	public String procesar(Paquete paquete, String msj) {
		String resp="n";
		if (msj.equals("3")) {
			try {
				String sala = (String)paquete.getEntrada().readObject();
				SettingsPartida part =Servidor.darConfigSalas(sala);
				if (Servidor.getSalas().get(sala).size() < Servidor.getMaxSalas().get(sala).getCantJugadores() && !part.isInGame()) {
					paquete.getSalida().writeObject("y");
					if (Servidor.agregarClienteSala(paquete, sala))
						paquete.setSala(sala);
						paquete.setHostSala(false);
				} else {
					paquete.getSalida().writeObject("n");
					resp="y";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		} else
			return siguiente.procesar(paquete, msj);
	}
}
