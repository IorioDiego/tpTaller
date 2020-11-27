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
