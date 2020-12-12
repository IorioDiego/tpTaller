package comandos;

import java.io.IOException;

import servidor.Paquete;
import servidor.Servidor;

public class AvisarIngreso implements ComandosServer {
	private ComandosServer siguiente;

	@Override
	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;

	}

	@Override
	public String procesar(Paquete paquete, String msj) {
		if (msj.equals("13")) {
			try {
				for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
					if (!paqueteCliente.getCliente().equals(paquete.getCliente())
							&& paqueteCliente.getCliente().isConnected()) {
						paqueteCliente.getSalida().writeObject("actualizar");
					}
				}
			} catch (IOException ex) {
				ex.getStackTrace();
			}
			return "y";
		} else
			return siguiente.procesar(paquete, msj);
	}
}
