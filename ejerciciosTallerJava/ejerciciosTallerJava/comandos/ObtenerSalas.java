package comandos;

import servidor.Paquete;

public class ObtenerSalas implements ComandosServer{

	private ComandosServer siguiente;
	@Override
	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;
		
	}
	@Override
	public String procesar(Paquete paquete, String msj) {
		
		return null;
	}
	
	

}
