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
		boolean existeSala = false;
		if (msj.equals("4")) {
			try {
				paquete.getSalida().writeUTF("Ingrese la sala de la cual quiere salir");
				for (String salas : paquete.getSala()) {
					paquete.getSalida().writeUTF("SALAS" + "\n" + salas);
				}

				String sala = paquete.getEntrada().readUTF();
				for (String salas : paquete.getSala()) {
					if (salas.equals(sala))
						existeSala = true;
				}
				if (existeSala) {
					paquete.dejarSala(sala);
					Servidor.eliminarClienteDeSala(paquete, sala);
					paquete.getSalida().writeUTF("Desea crear,ingresar o salir de una sala(y/n)");
					rta = paquete.getEntrada().readUTF();
					if (paquete.cantidadSalas() == 0 && !rta.equals("y"))
						rta = "y";
				} else {
					paquete.getSalida().writeUTF("Sala inexistente");
					rta = "y";
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			return rta;
		} else
			return siguiente.procesar(paquete, msj);
	}

}
