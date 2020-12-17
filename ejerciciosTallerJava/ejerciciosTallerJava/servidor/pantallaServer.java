package servidor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class pantallaServer extends JFrame {

	private JPanel contentPane;
	private boolean serverIniciado = false;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pantallaServer frame = new pantallaServer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public pantallaServer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("SERVER ");

		JLabel lblMantengaEstaVentana = new JLabel("MANTENGA ESTA VENTANA ABIERTA MIENTRAS USTED SEA EL SERVER");
		lblNewLabel.setLabelFor(lblMantengaEstaVentana);
		
		JLabel labelPrender = new JLabel("Server inicializado.....");
		
		JLabel labelApagar = new JLabel("Server apagado....");
		
		labelApagar.setVisible(false);
		labelPrender.setVisible(false);

		JButton btnApagarServer = new JButton("Apagar server");
		btnApagarServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (serverIniciado) {
					Servidor.setApagar(true);
					serverIniciado = false;
					labelApagar.setVisible(true);
					labelPrender.setVisible(false);
				} else
					JOptionPane.showMessageDialog(null, "El server esta apagado", "Server", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		

		JButton btnIniciarServer = new JButton("Iniciar server");
		btnIniciarServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!serverIniciado) {
						Servidor server = new Servidor(20000);
						server.start();
						serverIniciado = true;
						labelPrender.setVisible(true);
						labelApagar.setVisible(false);
					} else
						JOptionPane.showMessageDialog(null, "El server ya ha sido prendido", "Server",
								JOptionPane.ERROR_MESSAGE);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(51, Short.MAX_VALUE)
					.addComponent(btnApagarServer, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addGap(85)
					.addComponent(btnIniciarServer, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addGap(58))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(185)
					.addComponent(lblNewLabel)
					.addContainerGap(208, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMantengaEstaVentana, GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
					.addGap(31))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(114)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(labelApagar, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelPrender, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(151, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(27)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblMantengaEstaVentana, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnApagarServer, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnIniciarServer, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(labelPrender, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(labelApagar)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		cerrar();

	}

	public void cerrar() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				confirmarSalida();
			}
		});
	}

	public void confirmarSalida() {
		JOptionPane.showMessageDialog(this, "Si una partida se encuentra en ejecucion pueden ocurrir problemas", "Server",
				JOptionPane.WARNING_MESSAGE);
		int valor = JOptionPane.showConfirmDialog(this, "Desea salir de la aplicacion servidora", "Cerrar", JOptionPane.WARNING_MESSAGE);
		if (valor == JOptionPane.YES_OPTION) {
			if (serverIniciado) {
				Servidor.setApagar(true);
				serverIniciado = false;
			}
			System.exit(0);
		}
	}
}
