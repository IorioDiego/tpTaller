package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	Font fuente = new Font("Calibri", Font.PLAIN,16);
	 
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
		lista.setLayout(new GridLayout(1,1));
		//lista.setLayout(new BorderLayout());
		// contentPane.
		nickname.setLayout(new GridLayout(3, 1));
		textField = new JTextField("Ingrese nikname:",25);
		textField.setFont(fuente);
		
		textField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                textField.setText("");
            }
        });
		
		list = new JList<String>(nombres);
		
		list.setFixedCellWidth(5);
        
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
		
		//lista.setLayout(new FlowLayout(FlowLayout.CENTER,15,15));
		input.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		botones.setLayout(new FlowLayout(FlowLayout.CENTER, 45, 15));
		
		input.add(textField);
		lista.add(list);
		botones.add(crear);
		botones.add(Ingresar);
		
		nickname.add(input);
		nickname.add(lista);
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
