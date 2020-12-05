package comandos;

import java.io.IOException;

import servidor.Paquete;
import servidor.Servidor;
import servidor.SettingsPartida;

public class AniadirConfig implements ComandosServer {

	private ComandosServer siguiente;

	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;

	}

	public String procesar(Paquete paquete, String msj) {
		String resp = "?";
		if (msj.equals("16")) {
			try {
				SettingsPartida configPart = Servidor.darConfigSalas(paquete.getSala());
				String orden = (String)paquete.getEntrada().readObject();
				String jInicial = (String)paquete.getEntrada().readObject();
				configPart.setOrden(orden);
				configPart.setJugadorIncial(jInicial);		
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		} else
			return siguiente.procesar(paquete, msj);
	}

}
