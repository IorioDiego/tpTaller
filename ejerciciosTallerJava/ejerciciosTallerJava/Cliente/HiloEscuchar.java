package Cliente;

import java.awt.EventQueue;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import InterfaceGrafica.DentroDeSala;
import InterfaceGrafica.Salas;
import InterfaceGrafica.Tablero;
import game.Partida;

public class HiloEscuchar extends Thread {
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	private DefaultListModel dlm;
	private ArrayList<String> nickNames;
	private Salas miSala;
	private DentroDeSala configSala;

//	private ObjectOutputStream;
	public HiloEscuchar(ObjectInputStream entrada, ObjectOutputStream salida, DefaultListModel dlm, Salas miSala, DentroDeSala configSala) {
		this.entrada = entrada;
		this.dlm = dlm;
		this.salida = salida;
		this.miSala = miSala;
		this.configSala = configSala;
	}

	@Override
	public void run() {
		try {
			String msj;
			int cont = 1;
			while (!((msj = (String) entrada.readObject()).equals("--ComenzoJuego")) && !msj.equals("-salir")) {
				if (msj.equals("salioJugador"))
					cont--;
				if (msj.equals("actualizar"))
					cont++;
				dlm.clear();
				salida.writeObject("12");
				nickNames = (ArrayList<String>) entrada.readObject();
				for (String item : nickNames) {
					dlm.addElement(item);
				}
				String esHostserver = (String) entrada.readObject();
				String nombreHost = (String) entrada.readObject();
				Integer cantJugadores = Integer.valueOf((String) entrada.readObject());

				configSala.getCombo1().removeAllItems();
				configSala.getCombo2().removeAllItems();
				configSala.getCombo1().addItem("Izquierda");
				configSala.getCombo1().addItem("Derecha");
				for (String item : nickNames) {
					configSala.getCombo2().addItem(item);
				}
				if (cantJugadores == cont) {
					if (esHostserver.equals("Host"))
						configSala.getComenzar().setEnabled(true);
				} else
					configSala.getComenzar().setEnabled(false);
			}
			
			if (msj.equals("-salir")) {
				miSala.setVisible(true);
			} else {
				Partida nuevaPartida;
				try {
					nuevaPartida = (Partida) entrada.readObject();
					salida.writeObject("17");
					iniciarJuego(nuevaPartida);
				} catch (Exception e) {
					e.getStackTrace();
				}
			}
			configSala.dispose();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void iniciarJuego(Partida nPartida)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tablero frame = new Tablero(nPartida);
					frame.setVisible(true);
					frame.init(nPartida.getJugadores(),nPartida,miSala,entrada,salida);
					frame.setExtendedState(JFrame.NORMAL);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}
}
