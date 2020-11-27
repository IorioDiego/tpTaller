package InterfaceGrafica;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.sun.java.swing.plaf.windows.resources.windows;

import comandos.CrearSala;
import servidor.Paquete;
import servidor.SalaSerealizable;
import servidor.SettingsPartida;

public class Salas extends JFrame {

	private ArrayList<SalaSerealizable> salas;
	private JPanel contentPane;
	private JPanel nickname = new JPanel();
	private JTextField textField;
	private JList<String> list;
	private boolean tocoEnter = false;
	private static Integer indexSala;
	private JButton crear;
	private JButton Ingresar;
	private Salas miSala = this;
	private ObjectInputStream dis;
	private ObjectOutputStream dos;
	private Socket cliente;

	Font fuente = new Font("Calibri", Font.PLAIN, 16);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init(ObjectInputStream disObject, ObjectOutputStream dosObject, Socket cliente) {

		JPanel gui = new JPanel();
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(Box.createVerticalStrut(5));
		textField = new JTextField("Ingrese Nickname", 25);
		gui.add(textField);
		dis = disObject;
		dos = dosObject;
		this.cliente = cliente;
		gui.setAlignmentX(Component.CENTER_ALIGNMENT);

		gui.setPreferredSize(new Dimension(400, 35));
		gui.setMaximumSize(new Dimension(400, 35));

		this.getContentPane().add(gui);
		getContentPane().add(Box.createVerticalStrut(5));
		setTitle("Seleccion de sala");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		textField.setFont(fuente);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!tocoEnter)
					textField.setText("");
			}
		});

		GridLayout layout = new GridLayout(1, 2);
		layout.setHgap(25);

		JPanel botones = new JPanel();
		botones.setLayout(layout);

		JButton btnCrear = new JButton("Crear Sala");
		JButton btnIngresar = new JButton("Ingresar a Sala");
		JButton btnRefresh = new JButton("Refrescar salas");

		botones.add(btnCrear);
		btnCrear.setEnabled(false);
		btnIngresar.setEnabled(false);
		botones.add(btnIngresar);
		botones.add(btnRefresh);
		btnRefresh.setEnabled(false);

		botones.setPreferredSize(new Dimension(450, 25));
		botones.setMaximumSize(new Dimension(450, 25));
//        DefaultListModel dlm = new DefaultListModel();
//        ArrayList<String> salas = new ArrayList<String>();
//        
//        for (int i = 0; i < 100; i++) {
//            salas.add("Sala " + (i+1));
//        }
//        
//        for (String item : salas) {
//            dlm.addElement(item);
//          }
//
// 		

		DefaultListModel dlm = new DefaultListModel();

		for (SalaSerealizable item : salas) {
			dlm.addElement(item);
		}

		JList list = new JList(dlm);
		JScrollPane scroll = new JScrollPane(list);

		((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		add(list);
		add(scroll);
		scroll.getViewport().add(list);
		list.setEnabled(false);

		// btnIngresar.setEnabled(false);
		btnIngresar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				enviarMsj(dosObject, "3");
				enviarMsj(dosObject, salas.get(indexSala).getSetPart().getNombreSala());
				String hayEspacio = (String) leerMsj(disObject);
				if (hayEspacio.equals("y")) {
					DentroDeSala sala = new DentroDeSala(miSala);
					dispose();
					enviarMsj(dosObject, "13"); // avisa ingreso
					sala.init(disObject, dosObject, salas.get(indexSala).getSetPart().getNombreSala());
				} else
					JOptionPane.showMessageDialog(null, "La sala a la cual quiere ingresar esta llena eliga otra",
							"Sala llena", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				enviarMsj(dosObject, "11");
				salas = (ArrayList<SalaSerealizable>) leerMsj(disObject);
				dlm.clear();
				for (SalaSerealizable item : salas) {
					dlm.addElement(item);
				}

			}
		});

		btnCrear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InterfazCrearSala crearSala = new InterfazCrearSala(miSala);
				enviarMsj(dosObject, "2");
				crearSala.init(disObject, dosObject);
				setVisible(false);
			}
		});

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getFirstIndex() != -1 && !e.getValueIsAdjusting()) {
					indexSala = list.getSelectedIndex();
					btnIngresar.setEnabled(true);

					/// Borrado---
					// list.removeListSelectionListener(this);
					// list.clearSelection();
					// dlm.remove(firstIndex);
					// salas.remove(firstIndex);
					// list.addListSelectionListener(this);
				}
			}
		});

//		JOptionPane.showMessageDialog(null, "Debe ingresar un nickName para continuar", 
//		"Sala llena", JOptionPane.ERROR_MESSAGE);

		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (textField.getText().equals("") || textField.getText().equals("Ingrese Nickname"))

						JOptionPane.showMessageDialog(null, "Debe ingresar un nickName para continuar",
								"Ingreso nickName", JOptionPane.ERROR_MESSAGE);
					else {
						enviarMsj(dosObject, textField.getText());
						tocoEnter = true;
						btnCrear.setEnabled(true);
						list.setEnabled(true);
						btnIngresar.setEnabled(true);
						btnRefresh.setEnabled(true);
						textField.setEnabled(false);
					}
				}
			}
		});

		getContentPane().add(Box.createVerticalStrut(10));
		getContentPane().add(botones);
		getContentPane().add(Box.createVerticalStrut(10));
		setTitle("Seleccion de salas");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		requestFocusInWindow();
		cerrar();
		setResizable(false);
		setBounds(500, 250, 450, 300);
	}

	public void cerrar() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				confirmarSalida();
			}

		});
	}

	public void confirmarSalida() {
		int valor = JOptionPane.showConfirmDialog(this, "Quiere salir de la aplicacion", "Cerrar",
				JOptionPane.WARNING_MESSAGE);
		if (valor == JOptionPane.YES_OPTION) {
			enviarMsj(dos, "-/-1");
			cerrarConexion();
			System.exit(0);
		}
	}

	public Salas() {
		contentPane = new JPanel();
		// this.partida = partida;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBounds(200, 117, 1366, 768);
		// getContentPane().setBounds(626, 417, 800, 513);**7
		setContentPane(contentPane);
		salas = new ArrayList<>();

	}

	public void enviarMsj(ObjectOutputStream dosObject, Object msj) {
		try {
			dosObject.writeObject(msj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cerrarConexion() {
		try {
			dis.close();
			dos.close();
			cliente.close();
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

	public ArrayList<SalaSerealizable> getSalas() {
		return salas;
	}

	public static int getSala() {
		return indexSala;
	}

	public void setSalas(ArrayList<SalaSerealizable> salas) {
		this.salas = salas;
	}

	public void agregarSalas(SalaSerealizable s) {
		this.salas.add(s);

	}
}
