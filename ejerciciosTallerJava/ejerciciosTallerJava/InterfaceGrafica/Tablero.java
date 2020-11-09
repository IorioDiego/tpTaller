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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import org.omg.CORBA.portable.ValueBase;

import cartas.Carta;
import cartas.Guardia;
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
	private int distDescarte=0;
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



	public void turnoJugador(Jugador jugador) {
		jugadorActivo = jugador;
	}

	public void init(ArrayList<Jugador> jugadores,Partida partida) {
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

		pack();
		setTitle("LoveLetter");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		requestFocusInWindow();
		// getContentPane().setBounds(626, 417, 800, 513);
		setResizable(false); // no puedes maximizar/minimizar la ventana
		setBounds(0, 0, 905, 727);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent m) {
				if ((m.getX() >= 340 && m.getX() <= 420 && m.getY() >= 550 && m.getY() <= 670 && jugadorActivo.getTamañoMano() > 1 )) {
					distDescarte+=30;
					DibujoCarta cartaTiradas = new DibujoCarta(jugadorActivo.getMano(0), 270 + distDescarte , 140);
					dibujos.add(cartaTiradas);
					jugadorActivo.jugarCarta(partida,0);
					turnoJugador(partida.proximoTurnoJugador(jugadorActivo));
					refresh();
				}else if ((m.getX() >= 200 && m.getX() <= 510) && (m.getY() >= 210 && m.getY() <= 470 && jugadorActivo.getTamañoMano() == 1 )) {
					partida.getMazo().darCarta(jugadorActivo);
					tomoCarta = true;
					refresh();
				}else if ((m.getX() >= 450 && m.getX() <= 610 && m.getY() >= 550 && m.getY() <= 670) && jugadorActivo.getTamañoMano() == 2) {
					distDescarte+=30;
					DibujoCarta cartaTiradas = new DibujoCarta(jugadorActivo.getMano(1), 270 + distDescarte , 140);
					dibujos.add(cartaTiradas);
					jugadorActivo.jugarCarta(partida,1);
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

			dibujarCartas(g2, "Dorso", 350, 0);
			dibujarCartas(g2, jugadorActivo.getMano(0).getNombre(), 290, 330);		
			
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setFont(new Font("Segoe Script", Font.HANGING_BASELINE ,15));
	        g2.setPaint(Color.WHITE);
	        g2.drawImage(cartaAmor, 730, 400, 50, 35, this);
	        g2.drawString(jugadorActivo.getNombre(), 510, 430);
	        g2.drawString("Afectos: " + String.valueOf(jugadorActivo.getAfectosConseguidos()),640, 430);
			
			
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
