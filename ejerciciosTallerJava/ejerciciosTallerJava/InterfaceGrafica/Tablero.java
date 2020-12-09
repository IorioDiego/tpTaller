package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import cartas.*;
import game.Jugador;
import game.Partida;
import servidor.Paquete;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Frame;
import java.awt.GradientPaint;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;

public class Tablero extends JFrame {

	private ArrayList<Jugador> jugadores = new ArrayList<>();

	private ArrayList<DibujoCarta> dibujos = new ArrayList<>();

	private ArrayList<ArrayList<DibujoCarta>> descartes = new ArrayList<ArrayList<DibujoCarta>>();
	private Map<String, Integer> indexDes = new HashMap<String, Integer>();
	private Map<String, Integer> distdDes = new HashMap<String, Integer>();
	private Map<String, Integer[]> posiciones = new HashMap<String, Integer[]>();
	private ArrayList<Carta> mano = new ArrayList<>();

	private HiloEscuchaTablero hiloTablero;
	private Integer espera = 0;

	private boolean levantarCarta = false;
	private boolean jugarCarta = false;
	private boolean miTurno = false;

//	private Map<String, ArrayList<DibujoCarta>> descarteTodos = new HashMap<String, ArrayList<DibujoCarta>>();

	private String nombreJActivo;

	private Tablero t = this;

	int indexJ1;
	int idnexJ2;
	int indexJ3;
	int myIndex;

	ObjectInputStream in;
	ObjectOutputStream out;

	private JPanel contentPane;
	private Tablero miTablero = this;
	private BufferedImage dorso;
	private DrawPanel drawPanel;
	private Jugador jugadorActivo;
	private Salas sala;
	// BARON
	private String jugadorBaron;

	public String getJugadorBaron() {
		return jugadorBaron;
	}

	public void setJugadorBaron(String jugadorBaron) {
		this.jugadorBaron = jugadorBaron;
	}

	public String getJugadorBaronOp() {
		return jugadorBaronOp;
	}

	public void setJugadorBaronOp(String jugadorBaronOp) {
		this.jugadorBaronOp = jugadorBaronOp;
	}

	public Carta getCartaBaron() {
		return cartaBaron;
	}

	public void setCartaBaron(Carta cartaBaron) {
		this.cartaBaron = cartaBaron;
	}

	public Carta getCartaBaronOp() {
		return cartaBaronOp;
	}

	public void setCartaBaronOp(Carta cartaBaronOp) {
		this.cartaBaronOp = cartaBaronOp;
	}

	private String jugadorBaronOp;
	private Carta cartaBaron;
	private Carta cartaBaronOp;

	private int distDescarte = 0;
	private Partida partida;
	private boolean dibManoOp = false;
	private Sound sonidoFondo;
	private Sound sonidoTirarCarta;
	private ImageIcon banner;
	private BufferedImage background;
	private int CantJugadores;
	private int anchoCarta = 100;
	private int largoCarta = 140;
	private float volumen = (float) 0.7;

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

	private boolean tomoCarta = false;
	private boolean finalizar = false;
	private boolean compararManos = false;
	private boolean cambiarJugador = false;

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
	private JButton volver;
	private JButton volverC;

	private static int jugadorElegido;
	private static int cartaElegida;

	// private ImageIcon descriprueca = new ImageIcon("loveImg/banner.jpg");

	public void bloquearBoton() {
//comando, y pedir el array de jugadores
		enviarMsj(out, "6");
		partida = (Partida) leerMsj(in);
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

	public void tocarCartaIzquierda(MouseEvent m, ObjectInputStream entrada, ObjectOutputStream salida, JDialog lista,
			JDialog listaCartas) {
		Carta cartaOp = null;
		String nombreOp = null;
		Carta cartaPerdedor = null;
		boolean sw = false;
		bloquearBoton();
		Tablero.enviarMsj(salida, "4");
		Tablero.enviarMsj(salida, mano.get(0));
		Tablero.enviarMsj(salida, 0);

//		int idx = indexDes.get(nombreJActivo);
//		Integer[] pos = posiciones.get(nombreJActivo);
//		int x = pos[0];
//		int y = pos[1];
//		int nuevaDist = distdDes.get(nombreJActivo) + 30;
//		distdDes.replace(nombreJActivo, nuevaDist);
//		DibujoCarta dibujo = new DibujoCarta(mano.get(0), x + nuevaDist, y);
//		descartes.get(idx).add(dibujo);

		if (mano.get(0).equals(new Guardia()) || mano.get(0).equals(new Sacerdote()) || mano.get(0).equals(new Baron())
				|| mano.get(0).equals(new Rey()) || mano.get(0).equals(new Principe())) {
			lista.setVisible(true);
			
			if (mano.get(0).equals(new Guardia())) {
				listaCartas.setVisible(true);

			}
		}

		boolean itsMeMario = false;

		switch (mano.get(0).getNombre()) {
		case "Princesa": {
		}

			break;
		case "Condesa": {
		}
			break;
		case "Principe": {
			nombreOp = (String) leerMsj(entrada);
			cartaOp = (Carta) Tablero.leerMsj(entrada);
			sw = true;
			if (getNombreJActivo().equals(nombreOp)) {
				itsMeMario = true;
			}
			
			if (itsMeMario) {
				Carta nuevaCarta = (Carta) Tablero.leerMsj(entrada);
				getMano().add(nuevaCarta);
				getMano().remove(0);
				
				itsMeMario = false;
			}

		}
			break;
		case "Mucama": {
			
			
		}
			break;
		case "Baron": {
			nombreOp = (String) leerMsj(entrada);
			cartaBaronOp = (Carta) leerMsj(entrada);
			cartaBaron = (Carta) leerMsj(entrada);
			jugadorBaron = nombreJActivo;
			jugadorBaronOp = nombreOp;
			setCompararManos(true);

			String msj = (String) leerMsj(entrada);
			if (msj.equals("PerdioOponente")) {
				sw = true;
				cartaOp = cartaBaronOp;
				nombreOp = jugadorBaronOp;

//		
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
		}
			break;
		case "Rey": {
		}
			break;
		}

		pintarCarta(mano.get(0), nombreJActivo);
		if (sw) {
			pintarCarta(cartaOp, nombreOp);

		}
		sw = false;
		distDescarte += 30;
//			DibujoCarta cartaTiradas = new DibujoCarta(jugadorActivo.getMano(0), 430 + distDescarte, 295);
		DibujoCarta cartaTiradas = new DibujoCarta(mano.get(0), 430 + distDescarte, 295);
		dibujos.add(cartaTiradas);
//			if (jugadorActivo.getMano(0).equals(new Sacerdote()))
		if (mano.get(0).equals(new Sacerdote()))
//				dibManoOp = true;

//			if (jugadorActivo.getMano(0).equals(new Baron())) {
			if (mano.get(0).equals(new Baron())) {
//				jugadorBaron = jugadorActivo;
//				cartaBaron = jugadorActivo.getMano(1);
//				cartaBaronOp = jugadores.get(getJugadorElegido()).getMano(0);
//				compararManos = true;
			}

//			jugadorActivo.jugarCarta(partida, 0, lista, listaCartas);

		sonidoTirarCarta.play();
		mano.remove(0);
		refresh();
		if (cartaTiradas.getCartaDib().equals(new Principe())) {
//				distDescarte += 30;
//				DibujoCarta cartaOp = new DibujoCarta(jugadores.get(getJugadorElegido()).getUltimaDescartada(),
//						430 + distDescarte, 295);
//				dibujos.add(cartaOp);
//
//				if (jugadores.get(getJugadorElegido()).getManoCompleta().isEmpty())
//					jugadores.get(getJugadorElegido()).getManoCompleta().add(partida.getCartaEliminda());

		}
		jugarCarta = true;
		Tablero.enviarMsj(salida, "5");
		// turnoJugador(partida.proximoTurnoJugador(jugadorActivo));
		refresh();
	}

	public void tocarCartaDerecha(MouseEvent m, ObjectInputStream entrada, ObjectOutputStream salida, JDialog lista,
			JDialog listaCartas) {

		Carta cartaOp = null;
		String nombreOp = null;
		boolean sw = false;
		bloquearBoton();
		Tablero.enviarMsj(salida, "4");
		Tablero.enviarMsj(salida, mano.get(1));
		Tablero.enviarMsj(salida, 1);

//		int idx = indexDes.get(nombreJActivo);
//		Integer[] pos = posiciones.get(nombreJActivo);
//		int x = pos[0];
//		int y = pos[1];
//		int nuevaDist = distdDes.get(nombreJActivo) + 30;
//		distdDes.replace(nombreJActivo, nuevaDist);
//		DibujoCarta dibujo = new DibujoCarta(mano.get(1), x + nuevaDist, y);
//		descartes.get(idx).add(dibujo);

		if (mano.get(1).equals(new Guardia()) || mano.get(1).equals(new Sacerdote()) || mano.get(1).equals(new Baron())
				|| mano.get(1).equals(new Rey()) || mano.get(1).equals(new Principe())) {

			lista.setVisible(true);

			if (mano.get(1).equals(new Guardia())) {
				listaCartas.setVisible(true);

			}
		}
		boolean itsMeMario = false;

		switch (mano.get(1).getNombre()) {
		case "Princesa": {
		}

			break;
		case "Condesa": {
		}
			break;
		case "Principe": {
			nombreOp = (String) leerMsj(entrada);
			cartaOp = (Carta) Tablero.leerMsj(entrada);
			sw = true;
			if (getNombreJActivo().equals(nombreOp)) {
				itsMeMario = true;
			}
			if (itsMeMario) {
				Carta nuevaCarta = (Carta) Tablero.leerMsj(entrada);
				getMano().add(nuevaCarta);
				getMano().remove(0);
				
				itsMeMario = false;
			}

		}
			break;
		case "Mucama": {

		}
			break;
		case "Baron": {

			nombreOp = (String) leerMsj(entrada);
			cartaBaronOp = (Carta) leerMsj(entrada);
			cartaBaron = (Carta) leerMsj(entrada);
			jugadorBaron = nombreJActivo;
			jugadorBaronOp = nombreOp;
			compararManos = true;

			String msj = (String) leerMsj(entrada);
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
		}
			break;
		case "Rey": {
		}
			break;
		}

		pintarCarta(mano.get(1), nombreJActivo);
		if (sw) {
			pintarCarta(cartaOp, nombreOp);
		}
		sw = false;

		distDescarte += 30;
//			DibujoCarta cartaTiradas = new DibujoCarta(jugadorActivo.getMano(1), 430 + distDescarte, 295);
		DibujoCarta cartaTiradas = new DibujoCarta(mano.get(1), 430 + distDescarte, 295);
		dibujos.add(cartaTiradas);

//			if (jugadorActivo.getMano(1).equals(new Sacerdote()))
		if (mano.get(1).equals(new Sacerdote()))
//				dibManoOp = true;

//			if (jugadorActivo.getMano(1).equals(new Baron())) {
			if (mano.get(1).equals(new Baron())) {
//				jugadorBaron = jugadorActivo;
//				cartaBaron = jugadorActivo.getMano(0);
//				cartaBaronOp = jugadores.get(getJugadorElegido()).getMano(0);
//				compararManos = true;

			}

//			jugadorActivo.jugarCarta(partida, 1, lista, listaCartas);
		sonidoTirarCarta.play();
		mano.remove(1);

		if (cartaTiradas.getCartaDib().equals(new Principe())) {
//				distDescarte += 30;
//				DibujoCarta cartaOp = new DibujoCarta(jugadores.get(getJugadorElegido()).getUltimaDescartada(),
//						430 + distDescarte, 295);
//				dibujos.add(cartaOp);
//
//				if (jugadores.get(getJugadorElegido()).getManoCompleta().isEmpty())
//					jugadores.get(getJugadorElegido()).getManoCompleta().add(partida.getCartaEliminda());
		}
		// turnoJugador(partida.proximoTurnoJugador(jugadorActivo));
		jugarCarta = true;
		Tablero.enviarMsj(salida, "5");

		refresh();

	}

	public void tomarCartaMazo(MouseEvent m, ObjectInputStream entrada, ObjectOutputStream salida) {

		sonidoTirarCarta.play();

		Tablero.enviarMsj(salida, "3");
		Carta cTomada = (Carta) Tablero.leerMsj(entrada);
		String quienJuega = (String) Tablero.leerMsj(entrada);
		Carta cDescartada = (Carta) Tablero.leerMsj(entrada);
		mano.add(cTomada);

//		if (new Condesa().equals(partida.getMazo().darCarta(jugadorActivo))
//				&& new Condesa().equals(jugadorActivo.getUltimaDescartada())) {
//			distDescarte += 30;
//			DibujoCarta cartaTiradas = new DibujoCarta(new Condesa(), 430 + distDescarte, 295);
//			dibujos.add(cartaTiradas);
//
//			turnoJugador(partida.proximoTurnoJugador(jugadorActivo));
//		}

//		if (new Condesa().equals(cTomada) && new Condesa().equals(cDescartada)) {
//			distDescarte += 30;
//			DibujoCarta cartaTiradas = new DibujoCarta(new Condesa(), 430 + distDescarte, 295);
//			dibujos.add(cartaTiradas);
//			int i = mano.indexOf(cTomada);
//			mano.remove(i);
//
////			turnoJugador(partida.proximoTurnoJugador(jugadorActivo));
//			Tablero.enviarMsj(salida, "5");
//		}

		tomoCarta = true;
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

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		in = entrada;
		out = salida;
		HiloEscuchaTablero hiloTablero = new HiloEscuchaTablero(entrada, salida, this, espera);
		Carta c = (Carta) leerMsj(entrada);
		nombreJActivo = (String) leerMsj(entrada);
		mano.add(c);
		construirDescarte();

		sonidoFondo.setVolume(volumen);
//		sonidoFondo.play();
//		sonidoFondo.loopear();
		this.sala = salaPrincipal;
		this.jugadores = jugadores;
		CantJugadores = jugadores.size();
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

		if (CantJugadores >= 3) {

			j3 = new JButton(jugadores.get(2).getNombre());
			j3.setBackground(Color.decode("#f7db97"));
			predefined.add(j3);
			if (CantJugadores == 4) {
				j4 = new JButton(jugadores.get(3).getNombre());
				j4.setBackground(Color.decode("#f7db97"));
				predefined.add(j4);
			}

		}

		JPanel salir = new JPanel();
		salir.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		salir.setBackground(Color.decode("#f7db97"));
		volver = new JButton("Volver");

		volver.setBackground(Color.decode("#f7db97"));
		// volver.setSize(20, 20);
		salir.add(volver);
		// salir.setSize(20, 20);

		lista.getContentPane().setBackground(Color.decode("#f7db97"));
		lista.getContentPane().add(imagen);
		lista.getContentPane().setLayout(new BoxLayout(lista.getContentPane(), BoxLayout.Y_AXIS));
		lista.getContentPane().add(predefined);
		lista.getContentPane().add(salir);
		lista.setSize(250, 400);

		lista.setLocationRelativeTo(null);
		lista.setUndecorated(true);
		// lista.setVisible(true);

		j1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setJugadorElegido(0);
				enviarMsj(salida, 0);
				lista.setVisible(false);

			}
		});

		j2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarMsj(salida, 1);
				setJugadorElegido(1);
				lista.setVisible(false);

			}
		});

		if (CantJugadores >= 3) {
			j3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setJugadorElegido(2);
					enviarMsj(salida, 2);
					lista.setVisible(false);

				}
			});

		}

		if (CantJugadores == 4) {
			j4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setJugadorElegido(3);
					enviarMsj(salida, 3);
					lista.setVisible(false);

				}
			});
		}

		listaCartas = new JDialog(this, "Lista Cartas", true);
		/*
		 * listaCartas.getContentPane().setLayout(new
		 * BoxLayout(listaCartas.getContentPane(), BoxLayout.X_AXIS));
		 * listaCartas.setResizable(true); listaCartas.setSize(643, 65);
		 */

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

		/*
		 * listaCartas.setLocationRelativeTo(null); listaCartas.setVisible(false);
		 * listaCartas.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		 * listaCartas.setResizable(false); listaCartas.pack();
		 */

		JPanel salirC = new JPanel();
		salirC.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		salirC.setBackground(Color.decode("#f7db97"));
		volverC = new JButton("Volver");

		volverC.setBackground(Color.decode("#f7db97"));

		salirC.add(volverC);

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
				setCartaElegida(0);
				enviarMsj(salida, 0);
				listaCartas.setVisible(false);
			}
		});

		c3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCartaElegida(1);
				enviarMsj(salida, 1);
				listaCartas.setVisible(false);
			}
		});

		c4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCartaElegida(2);
				enviarMsj(salida, 2);
				listaCartas.setVisible(false);
			}
		});

		c5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCartaElegida(3);
				enviarMsj(salida, 3);
				listaCartas.setVisible(false);
			}
		});

		c6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCartaElegida(4);
				enviarMsj(salida, 4);
				listaCartas.setVisible(false);
			}
		});

		c7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCartaElegida(5);
				enviarMsj(salida, 5);
				listaCartas.setVisible(false);
			}
		});

		c8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCartaElegida(6);
				enviarMsj(salida, 6);
				listaCartas.setVisible(false);
			}
		});

		pack();
		setTitle("LoveLetter");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		requestFocusInWindow();

		setResizable(false);
		// setBounds(500, 156, 905, 727);***1
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

//		if (partida.isFinalizoPartida()) {
//		miTablero.dispose();
//		sala.setVisible(true);
//	}

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent m) {

				if ((m.getX() >= 575 && m.getX() <= 675 && (m.getY() >= 615 && m.getY() <= 755) && mano.size() >= 1)) {
					if (m.getButton() == MouseEvent.BUTTON3)
						mostrarLista(0);
					else if (miTurno && mano.size() == 2)
						tocarCartaIzquierda(m, entrada, salida, lista, listaCartas);

				} else if ((m.getX() >= 200 && m.getX() <= 510)
						&& (m.getY() >= 210 && m.getY() <= 470 && mano.size() == 1)) {
					if (miTurno) {
						tomarCartaMazo(m, entrada, salida);
						levantarCarta = true;
					}

				} else if ((m.getX() >= 695 && m.getX() <= 795 && (m.getY() >= 615 && m.getY() <= 755))
						&& mano.size() == 2) {
					if (m.getButton() == MouseEvent.BUTTON3)
						mostrarLista(1);
					else if (miTurno)
						tocarCartaDerecha(m, entrada, salida, lista, listaCartas);
				}
				refresh();
				if (jugarCarta && levantarCarta) {
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

			if (partida.isHuboEliminacion()) {
				distDescarte += 30;
				Jugador eliminado = jugadores.get(Tablero.getJugadorElegido());

				if (partida.isEliminoActBaron()) {
					eliminado = jugadorActivo;
					partida.setEliminoActBaron(false);
				}

				DibujoCarta cartaJugadorElim = new DibujoCarta(eliminado.getMano(0), 430 + distDescarte, 295);
				dibujos.add(cartaJugadorElim);
				partida.setHuboEliminacion(false);

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
			g2.drawString("Ronda: " + partida.getNroRonda(), 1234, 39);

			dibujarCartas(g2, "Dorso", 635, 10);

//				dibujarCartas(g2, jugadorActivo.getMano(1).getNombre(), 695, 615);

//			dibujarCartas(g2, jugadorActivo.getMano(0).getNombre(), 575, 615);
//			if (jugadorActivo.getTamanioMano() > 1)
//				dibujarCartas(g2, jugadorActivo.getMano(1).getNombre(), 695, 615);

			dibujarCartas(g2, mano.get(0).getNombre(), 575, 615);
			if (mano.size() > 1) {
				dibujarCartas(g2, mano.get(1).getNombre(), 695, 615);
			}

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setFont(seagram);
			g2.setPaint(Color.decode("#653b33"));
			g2.drawImage(cartaAmor, 1283, 698, 60, 39, this);
//			g2.drawString(jugadorActivo.getNombre(), 1035, 741);
//			g2.drawString("Afectos: " + String.valueOf(jugadorActivo.getAfectosConseguidos()), 1155, 741);
			g2.drawString(nombreJActivo, 1035, 741);
			g2.drawString("Afectos: " + String.valueOf(jugadorActivo.getAfectosConseguidos()), 1155, 741);

			if (partida.getCantJugadores() == 3) {
				dibujarCartas(g2, "Dorso", 20, 285);

			}

			if (partida.getCantJugadores() == 4) {

				dibujarCartas(g2, "Dorso", 20, 285);
				dibujarCartas(g2, "Dorso", 1250, 285);

			}

//			for (DibujoCarta dib : dibujos) {
//				dibujarCartas(g2, dib.getCartaDib().getNombre(), dib.getEjeX(), dib.getEjeY());
//			}

			for (int i = 0; i < partida.getJugadores().size(); i++) {
				String nombre = partida.getJugadores().get(i).getNombre();
				int idx = indexDes.get(nombre);

				for (DibujoCarta dib : descartes.get(idx)) {
					dibujarCartas(g2, dib.getCartaDib().getNombre(), dib.getEjeX(), dib.getEjeY());
				}
			}

			if (dibManoOp) {
				int renglon = 0;

				g2.setFont(new Font("Segoe Script", Font.HANGING_BASELINE, 15));
				g2.setPaint(Color.WHITE);
				g2.drawImage(fondoVerCarta, 330, 150, 555, 380, this);

				for (String texto : jugadores.get(Tablero.getJugadorElegido()).getMano(0).toString().split("\n")) {
					g2.drawString(texto, 380, 450 + renglon);
					renglon += 20;
				}
				g2.setFont(new Font("Segoe Script", Font.HANGING_BASELINE, 40));

				g2.drawString(jugadores.get(Tablero.getJugadorElegido()).getNombre(), 510, 220);
				dibujarCartas(g2, jugadores.get(Tablero.getJugadorElegido()).getMano(0).getNombre(), 560, 280);

				dibManoOp = false;
			}

//			if (cambiarJugador) {
//				cambiarJugador = false;
//				g2.drawImage(fondoVerCarta, 0, 0, getWidth(), getHeight(), this);
//				g2.setFont(new Font("Segoe Script", Font.HANGING_BASELINE, 30));
//				g2.setPaint(Color.WHITE);
//				g2.drawString("Siguiente Jugador", 265, 170);
//				g2.drawString(jugadorActivo.getNombre(), 345, 250);
//			}

			if (compararManos) {

				g2.drawImage(fondoVerCarta, 330, 150, 555, 380, this);
				g2.setFont(new Font("Segoe Script", Font.HANGING_BASELINE, 35));
				g2.setPaint(Color.WHITE);

				g2.drawString(jugadorBaron, 400, 220);
				dibujarCartas(g2, cartaBaron.getNombre(), 430, 280);

				g2.setFont(new Font("Segoe Script", Font.HANGING_BASELINE, 35));
				g2.setPaint(Color.WHITE);

				g2.drawString(jugadorBaronOp, 640, 220);
				dibujarCartas(g2, cartaBaronOp.getNombre(), 670, 280);
//				dibujarCartas(g2, jugadores.get(Tablero.getJugadorElegido()).getMano(0).getNombre(), 670, 280);
				compararManos = false;
			}

			if (partida.getReinicio()) {
				dibujos.clear();
				partida.setReinicio(false);
				g2.setFont(new Font("Segoe Script", Font.HANGING_BASELINE, 30));
				g2.setPaint(Color.WHITE);
				g2.drawImage(fondoVerCarta, 230, 120, 350, 200, this);
				g2.drawString("Fin de ronda", 300, 170);
				g2.drawString(partida.getGanadoRonda().getNombre(), 270, 240);
				g2.drawImage(cartaAmor, 430, 210, 50, 35, this);
				g2.drawString("+1", 480, 240);
				distDescarte = 0;
			}

			if (partida.isFinalizoPartida()) {

				g2.setFont(new Font("Segoe Script", Font.HANGING_BASELINE, 30));
				g2.setPaint(Color.WHITE);
				g2.drawImage(fondoVerCarta, 230, 120, 350, 200, this);
				g2.drawString("Partida finalizada", 245, 170);
				g2.drawString(" Ganador " + jugadorActivo.getNombre(), 250, 240);
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
			case "Dorso":
				g2.drawImage(dorso, x, y, anchoCarta, largoCarta, this);
				g2.setColor(Color.WHITE);
				g2.drawRect(x - 1, y, anchoCarta, largoCarta);
				break;
			}
//preguntar por mi turno
//			if(miTurno) {
//			bloquearBoton();
//			desbloquearBoton();
//			}
		}

		@Override
		public Dimension getPreferredSize() {

			return new Dimension(1366, 768);

		}

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

		Integer[] pos2 = { 300, 50 };
		posiciones.put(n.get(0), pos2);

		if (n.size() >= 2) {
			Integer[] pos3 = { 635, 50 };
			posiciones.put(n.get(1), pos3);
			if (n.size() == 3) {
				Integer[] pos4 = { 800, 50 };
				posiciones.put(n.get(2), pos4);
			}
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

	public boolean isDibManoOp() {
		return dibManoOp;
	}

	public void setDibManoOp(boolean dibManoOp) {
		this.dibManoOp = dibManoOp;
	}

	public boolean isCompararManos() {
		return compararManos;
	}

	public void setCompararManos(boolean compararManos) {
		this.compararManos = compararManos;
	}

	public String getNombreJActivo() {
		return nombreJActivo;
	}

	public void setNombreJActivo(String nombreJActivo) {
		this.nombreJActivo = nombreJActivo;
	}

	public ArrayList<Carta> getMano() {
		return mano;
	}

	public void setMano(ArrayList<Carta> mano) {
		this.mano = mano;
	}

	public ArrayList<ArrayList<DibujoCarta>> getDescartes() {
		return descartes;
	}

	public void setDescartes(ArrayList<ArrayList<DibujoCarta>> descartes) {
		this.descartes = descartes;
	}

	public Map<String, Integer> getIndexDes() {
		return indexDes;
	}

	public void setIndexDes(Map<String, Integer> indexDes) {
		this.indexDes = indexDes;
	}

	public Map<String, Integer> getDistdDes() {
		return distdDes;
	}

	public void setDistdDes(Map<String, Integer> distdDes) {
		this.distdDes = distdDes;
	}

	public Map<String, Integer[]> getPosiciones() {
		return posiciones;
	}

	public void setPosiciones(Map<String, Integer[]> posiciones) {
		this.posiciones = posiciones;
	}

	public boolean isMiTurno() {
		return miTurno;
	}

	public void setMiTurno(boolean miTurno) {
		this.miTurno = miTurno;
	}

	public static int getCartaElegida() {
		return cartaElegida;
	}

	public void setCartaElegida(int cartaElegida) {
		Tablero.cartaElegida = cartaElegida;
	}

	public static int getJugadorElegido() {
		return jugadorElegido;
	}

	public void setJugadorElegido(int jugadorElegido) {
		Tablero.jugadorElegido = jugadorElegido;
	}

	public void turnoJugador(Jugador jugador) {
		if (!compararManos && !dibManoOp)
			cambiarJugador = true;
		jugadorActivo = jugador;
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public void setIn(ObjectInputStream in) {
		this.in = in;
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}

	private void pintarCarta(Carta cJugada, String nombreJugo) {
		int idx = getIndexDes().get(nombreJugo);
		Integer[] pos = getPosiciones().get(nombreJugo);
		int x = pos[0];
		int y = pos[1];
		int nuevaDist = getDistdDes().get(nombreJugo) + 30;
		getDistdDes().replace(nombreJugo, nuevaDist);
		DibujoCarta dibujo = new DibujoCarta(cJugada, x + nuevaDist, y);
		getDescartes().get(idx).add(dibujo);
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
}
