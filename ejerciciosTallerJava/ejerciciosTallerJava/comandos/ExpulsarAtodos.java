package comandos;

import java.io.IOException;
import java.util.ArrayList;

import servidor.Paquete;
import servidor.Servidor;

public class ExpulsarAtodos implements ComandosServer {

	private ComandosServer siguiente;

	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;

	}

	// !paqueteCliente.getCliente().equals(paquete.getCliente()) &&
	public String procesar(Paquete paquete, String msj) {
		String avisar = "y";
		if (msj.equals("14")) {
			try {
				if (Servidor.darClientesDeSala(paquete.getSala()).size() > 1) {
					String sala = paquete.getSala();
					ArrayList<Paquete> paquetes = new ArrayList<>(Servidor.darClientesDeSala(paquete.getSala()));
					for (Paquete paqueteCliente : paquetes) {
						if (paqueteCliente.getCliente().isConnected()) {
							paqueteCliente.getSalida().writeObject("-salir");
						}
					}
					for (Paquete paqElim : paquetes) {
						String salirDeSala = paqElim.getSala();
						paqElim.dejarSala(salirDeSala);
						Servidor.eliminarClienteDeSala(paqElim, salirDeSala);
					}
				} else {
					paquete.getSalida().writeObject("-salir");
					String salirDeSala = paquete.getSala();
					paquete.dejarSala(salirDeSala);
					Servidor.eliminarClienteDeSala(paquete, salirDeSala);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return avisar;
		} else
			return siguiente.procesar(paquete, msj);
	}

}
