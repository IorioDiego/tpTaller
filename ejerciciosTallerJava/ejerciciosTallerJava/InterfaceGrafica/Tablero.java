package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import cartas.Baron;
import cartas.Carta;
import cartas.Condesa;
import cartas.Guardia;
import cartas.Mucama;
import cartas.Princesa;
import cartas.Principe;
import cartas.Rey;
import cartas.Sacerdote;
import estados.Estado;
import game.Jugador;
import game.Partida;

public class Tablero extends JFrame {

	private ArrayList<Jugador> jugadores = new ArrayList<>();

	// descarte
	private ArrayList<ArrayList<DibujoCarta>> descartes = new ArrayList<ArrayList<DibujoCarta>>();
	private Map<String, Integer> indexDes = new HashMap<String, Integer>();
	private Map<String, Integer> distdDes = new HashMap<String, Integer>();
	private Map<String, Integer[]> posiciones = new HashMap<String, Integer[]>();
	private ArrayList<Carta> mano = new ArrayList<>();

	// hilos
	private HiloEscuchaTablero hiloTablero;
	private Integer espera = 0;

	private boolean levantarCarta = false;
	private boolean jugarCarta = false;
	private boolean miTurno = false;
	private boolean seJugoGuardia = false;
	private String ganadorRonda;
	private boolean reinicioRonda = false;
	private boolean finalizarPartida = false;
	private int nroRonda = 0;
	private String nombreJActivo;

	ObjectInputStream in;
	ObjectOutputStream out;

	// FRAMES-DIALOGS-BUTTONS-PANELS
	private JPanel contentPane;
	private DrawPanel drawPanel;
	private Jugador jugadorActivo;
	private Salas sala;
	JPanel predefined = new JPanel();
	private JDialog lista;
	private JButton j1;
	private JButton j2;
	private JButton j3;
	private JButton j4;

	private JDialog listaCartas;
	private JButton c2;
	private JButton c3;
	private JButton c4;
	private JButton c5;
	private JButton c6;
	private JButton c7;
	private JButton c8;

	// BARON
	private String jugadorBaron;
	private String jugadorBaronOp;
	private Carta cartaBaron;
	private Carta cartaBaronOp;
	// GUARDIA
	private String jugadorGuarida;
	private Carta cartaElegidaGuardia;
	private String jugadorGuaridaOp;
	private boolean acertoGuardia = false;
	// SACERDOTE
	private String jugadorElegSacer;
	private Carta cartaOpSacer;
	private boolean dibManoOp = false;

	// PARTIDA
	private Partida partida;

	// GENERALES
	private Sound sonidoFondo;
	private Sound sonidoTirarCarta;
	private int cantJugadores;// ---------->SE PUEDE SACAR
	private int anchoCarta = 100;
	private int largoCarta = 140;
	private float volumen = (float) 0.7;

	// imagenes
	private BufferedImage guardia;
	private BufferedImage princesa;
	private BufferedImage principe;
	private BufferedImage rey;
	private BufferedImage condesa;
	private BufferedImage mucama;
	private BufferedImage sacerdote;
	private BufferedImage baron;
	private BufferedImage cartaAmor;
	private BufferedImage fondoVerCarta;
	private ImageIcon exit;
	private ImageIcon banner;
	private BufferedImage fondoPlayerName;
	private BufferedImage background;
	private BufferedImage dorso;

	private boolean tomoCarta = false;
	private boolean finalizar = false;
	private boolean compararManos = false;
	private boolean cambiarJugador = false;
	private boolean finPartida = false;

	// private ImageIcon descriprueca = new ImageIcon("loveImg/banner.jpg");

	public void bloquearBoton() {
		enviarMsj(out, "6");

		for (int i = 0; i < partida.getJugadores().size(); i++) {
			Estado estado = (Estado) leerMsj(in);
			partida.getJugadores().get(i).setEstado(estado);
		}

		for (int i = 0; i < partida.getJugadores().size(); i++) {
			if (jugadores.get(i).isBlockedOrDelete()) {

				switch (i) {
				case 0:
					j1.setEnabled(false);
					break;
				case 1:
					j2.setEnabled(false);
					break;
				case 2:
					j3.setEnabled(false);
					break;
				case 3:
					j4.setEnabled(false);
					break;

				default:
					break;
				}
			}

		}

	}

	public void desbloquearBoton() {
		enviarMsj(out, "6");
		for (int i = 0; i < partida.getJugadores().size(); i++) {
			Estado estado = (Estado) leerMsj(in);
			partida.getJugadores().get(i).setEstado(estado);
		}

		for (int i = 0; i < jugadores.size(); i++) {
			if (!jugadores.get(i).isBlockedOrDelete()) {
				switch (i) {
				case 0:
					j1.setEnabled(true);
					break;
				case 1:
					j2.setEnabled(true);
					break;
				case 2:
					j3.setEnabled(true);
					break;
				case 3:
					j4.setEnabled(true);
					break;

				default:
					break;
				}
			}

		}
	}

	public void rendirse() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				confirmarSalida();
			}

		});
	}

	public void confirmarSalida() {
		int valor = JOptionPane.showConfirmDialog(this, "Desea rendirse", "Cerrar", JOptionPane.WARNING_MESSAGE);
		if (valor == JOptionPane.YES_OPTION) {
			if (miTurno && !levantarCarta && !jugarCarta) {
				if (partida.getJugadores().size() > 2) {
					enviarMsj(out, "9");
					enviarMsj(out, "5");
				}
				this.dispose();
				sala.setVisible(true);
				enviarMsj(out, "2");
				synchronized (espera) {
					espera.notify();
				}
			} else {
				if (levantarCarta && !jugarCarta)
					JOptionPane.showMessageDialog(this, "Debe terminar de jugar antes de rendirse");
				if (!miTurno)
					JOptionPane.showMessageDialog(this, "Debe esperar su turno antes de rendirse");
			}

		}
	}

	public void tocarCartaIzquierda(MouseEvent m, ObjectInputStream entrada, ObjectOutputStream salida, JDialog lista,
			JDialog listaCartas) {
		Carta miCarta = mano.get(0);
		bloquearBoton();
		desbloquearBoton();
		enviarMsj(salida, "4");
		enviarMsj(salida, mano.get(0));
		enviarMsj(salida, 0);
		mano.remove(0);

		if (miCarta.equals(new Guardia()) || miCarta.equals(new Sacerdote()) || miCarta.equals(new Baron())
				|| miCarta.equals(new Rey()) || miCarta.equals(new Principe())) {
			lista.setVisible(true);

			if (miCarta.equals(new Guardia())) {
				listaCartas.setVisible(true);

			}
		}
		efectoCartas(miCarta);
		sonidoTirarCarta.play();
		jugarCarta = true;
		String reiniRonda = (String) leerMsj(entrada);
		String finPartida = (String) leerMsj(entrada);
		if (!reiniRonda.equals("finDeRonda") && !finPartida.equals("finPartida"))
			enviarMsj(salida, "5");
		else if (reiniRonda.equals("finDeRonda"))
			reiniciarRonda();
		else if (finPartida.equals("finPartida"))
			finDePartida();
		refresh();
	}

	public void tocarCartaDerecha(MouseEvent m, ObjectInputStream entrada, ObjectOutputStream salida, JDialog lista,
			JDialog listaCartas) {
		Carta miCarta = mano.get(1);
		desbloquearBoton();
		bloquearBoton();
		enviarMsj(salida, "4");
		enviarMsj(salida, mano.get(1));
		enviarMsj(salida, 1);
		mano.remove(1);

		if (miCarta.equals(new Guardia()) || miCarta.equals(new Sacerdote()) || miCarta.equals(new Baron())
				|| miCarta.equals(new Rey()) || miCarta.equals(new Principe())) {

			lista.setVisible(true);

			if (miCarta.equals(new Guardia())) {
				listaCartas.setVisible(true);

			}
		}
		efectoCartas(miCarta);
		sonidoTirarCarta.play();
		jugarCarta = true;
		String reiniRonda = (String) leerMsj(entrada);
		String finPartida = (String) leerMsj(entrada);
		if (!reiniRonda.equals("finDeRonda") && !finPartida.equals("finPartida"))
			enviarMsj(salida, "5");
		else if (reiniRonda.equals("finDeRonda"))
			reiniciarRonda();
		else if (finPartida.equals("finPartida"))
			finDePartida();
		refresh();

	}

	public void tomarCartaMazo(MouseEvent m, ObjectInputStream entrada, ObjectOutputStream salida) {

		sonidoTirarCarta.play();

		enviarMsj(salida, "3");

		Carta cTomada = (Carta) leerMsj(entrada);
		String reiniRonda = (String) leerMsj(entrada);
		String finPartida = (String) leerMsj(entrada);
		Carta cDescartada = null;
		boolean condesa = false;
		if (cTomada != null) {
			mano.add(cTomada);
		}

		if ((new Condesa()).equals(cTomada) && (mano.contains(new Principe()) || mano.contains(new Rey()))) {
			cDescartada = mano.remove(1);
			condesa = true;
		} else if (mano.contains(new Condesa()) && ((new Principe()).equals(cTomada) || (new Rey()).equals(cTomada))) {
			cDescartada = mano.remove(0);
			condesa = true;
		}

		if (condesa) {
			enviarMsj(salida, "7");
			enviarMsj(salida, cDescartada);
			pintarCarta(cDescartada, nombreJActivo);
			enviarMsj(salida, "5");
			jugarCarta = true;
		}

		if (reiniRonda.equals("finDeRonda")) {
			jugarCarta = true;
			reiniciarRonda();
		} else if (finPartida.equals("finPartida")) {
			finDePartida();
			jugarCarta = true;
		}
		levantarCarta = true;
		refresh();
	}

	public void init(ArrayList<Jugador> jugadores, Partida partida, Salas salaPrincipal, ObjectInputStream entrada,
			ObjectOutputStream salida) {
		try {
			background = ImageIO.read(new File("loveImg/rombos.jpg"));
			dorso = ImageIO.read(new File("loveImg/dorso.jpg"));
			cartaAmor = ImageIO.read(new File("loveImg/IconoCarta.png"));
			guardia = ImageIO.read(new File("cartasImg/guardia.jpg"));
			princesa = ImageIO.read(new File("cartasImg/princesa.jpg"));
			principe = ImageIO.read(new File("cartasImg/principe.jpg"));
			rey = ImageIO.read(new File("cartasImg/rey.jpg"));
			mucama = ImageIO.read(new File("cartasImg/mucama.jpg"));
			baron = ImageIO.read(new File("cartasImg/baron.jpg"));
			condesa = ImageIO.read(new File("cartasImg/condesa.jpg"));
			sacerdote = ImageIO.read(new File("cartasImg/sacerdote.jpg"));
			fondoVerCarta = ImageIO.read(new File("loveImg/fondoVerCartaOp.jpeg"));
			sonidoTirarCarta = new Sound("sounds/tirarCarta.wav");
			sonidoFondo = new Sound("sounds/music.wav");
			banner = new ImageIcon("loveImg/banner.jpg");
			exit = new ImageIcon("loveImg/exit.png");
			fondoPlayerName = ImageIO.read(new File("loveImg/fondoNombreJug.png"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		in = entrada;
		out = salida;
		HiloEscuchaTablero hiloTablero = new HiloEscuchaTablero(entrada, this, espera);
		recibirCartas();
		construirDescarte();

		sonidoFondo.setVolume(volumen);
//		sonidoFondo.play();
//		sonidoFondo.loopear();
		this.sala = salaPrincipal;
		this.jugadores = jugadores;
		cantJugadores = jugadores.size();
		this.partida = partida;

		drawPanel = new DrawPanel();
		getContentPane().add(drawPanel);

		for (Jugador jugador : jugadores) {
			if (jugador.getNombre().equals(partida.getjInicial()))
				turnoJugador(jugador);
		}

		lista = new JDialog(this, "Lista Jugadores", true);

		JPanel predefined = new JPanel();
		predefined.setLayout(new GridLayout(4, 1));
		predefined.setBorder(new TitledBorder("Jugadores"));
		predefined.setBackground(Color.decode("#f7db97"));

		JPanel imagen = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(banner.getImage(), 0, 0, getWidth(), getHeight(), this);
			}

			@Override
			public Dimension getPreferredSize() {
				Dimension size = super.getPreferredSize();
				size.width = Math.max(banner.getIconWidth(), size.width);
				size.width = Math.max(banner.getIconHeight(), size.height);
				return size;
			}
		};

		j1 = new JButton(jugadores.get(0).getNombre());
		j2 = new JButton(jugadores.get(1).getNombre());
		j1.setBackground(Color.decode("#f7db97"));
		j2.setBackground(Color.decode("#f7db97"));
		predefined.add(j1);
		predefined.add(j2);

		if (cantJugadores >= 3) {
			j3 = new JButton(jugadores.get(2).getNombre());
			j3.setBackground(Color.decode("#f7db97"));
			predefined.add(j3);
			if (cantJugadores == 4) {
				j4 = new JButton(jugadores.get(3).getNombre());
				j4.setBackground(Color.decode("#f7db97"));
				predefined.add(j4);
			}

		}

		JPanel salir = new JPanel();
		salir.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		salir.setBackground(Color.decode("#f7db97"));

		lista.getContentPane().setBackground(Color.decode("#f7db97"));
		lista.getContentPane().add(imagen);
		lista.getContentPane().setLayout(new BoxLayout(lista.getContentPane(), BoxLayout.Y_AXIS));
		lista.getContentPane().add(predefined);
		lista.getContentPane().add(salir);
		lista.setSize(250, 400);
		lista.setLocationRelativeTo(null);
		lista.setUndecorated(true);

		j1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarMsj(salida, 0);
				lista.setVisible(false);
			}
		});

		j2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarMsj(salida, 1);
				lista.setVisible(false);
			}
		});

		if (cantJugadores >= 3) {
			j3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					enviarMsj(salida, 2);
					lista.setVisible(false);
				}
			});

		}

		if (cantJugadores == 4) {
			j4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					enviarMsj(salida, 3);
					lista.setVisible(false);

				}
			});
		}

		listaCartas = new JDialog(this, "Lista Cartas", true);

		JPanel cards = new JPanel();
		cards.setLayout(new GridLayout(7, 1));
		cards.setBorder(new TitledBorder("Cartas"));
		cards.setBackground(Color.decode("#f7db97"));

		JPanel imagen2 = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(banner.getImage(), 0, 0, getWidth(), getHeight(), this);
			}

			@Override
			public Dimension getPreferredSize() {
				Dimension size = super.getPreferredSize();
				size.width = Math.max(banner.getIconWidth(), size.width);
				size.width = Math.max(banner.getIconHeight(), size.height);

				return size;
			}
		};

		c2 = new JButton(new Sacerdote().getNombre());
		listaCartas.getContentPane().add(c2);

		c3 = new JButton(new Baron().getNombre());
		listaCartas.getContentPane().add(c3);

		c4 = new JButton(new Mucama().getNombre());
		listaCartas.getContentPane().add(c4);

		c5 = new JButton(new Principe().getNombre());
		listaCartas.getContentPane().add(c5);

		c6 = new JButton(new Rey().getNombre());
		listaCartas.getContentPane().add(c6);

		c7 = new JButton(new Condesa().getNombre());
		listaCartas.getContentPane().add(c7);

		c8 = new JButton(new Princesa().getNombre());
		listaCartas.getContentPane().add(c8);

		c2.setBackground(Color.decode("#f7db97"));
		c3.setBackground(Color.decode("#f7db97"));
		c4.setBackground(Color.decode("#f7db97"));
		c5.setBackground(Color.decode("#f7db97"));
		c6.setBackground(Color.decode("#f7db97"));
		c7.setBackground(Color.decode("#f7db97"));
		c8.setBackground(Color.decode("#f7db97"));
		cards.add(c2);
		cards.add(c3);
		cards.add(c4);
		cards.add(c5);
		cards.add(c6);
		cards.add(c7);
		cards.add(c8);

		JPanel salirC = new JPanel();
		salirC.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		salirC.setBackground(Color.decode("#f7db97"));

		listaCartas.getContentPane().setBackground(Color.decode("#f7db97"));
		listaCartas.getContentPane().add(imagen2);
		listaCartas.getContentPane().setLayout(new BoxLayout(listaCartas.getContentPane(), BoxLayout.Y_AXIS));
		listaCartas.getContentPane().add(cards);
		listaCartas.getContentPane().add(salirC);
		listaCartas.setSize(250, 500);

		listaCartas.setLocationRelativeTo(null);
		listaCartas.setUndecorated(true);

		c2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarMsj(salida, 0);
				listaCartas.setVisible(false);
			}
		});

		c3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarMsj(salida, 1);
				listaCartas.setVisible(false);
			}
		});

		c4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarMsj(salida, 2);
				listaCartas.setVisible(false);
			}
		});

		c5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarMsj(salida, 3);
				listaCartas.setVisible(false);
			}
		});

		c6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarMsj(salida, 4);
				listaCartas.setVisible(false);
			}
		});

		c7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarMsj(salida, 5);
				listaCartas.setVisible(false);
			}
		});

		c8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarMsj(salida, 6);
				listaCartas.setVisible(false);
			}
		});

		pack();
		setTitle("LoveLetter");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		requestFocusInWindow();
		rendirse();

		setResizable(false);
		setBounds(80, 36, 1366, 768);

		hiloTablero.start();

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_S && volumen >= 0 && volumen < 1) { /// Subir volumen
					volumen += 0.1;
					sonidoFondo.setVolume(volumen);
				}
				if (e.getKeyCode() == KeyEvent.VK_B && volumen > 0.1 && volumen <= 1) { /// Bajar volumen
					volumen -= 0.1;
					sonidoFondo.setVolume(volumen);
				}

			}

		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent m) {

				if (!finPartida) {
					if ((m.getX() >= 575 && m.getX() <= 675 && (m.getY() >= 615 && m.getY() <= 755)
							&& mano.size() >= 1)) {
						if (m.getButton() == MouseEvent.BUTTON3)
							mostrarLista(0);
						else if (miTurno && mano.size() == 2)
							tocarCartaIzquierda(m, entrada, salida, lista, listaCartas);

					} else if ((m.getX() >= 200 && m.getX() <= 510)
							&& (m.getY() >= 210 && m.getY() <= 470 && mano.size() == 1)) {
						if (miTurno) {
							tomarCartaMazo(m, entrada, salida);

						}

					} else if ((m.getX() >= 695 && m.getX() <= 795 && (m.getY() >= 615 && m.getY() <= 755))
							&& mano.size() == 2) {
						if (m.getButton() == MouseEvent.BUTTON3)
							mostrarLista(1);
						else if (miTurno)
							tocarCartaDerecha(m, entrada, salida, lista, listaCartas);
					}

				} else {
					sala.setVisible(true);
					dispose();
				}
				refresh();
				if (jugarCarta && levantarCarta) {
					if (!finPartida)
						miTurno = false;
					jugarCarta = false;
					levantarCarta = false;
					synchronized (espera) {
						espera.notify();
					}
				}
			}
		});

	}

	private class DrawPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;
			Dimension currentDimension = getContentPane().getSize();
			g2.scale(currentDimension.getWidth() / 1358, currentDimension.getHeight() / 768);
			g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);
			for (int i = 0; i < 5; i++) {

				g2.drawImage(dorso, 280 + i * 2, 285 + i * 3, 100, 140, this);
				g2.setColor(Color.WHITE);

				g2.drawRect(280 + i * 2, 285 + i * 3, 100, 140);
			}

			Font seagram = null;
			try {
				seagram = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Seagram tfb.ttf")).deriveFont(18f);
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Seagram tfb.ttf")));

			} catch (IOException | FontFormatException e) {

			}
			g2.setFont(seagram);
			g2.setPaint(Color.decode("#653b33"));
			g2.drawString("Ronda: " + nroRonda, 1234, 39);

			dibujarCartas(g2, mano.get(0).getNombre(), 575, 615);
			if (mano.size() > 1) {
				dibujarCartas(g2, mano.get(1).getNombre(), 695, 615);
			}

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setFont(seagram);
			g2.setPaint(Color.decode("#653b33"));
			g2.drawImage(cartaAmor, 1283, 698, 60, 39, this);
			g2.drawString(nombreJActivo, 1020, 741);
			String muestroAfectos = "Afectos:"
					+ String.valueOf(partida.getJugadores().get(indexDes.get(nombreJActivo)).getAfectosConseguidos());
			g2.drawString(muestroAfectos, 1185, 741);

			if (miTurno) {
				g2.drawString("[Tu Turno]", 1080, 741);
			} else {
				g2.drawString("[Esperando]", 1080, 741);
			}

			for (int i = 0; i < partida.getJugadores().size(); i++) {
				String nombre = partida.getJugadores().get(i).getNombre();
				muestroAfectos = "Afectos:"
						+ String.valueOf(partida.getJugadores().get(indexDes.get(nombre)).getAfectosConseguidos());
				int idx = indexDes.get(nombre);
				for (DibujoCarta dib : descartes.get(idx)) {
					dibujarCartas(g2, dib.getCartaDib().getNombre(), dib.getEjeX(), dib.getEjeY());
				}

				if (!nombreJActivo.equals(nombre)) {
					Integer[] pos = posiciones.get(nombre);
					g2.drawImage(fondoPlayerName, pos[0], pos[1] - 50, 190, 60, this);
					g2.drawString(nombre + " " + muestroAfectos, pos[0] + 20, pos[1] - 16);
				}
			}

			if (dibManoOp) {
				g2.setFont(new Font("Segoe Script", Font.HANGING_BASELINE, 30));
				g2.setPaint(Color.WHITE);
				g2.drawImage(fondoVerCarta, 400, 150, 555, 380, this);
				g2.drawString(jugadorElegSacer, 620, 220);
				dibujarCartas(g2, cartaOpSacer.getNombre(), 630, 280);
				dibManoOp = false;
			}
			
			if (compararManos) {

				g2.drawImage(fondoVerCarta, 400, 150, 555, 380, this);
				g2.setFont(new Font("Segoe Script", Font.HANGING_BASELINE, 30));
				g2.setPaint(Color.WHITE);

				g2.drawString(jugadorBaron, 500, 220);
				dibujarCartas(g2, cartaBaron.getNombre(), 500, 280);

				g2.setFont(new Font("Segoe Script", Font.HANGING_BASELINE, 30));
				g2.setPaint(Color.WHITE);

				g2.drawString(jugadorBaronOp, 750, 220);
				dibujarCartas(g2, cartaBaronOp.getNombre(), 750, 280);
				compararManos = false;
			}

			if (seJugoGuardia) {

				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(new Font("Segoe Script", Font.HANGING_BASELINE, 20));
				g2.setPaint(Color.WHITE);
				g2.drawImage(fondoVerCarta, 400, 150, 555, 380, this);

				g2.drawString(jugadorGuarida + " dice que " + jugadorGuaridaOp + " tiene", 560, 240);
				dibujarCartas(g2, cartaElegidaGuardia.getNombre(), 630, 280);
				if (acertoGuardia) {
					g2.drawString("ACERTO", 615, 460);
					acertoGuardia = false;
				} else
					g2.drawString("NO ACERTO", 615, 460);
				seJugoGuardia = false;
			}

			if (reinicioRonda) {
				reinicioRonda = false;
				g2.setFont(new Font("Segoe Script", Font.HANGING_BASELINE, 30));
				g2.setPaint(Color.WHITE);
				g2.drawImage(fondoVerCarta, 500, 50, 350, 200, this);
				g2.drawString("Fin de ronda", 570, 100);
				g2.drawString("dieguin", 540, 170);
				g2.drawImage(cartaAmor, 680, 140, 50, 35, this);
				g2.drawString("+  1", 750, 170);

			}

		
			if (finPartida) {

				g2.setFont(new Font("Segoe Script", Font.HANGING_BASELINE, 30));
				g2.setPaint(Color.WHITE);
				g2.drawImage(fondoVerCarta, 500, 50, 350, 200, this);
				g2.drawString("Partida finalizada", 515, 140);
				g2.drawString(" Ganador " + "Dieguin", 517, 180);
			}
			
			

		}

		public void dibujarCartas(Graphics g2, String carta, int x, int y) {
			switch (carta) {
			case "Princesa":
				g2.drawImage(princesa, x, y, anchoCarta, largoCarta, this);
				break;
			case "Condesa":
				g2.drawImage(condesa, x, y, anchoCarta, largoCarta, this);
				break;
			case "Principe":
				g2.drawImage(principe, x, y, anchoCarta, largoCarta, this);
				break;
			case "Mucama":
				g2.drawImage(mucama, x, y, anchoCarta, largoCarta, this);
				break;
			case "Baron":
				g2.drawImage(baron, x, y, anchoCarta, largoCarta, this);
				break;
			case "Sacerdote":
				g2.drawImage(sacerdote, x, y, anchoCarta, largoCarta, this);
				break;
			case "Guardia":
				g2.drawImage(guardia, x, y, anchoCarta, largoCarta, this);
				break;
			case "Rey":
				g2.drawImage(rey, x, y, anchoCarta, largoCarta, this);
				break;
			}

		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(1366, 768);

		}

	}

	public void finDePartida() {
		miTurno = true;
		finPartida = true;
		ganadorRonda = (String) leerMsj(in);
		enviarMsj(out, "2");
	}

	public void reiniciarRonda() {
		nroRonda++;
		ganadorRonda = (String) leerMsj(in);
		reinicioRonda = true;
		mano.clear();
		indexDes.clear();
		distdDes.clear();
		posiciones.clear();
		descartes.clear();
		recibirCartas();
		construirDescarte();

	}

	public static void cambioJugador() {
		JOptionPane.showMessageDialog(null, "hola diegui");
	}

	public void refresh() {
		drawPanel.repaint();
	}

	@Override
	public void update(Graphics g) {

		paint(g);
	}

	public Tablero(Partida partida) {

		this.partida = partida;
		contentPane = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBounds(200, 117, 1366, 768);
		setContentPane(contentPane);

	}

	public void construirDescarte() {
		ArrayList<String> n = new ArrayList<>();
		for (int i = 0; i < this.partida.getJugadores().size(); i++) {
			String nombre = (partida.getJugadores().get(i).getNombre());
			n.add(nombre);
			this.descartes.add(new ArrayList<DibujoCarta>());
			indexDes.put(nombre, i);
			distdDes.put(nombre, 0);

		}

		int miIdx = n.indexOf(nombreJActivo);
		Integer[] pos = { 500, 450 };
		posiciones.put(n.get(miIdx), pos);
		n.remove(miIdx);

		if (n.size() > 1) {
			Integer[] pos2 = { 150, 50 };
			posiciones.put(n.get(0), pos2);

			Integer[] pos3 = { 450, 50 };
			posiciones.put(n.get(1), pos3);
			if (n.size() > 3) {
				Integer[] pos4 = { 750, 50 };
				posiciones.put(n.get(2), pos4);
			}
		} else {
			Integer[] pos2 = { 450, 50 };
			posiciones.put(n.get(0), pos2);
		}

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

	private void pintarCarta(Carta cJugada, String nombreJugo) {
		int idx = indexDes.get(nombreJugo);
		Integer[] pos = posiciones.get(nombreJugo);
		int x = pos[0];
		int y = pos[1];
		int nuevaDist = distdDes.get(nombreJugo) + 30;
		distdDes.replace(nombreJugo, nuevaDist);
		DibujoCarta dibujo = new DibujoCarta(cJugada, x + nuevaDist, y);
		descartes.get(idx).add(dibujo);
	}

	public void mostrarLista(int indexMano) {
		JDialog j = new JDialog();
		j.setUndecorated(true);
		j.setVisible(true);
		j.setBounds(480, 210, 600, 360);

		ImageIcon descrip = new ImageIcon(mano.get(indexMano).toString());

		JPanel cartaDescrip = new JPanel(new BorderLayout()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(descrip.getImage(), 0, 0, getWidth(), getHeight(), this);
			}

			@Override
			public Dimension getPreferredSize() {
				Dimension size = super.getPreferredSize();
				size.width = Math.max(descrip.getIconWidth(), size.width);
				size.width = Math.max(descrip.getIconHeight(), size.height);

				return size;
			}
		};

		Image image = exit.getImage();
		Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		exit = new ImageIcon(newimg);
		cartaDescrip.setLayout(new BorderLayout());
		int gap = -3;
		cartaDescrip.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));

		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		southPanel.setOpaque(false);
		JButton button = new JButton(exit);
		button.setPreferredSize(new Dimension(50, 50));

		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		southPanel.add(button);
		cartaDescrip.add(southPanel, BorderLayout.NORTH);

		button.addActionListener(e -> {
			j.dispose();
		});
		j.add(cartaDescrip);
	}

	public void efectoCartas(Carta miCarta) {
		Carta cartaOp = null;
		String nombreOp = null;
		boolean sw = false;
		boolean itsMeMario = false;

		switch (miCarta.getNombre()) {
		case "Princesa": {
			nombreOp = (String) leerMsj(in);
			cartaOp = (Carta) leerMsj(in);
			sw = true;
		}
			break;
		case "Condesa": {
		}
			break;
		case "Principe": {
			nombreOp = (String) leerMsj(in);
			cartaOp = (Carta) leerMsj(in);
			sw = true;
			if (nombreJActivo.equals(nombreOp)) {
				itsMeMario = true;
			}
			if (itsMeMario) {
				Carta nuevaCarta = (Carta) leerMsj(in);
				mano.add(nuevaCarta);
				mano.remove(0);

				itsMeMario = false;
			}

		}
			break;
		case "Mucama": {

		}
			break;
		case "Baron": {
			nombreOp = (String) leerMsj(in);
			cartaBaronOp = (Carta) leerMsj(in);
			cartaBaron = (Carta) leerMsj(in);
			jugadorBaron = nombreJActivo;
			jugadorBaronOp = nombreOp;
			compararManos = true;

			String msj = (String) leerMsj(in);
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
			jugadorElegSacer = (String) leerMsj(in);
			cartaOpSacer = (Carta) leerMsj(in);
			dibManoOp = true;
		}
			break;
		case "Guardia": {
			cartaElegidaGuardia = (Carta) leerMsj(in);
			nombreOp = (String) leerMsj(in);
			jugadorGuarida = nombreJActivo;
			jugadorGuaridaOp = nombreOp;
			seJugoGuardia = true;
			String msj = (String) leerMsj(in);
			if (msj.equals("Acierto")) {
				cartaOp = (Carta) leerMsj(in);
				acertoGuardia = true;
				sw = true;
				// Remover la carta?
			}
		}
			break;
		case "Rey": {
			nombreOp = (String) leerMsj(in);
			cartaOp = (Carta) leerMsj(in);
			if (nombreJActivo.equals(nombreOp)) {
				itsMeMario = true;
			}

			if (itsMeMario) {
				cartaOp = (Carta) leerMsj(in);
			}
			mano.remove(0);
			mano.add(cartaOp);
		}
			break;
		}

		pintarCarta(miCarta, nombreJActivo);
		if (sw) {
			pintarCarta(cartaOp, nombreOp);
		}
		sw = false;

	}

	public void eliminarJugadorTablero(String nickAbandono) {
		Jugador player = null;
		for (Jugador players : partida.getJugadores()) {
			if (nickAbandono.equals(players.getNombre()))
				player = players;
		}
		int index = partida.getJugadores().indexOf(player);
		partida.getJugadores().remove(player);
		switchNotVisible(index);

	}

	public void switchNotVisible(int index) {
		switch (index) {
		case 0:
			j1.setVisible(false);
			break;
		case 1:
			j2.setVisible(false);
			break;
		case 2:
			j3.setVisible(false);
			break;
		case 3:
			j4.setVisible(false);
			break;

		default:
			break;
		}
	}

	public void recibirCartas() {
		Carta c = (Carta) leerMsj(in);
		nombreJActivo = (String) leerMsj(in);
		mano.add(c);
		for (Jugador jugadores : partida.getJugadores()) {
			int afectos = (int) leerMsj(in);
			jugadores.setAfectosConseguidos(afectos);
		}
	}

	public void turnoJugador(Jugador jugador) {
		if (!compararManos && !dibManoOp)
			cambiarJugador = true;
		jugadorActivo = jugador;
	}

	public String getNombreJActivo() {
		return nombreJActivo;
	}

	public ArrayList<Carta> getMano() {
		return mano;
	}

	public ArrayList<ArrayList<DibujoCarta>> getDescartes() {
		return descartes;
	}

	public Map<String, Integer> getIndexDes() {
		return indexDes;
	}

	public void setCartaElegidaGuardia(Carta cartaElegidaGuardia) {
		this.cartaElegidaGuardia = cartaElegidaGuardia;
	}

	public void setIndexDes(Map<String, Integer> indexDes) {
		this.indexDes = indexDes;
	}

	public Map<String, Integer> getDistdDes() {
		return distdDes;
	}

	public Map<String, Integer[]> getPosiciones() {
		return posiciones;
	}

	public boolean isMiTurno() {
		return miTurno;
	}

	public void setMiTurno(boolean miTurno) {
		this.miTurno = miTurno;
	}

	public void setJugadorGuarida(String jugadorGuarida) {
		this.jugadorGuarida = jugadorGuarida;
	}

	public void setJugadorGuaridaOp(String jugadorGuaridaOp) {
		this.jugadorGuaridaOp = jugadorGuaridaOp;
	}

	public void setSeJugoGuardia(boolean seJugoGuardia) {
		this.seJugoGuardia = seJugoGuardia;
	}

	public void setJugadorBaron(String jugadorBaron) {
		this.jugadorBaron = jugadorBaron;
	}

	public void setJugadorBaronOp(String jugadorBaronOp) {
		this.jugadorBaronOp = jugadorBaronOp;
	}

	public void setCartaBaron(Carta cartaBaron) {
		this.cartaBaron = cartaBaron;
	}

	public void setCartaBaronOp(Carta cartaBaronOp) {
		this.cartaBaronOp = cartaBaronOp;
	}

	public int getNroRonda() {
		return nroRonda;
	}

	public void setNroRonda(int nroRonda) {
		this.nroRonda = nroRonda;
	}

	public void setReinicioRonda(boolean reinicioRonda) {
		this.reinicioRonda = reinicioRonda;
	}

	public void setGanadorRonda(String ganadorRonda) {
		this.ganadorRonda = ganadorRonda;
	}

	public void setAcertoGuardia(boolean acertoGuardia) {
		this.acertoGuardia = acertoGuardia;
	}

	public void setCompararManos(boolean compararManos) {
		this.compararManos = compararManos;
	}

}
