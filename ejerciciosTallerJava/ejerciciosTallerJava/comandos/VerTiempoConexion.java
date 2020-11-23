package comandos;
import java.io.IOException;

import servidor.Paquete;
import servidor.Servidor;

public class VerTiempoConexion implements ComandosServer {

	private ComandosServer siguiente;

	@Override
	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;

	}

	@Override
	public String procesar(Paquete paquete,String msj) {
		String resp= "salir";
		if (msj.equals("7")) {
			try {
				for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSalaActiva()))
					paquete.getSalida().writeUTF(paqueteCliente.getTiempoConexion());
			} catch (IOException e) {
				e.printStackTrace();
			}
		return resp;
		} 
		else
			return siguiente.procesar(paquete, msj);
	}
}
