package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import Cliente.HiloEscuchar;
import servidor.SalaSerealizable;

public class DentroDeSala extends JFrame {

	private InterfazCrearSala crearSala;
	//private DefaultListModel dlm;
	private JPanel contentPane;
	private Salas salaPrincipal;
	private JTextField textField;
	private ArrayList<String> nickNames;
	private JComboBox combo1;
	private JComboBox combo2;
	private boolean esHost = true;
	// private JLabel jugadores;

	private JButton btnComenzar;

	public void init(ObjectInputStream disObject, ObjectOutputStream dosObject, String nombreSala) {

		JPanel panelIzq = new JPanel();
		JPanel panelDer = new JPanel();
		JPanel panelAbajo = new JPanel();
		JPanel cBoxContainer1 = new JPanel();
		JPanel cBoxContainer2 = new JPanel();

		GridLayout layout = new GridLayout();
		layout.setHgap(40);
		// layout.setHgap(40);

		JPanel gridBotones = new JPanel(layout);
		JButton botonVolver = new JButton("Volver");

		JPanel topContainer = new JPanel();
		panelIzq.setLayout(new BoxLayout(panelIzq, BoxLayout.Y_AXIS));
		panelDer.setLayout(new BoxLayout(panelDer, BoxLayout.Y_AXIS));
		topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.X_AXIS));
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JLabel jugadores = new JLabel("Jugadores:     ");
		JLabel combolabel1 = new JLabel("Orden de ronda[izq/der]   ");
		JLabel combolabel2 = new JLabel("Jugador que comieza la ronda   ");

		jugadores.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		enviarMsj(dosObject, "12");

		nickNames = (ArrayList<String>) leerMsj(disObject);
		DefaultListModel dlm = new DefaultListModel();

		for (String item : nickNames) {
			dlm.addElement(item);
		}

		JList lista = new JList(dlm);
		lista.setPreferredSize(new Dimension(300, 300));
		lista.setMaximumSize(new Dimension(300, 300));

		panelIzq.add(jugadores);
		panelIzq.add(lista);

		panelIzq.setPreferredSize(new Dimension(500, 300));
		panelIzq.setMaximumSize(new Dimension(500, 300));

		panelDer.setPreferredSize(new Dimension(500, 300));
		panelDer.setMaximumSize(new Dimension(500, 300));

		combo1 = new JComboBox();
		combo2 = new JComboBox();

		for (String item : nickNames) {
			combo1.addItem(item);
			combo2.addItem(item);
		}

		botonVolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (esHost)
					enviarMsj(dosObject, "14");
				else
					enviarMsj(dosObject, "8");
			}
		});
		
//		btnComenzar.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//			}
//		});
		
		
		panelAbajo.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

		cBoxContainer1.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		cBoxContainer1.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		combo1.setPreferredSize(new Dimension(160, 25));
		combo1.setMaximumSize(new Dimension(160, 25));

		cBoxContainer2.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
		cBoxContainer2.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
		combo2.setPreferredSize(new Dimension(160, 25));
		combo2.setMaximumSize(new Dimension(160, 25));

		cBoxContainer1.add(combolabel1);
		cBoxContainer1.add(combo1);
		cBoxContainer2.add(combolabel2);
		cBoxContainer2.add(combo2);
		panelDer.add(cBoxContainer1);
		panelDer.add(cBoxContainer2);
		btnComenzar = new JButton("Comenzar Partida");
		gridBotones.add(btnComenzar);
		gridBotones.add(botonVolver);
		panelAbajo.add(gridBotones);

		topContainer.add(panelIzq);
		topContainer.add(panelDer);
		getContentPane().add(topContainer);
		getContentPane().add(panelAbajo);

		// String esHost = (String) leerMsj(disObject);
		//String[] partes = ((String) leerMsj(disObject)).split(" ");
		String esHostserver = (String) leerMsj(disObject);
		String nombreHost = (String) leerMsj(disObject);
		String cantJugadores = (String) leerMsj(disObject);
		
		if (esHostserver.equals("noHost")) {
			combo1.setEnabled(false);
			combo2.setEnabled(false);
			esHost = false;
		}
		btnComenzar.setEnabled(false);

		setTitle("Configuracion de la Partida " + "Sala: " + nombreSala + "Host: " + nombreHost);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // VER POR QUE LO IGNORA
		setLocationRelativeTo(null);
		setFocusable(true);
		requestFocusInWindow();
		setVisible(true);
		setBounds(500, 250, 500, 270);

		HiloEscuchar ingresoJug = new HiloEscuchar(disObject, dosObject, dlm, combo1, combo2,salaPrincipal,this);
		ingresoJug.start(); // hilo de actualizacion
	}

	public DentroDeSala(Salas sala) {
		contentPane = new JPanel();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setBounds(200, 117, 1366, 768);
		setContentPane(contentPane);
		salaPrincipal = sala;
	}

	public JComboBox getCombo1() {
		return combo1;
	}

	public JComboBox getCombo2() {
		return combo2;
	}


	public JButton getComenzar() {
		return btnComenzar;
	}


	public void enviarMsj(ObjectOutputStream dosObject, Object msj) {
		try {
			dosObject.writeObject(msj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object leerMsj(ObjectInputStream disObject) {
		try {
			return disObject.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
