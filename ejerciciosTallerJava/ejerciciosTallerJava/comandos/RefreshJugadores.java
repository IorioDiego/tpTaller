package comandos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import servidor.Paquete;
import servidor.SalaSerealizable;
import servidor.Servidor;
import servidor.SettingsPartida;

public class RefreshJugadores implements ComandosServer {

	private ComandosServer siguiente;

	@Override
	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;

	}

	@Override
	public String procesar(Paquete paquete, String msj) {
		if (msj.equals("12")) {
			try {
				ArrayList<String> nickPlayers = new ArrayList<>();
				for (Paquete item : Servidor.getSalas().get(paquete.getSala())) {
					nickPlayers.add(item.getNick());
				}
				paquete.getSalida().writeObject(nickPlayers);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "y";
		} else
			return siguiente.procesar(paquete, msj);
	}

}
