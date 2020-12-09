package comandosJuego;

import cartas.Carta;
import game.Jugador;
import game.Partida;
import servidor.Paquete;
import servidor.Servidor;

public class DibujarCondesa implements ComandosJuego{

	private ComandosJuego siguiente;
	@Override
	public void establecerSiguiente(ComandosJuego siguiente) {
		this.siguiente = siguiente;

	}
	@Override
	public String procesar(Paquete paquete, String msj, Partida partida) {
		String resp = "?";
		if (msj.equals("7")) {
			try {
			
				Carta cartaJugada = (Carta) paquete.getEntrada().readObject();
			
				for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
					if ( !paquete.equals(paqueteCliente)) {
						paqueteCliente.getSalida().writeObject("actualizarTablero");
						paqueteCliente.getSalida().writeObject(cartaJugada);
						paqueteCliente.getSalida().writeObject(paquete.getNick());
						
					}
				}
				
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		} else
			return siguiente.procesar(paquete, msj, partida);
	}

}
