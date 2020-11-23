package comandos;

import java.io.IOException;

import servidor.Paquete;
import servidor.Servidor;

public class EnviarMsjAllSala implements ComandosServer {

	private ComandosServer siguiente;

	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;

	}

	public String procesar(Paquete paquete, String msj) {
		String dejarSala = "dejarSala";
		if (msj.equals("5")) {
			try {
				paquete.getSalida().writeUTF("Para cambiar de sala de chat ingrese [salir]");
				while (!(msj = paquete.getEntrada().readUTF()).equals("salir")) {
					Servidor.agregarAHistorial(paquete.getSalaActiva(), "[General] " + paquete + msj);
					for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSalaActiva())) {
						if (!paqueteCliente.getCliente().equals(paquete.getCliente()) && 
								paqueteCliente.getCliente().isConnected() &&
								!paqueteCliente.isEnChatPrivado() &&
								paqueteCliente.getSalaActiva().equals(paquete.getSalaActiva())) {
							paqueteCliente.getSalida().writeUTF("[General] "+paquete + msj);
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			return dejarSala;
		} else
			return siguiente.procesar(paquete, msj);
	}

}
