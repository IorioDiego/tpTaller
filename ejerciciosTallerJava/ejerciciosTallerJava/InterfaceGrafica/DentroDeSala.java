package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import servidor.SalaSerealizable;

public class DentroDeSala extends JFrame {

	private JPanel contentPane;
	
	private JTextField textField;
	//private JLabel jugadores;

	private JButton comenzar;
	
	
	public void init(ObjectInputStream disObject, ObjectOutputStream dosObject) {
		
		String[] valores= {"gato","perro","loro","pollo","vaca"};

		JPanel panelIzq = new JPanel();
		JPanel panelDer = new JPanel();
		JPanel panelAbajo = new JPanel();
		JPanel  cBoxContainer1 =new JPanel();
		JPanel  cBoxContainer2 =new JPanel();
		
		JPanel topContainer = new JPanel();
		panelIzq.setLayout(new BoxLayout(panelIzq, BoxLayout.Y_AXIS));
		panelDer.setLayout(new BoxLayout(panelDer, BoxLayout.Y_AXIS));
		topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.X_AXIS));
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JLabel jugadores = new JLabel("Jugadores:     ");
		JLabel combolabel1 = new JLabel("combolabel1:   ");
		JLabel combolabel2 = new JLabel("combolabel2:   ");
		
		jugadores.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		
		JList lista = new JList(valores);
		lista.setPreferredSize(new Dimension(300, 300));
		lista.setMaximumSize(new Dimension(300, 300));
		
		panelIzq.add(jugadores);
		panelIzq.add(lista);
		
		panelIzq.setPreferredSize(new Dimension(500, 300));
		panelIzq.setMaximumSize(new Dimension(500, 300));
		
		panelDer.setPreferredSize(new Dimension(500, 300));
		panelDer.setMaximumSize(new Dimension(500, 300));		

		JComboBox combo1 = new JComboBox(valores);
		JComboBox combo2 = new JComboBox(valores);
		
		panelAbajo.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		
		cBoxContainer1.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		cBoxContainer1.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		combo1.setPreferredSize(new Dimension(160, 25));
		combo1.setMaximumSize(new Dimension(160, 25));
		
		cBoxContainer2.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
		cBoxContainer2.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
		combo2.setPreferredSize(new Dimension(160, 25));
		combo2.setMaximumSize(new Dimension(160, 25));

		cBoxContainer1.add(combolabel1);
		cBoxContainer1.add(combo1);
		cBoxContainer2.add(combolabel2);
		cBoxContainer2.add(combo2);
		panelDer.add(cBoxContainer1);
		panelDer.add(cBoxContainer2);
		JButton comenzar = new JButton("Comenzar Partida");
		panelAbajo.add(comenzar);
		topContainer.add(panelIzq);
		topContainer.add(panelDer);
		getContentPane().add(topContainer);
		getContentPane().add(panelAbajo);
		
        setTitle("Configuracion de la Partida");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);
        setBounds(500, 250, 500, 270);
	}
	
	public DentroDeSala(){
		contentPane = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBounds(200, 117, 1366, 768);
		setContentPane(contentPane);
	}
	
//	public static void main(String[] args) {
//	DentroDeSala sala = new DentroDeSala();
//	sala.init();
//	}
}
