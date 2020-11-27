package comandos;

import servidor.Paquete;
import servidor.Servidor;
import servidor.SettingsPartida;

public class CrearSala implements ComandosServer {

	private ComandosServer siguiente;

	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;

	}

	public String procesar(Paquete paquete, String msj) {
		String resp = "n";
		if (msj.equals("2")) {
			try {
				if (((String) paquete.getEntrada().readObject()).equals("crear")) {
					String nombSala = (String) paquete.getEntrada().readObject();
					if (!Servidor.getSalas().containsKey(nombSala)) {
						paquete.getSalida().writeObject("no existe");
						SettingsPartida setPart = new SettingsPartida(
								(SettingsPartida) paquete.getEntrada().readObject());
						paquete.setSala(setPart.getNombreSala());
						Servidor.crearSala(paquete, setPart.getNombreSala(), setPart);
						paquete.setHostSala(true);
					} else {
						paquete.getSalida().writeObject("existe");
						resp = "y";
					}
				} else
					resp = "y";
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp; /// si toco [boton volver] devuelvo "y" si no "n"
		} else
			return siguiente.procesar(paquete, msj);
	}

}
