package comandosJuego;



import game.Partida;
import servidor.Paquete;


public class BloquearJugadores implements ComandosJuego{
	
	
	private ComandosJuego siguiente;

	public void establecerSiguiente(ComandosJuego siguiente) {
		this.siguiente = siguiente;

	}

	public String procesar(Paquete paquete, String msj, Partida partida) {
		String resp = "?";
		if (msj.equals("6")) {
			try {
				paquete.getSalida().writeObject(partida);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		} else
			return siguiente.procesar(paquete, msj, partida);
	}


}
