package comandos;
import servidor.Paquete;
import servidor.Servidor;

public class IngresarSala implements ComandosServer {

	private ComandosServer siguiente;

	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;

	}

	public String procesar(Paquete paquete, String msj) {
		String resp = "n";
		if (msj.equals("3")) {
			try {
				msj = (String)paquete.getEntrada().readObject();
				if (Servidor.agregarClienteSala(paquete, msj)) 
					paquete.setSala(msj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		} else
			return siguiente.procesar(paquete, msj);
	}
}
