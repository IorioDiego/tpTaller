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
				paquete.getSalida().writeUTF("Ingrese nombre de la sala: ");
				msj = paquete.getEntrada().readUTF();
				paquete.setSala(msj);
				Servidor.crearSala(paquete, msj);
				paquete.getSalida().writeUTF("\n" + "Sala creada con exito ");
				if (paquete.cantidadSalas() < 3) {
					paquete.getSalida().writeUTF("Desea ingresar o crear otra sala(y/n)");
					resp = paquete.getEntrada().readUTF();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		} else
			return siguiente.procesar(paquete, msj);
	}

}
