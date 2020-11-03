package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;




public class Tablero extends JFrame {

	private JPanel contentPane;
	private BufferedImage  background;
	private DrawPanel drawPanel;

	/**
	 * Launch the application.
	 */
	
	public void init() {
		try {
			background = ImageIO.read(new File("algo.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		drawPanel = new DrawPanel();
		getContentPane().add(drawPanel);
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		requestFocusInWindow();
			
	}
	
	
	private class DrawPanel extends JPanel {
		private static final long serialVersionUID = 91574813372177663L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			Dimension currentDimension = getContentPane().getSize();
			g2.scale(currentDimension.getWidth() / 800, currentDimension.getHeight() / 450);

			g2.drawImage(background, null, 0, 0);
	
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(626, 417);
		}
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tablero frame = new Tablero();
					frame.setVisible(true);
					frame.init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Tablero() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(626, 417, 558, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}
	
	

}
