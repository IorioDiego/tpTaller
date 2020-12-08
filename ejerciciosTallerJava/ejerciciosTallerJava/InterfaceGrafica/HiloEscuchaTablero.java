package InterfaceGrafica;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;

import cartas.Carta;

public class HiloEscuchaTablero extends Thread {

	private ObjectInputStream dis;
	private ObjectOutputStream dos;
	private Tablero tablero;
	private Integer espera;

	public void run() {

		while (!tablero.isMiTurno()) {
			String msj = (String) leerMsj(dis);

			if (msj.equals("tuTurno")) {

				cambioTurno();

			} else if (msj.equals("actualizarTablero")) {
				actualizar();

			}

		}

	}

	public void actualizar() {
		Carta cJugada = (Carta) Tablero.leerMsj(dis);
		String nombreJugo = (String) leerMsj(dis);
		String nombreOp = null;
		Carta cartaOp = null;
		boolean sw = false;

		switch (cJugada.getNombre()) {
		case "Princesa": {
		}

			break;
		case "Condesa": {
		}
			break;
		case "Principe": {

			
			
			cartaOp = (Carta) Tablero.leerMsj(dis);
			nombreOp = (String) leerMsj(dis);
			

			if (tablero.getNombreJActivo().equals(nombreOp)) {
				Carta nuevaCarta = (Carta) Tablero.leerMsj(dis);
				tablero.getMano().add(nuevaCarta);
				tablero.remove(0);
				sw = true;
			}

		}
			break;
		case "Mucama": {
		}
			break;
		case "Baron": {
		}
			break;
		case "Sacerdote": {
		}
			break;
		case "Guardia": {
		}
			break;
		case "Rey": {
		}
			break;
		}

		pintarCarta(cJugada, nombreJugo);
		if (sw) {
			pintarCarta(cartaOp, nombreOp);
		}

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public HiloEscuchaTablero(ObjectInputStream dis, ObjectOutputStream dos, Tablero tablero, Integer espera) {
		this.dis = dis;
		this.dos = dos;
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
