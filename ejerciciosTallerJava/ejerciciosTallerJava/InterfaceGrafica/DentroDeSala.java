package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

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
	private JLabel jugadores;

	private JButton comenzar;
	
	
	public void init() {

		
		JPanel panelDer = new JPanel();
		JPanel cBoxOrden = new JPanel();
		JPanel cBoxComienzo = new JPanel();
		JPanel boton = new JPanel();
		JButton comenzar = new JButton("Comenzar Partida");
		
		boton.setLayout(new GridLayout(1,1));
		
		
		JPanel panelIzq = new JPanel(); 
		JPanel total = new JPanel();
		 this.setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		jugadores = new JLabel("jugadores");
		panelIzq.setLayout(new BoxLayout(panelIzq,BoxLayout.PAGE_AXIS ));
		panelDer.setLayout(new BoxLayout(panelDer, BoxLayout.Y_AXIS ));
        panelIzq.add(jugadores);
        
        
        
//        panelIzq.setAlignmentX(Component.RIGHT_ALIGNMENT);
    
      
     
        
        
        
        
        
        
        DefaultListModel dlm = new DefaultListModel();
        
        JList lista = new JList(dlm);
        lista.setPreferredSize(new Dimension(300, 250));
        lista.setMaximumSize(new Dimension(300, 250));
        dlm.addElement("Jony");
   
        panelIzq.add(lista);
    
//        panelDer.setBorder (BorderFactory.createEmptyBorder (0, 25, 0, 0));

      
    	JComboBox orden = new JComboBox();
    	JComboBox comienzo= new JComboBox();
    	cBoxOrden.add(orden);
    	cBoxComienzo.add(comienzo);
    	
    	
    	cBoxOrden.setBorder (BorderFactory.createEmptyBorder (0, 0, 0, 0));
    	cBoxComienzo.setBorder (BorderFactory.createEmptyBorder (100, 0, 0, 0));
    	orden.addItem("izquierda");
    	orden.addItem("derecha");
  
    
          
    	comienzo.setPreferredSize(new Dimension(120, 20));
         comienzo.setMaximumSize(new Dimension(120, 20)); 
         orden.setPreferredSize(new Dimension(120, 20));
         orden.setMaximumSize(new Dimension(120, 20)); 
         comienzo.addItem("Jony");
         comienzo.addItem("Lucas");
         comienzo.addItem("Diego");
         
    	panelDer.add(cBoxComienzo);
    	panelDer.add(cBoxOrden);
    	
    	this.add(panelIzq);

         this.add(panelDer);

         boton.add(comenzar);
         
         this.add(boton);
     
     
        	
//        ((JComponent) getContentPane()).setBorder (BorderFactory.createEmptyBorder (0, 5, 0, 5));
//        
//        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        gui.add(list);
//        
//        
//        gui.setPreferredSize(new Dimension(400, 35));
//        gui.setMaximumSize(new Dimension(400, 35));
//        this.getContentPane().add(panelIzq);
        getContentPane().add(Box.createVerticalStrut(5)); 
        setTitle("Configuracion de la Partida");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);
        setBounds(500, 250, 400, 500);
	}
	
	public DentroDeSala(){
		contentPane = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBounds(200, 117, 1366, 768);
		setContentPane(contentPane);
	}
	
	public static void main(String[] args) {
	DentroDeSala sala = new DentroDeSala();
	sala.init();
	}
}
