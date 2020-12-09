package comandosJuego;

import cartas.Carta;
import game.Jugador;
import game.Partida;
import servidor.Paquete;
import servidor.Servidor;

public class JugarCartas implements ComandosJuego {

	
	private ComandosJuego siguiente;
	@Override
	public void establecerSiguiente(ComandosJuego siguiente) {
		this.siguiente = siguiente;

	}
	@Override
	public String procesar(Paquete paquete, String msj, Partida partida) {
		String resp = "?";
		if (msj.equals("4")) {
			try {
			
				Carta cartaJugada = (Carta) paquete.getEntrada().readObject();
			
				for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
					if (paqueteCliente.getCliente().isConnected() && !paquete.equals(paqueteCliente)) {
						paqueteCliente.getSalida().writeObject("actualizarTablero");
						paqueteCliente.getSalida().writeObject(cartaJugada);
						paqueteCliente.getSalida().writeObject(paquete.getNick());
						
					}
				}
				
				int index = (int) paquete.getEntrada().readObject();
				
				for (Jugador j: partida.getJugadores()) {
					if(j.getNombre().equals(paquete.getNick()))
						j.jugarCarta(partida, index,paquete );
				}
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		} else
			return siguiente.procesar(paquete, msj, partida);
	}
}
