package InterfaceGrafica;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import comandos.CrearSala;
import servidor.Paquete;
import servidor.SalaSerealizable;
import servidor.SettingsPartida;

public class Salas extends JFrame {

	private ArrayList<SalaSerealizable> salas;
	private JPanel contentPane;
	private JPanel nickname = new JPanel();
	private JTextField textField;
	private JList<String> list;
	private Integer tocoBoton;
	private static Integer indexSala;
	private JButton crear;
	private JButton Ingresar;
	private ObjectInputStream disObj = null;
	private ObjectOutputStream dosObj = null;

	Font fuente = new Font("Calibri", Font.PLAIN, 16);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init(Integer IndexSala,ObjectInputStream disObject,ObjectOutputStream dosObject) {

		JPanel gui = new JPanel();
		tocoBoton = IndexSala;
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(Box.createVerticalStrut(5));
		textField = new JTextField("Ingrese Nickname", 25);
		disObj = disObject;
		dosObj = dosObject;
		gui.add(textField);

		gui.setAlignmentX(Component.CENTER_ALIGNMENT);

		gui.setPreferredSize(new Dimension(400, 35));
		gui.setMaximumSize(new Dimension(400, 35));

		this.getContentPane().add(gui);
		getContentPane().add(Box.createVerticalStrut(5));
		setTitle("Seleccion de sala");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		textField.setFont(fuente);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("");
			}
		});

		
		GridLayout layout = new GridLayout(1, 2);
		layout.setHgap(55);

		JPanel botones = new JPanel();
		botones.setLayout(layout);

		JButton btnCrear = new JButton("Crear Sala");
		JButton btnIngresar = new JButton("Ingresar a Sala");
		botones.add(btnCrear);
		btnCrear.setEnabled(false);
		btnIngresar.setEnabled(false);
		botones.add(btnIngresar);

		botones.setPreferredSize(new Dimension(300, 25));
		botones.setMaximumSize(new Dimension(300, 25));
//        DefaultListModel dlm = new DefaultListModel();
//        ArrayList<String> salas = new ArrayList<String>();
//        
//        for (int i = 0; i < 100; i++) {
//            salas.add("Sala " + (i+1));
//        }
//        
//        for (String item : salas) {
//            dlm.addElement(item);
//          }
//
// 		
		  
		DefaultListModel dlm = new DefaultListModel();

		for (SalaSerealizable item : salas) {
			dlm.addElement(item);
		}

		JList list = new JList(dlm);
		JScrollPane scroll = new JScrollPane(list);

		((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		add(list);
		add(scroll);
		scroll.getViewport().add(list);
		list.setEnabled(false);

		// btnIngresar.setEnabled(false);
		btnIngresar.addActionListener(new ActionListener() {

			@Override
			
			public void actionPerformed(ActionEvent e) {
				try {
					dosObject.writeObject(String.valueOf(indexSala));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnCrear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				InterfazCrearSala2 crearSala = new InterfazCrearSala2();
				crearSala.init(disObject,dosObject);
				dispose();
			}
		});

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getFirstIndex() != -1 && !e.getValueIsAdjusting()) {
					indexSala = list.getSelectedIndex();
					btnIngresar.setEnabled(true);

					/// Borrado---
					// list.removeListSelectionListener(this);
					// list.clearSelection();
					// dlm.remove(firstIndex);
					// salas.remove(firstIndex);
					// list.addListSelectionListener(this);
				}
			}
		});
		
		
		 textField.addKeyListener(new KeyAdapter() {
	           @Override
	           public void keyPressed(KeyEvent e) {
	               if(e.getKeyCode() == KeyEvent.VK_ENTER){
	                  textField.getText();
	                  btnCrear.setEnabled(true);
	          		  list.setEnabled(true);
	          		  btnIngresar.setEnabled(true);
	          		  textField.setEnabled(false);
	               }
	           }
	       });
		
		
		getContentPane().add(Box.createVerticalStrut(10));
		getContentPane().add(botones);
		getContentPane().add(Box.createVerticalStrut(10));
		setTitle("Seleccion de salas");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		requestFocusInWindow();

		setResizable(false);
		setBounds(500, 250, 410, 280);
	}

	public Salas() {
		contentPane = new JPanel();
		// this.partida = partida;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBounds(200, 117, 1366, 768);
		// getContentPane().setBounds(626, 417, 800, 513);**7
		setContentPane(contentPane);
		salas = new ArrayList<>();

	}
	
	

	public ArrayList<SalaSerealizable> getSalas() {
		return salas;
	}

	public static int getSala() {
		return indexSala;
	}

	public void setSalas(ArrayList<SalaSerealizable> salas) {
		this.salas = salas;
	}

	public void agregarSalas(SalaSerealizable s) {
		this.salas.add(s);

	}
}
