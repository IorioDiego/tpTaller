package comandos;

import comandosJuego.ComandosJuego;
import comandosJuego.DefaultJuego;
import comandosJuego.ObtenerMazo;
import comandosJuego.VolverPantallaInicial;
import game.Mazo;
import servidor.Paquete;
import servidor.Servidor;

public class DentroDeJuego implements ComandosServer {
	
	private ComandosServer siguiente;
	private ComandosJuego comanJuego; 

	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;

	}

	public String procesar(Paquete paquete, String msj) {
		String resp = "--VolverAlLobby";
		ChainParaJuego();
		if (msj.equals("17")) {
			try {
				do {
					msj = (String) paquete.getEntrada().readObject();
				} while (!(comanJuego.procesar(paquete, msj)).equals("--Salir"));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		} else
			return siguiente.procesar(paquete, msj);
	}
	
	public void ChainParaJuego()
	{	
		ComandosJuego defaultJuego = new DefaultJuego();
		ComandosJuego finJuego = new VolverPantallaInicial();
		
		this.comanJuego = new ObtenerMazo();
		comanJuego.establecerSiguiente(finJuego);
		finJuego.establecerSiguiente(defaultJuego);
	}
	
}
