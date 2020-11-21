package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import game.Jugador;
import game.Partida;

public class Salas extends JFrame {

	private JPanel contentPane;
	private JPanel nickname = new JPanel();
	private JTextField textField;
	private JList<String> list;
	private int index;
	private JButton crear;
	private JButton Ingresar;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() {

		String[] nombres = { "jony", "lucas", "tomy" };
		JPanel botones = new JPanel();
		
		JPanel input = new JPanel();
		JPanel lista = new JPanel();
		
		botones.setLayout(new GridLayout(1,2));
		//lista.setLayout(new GridLayout(1,1));
		lista.setLayout(new BorderLayout());
		// contentPane.
		nickname.setLayout(new GridLayout(3, 1));
		textField = new JTextField("Nikname:",25);
		list = new JList<String>(nombres);
		
		list.setMaximumSize(new Dimension(50, 50));  // this line does not do the job
        list.setMinimumSize (new Dimension (50,50));
        
       // DefaultListModel modelo = (DefaultListModel) list.getModel();
		//modelo.remove(index);
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				
				int index = list.getSelectedIndex();
				
				System.out.println(index);
			}
		});
		

		//list.setVisibleRowCount(20);
		// nickname.getPreferredSize(800,800);
		// nickname.setBorder(new TitledBorder("Jugadores"));
		// nickname.setBackground(Color.decode("#f7db97"));
		
		crear = new JButton("Crear Sala");
		Ingresar = new JButton("Ingresar Sala");

		input.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 15));
		botones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
		//nickname.add(textField, BorderLayout.CENTER);
		input.add(textField);
		nickname.add(input);
		nickname.add(list);
		botones.add(crear);
		botones.add(Ingresar);
		nickname.add(botones);
		
		// textField.setColumns(10);
		getContentPane().add(nickname);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		pack();
		setTitle("LoveLetter");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		requestFocusInWindow();

	}

	public Salas() {
		contentPane = new JPanel();
		// this.partida = partida;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBounds(200, 117, 1366, 768);
		// getContentPane().setBounds(626, 417, 800, 513);**7
		setContentPane(contentPane);

	}

	public static void main(String[] args) {
		Salas sala = new Salas();
		sala.init();
	}
}
