package comandosJuego;

import java.io.IOException;

import game.Partida;
import servidor.Paquete;

public class DefaultJuego implements ComandosJuego {
	
	@Override
	public void establecerSiguiente(ComandosJuego siguiente) {
		
		
	}

	@Override
	public String procesar(Paquete paquete, String msj,Partida partida) {
		try {
			paquete.getSalida().writeUTF("Error,Comando invalido ingrese nuevamente");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "y";
	}
	
}
