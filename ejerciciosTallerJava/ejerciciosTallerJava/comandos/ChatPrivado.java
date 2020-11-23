package comandos;
import java.io.IOException;


import servidor.Paquete;
import servidor.Servidor;

public class ChatPrivado implements ComandosServer {

	private ComandosServer siguiente;

	@Override
	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;

	}

	@Override
	public String procesar(Paquete paquete, String msj) {
		String resp = "Salir";
		if (msj.equals("6")) {
			try {
				Paquete paqPrivado = new Paquete();
				boolean exiteUsuario = false;
				paquete.getSalida().writeUTF("Ingresa nombre del usuario con el que quiere chatear:" + "\n");

				for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSalaActiva()))
					paquete.getSalida().writeUTF(paqueteCliente.getNick());
				String nickName = paquete.getEntrada().readUTF();

				for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSalaActiva()))
					if (paqueteCliente.getNick().equals(nickName)) {
						paqPrivado = paqueteCliente;
						exiteUsuario = true;
					}

				if (exiteUsuario) {
					paquete.setEnChatPrivado(true);
					paquete.getSalida().writeUTF("Para salir de chat privado ingrese [salir]");
					while (!(msj = paquete.getEntrada().readUTF()).equals("salir"))
						paqPrivado.getSalida().writeUTF("[Privado] " + paquete + msj);
					paquete.setEnChatPrivado(false);
				}
				else
					paquete.getSalida().writeUTF("Error,el nombre no corresponde a un usuario conectado");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return resp;

		} else
			return siguiente.procesar(paquete, msj);
	}

}
