package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.GuardedObject;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cartas.Baron;
import cartas.Condesa;
import cartas.Guardia;
import cartas.Mucama;
import cartas.Princesa;
import cartas.Principe;
import cartas.Rey;
import cartas.Sacerdote;

import javax.swing.JLabel;




public class Tablero extends JFrame {

	private ArrayList <Jugador> players = new ArrayList<>();
	
	private JPanel contentPane;
	private BufferedImage dorso;
	private DrawPanel drawPanel;
	private JLabel label;	
	private BufferedImage  background;
	
	
	private BufferedImage  guardia;
	private BufferedImage  princesa;
	private BufferedImage  principe;
	private BufferedImage  rey;
	private BufferedImage  condesa;
	private BufferedImage  mucama;
	private BufferedImage  sacerdote;
	private BufferedImage  baron;
	
	private int xG=100;
	private int yG=100;
	
	/**
	 * Launch the application.
	 */
	

	public void init(ArrayList <Jugador> jugadores) {
		try {
			background = ImageIO.read(new File("terciopelo.jpg"));
			dorso= ImageIO.read(new File("dorso.jpg"));	
			guardia= ImageIO.read(new File("cartasImg/guardia.jpg"));
			princesa= ImageIO.read(new File("cartasImg/princesa.jpg"));
			principe=ImageIO.read(new File("cartasImg/principe.jpg"));
			rey=ImageIO.read(new File("cartasImg/rey.jpg"));
			mucama=ImageIO.read(new File("cartasImg/mucama.jpg"));
			baron= ImageIO.read(new File("cartasImg/baron.jpg"));
			condesa=ImageIO.read(new File("cartasImg/condesa.jpg"));
 			sacerdote=ImageIO.read(new File("cartasImg/sacerdote.jpg"));
 			players = jugadores;
 			
 			
 			
			
//			label = new JLabel("Titulo");
//			label.setLayout(null);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		drawPanel = new DrawPanel();
		add(drawPanel);
//		drawPanel.add(label);
		
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		requestFocusInWindow();
			
	}
	
	public void display() {
		drawPanel.repaint();
	}

	private class DrawPanel extends JPanel {
	
		private static final long serialVersionUID = 1L;

		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
		
			Graphics2D g2 = (Graphics2D) g;
			Dimension size =  new Dimension (background.getWidth(null),background.getHeight(null));
			Dimension currentDimension = getContentPane().getSize();
			g2.scale(currentDimension.getWidth() / 800, currentDimension.getHeight() / 450);
			//g2.drawImage(background, null, 0, 0);
			g2.drawImage(background,0,0,getWidth(),getHeight(),this);
			
		
			comenzarRonda(g2,players);
			
			
//			g2.drawImage(princesa,420,100,100,120, this);
//			g2.drawImage(condesa,500,100,100,120, this);
//			g2.drawImage(sacerdote,590,100,100,120, this);
//			g2.drawImage(principe,350,250,100,120, this);
//			
//			g2.drawImage(baron,350,250,100,120, this);
			
//			for (int i = 0; i < 7; i++) {
//				//g2.setColor(Color.ORANGE);
//				//g2.fillRect(30+i*4,100+i*2,50,60);
//				//g2.setColor(Color.BLACK);
//				//g2.drawRect(30+i*4,100+i*2,50,60);
//				g2.drawImage(dorso,200+i*2,100+i*3,100,120, this);
//				g2.setColor(Color.WHITE);
//				g2.drawRect(200+i*2,100+i*3,100,120);
//			}
//			g2.drawImage(guardia,200,100,50,70, this);
			
		}
		
		public void comenzarRonda(Graphics g2,ArrayList<Jugador> players)
		{
			for (int i = 0; i < 7; i++) {
			g2.drawImage(dorso,200+i*2,100+i*3,100,120, this);
			g2.setColor(Color.WHITE);
			g2.drawRect(200+i*2,100+i*3,100,120);
			}
			dibujarCartas(g2, players.get(0));
		}
			
		
		
		public void dibujarCartas(Graphics g2,Jugador jugador)
		{
			switch (jugador.getMano().getNombre()) 
	        {
	            case "Princesa":  g2.drawImage(princesa,350,250,100,120,this);
	                     break;
	            case "Condesa":  g2.drawImage(condesa,350,250,100,120, this);
	                     break;
	            case "Principe":  g2.drawImage(principe,350,250,100,120, this);
	                     break;
	            case "Mucama":  g2.drawImage(mucama,350,250,100,120, this);
	                     break;
	            case "Baron":   g2.drawImage(baron,350,250,100,120, this);
	                     break;
	            case "Sacerdote": g2.drawImage(sacerdote,350,250,100,120, this);
	                     break;
	            case "Guardia":   g2.drawImage(guardia,350,250,100,120, this);
	                     break;
	            case "Rey":  g2.drawImage(rey,350,250,100,120, this);
                		 break;
	        }
		}	

		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(1920, 1080);
		}
		
		
		
	}
	public void move () {
		xG+=2;
		yG+=2;
	}
	
	private class iniciarPartida extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
		
			Graphics2D g2 = (Graphics2D) g;
			for (int i = 0; i < 16; i++) {
				g2.setColor(Color.ORANGE);
				g2.fillRect(30+i*8,100+i*2,100,200);
				g2.setColor(Color.BLACK);
				g2.drawRect(30+i*8,100+i*2,100,200);
			}
		
			
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(800, 513);
		}
	
	}
	
	public void refresh() {
		drawPanel.repaint();
	}
	
	/**
	 * Create the frame.
	 */
	public Tablero() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(626, 417, 800, 513);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	

	
	
	

}
