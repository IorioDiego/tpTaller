package Cliente;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import InterfaceGrafica.DentroDeSala;
import InterfaceGrafica.Salas;
import InterfaceGrafica.Tablero;
import game.Partida;
import servidor.Paquete;
import servidor.Servidor;

public class HiloEscuchar extends Thread {

	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	private DefaultListModel dlm;
	private ArrayList<String> nickNames;
	private Salas miSala;
	private DentroDeSala configSala;
	private JComboBox<String> combo1;
	private JComboBox<String> combo2;

//	private ObjectOutputStream;
	public HiloEscuchar(ObjectInputStream entrada, ObjectOutputStream salida, DefaultListModel dlm, JComboBox combo1,
			JComboBox combo2, Salas miSala, DentroDeSala configSala) {
		this.entrada = entrada;
		this.dlm = dlm;
		this.salida = salida;
		this.combo1 = combo1;
		this.combo2 = combo2;
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
				// if (msj.equals("actualizar") || msj.equals("salioJugador")) {
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
//					configSala.getCombo1().addItem(item);

					configSala.getCombo2().addItem(item);
				}
				if (cantJugadores == cont) {
					if (esHostserver.equals("Host"))
						configSala.getComenzar().setEnabled(true);
				} else
					configSala.getComenzar().setEnabled(false);
				// }
			}
			// cont--;
			
			if (msj.equals("-salir")) {
				miSala.setVisible(true);
			} else {
				Partida nuevaPartida;
				try {
					nuevaPartida = (Partida) entrada.readObject();
					nuevaPartida.setDis(entrada);
					nuevaPartida.setDos(salida);
					salida.writeObject("17");
					nuevaPartida.iniciarPartida();
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
					frame.init(nPartida.getJugadores(),nPartida,miSala);
					//Dos y Dis al init
					frame.setExtendedState(JFrame.NORMAL);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}
}
