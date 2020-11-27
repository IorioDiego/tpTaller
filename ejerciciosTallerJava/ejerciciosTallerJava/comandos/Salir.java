package comandos;
import java.io.IOException;

import servidor.Paquete;
import servidor.Servidor;

public class Salir implements ComandosServer {

	private ComandosServer siguiente;

	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;

	}

	public String procesar(Paquete paquete,String msj) {
		String resp="Salir";
		if (msj.equals("-/-1")) {
			try {
				paquete.getSalida().close();
				paquete.getEntrada().close();
				paquete.getCliente().close();
				if (!paquete.getSala().equals(".."))
					Servidor.eliminarClienteDeSalas(paquete);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return resp;
		
		} else
			return siguiente.procesar(paquete,msj);
	}
}
