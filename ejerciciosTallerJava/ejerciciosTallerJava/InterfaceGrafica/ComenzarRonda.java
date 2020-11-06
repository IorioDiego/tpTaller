package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import game.Jugador;
import game.Partida;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Canvas;

public class ComenzarRonda extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private BufferedImage backGround;
	private DrawPanel drawPanel;

	public void init() {
		try {
			backGround = ImageIO.read(new File("header.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		drawPanel = new DrawPanel();
		contentPane.add(drawPanel);

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		requestFocusInWindow();
	}

	private class DrawPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			Dimension size = new Dimension(backGround.getWidth(null), backGround.getHeight(null));
			Dimension currentDimension = getContentPane().getSize();
			g2.scale(currentDimension.getWidth() / 460 , currentDimension.getHeight() /250);
			g2.drawImage(backGround, 0, 0, getWidth(), getHeight(), this);
		
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(460, 215);
		}
	}

	public ComenzarRonda() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JButton btnNewButton = new JButton("Comenzar Juego");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				ArrayList<Jugador> jugadores = new ArrayList<>();
				jugadores.add(new Jugador("Dieguien", 3));
				jugadores.add(new Jugador("Lucardo", 2));
				jugadores.add(new Jugador("Tomarson", 1));
				jugadores.add(new Jugador("Leandrovich", 4));

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Partida partida = new Partida(3, 4, jugadores);
							partida.iniciarPartida();
							Tablero frame = new Tablero();
							frame.setVisible(true);
							frame.init(jugadores);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				});
			}
		});
		contentPane.add(btnNewButton, BorderLayout.SOUTH);	
	}
}
