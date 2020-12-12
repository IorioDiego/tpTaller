package InterfaceGrafica;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



import cartas.Carta;

public class HiloEscuchaTablero extends Thread {

	private ObjectInputStream dis;
	private Tablero tablero;
	private Integer espera;

	public void run() {
		while (!tablero.isMiTurno()) {
			String msj = (String) leerMsj(dis);
			if (msj.equals("tuTurno")) {
				cambioTurno();
			} else if (msj.equals("actualizarTablero")) {
				actualizar();
			} else if (msj.equals("finDeRonda")) {
				reiniciarRonda();
			} else if (msj.equals("finPartida")) {
				finDePartida();
			} else if (msj.equals("Abandono")) {
				abandono();
			}
		}

	}

	public void reiniciarRonda() {
		tablero.setNroRonda(tablero.getNroRonda() + 1);
		leerMsj(dis); 	
		String ganador = (String) leerMsj(dis);
		tablero.setGanadorRonda(ganador);
		tablero.setReinicioRonda(true);
		tablero.getIndexDes().clear();
		tablero.getDistdDes().clear();
		tablero.getMano().clear();
		tablero.getPosiciones().clear();
		tablero.getDescartes().clear();
		tablero.recibirCartas();
		tablero.construirDescarte();;
	}
	
	public void abandono()
	{
		String nickAbandono = (String)leerMsj(dis);
		tablero.eliminarJugadorTablero(nickAbandono);
		tablero.refresh();
	}
	
	public void finDePartida() {
		tablero.finDePartida();
		tablero.refresh();
	}

	public void actualizar() {
		Carta cJugada = (Carta) Tablero.leerMsj(dis);
		String nombreJugo = (String) leerMsj(dis);
		String nombreOp = null;
		Carta cartaElegidaOp = null;
		Carta cartaOp = null;
		Carta cartaBaron = null;
		Carta cartaBaronOp = null;
		String jugadorBaron = null;
		String jugadorBaronOp = null;
		boolean itsMeMario = false;
		boolean sw = false;

		switch (cJugada.getNombre()) {
		case "Princesa": {
			nombreOp = (String) leerMsj(dis);
			cartaOp = (Carta) Tablero.leerMsj(dis);
			sw = true;
		}
			break;
		case "Condesa": {
		}
			break;
		case "Principe": {
			nombreOp = (String) leerMsj(dis);
			cartaOp = (Carta) Tablero.leerMsj(dis);
			sw = true;
			if (tablero.getNombreJActivo().equals(nombreOp)) {
				itsMeMario = true;
			}

			if (itsMeMario) {
				Carta nuevaCarta = (Carta) Tablero.leerMsj(dis);
				tablero.getMano().add(nuevaCarta);
				tablero.getMano().remove(0);

			}

		}
			break;
		case "Mucama": {
		}
			break;
		case "Baron": {
			nombreOp = (String) leerMsj(dis);
			cartaBaronOp = (Carta) Tablero.leerMsj(dis);
			cartaBaron = (Carta) Tablero.leerMsj(dis);

			if (tablero.getNombreJActivo().equals(nombreOp)) {
				itsMeMario = true;
			}

			jugadorBaron = nombreJugo;
			jugadorBaronOp = nombreOp;
			if (itsMeMario) {
				tablero.setJugadorBaronOp(nombreOp);
				tablero.setCartaBaronOp(cartaBaronOp);
				tablero.setCartaBaron(cartaBaron);
				tablero.setJugadorBaron(nombreJugo);
				tablero.setCompararManos(true);

			}

			String msj = (String) leerMsj(dis);
			if (msj.equals("PerdioOponente")) {
				sw = true;
				cartaOp = cartaBaronOp;
				nombreOp = jugadorBaronOp;
			} else if (msj.equals("PerdioJugador")) {
				sw = true;
				cartaOp = cartaBaron;
				nombreOp = jugadorBaron;

			}

		}
			break;
		case "Sacerdote": {

		}
			break;
		case "Guardia": {
			cartaElegidaOp = (Carta) Tablero.leerMsj(dis);
			nombreOp = (String) leerMsj(dis);
			tablero.setSeJugoGuardia(true);
			tablero.setCartaElegidaGuardia(cartaElegidaOp);
			tablero.setJugadorGuaridaOp(nombreOp);
			tablero.setJugadorGuarida(nombreJugo);
			String msj = (String) leerMsj(dis);
			if (msj.equals("Acierto")) {
				cartaOp = (Carta) Tablero.leerMsj(dis);
				tablero.setAcertoGuardia(true);
				sw = true;
			}
		}
			break;
		case "Rey": {
			nombreOp = (String) leerMsj(dis);

			if (tablero.getNombreJActivo().equals(nombreOp)) {
				itsMeMario = true;
			}
			if (itsMeMario) {
				cartaOp = (Carta) leerMsj(dis);
				tablero.getMano().remove(0);
				tablero.getMano().add(cartaOp);
			}
		}
			break;
		}

		pintarCarta(cJugada, nombreJugo);
		if (sw) {
			pintarCarta(cartaOp, nombreOp);
		}
		sw = false;
		tablero.refresh();

	}

	private void pintarCarta(Carta cJugada, String nombreJugo) {
		int idx = tablero.getIndexDes().get(nombreJugo);
		Integer[] pos = tablero.getPosiciones().get(nombreJugo);
		int x = pos[0];
		int y = pos[1];
		int nuevaDist = tablero.getDistdDes().get(nombreJugo) + 30;
		tablero.getDistdDes().replace(nombreJugo, nuevaDist);
		DibujoCarta dibujo = new DibujoCarta(cJugada, x + nuevaDist, y);
		tablero.getDescartes().get(idx).add(dibujo);
	}

	public void cambioTurno() {
		tablero.setMiTurno(true);
		synchronized (espera) {
			try {
				espera.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	public HiloEscuchaTablero(ObjectInputStream dis, Tablero tablero, Integer espera) {
		this.dis = dis;
		this.tablero = tablero;
		this.espera = espera;
	}

	public static void enviarMsj(ObjectOutputStream dosObject, Object msj) {
		try {
			dosObject.writeObject(msj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object leerMsj(ObjectInputStream disObject) {
		try {
			return disObject.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
