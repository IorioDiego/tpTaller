package comandosJuego;



import game.Jugador;
import game.Partida;
import servidor.Paquete;


public class BloquearJugadores implements ComandosJuego{
	
	
	private ComandosJuego siguiente;
	@Override
	public void establecerSiguiente(ComandosJuego siguiente) {
		this.siguiente = siguiente;

	}
	@Override
	public String procesar(Paquete paquete, String msj, Partida partida) {
		String resp = "?";
		if (msj.equals("6")) {
			try {
				for (Jugador player: partida.getJugadores()) {
					paquete.getSalida().writeObject(player.getEstado());	
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		} else
			return siguiente.procesar(paquete, msj, partida);
	}


}
