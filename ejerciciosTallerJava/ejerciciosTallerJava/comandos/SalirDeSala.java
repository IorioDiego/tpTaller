package comandos;

import java.io.IOException;

import servidor.Paquete;
import servidor.Servidor;

public class SalirDeSala implements ComandosServer {

	private ComandosServer siguiente;

	@Override
	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;
	}

	@Override
	public String procesar(Paquete paquete, String msj) {
		String rta = "n";
		if (msj.equals("4")) {
			String salirDeSala = paquete.getSala();
			paquete.dejarSala(salirDeSala);
			Servidor.eliminarClienteDeSala(paquete, salirDeSala);
			rta = "y";		///si toco boton volver "y" si no devuelvo "n"
			return "n";
		} else
			return siguiente.procesar(paquete, msj);
	}

}
