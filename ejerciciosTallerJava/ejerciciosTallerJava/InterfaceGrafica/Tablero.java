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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

	private JPanel contentPane;

	private BufferedImage dorso;
	private DrawPanel drawPanel;
	private Jugador jugadorActivo;
	//BARON
	private Jugador jugadorBaron;
	private Carta cartaBaron;
	private Carta cartaBaronOp;

	
	private int distDescarte = 0;
	private Partida partida;
	private boolean dibManoOp = false;
	private Sound sonidoFondo;
	private Sound sonidoTirarCarta;
	private ImageIcon banner = new ImageIcon("loveImg/banner.jpg");
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
	


	private ImageIcon descriprueca = new ImageIcon("loveImg/banner.jpg");

	public void bloquearBoton() {

		for (int i = 0; i < jugadores.size(); i++) {
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

	public void tocarCartaIzquierda(MouseEvent m) {

		if (m.getButton() == MouseEvent.BUTTON1) {
			distDescarte += 30;
			DibujoCarta cartaTiradas = new DibujoCarta(jugadorActivo.getMano(0), 430 + distDescarte, 295);
			dibujos.add(cartaTiradas);
			if (jugadorActivo.getMano(0).equals(new Sacerdote()))
				dibManoOp = true;

			if (jugadorActivo.getMano(0).equals(new Baron())) {
				jugadorBaron = jugadorActivo;
				cartaBaron = jugadorActivo.getMano(1);
				cartaBaronOp = jugadores.get(getJugadorElegido()).getMano(0);
				compararManos = true;
			}

			jugadorActivo.jugarCarta(partida, 0, lista, listaCartas);
			sonidoTirarCarta.play();

			refresh();

			if (cartaTiradas.getCartaDib().equals(new Principe())) {
				distDescarte += 30;
				DibujoCarta cartaOp = new DibujoCarta(
						jugadores.get(getJugadorElegido()).getUltimaDescartada(), 430 + distDescarte, 295);
				dibujos.add(cartaOp);

				if (jugadores.get(getJugadorElegido()).getManoCompleta().isEmpty())
					jugadores.get(getJugadorElegido()).getManoCompleta().add(partida.getCartaEliminda());

			}

			turnoJugador(partida.proximoTurnoJugador(jugadorActivo));
		} else if (m.getButton() == MouseEvent.BUTTON3) {
			JDialog j = new JDialog();
			j.setUndecorated(true);
			j.setVisible(true);
			j.setBounds(480, 210, 600, 360);
			

			ImageIcon descrip = new ImageIcon(jugadorActivo.getMano(0).toString());

			JPanel cartaDescrip = new JPanel(new BorderLayout()) {

				/**
				 * 
				 */
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

			ImageIcon exit = new ImageIcon("loveImg/exit.png");
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
		refresh();
	}
	
	public void tocarCartaDerecha(MouseEvent m) {
		
//		JDialog j = new JDialog();
		if (m.getButton() == MouseEvent.BUTTON1) {
			distDescarte += 30;
			DibujoCarta cartaTiradas = new DibujoCarta(jugadorActivo.getMano(1), 430 + distDescarte, 295);
			dibujos.add(cartaTiradas);
			if (jugadorActivo.getMano(1).equals(new Sacerdote()))
				dibManoOp = true;

			if (jugadorActivo.getMano(1).equals(new Baron())) {
				jugadorBaron = jugadorActivo;
				cartaBaron = jugadorActivo.getMano(0);
				cartaBaronOp = jugadores.get(getJugadorElegido()).getMano(0);
				compararManos = true;

			}

			jugadorActivo.jugarCarta(partida, 1, lista, listaCartas);
			sonidoTirarCarta.play();

			refresh();

			if (cartaTiradas.getCartaDib().equals(new Principe())) {
				distDescarte += 30;
				DibujoCarta cartaOp = new DibujoCarta(
						jugadores.get(getJugadorElegido()).getUltimaDescartada(), 430 + distDescarte, 295);
				dibujos.add(cartaOp);

				if (jugadores.get(getJugadorElegido()).getManoCompleta().isEmpty())
					jugadores.get(getJugadorElegido()).getManoCompleta().add(partida.getCartaEliminda());
			}

			turnoJugador(partida.proximoTurnoJugador(jugadorActivo));
		} else if (m.getButton() == MouseEvent.BUTTON3) {

			JDialog j = new JDialog();
			j.setUndecorated(true);
			j.setVisible(true);
			j.setBounds(480, 210, 600, 360);
	
		
	
			

			ImageIcon descrip = new ImageIcon(jugadorActivo.getMano(1).toString());

			JPanel cartaDescrip = new JPanel(new BorderLayout()) {

				/**
				 * 
				 */
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

			ImageIcon exit = new ImageIcon("loveImg/exit.png");
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

		refresh();
		
	}
	
	
	public void tomarCartaMazo(MouseEvent m){
		
		sonidoTirarCarta.play();
		if (new Condesa().equals(partida.getMazo().darCarta(jugadorActivo))
				&& new Condesa().equals(jugadorActivo.getUltimaDescartada())) {
			distDescarte += 30;
			DibujoCarta cartaTiradas = new DibujoCarta(new Condesa(), 430 + distDescarte, 295);
			dibujos.add(cartaTiradas);

			turnoJugador(partida.proximoTurnoJugador(jugadorActivo));
		}

		tomoCarta = true;
		refresh();
		
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

	public void init(ArrayList<Jugador> jugadores, Partida partida) {
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

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		sonidoFondo.setVolume(volumen);
//		sonidoFondo.play();
//		sonidoFondo.loopear();
		this.jugadores = jugadores;
		CantJugadores = jugadores.size();
		this.partida = partida;

		drawPanel = new DrawPanel();
		getContentPane().add(drawPanel);
	
		for (Jugador jugador : jugadores) {
			if(jugador.getNombre().equals(partida.getjInicial()))
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
				lista.setVisible(false);

			}
		});

		j2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setJugadorElegido(1);
				lista.setVisible(false);

			}
		});

		if (CantJugadores >= 3) {
			j3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setJugadorElegido(2);
					lista.setVisible(false);

				}
			});

		}

		if (CantJugadores == 4) {
			j4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setJugadorElegido(3);
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
		// volver.setSize(20, 20);
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
				listaCartas.setVisible(false);
			}
		});

		c3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCartaElegida(1);
				listaCartas.setVisible(false);
			}
		});

		c4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCartaElegida(2);
				listaCartas.setVisible(false);
			}
		});

		c5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCartaElegida(3);
				listaCartas.setVisible(false);
			}
		});

		c6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCartaElegida(4);
				listaCartas.setVisible(false);
			}
		});

		c7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCartaElegida(5);
				listaCartas.setVisible(false);
			}
		});

		c8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCartaElegida(6);
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

		setResizable(false); // no puedes maximizar/minimizar la ventana
		// setBounds(500, 156, 905, 727);***1
		setBounds(80, 36, 1366, 768);

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

			
				if ((m.getX() >= 575 && m.getX() <= 675 && (m.getY() >= 615 && m.getY() <= 755)
						&& jugadorActivo.getTamanioMano() > 1)) {
					tocarCartaIzquierda(m);
//					if (m.getButton() == MouseEvent.BUTTON1) {
//						distDescarte += 30;
//						DibujoCarta cartaTiradas = new DibujoCarta(jugadorActivo.getMano(0), 430 + distDescarte, 295);
//						dibujos.add(cartaTiradas);
//						if (jugadorActivo.getMano(0).equals(new Sacerdote()))
//							dibManoOp = true;
//
//						if (jugadorActivo.getMano(0).equals(new Baron())) {
//							jugadorBaron = jugadorActivo;
//							cartaBaron = jugadorActivo.getMano(1);
//							cartaBaronOp = jugadores.get(getJugadorElegido()).getMano(0);
//							compararManos = true;
//						}
//
//						jugadorActivo.jugarCarta(partida, 0, lista, listaCartas);
//						sonidoTirarCarta.play();
//
//						refresh();
//
//						if (cartaTiradas.getCartaDib().equals(new Principe())) {
//							distDescarte += 30;
//							DibujoCarta cartaOp = new DibujoCarta(
//									jugadores.get(getJugadorElegido()).getUltimaDescartada(), 430 + distDescarte, 295);
//							dibujos.add(cartaOp);
//
//							if (jugadores.get(getJugadorElegido()).getManoCompleta().isEmpty())
//								jugadores.get(getJugadorElegido()).getManoCompleta().add(partida.getCartaEliminda());
//
//						}
//
//						turnoJugador(partida.proximoTurnoJugador(jugadorActivo));
//					} else if (m.getButton() == MouseEvent.BUTTON3) {
//						JDialog j = new JDialog();
//						j.setUndecorated(true);
//						j.setVisible(true);
//						j.setBounds(480, 210, 600, 360);
//
//						ImageIcon descrip = new ImageIcon(jugadorActivo.getMano(1).toString());
//
//						JPanel cartaDescrip = new JPanel(new BorderLayout()) {
//
//							/**
//							 * 
//							 */
//							private static final long serialVersionUID = 1L;
//
//							@Override
//							protected void paintComponent(Graphics g) {
//								super.paintComponent(g);
//								g.drawImage(descrip.getImage(), 0, 0, getWidth(), getHeight(), this);
//							}
//
//							@Override
//							public Dimension getPreferredSize() {
//								Dimension size = super.getPreferredSize();
//								size.width = Math.max(descrip.getIconWidth(), size.width);
//								size.width = Math.max(descrip.getIconHeight(), size.height);
//
//								return size;
//							}
//						};
//
//						ImageIcon exit = new ImageIcon("loveImg/exit.png");
//						Image image = exit.getImage();
//						Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
//						exit = new ImageIcon(newimg);
//						cartaDescrip.setLayout(new BorderLayout());
//						int gap = -3;
//						cartaDescrip.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
//
//						JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//						southPanel.setOpaque(false);
//						JButton button = new JButton(exit);
//						button.setPreferredSize(new Dimension(50, 50));
//
//						button.setOpaque(false);
//						button.setContentAreaFilled(false);
//						button.setBorderPainted(false);
//						southPanel.add(button);
//						cartaDescrip.add(southPanel, BorderLayout.NORTH);
//
//						button.addActionListener(e -> {
//							j.dispose();
//						});
//						j.add(cartaDescrip);
//
//					}
//					refresh();
				} else if ((m.getX() >= 200 && m.getX() <= 510)
						&& (m.getY() >= 210 && m.getY() <= 470 && jugadorActivo.getTamanioMano() == 1)) {
					tomarCartaMazo(m);
//					sonidoTirarCarta.play();
//
//					if (new Condesa().equals(partida.getMazo().darCarta(jugadorActivo))
//							&& new Condesa().equals(jugadorActivo.getUltimaDescartada())) {
//						distDescarte += 30;
//						DibujoCarta cartaTiradas = new DibujoCarta(new Condesa(), 430 + distDescarte, 295);
//						dibujos.add(cartaTiradas);
//
//						turnoJugador(partida.proximoTurnoJugador(jugadorActivo));
//					}
//
//					tomoCarta = true;
//					refresh();
				} else if ((m.getX() >= 695 && m.getX() <= 795 && (m.getY() >= 615 && m.getY() <= 755))
						&& jugadorActivo.getTamanioMano() == 2) {
					tocarCartaDerecha(m);
////					JDialog j = new JDialog();
//					if (m.getButton() == MouseEvent.BUTTON1) {
//						distDescarte += 30;
//						DibujoCarta cartaTiradas = new DibujoCarta(jugadorActivo.getMano(1), 430 + distDescarte, 295);
//						dibujos.add(cartaTiradas);
//						if (jugadorActivo.getMano(1).equals(new Sacerdote()))
//							dibManoOp = true;
//
//						if (jugadorActivo.getMano(1).equals(new Baron())) {
//							jugadorBaron = jugadorActivo;
//							cartaBaron = jugadorActivo.getMano(0);
//							cartaBaronOp = jugadores.get(getJugadorElegido()).getMano(0);
//							compararManos = true;
//
//						}
//
//						jugadorActivo.jugarCarta(partida, 1, lista, listaCartas);
//						sonidoTirarCarta.play();
//
//						refresh();
//
//						if (cartaTiradas.getCartaDib().equals(new Principe())) {
//							distDescarte += 30;
//							DibujoCarta cartaOp = new DibujoCarta(
//									jugadores.get(getJugadorElegido()).getUltimaDescartada(), 430 + distDescarte, 295);
//							dibujos.add(cartaOp);
//
//							if (jugadores.get(getJugadorElegido()).getManoCompleta().isEmpty())
//								jugadores.get(getJugadorElegido()).getManoCompleta().add(partida.getCartaEliminda());
//						}
//
//						turnoJugador(partida.proximoTurnoJugador(jugadorActivo));
//					} else if (m.getButton() == MouseEvent.BUTTON3) {
//
//						JDialog j = new JDialog();
//						j.setUndecorated(true);
//						j.setVisible(true);
//						j.setBounds(480, 210, 600, 360);
//
//						ImageIcon descrip = new ImageIcon(jugadorActivo.getMano(1).toString());
//
//						JPanel cartaDescrip = new JPanel(new BorderLayout()) {
//
//							/**
//							 * 
//							 */
//							private static final long serialVersionUID = 1L;
//
//							@Override
//							protected void paintComponent(Graphics g) {
//								super.paintComponent(g);
//								g.drawImage(descrip.getImage(), 0, 0, getWidth(), getHeight(), this);
//							}
//
//							@Override
//							public Dimension getPreferredSize() {
//								Dimension size = super.getPreferredSize();
//								size.width = Math.max(descrip.getIconWidth(), size.width);
//								size.width = Math.max(descrip.getIconHeight(), size.height);
//
//								return size;
//							}
//						};
//
//						ImageIcon exit = new ImageIcon("loveImg/exit.png");
//						Image image = exit.getImage();
//						Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
//						exit = new ImageIcon(newimg);
//						cartaDescrip.setLayout(new BorderLayout());
//						int gap = -3;
//						cartaDescrip.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
//
//						JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//						southPanel.setOpaque(false);
//						JButton button = new JButton(exit);
//						button.setPreferredSize(new Dimension(50, 50));
//
//						button.setOpaque(false);
//						button.setContentAreaFilled(false);
//						button.setBorderPainted(false);
//						southPanel.add(button);
//						cartaDescrip.add(southPanel, BorderLayout.NORTH);
//
//						button.addActionListener(e -> {
//							j.dispose();
//						});
//						j.add(cartaDescrip);
//
//					}
//
//					refresh();

				}
				refresh();
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
			
				if(partida.isEliminoActBaron()) {
					eliminado = jugadorActivo;
					partida.setEliminoActBaron(false);				 
				}
				
				DibujoCarta cartaJugadorElim = new DibujoCarta(eliminado.getMano(0),
						430 + distDescarte, 295);
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
			dibujarCartas(g2, jugadorActivo.getMano(0).getNombre(), 575, 615);
			if (jugadorActivo.getTamanioMano() > 1)
				dibujarCartas(g2, jugadorActivo.getMano(1).getNombre(), 695, 615);

		
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setFont(seagram);
			g2.setPaint(Color.decode("#653b33"));
			g2.drawImage(cartaAmor, 1283, 698, 60, 39, this);
			g2.drawString(jugadorActivo.getNombre(), 1035, 741);
			g2.drawString("Afectos: " + String.valueOf(jugadorActivo.getAfectosConseguidos()), 1155, 741);

			if (partida.getCantJugadores() == 3) {
				dibujarCartas(g2, "Dorso", 20, 285);
				
			}

			if (partida.getCantJugadores() == 4) {
				
				dibujarCartas(g2, "Dorso", 20, 285);
				dibujarCartas(g2, "Dorso", 1250, 285);

			}

			for (DibujoCarta dib : dibujos) {
				dibujarCartas(g2, dib.getCartaDib().getNombre(), dib.getEjeX(), dib.getEjeY());
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

			if (cambiarJugador) {
				cambiarJugador = false;
				g2.drawImage(fondoVerCarta, 0, 0, getWidth(), getHeight(), this);
				g2.setFont(new Font("Segoe Script", Font.HANGING_BASELINE, 30));
				g2.setPaint(Color.WHITE);
				g2.drawString("Siguiente Jugador", 265, 170);
				g2.drawString(jugadorActivo.getNombre(), 345, 250);
			}

			if (compararManos) {

				
				g2.drawImage(fondoVerCarta, 330, 150, 555, 380, this);
				g2.setFont(new Font("Segoe Script", Font.HANGING_BASELINE, 35));
				g2.setPaint(Color.WHITE);

				g2.drawString(jugadorBaron.getNombre(), 400, 220);
				dibujarCartas(g2, cartaBaron.getNombre(), 430, 280);

				g2.setFont(new Font("Segoe Script", Font.HANGING_BASELINE, 35));
				g2.setPaint(Color.WHITE);

				g2.drawString(jugadores.get(Tablero.getJugadorElegido()).getNombre(), 640, 220);
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

			bloquearBoton();
			desbloquearBoton();

		}

		@Override
		public Dimension getPreferredSize() {
			// return new Dimension(900, 700);**8
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
	
		contentPane = new JPanel();
		this.partida = partida;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBounds(200, 117, 1366, 768);
		// getContentPane().setBounds(626, 417, 800, 513);**7
		setContentPane(contentPane);

	}
}
