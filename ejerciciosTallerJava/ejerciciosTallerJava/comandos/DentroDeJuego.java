package comandos;

import comandosJuego.BloquearJugadores;
import comandosJuego.CambioJugador;
import comandosJuego.ComandosJuego;
import comandosJuego.DefaultJuego;
import comandosJuego.DibujarCondesa;
import comandosJuego.JugarCartas;
import comandosJuego.TomarCartas;
import comandosJuego.VolverPantallaInicial;
import game.Partida;
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
		Partida partida = Servidor.darConfigSalas(paquete.getSala()).getPartida();
		ChainParaJuego();
		if (msj.equals("17")) {
			try {
				do {
					
					msj = (String) paquete.getEntrada().readObject();	
				} while (!(comanJuego.procesar(paquete, msj,partida)).equals("--Salir"));
				
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
		ComandosJuego tomarCarta = new TomarCartas();
		ComandosJuego jugarCarta =  new JugarCartas();
		ComandosJuego cambioJugador = new CambioJugador();
		ComandosJuego bloquearJugador= new BloquearJugadores();
		ComandosJuego dibujarCondesa = new DibujarCondesa();
		
		this.comanJuego = new VolverPantallaInicial();
		comanJuego.establecerSiguiente(tomarCarta);
		tomarCarta.establecerSiguiente(jugarCarta);
		jugarCarta.establecerSiguiente(cambioJugador);
		cambioJugador.establecerSiguiente(dibujarCondesa);
		dibujarCondesa.establecerSiguiente(bloquearJugador);
		bloquearJugador.establecerSiguiente(defaultJuego);
	}
	
}
