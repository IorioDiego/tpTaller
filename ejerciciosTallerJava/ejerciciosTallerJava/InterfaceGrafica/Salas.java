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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

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

import servidor.SalaSerealizable;

public class Salas extends JFrame {

	private ArrayList<SalaSerealizable> salas = new ArrayList<SalaSerealizable>();
	private JPanel contentPane;
	private DefaultListModel dlm;
	private JTextField textField;
	private boolean tocoEnter = false;
	private static Integer indexSala = -1;
	private JButton btnIngresar;
	private Salas miSala = this;
	private ObjectInputStream dis;
	private ObjectOutputStream dos;
	private Socket cliente;

	Font fuente = new Font("Calibri", Font.PLAIN, 16);

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
		btnIngresar = new JButton("Ingresar a Sala");
		JButton btnRefresh = new JButton("Refrescar salas");

		botones.add(btnCrear);
		btnCrear.setEnabled(false);
		btnIngresar.setEnabled(false);
		botones.add(btnIngresar);
		botones.add(btnRefresh);
		btnRefresh.setEnabled(false);

		botones.setPreferredSize(new Dimension(450, 25));
		botones.setMaximumSize(new Dimension(450, 25));

		dlm = new DefaultListModel();

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

		btnIngresar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (indexSala != -1) {
					enviarMsj(dosObject, "3");
					enviarMsj(dosObject, salas.get(indexSala).getSetPart().getNombreSala());
					String hayEspacio = (String) leerMsj(disObject);
					if (hayEspacio.equals("y") && !salas.get(indexSala).getSetPart().isInGame()) {
						DentroDeSala sala = new DentroDeSala(miSala);
						dispose();
						enviarMsj(dosObject, "13");
						sala.init(disObject, dosObject, salas.get(indexSala).getSetPart().getNombreSala());
						indexSala = -1;
					} else {
						if (salas.get(indexSala).getSetPart().isInGame())
							JOptionPane.showMessageDialog(null, "El juego ya ha iniciado en esta sala elija otra",
									"Juego Iniciado", JOptionPane.ERROR_MESSAGE);
						else
							JOptionPane.showMessageDialog(null,
									"La sala a la cual quiere ingresar esta llena eliga otra", "Sala llena",
									JOptionPane.ERROR_MESSAGE);
					}
				} else
					JOptionPane.showMessageDialog(null, "Debe seleccionar una sala", "Error sala",
							JOptionPane.ERROR_MESSAGE);
			}
		});

		btnRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				enviarMsj(dosObject, "11");
				refresh();
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
				}
			}
		});

		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (textField.getText().equals("") || textField.getText().equals("Ingrese Nickname"))
						JOptionPane.showMessageDialog(null, "Debe ingresar un nickName para continuar",
								"Ingreso nickName", JOptionPane.ERROR_MESSAGE);
					else {
						if (textField.getText().length() > 10)
							JOptionPane.showMessageDialog(null, "El nick ingresado debe ser menor a 10 caracteres",
									"Ingreso nickName", JOptionPane.ERROR_MESSAGE);
						else {
							enviarMsj(dosObject, textField.getText());
							String nom = (String) leerMsj(disObject);
							if (nom.equals("Repetido"))
								JOptionPane.showMessageDialog(null, "El nick ingresado ya se encuentra en uso",
										"Ingreso nickName", JOptionPane.ERROR_MESSAGE);
							else {
								tocoEnter = true;
								btnCrear.setEnabled(true);
								list.setEnabled(true);
								if (salas.size() > 0)
									btnIngresar.setEnabled(true);
								btnRefresh.setEnabled(true);
								textField.setEnabled(false);
							}
						}
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

	public void refresh() {
		salas = (ArrayList<SalaSerealizable>) leerMsj(dis);
		dlm.clear();
		for (SalaSerealizable item : salas) {
			dlm.addElement(item);
		}
		if (salas.size() == 0)
			btnIngresar.setEnabled(false);
		else
			btnIngresar.setEnabled(true);
	}

	public Salas() {
		contentPane = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBounds(200, 117, 1366, 768);
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
