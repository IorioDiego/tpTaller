package comandos;

import java.io.IOException;

import servidor.Paquete;
import servidor.Servidor;

public class VolverLobby implements ComandosServer {

	private ComandosServer siguiente;

	@Override
	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;

	}

	@Override
	public String procesar(Paquete paquete, String msj) {
		if (msj.equals("8")) {
			try {
				for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
					if (!paqueteCliente.getCliente().equals(paquete.getCliente())
							&& paqueteCliente.getCliente().isConnected()) {
						paqueteCliente.getSalida().writeObject("salioJugador");
					}
				}
				paquete.getSalida().writeObject("-salir");
			} catch (IOException e) {
				e.printStackTrace();
			}
			String salirDeSala = paquete.getSala();
			paquete.dejarSala(salirDeSala);
			Servidor.eliminarClienteDeSala(paquete, salirDeSala);
			return "--VolverAlLobby";
		} else
			return siguiente.procesar(paquete, msj);
	}

}
