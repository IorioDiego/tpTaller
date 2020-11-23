package comandos;
import servidor.Paquete;

public class VolverLobby implements ComandosServer {

	private ComandosServer siguiente;

	@Override
	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;

	}

	@Override
	public String procesar(Paquete paquete, String msj) {
		if (msj.equals("8")) {
			paquete.setSalaActiva("");
			return "--VolverAlLobby";
		} else
			return siguiente.procesar(paquete, msj);
	}

}
