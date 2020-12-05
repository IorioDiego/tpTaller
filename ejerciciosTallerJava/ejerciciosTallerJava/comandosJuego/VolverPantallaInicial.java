package comandosJuego;

import java.io.IOException;

import servidor.Paquete;
import servidor.Servidor;

public class VolverPantallaInicial implements ComandosJuego {
	
	private ComandosJuego siguiente;
	
	@Override
	public void establecerSiguiente(ComandosJuego siguiente) {
		this.siguiente = siguiente;
		
	}

	@Override
	public String procesar(Paquete paquete, String msj) {
		if (msj.equals("2")) {
			String salirDeSala = paquete.getSala();
			paquete.dejarSala(salirDeSala);
			Servidor.eliminarClienteDeSala(paquete, salirDeSala);
			return "--Salir";
		} else
			return siguiente.procesar(paquete, msj);
	}

}
