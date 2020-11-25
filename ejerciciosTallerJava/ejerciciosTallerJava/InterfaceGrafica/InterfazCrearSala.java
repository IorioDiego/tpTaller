package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import java.awt.Window.Type;
import java.awt.SystemColor;

public class InterfazCrearSala extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazCrearSala frame = new InterfazCrearSala();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfazCrearSala() {
		setResizable(false);
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(InterfazCrearSala.class.getResource("/loveImg/IconoCarta.png")));
		setTitle("Configuracion sala");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		init();
	}

	public void init() {

		JButton btnNewButton = new JButton("Crear sala");
		btnNewButton.setBackground(Color.LIGHT_GRAY);

		JLabel lblNewLabel = new JLabel("Cantidad de jugadores");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));

		JLabel lblCantidadPrendasDe = new JLabel("Cantidad prendas de amor");
		lblCantidadPrendasDe.setBackground(Color.LIGHT_GRAY);
		lblCantidadPrendasDe.setFont(new Font("Tahoma", Font.BOLD, 13));

		JComboBox cantJugadores = new JComboBox();
		cantJugadores.setBackground(Color.LIGHT_GRAY);
		cantJugadores.setModel(new DefaultComboBoxModel(new String[] {"2", "3", "4"}));
		cantJugadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			String selectValue = (String)cantJugadores.getSelectedItem();
			int valor = Integer.valueOf(selectValue);
			System.out.println("se selecciono el valor: " + valor);
			}
		});
		
	
		JComboBox cantPrendasAmor = new JComboBox();
		cantPrendasAmor.setBackground(Color.LIGHT_GRAY);
		cantPrendasAmor.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		
		

		JLabel lblConfiguracionesBasicasDe = new JLabel("Configuraciones basicas de sala");
		lblConfiguracionesBasicasDe.setFont(new Font("Tahoma", Font.BOLD, 13));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblCantidadPrendasDe, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
							.addGap(71)
							.addComponent(cantPrendasAmor, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
							.addGap(62)
							.addComponent(cantJugadores, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblConfiguracionesBasicasDe, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(126)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(40, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblConfiguracionesBasicasDe)
					.addGap(38)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(cantJugadores, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCantidadPrendasDe)
						.addComponent(cantPrendasAmor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
					.addComponent(btnNewButton)
					.addGap(29))
		);
		contentPane.setLayout(gl_contentPane);	
	}

}
