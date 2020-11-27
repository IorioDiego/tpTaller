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
				String host = "";
				ArrayList<String> nickPlayers = new ArrayList<>();
				for (Paquete item : Servidor.getSalas().get(paquete.getSala())) {
					String nick = item.getNick();
					nickPlayers.add(nick);
					if(item.esHost())
						host = nick;
				}
				paquete.getSalida().writeObject(nickPlayers);
				if (paquete.esHost())
					paquete.getSalida().writeObject(host + " Host");
				else
					paquete.getSalida().writeObject(host + " noHost");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "y";
		} else
			return siguiente.procesar(paquete, msj);
	}

}
