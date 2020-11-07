package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
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

import cartas.Carta;
import cartas.Guardia;
import game.Jugador;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;

public class Tablero extends JFrame {

	private ArrayList<Jugador> jugadores = new ArrayList<>();

	private ArrayList <DibujoCarta> dibujos = new ArrayList<>();
	
	private JPanel contentPane;
	private BufferedImage dorso;
	private DrawPanel drawPanel;

	private BufferedImage background;
	private Carta carta = new Guardia();
	private boolean tocoCarta = false;
	private int CantJugadores;
	private int anchoCarta=100;
	private int largoCarta=120;

	private BufferedImage guardia;
	private BufferedImage princesa;
	private BufferedImage principe;
	private BufferedImage rey;
	private BufferedImage condesa;
	private BufferedImage mucama;
	private BufferedImage sacerdote;
	private BufferedImage baron;



	private ManoAbajo mano;
	
	private boolean tomoCarta= false;
	/**
	 * @wbp.nonvisual location=471,489
	 */
	private JLabel dibujito ;

	/**
	 * Launch the application.
	 */

	public void init(ArrayList<Jugador> jugadores) {
		try {
			this.jugadores = jugadores;
			CantJugadores = jugadores.size();
			background = ImageIO.read(new File("rombos.jpg"));
			dorso = ImageIO.read(new File("dorso.jpg"));
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

		drawPanel = new DrawPanel();
		getContentPane().add(drawPanel);
		
		
		pack();
		setTitle("LoveLetter");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		requestFocusInWindow();
		//getContentPane().setBounds(626, 417, 800, 513);
		setResizable(false); // no puedes maximizar/minimizar la ventana    
		setBounds(0, 0, 905, 727); 
		
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent m) {
				if ((m.getX() >= 410 && m.getX() <= 510 && m.getY() >= 550 && m.getY() <= 670) ) {
					tocoCarta = true;
					refresh();
					
				}else if( (m.getX() >= 200 && m.getX() <= 510) && (m.getY() >= 145 && m.getY() <= 670)) { 
					tomoCarta= true;
					refresh();
				}
				//refresh();
			
			}
		});


			
		
	}

	

 	private class DrawPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;
		//	Dimension size = new Dimension(background.getWidth(null), background.getHeight(null));
			Dimension currentDimension = getContentPane().getSize();
			g2.scale(currentDimension.getWidth() / 800, currentDimension.getHeight() / 450);
			g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);
			for (int i = 0; i < 5; i++) {
				g2.drawImage(dorso, 200 + i * 2, 145 + i * 3, 100, 120, this);
				g2.setColor(Color.WHITE);
				g2.drawRect(200 + i * 2, 145 + i * 3, 100, 120);
			}
						
			
				DibujoCarta cartaJ1= new DibujoCarta(jugadores.get(0).getMano(),350,0);
				DibujoCarta cartaJ2= new DibujoCarta(jugadores.get(1).getMano(),350,330) ;
				DibujoCarta cartaJ3= new DibujoCarta(jugadores.get(2).getMano(),0,145);
				DibujoCarta cartaJ4= new DibujoCarta (jugadores.get(3).getMano(),690,145);
				
				dibujos.add(cartaJ1);
				dibujos.add(cartaJ2);
				dibujos.add(cartaJ3);
				dibujos.add(cartaJ4);
				

				for (DibujoCarta dib : dibujos) {
					
					dibujarCartas(g2,dib.getCartaDib().getNombre(),dib.getEjeX(),dib.getEjeY());
				}
				
		
//			dibujarCartas(g2, jugadores.get(0).getMano().getNombre(),350,0);
//			dibujarCartas(g2, jugadores.get(1).getMano().getNombre(),350,330);
//			switch (CantJugadores) {
//			case 3: dibujarCartas(g2, jugadores.get(2).getMano().getNombre(),0,145);
//				break;
//			case 4: dibujarCartas(g2, jugadores.get(2).getMano().getNombre(),0,145);
//					dibujarCartas(g2, jugadores.get(3).getMano().getNombre(),690,145);
//				break;
//			}
//		
			
			
			if (tocoCarta) {
				DibujoCarta cartaJ5= new DibujoCarta(jugadores.get(0).getMano(),330,140);
				dibujos.add(cartaJ5);
				
				for (DibujoCarta dib : dibujos) {
					
					dibujarCartas(g2,dib.getCartaDib().getNombre(),dib.getEjeX(),dib.getEjeY());
				}
				//dibujarCartas(g2, jugadores.get(0).getMano().getNombre(),330, 140);
			
				tocoCarta = false;
			}

			if (tomoCarta) {
				
				DibujoCarta cartaJ5= new DibujoCarta(jugadores.get(0).getMano(),470,328);
				dibujos.add(cartaJ5);
				
				for (DibujoCarta dib : dibujos) {
					
					dibujarCartas(g2,dib.getCartaDib().getNombre(),dib.getEjeX(),dib.getEjeY());
				}
				
//				dibujarCartas(g2, jugadores.get(0).getMano().getNombre(),470, 328);
		
				tomoCarta = false;
			}


		}
		
	
		

				
		public void dibujarCartas(Graphics g2,String carta,int x,int y) {
			switch (carta) {
			case "Princesa":
				g2.drawImage(princesa, x, y, anchoCarta, largoCarta, this);
				break;
			case "Condesa":
				g2.drawImage(condesa, x, y, anchoCarta, largoCarta, this);
				break;
			case "Principe":
				g2.drawImage(principe,x, y, anchoCarta, largoCarta, this);
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

	/**
	 * Create the frame.
	 */
//	public Tablero() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(626, 417, 800, 513);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		contentPane.setLayout(new BorderLayout(0, 0));
//		setVisible(true);
//	}
	public Tablero() {
		contentPane = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		getContentPane().setBounds(626, 417, 800, 513);
		setContentPane(contentPane);
		
		JLabel cartaIzq = new JLabel("CartaIzq");
		cartaIzq.setSize(20,180);
		ImageIcon  img=  new ImageIcon("cartasImg/rey.jpg");
		Icon icon  = new ImageIcon(img.getImage().getScaledInstance(120, 170, Image.SCALE_DEFAULT));
		cartaIzq.setIcon(icon);
		

		SpringLayout sLayout = new SpringLayout();
		sLayout.putConstraint(SpringLayout.NORTH, cartaIzq, 488, SpringLayout.NORTH, contentPane);
		sLayout.putConstraint(SpringLayout.WEST, cartaIzq, 242, SpringLayout.WEST, contentPane);
		sLayout.putConstraint(SpringLayout.SOUTH, cartaIzq, 0, SpringLayout.SOUTH, contentPane);
		sLayout.putConstraint(SpringLayout.EAST, cartaIzq, -507, SpringLayout.EAST, contentPane);
		contentPane.setLayout(sLayout);
		contentPane.add(cartaIzq);
		
		cartaIzq.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent m) {
					getGraphics().drawOval(60, 60, 60, 60);
			}
		});
		
		
		
		JLabel cartaDer = new JLabel("CartaDer");
		sLayout.putConstraint(SpringLayout.NORTH, cartaDer, 488, SpringLayout.NORTH, contentPane);
		sLayout.putConstraint(SpringLayout.WEST, cartaDer, 34, SpringLayout.EAST, cartaIzq);
		sLayout.putConstraint(SpringLayout.SOUTH, cartaDer, 0, SpringLayout.SOUTH, contentPane);
		sLayout.putConstraint(SpringLayout.EAST, cartaDer, -318, SpringLayout.EAST, contentPane);
		ImageIcon  i=  new ImageIcon("cartasImg/condesa.jpg");
		Icon icono  = new ImageIcon(i.getImage().getScaledInstance(120, 170, Image.SCALE_DEFAULT));
		cartaDer.setIcon(icono);
		contentPane.add(cartaDer);
		cartaDer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent m) {
					getGraphics().drawOval(100, 60, 60, 60);
			}
		});
		
	}
}
