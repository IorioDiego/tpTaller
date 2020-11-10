package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import org.omg.CORBA.portable.ValueBase;

import cartas.*;
import game.Jugador;
import game.Partida;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;

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
	private int distDescarte = 0;
	private Partida partida;

	private BufferedImage background;
	private int CantJugadores;
	private int anchoCarta = 100;
	private int largoCarta = 120;

	private BufferedImage guardia;
	private BufferedImage princesa;
	private BufferedImage principe;
	private BufferedImage rey;
	private BufferedImage condesa;
	private BufferedImage mucama;
	private BufferedImage sacerdote;
	private BufferedImage baron;
	private BufferedImage cartaAmor;

	private boolean tomoCarta = false;

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

	private static int jugadorElegido;
	private static int cartaElegida;

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
		jugadorActivo = jugador;
	}

	public void init(ArrayList<Jugador> jugadores, Partida partida) {
		try {
			background = ImageIO.read(new File("rombos.jpg"));
			dorso = ImageIO.read(new File("dorso.jpg"));
			cartaAmor = ImageIO.read(new File("IconoCarta.png"));
			guardia = ImageIO.read(new File("cartasImg/guardia.jpg"));
			princesa = ImageIO.read(new File("cartasImg/princesa.jpg"));
			principe = ImageIO.read(new File("cartasImg/principe.jpg"));
			rey = ImageIO.read(new File("cartasImg/rey.jpg"));
			mucama = ImageIO.read(new File("cartasImg/mucama.jpg"));
			baron = ImageIO.read(new File("cartasImg/baron.jpg"));
			condesa = ImageIO.read(new File("cartasImg/condesa.jpg"));
			sacerdote = ImageIO.read(new File("cartasImg/sacerdote.jpg"));

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.jugadores = jugadores;
		CantJugadores = jugadores.size();
		this.partida = partida;

		drawPanel = new DrawPanel();
		getContentPane().add(drawPanel);
		turnoJugador(jugadores.get(0));

		lista = new JDialog(this, "Lista Jugadores", true);
		lista.getContentPane().setLayout(new BoxLayout(lista.getContentPane(), BoxLayout.X_AXIS));
		lista.setResizable(true);
		lista.setSize(50, 50);

		j1 = new JButton(jugadores.get(0).getNombre());
		lista.getContentPane().add(j1);
		j2 = new JButton(jugadores.get(1).getNombre());
		lista.getContentPane().add(j2);
		j2.setSize(500, 500);
		;

		if (CantJugadores >= 3) {

			j3 = new JButton(jugadores.get(2).getNombre());
			lista.getContentPane().add(j3);
			j3.setBounds(0, 20, 20, 20);
			if (CantJugadores == 4) {
				j4 = new JButton(jugadores.get(3).getNombre());
				j4.setBounds(0, 20, 20, 20);
				lista.getContentPane().add(j4);

			}

		}

		lista.setLocationRelativeTo(null);
		lista.setVisible(false);
		lista.pack();

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
		listaCartas.getContentPane().setLayout(new BoxLayout(listaCartas.getContentPane(), BoxLayout.X_AXIS));
		listaCartas.setResizable(true);
		listaCartas.setSize(643, 65);

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

		listaCartas.setLocationRelativeTo(null);
		listaCartas.setVisible(false);
		listaCartas.pack();

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
		// getContentPane().setBounds(626, 417, 800, 513);
		setResizable(false); // no puedes maximizar/minimizar la ventana
		setBounds(500, 156, 905, 727);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent m) {
				if ((m.getX() >= 340 && m.getX() <= 420 && m.getY() >= 550 && m.getY() <= 670
						&& jugadorActivo.getTamañoMano() > 1)) {
					distDescarte += 30;
					DibujoCarta cartaTiradas = new DibujoCarta(jugadorActivo.getMano(0), 270 + distDescarte, 140);
					dibujos.add(cartaTiradas);
					jugadorActivo.jugarCarta(partida, 0, lista, listaCartas);
					turnoJugador(partida.proximoTurnoJugador(jugadorActivo));
					refresh();
				} else if ((m.getX() >= 200 && m.getX() <= 510)
						&& (m.getY() >= 210 && m.getY() <= 470 && jugadorActivo.getTamañoMano() == 1)) {
					partida.getMazo().darCarta(jugadorActivo);
					tomoCarta = true;
					refresh();
				} else if ((m.getX() >= 450 && m.getX() <= 610 && m.getY() >= 550 && m.getY() <= 670)
						&& jugadorActivo.getTamañoMano() == 2) {
					distDescarte += 30;
					DibujoCarta cartaTiradas = new DibujoCarta(jugadorActivo.getMano(1), 270 + distDescarte, 140);
					dibujos.add(cartaTiradas);
					jugadorActivo.jugarCarta(partida, 1, lista, listaCartas);
					turnoJugador(partida.proximoTurnoJugador(jugadorActivo));
					refresh();
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
			g2.scale(currentDimension.getWidth() / 800, currentDimension.getHeight() / 450);
			g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);
			for (int i = 0; i < 5; i++) {
				g2.drawImage(dorso, 180 + i * 2, 145 + i * 3, 100, 120, this);
				g2.setColor(Color.WHITE);
				g2.drawRect(180 + i * 2, 145 + i * 3, 100, 120);
			}

			if (partida.getReinicio()) {
				dibujos.clear();
				partida.setReinicio(false);
				g2.drawImage(cartaAmor, 100, 100, 50, 35, this);
				distDescarte = 0;
			}

			dibujarCartas(g2, "Dorso", 350, 0);
			dibujarCartas(g2, jugadorActivo.getMano(0).getNombre(), 290, 330);

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setFont(new Font("Segoe Script", Font.HANGING_BASELINE, 15));
			g2.setPaint(Color.WHITE);
			g2.drawImage(cartaAmor, 730, 400, 50, 35, this);
			g2.drawString(jugadorActivo.getNombre(), 510, 430);
			g2.drawString("Afectos: " + String.valueOf(jugadorActivo.getAfectosConseguidos()), 640, 430);

			if (CantJugadores == 3) {
				dibujarCartas(g2, "Dorso", 0, 145);
			}

			if (CantJugadores == 4) {
				dibujarCartas(g2, "Dorso", 0, 145);
				dibujarCartas(g2, "Dorso", 690, 145);

			}

			if (tomoCarta) {
				dibujarCartas(g2, jugadorActivo.getMano(0).getNombre(), 290, 330);
				dibujarCartas(g2, jugadorActivo.getMano(1).getNombre(), 400, 330);
				tomoCarta = false;
			}

			for (DibujoCarta dib : dibujos) {
				dibujarCartas(g2, dib.getCartaDib().getNombre(), dib.getEjeX(), dib.getEjeY());
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
				break;
			}

			bloquearBoton();
			desbloquearBoton();
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(900, 700);

		}
	}

	public void refresh() {
		drawPanel.repaint();
	}

	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		paint(g);
	}

	public Tablero() {
		contentPane = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		getContentPane().setBounds(626, 417, 800, 513);
		setContentPane(contentPane);

	}
}
