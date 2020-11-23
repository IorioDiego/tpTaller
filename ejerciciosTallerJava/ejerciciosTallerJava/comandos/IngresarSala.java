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
				paquete.getSalida().writeUTF("Ingrese nombre de la sala: ");
				msj = paquete.getEntrada().readUTF();
				if (Servidor.agregarClienteSala(paquete, msj)) {
					paquete.setSala(msj);
					paquete.getSalida().writeUTF("Usted ha ingresado a la sala: " + msj);
					if (paquete.cantidadSalas() < 3) {
						paquete.getSalida().writeUTF("Desea ingresar o crear otra sala(y/n)");
						resp = paquete.getEntrada().readUTF();
					}
				} else {
					paquete.getSalida().writeUTF("Error,Sala inexistente");
					resp = "y";
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		} else
			return siguiente.procesar(paquete, msj);
	}
}
