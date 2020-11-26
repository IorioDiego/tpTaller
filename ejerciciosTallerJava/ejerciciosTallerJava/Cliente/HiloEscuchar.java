package Cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;

import InterfaceGrafica.Salas;

public class HiloEscuchar extends Thread {

	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	private DefaultListModel dlm;
	private ArrayList<String> nickNames;
	private JComboBox<String> combo1;
	private JComboBox<String> combo2;

//	private ObjectOutputStream;
	public HiloEscuchar(ObjectInputStream entrada, ObjectOutputStream salida, DefaultListModel dlm, JComboBox combo1,
			JComboBox combo2) {
		this.entrada = entrada;
		this.dlm = dlm;
		this.salida = salida;
		this.combo1 = combo1;
		this.combo2 = combo2;
	}

	@Override
	public void run() {
		try {
			String msj;
			while ((msj = (String) entrada.readObject()).equals("--ComenzoJuego") || !msj.equals("-salir")) {
				if (msj.equals("actualizar") || msj.equals("salioJugador")) {
					dlm.clear();
					salida.writeObject("12");
					nickNames = (ArrayList<String>) entrada.readObject();
					for (String item : nickNames) {
						dlm.addElement(item);
					}
					combo1.removeAllItems();
					combo2.removeAllItems();
					for (String item : nickNames) {
						combo1.addItem(item);
						combo2.addItem(item);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
