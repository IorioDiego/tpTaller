package comandosJuego;

import servidor.Paquete;

public interface ComandosJuego {
	public void establecerSiguiente(ComandosJuego siguiente);

	public String procesar(Paquete paquete,String msj);
}
