package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Scrollbar;
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
import javax.swing.SwingConstants;
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
	Font fuente = new Font("Calibri", Font.PLAIN, 16);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() {

		this.setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

		textField = new JTextField("Ingrese Nickname");
		textField.setBounds(10, 6, 416, 21);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(textField);
		textField.setColumns(10);

		setTitle("Seleccion de sala");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		textField.setFont(fuente);

		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("");
			}
		});
		
		GridLayout layout = new GridLayout(1,2);
	    layout.setHgap(55);
		
		JPanel botones =new JPanel();
		botones.setLayout(layout);

		JButton btnCrear = new JButton("Crear Sala");
		JButton btnIngresar = new JButton("Ingresar a Sala");
		botones.add(btnCrear);
		botones.add(btnIngresar);
		botones.setBounds(55, 232, 326, 21);
		getContentPane().add(botones);
		
		DefaultListModel dlm = new DefaultListModel();
        ArrayList<String> salas = new ArrayList<String>();
        
        for (int i = 0; i < 100; i++) {
        	salas.add("Sala " + (i+1));
        }
        
        for (String item : salas) {
            dlm.addElement(item);
          }

        JList list = new JList(dlm);
        JScrollPane scroll = new JScrollPane(list);
        
        //list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setMinimumSize(new Dimension(150,100));
        setPreferredSize (new Dimension (329, 124));
        setLayout (null);
        add (list);
        add (scroll);
        scroll.getViewport().add(list);
        scroll.setBounds(10, 37, 416, 185);
        list.setBounds(10, 37, 416, 185);
        //final DefaultListModel dlm = new DefaultListModel();
        
        
		list.addListSelectionListener(new ListSelectionListener() {

			 @Override
             public void valueChanged(ListSelectionEvent e) {
               if (e.getFirstIndex() != -1 && !e.getValueIsAdjusting()) {
                 int firstIndex = e.getFirstIndex();
                 list.removeListSelectionListener(this);
                 list.clearSelection();
                 dlm.remove(firstIndex);
                 salas.remove(firstIndex);
                 list.addListSelectionListener(this);
               }
             }
           }
           );

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
