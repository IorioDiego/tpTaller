package InterfaceGrafica;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import servidor.SettingsPartida;

public class InterfazCrearSala2 extends JFrame {

	private JPanel contentPane;
	private SettingsPartida setPartida = new SettingsPartida();
	private boolean seteoCantJuga = false;
	private boolean seteoPrendas = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazCrearSala2 frame = new InterfazCrearSala2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfazCrearSala2() {
		setResizable(false);
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(InterfazCrearSala.class.getResource("/loveImg/IconoCarta.png")));
		setTitle("Configuracion sala");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(450, 250, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// init();
	}

	public void init(ObjectInputStream disObject, ObjectOutputStream dosObject) {

		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		// PANELES
		JPanel panel0 = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		// LABELS

		JTextField nombreSala = new JTextField("Ingrese nombre de sala");
		nombreSala.setBackground(Color.WHITE);
		nombreSala.setFont(new Font("Tahoma", Font.BOLD, 13));
		this.add(nombreSala);

		nombreSala.setHorizontalAlignment(JTextField.CENTER);
		nombreSala.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				nombreSala.setText("");
			}
		});

		JLabel title1 = new JLabel("Configuraciones Basicas de Sala:");
		title1.setBackground(Color.WHITE);
		title1.setFont(new Font("Tahoma", Font.BOLD, 13));

		JLabel title3 = new JLabel("Cantidad Jugadores:");
		title3.setBackground(Color.WHITE);
		title3.setFont(new Font("Tahoma", Font.BOLD, 13));

		JLabel title2 = new JLabel("Cantidad prendas de Amor:");
		title2.setBackground(Color.LIGHT_GRAY);
		title2.setFont(new Font("Tahoma", Font.BOLD, 13));
		// BOTON
		JButton botonCrear = new JButton("Crear sala");
		botonCrear.setBackground(Color.LIGHT_GRAY);

		// COMBOBOX
		JComboBox cantPrendasAmor = new JComboBox();
		cantPrendasAmor.setBackground(Color.LIGHT_GRAY);
		cantPrendasAmor
				.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
		JComboBox cantJugadores = new JComboBox();
		cantJugadores.setBackground(Color.LIGHT_GRAY);
		cantJugadores.setModel(new DefaultComboBoxModel(new String[] { "2", "3", "4" }));

		botonCrear.setEnabled(false);
		cantPrendasAmor.setEnabled(false);
		cantJugadores.setEnabled(false);

		nombreSala.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String nombSala = nombreSala.getText();
					setPartida.setNombreSala(nombSala);
					cantPrendasAmor.setEnabled(true);
					cantJugadores.setEnabled(true);
					nombreSala.setEnabled(false);
					botonCrear.setEnabled(true);
				}
			}
		});

		cantJugadores.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectValue = (String) cantJugadores.getSelectedItem();
				setPartida.setCantJugadores(Integer.valueOf(selectValue));
				seteoCantJuga = true;
			}
		});

		cantPrendasAmor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectValue = (String) cantJugadores.getSelectedItem();
				setPartida.setPrendasAmor(Integer.valueOf(selectValue));
				seteoPrendas = true;
			}
		});

		botonCrear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (seteoCantJuga && seteoPrendas)
						{	
							dosObject.writeObject(setPartida);
						}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});

		// FORMATO
		cantPrendasAmor.setPreferredSize(new Dimension(160, 25));
		cantPrendasAmor.setMaximumSize(new Dimension(160, 25));
		cantJugadores.setPreferredSize(new Dimension(160, 25));
		cantJugadores.setMaximumSize(new Dimension(160, 25));

		panel1.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		panel2.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

		panel0.add(title1);
		panel1.add(title2);
		panel1.add(Box.createHorizontalStrut(30));
		panel1.add(cantPrendasAmor);
		panel2.add(title3);
		panel2.add(Box.createHorizontalStrut(72));
		panel2.add(cantJugadores);
		panel3.add(botonCrear);

		getContentPane().add(panel0);
		getContentPane().add(panel1);
		getContentPane().add(panel2);
		getContentPane().add(panel3);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
