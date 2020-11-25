package comandos;
import servidor.Paquete;
import servidor.Servidor;

public class CrearSala implements ComandosServer {

	private ComandosServer siguiente;

	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;

	}

	public String procesar(Paquete paquete, String msj) {
		String resp = "n";
		if (msj.equals("2")) {
			try {
				msj = paquete.getEntrada().readUTF();
				paquete.setSala(msj);
				Servidor.crearSala(paquete, msj,4);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		} else
			return siguiente.procesar(paquete, msj);
	}

}
