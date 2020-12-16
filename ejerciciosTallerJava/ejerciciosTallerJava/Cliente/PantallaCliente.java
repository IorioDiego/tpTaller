package Cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class PantallaCliente extends JFrame {

	private JPanel contentPane;
	private JTextField txtIngreseIpDel;
	private String ipServer = "";
	private boolean ingreso = false;
	private JFrame pantalla = this;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaCliente frame = new PantallaCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PantallaCliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		txtIngreseIpDel = new JTextField();
		txtIngreseIpDel.setText("Ingrese ip del servidor");
		txtIngreseIpDel.setColumns(10);

		JLabel lblNewLabel = new JLabel("IP-SERVIDOR");

		JLabel lblConfiguracionJugador = new JLabel("CONFIGURACION JUGADOR");

		JButton conectarse = new JButton("Conectar");
		conectarse.setEnabled(false);
		conectarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Cliente client = new Cliente(ipServer, 20000);
					client.start();
					pantalla.dispose();
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, "Ha ocurrido un problema con el server reinicie la aplicacion",
							"Server", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		});

		JButton reingresoIp = new JButton("Ingresar otra ip");
		reingresoIp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ingreso) {
					txtIngreseIpDel.setEnabled(true);
					ingreso = false;
					conectarse.setEnabled(false);
				}
			}
		});
		reingresoIp.setEnabled(false);

		txtIngreseIpDel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!txtIngreseIpDel.getText().equals("")) {
						ipServer = txtIngreseIpDel.getText();
						txtIngreseIpDel.setEnabled(false);
						conectarse.setEnabled(true);
						ingreso = true;
						reingresoIp.setEnabled(true);
					}
				}
			}
		});

		txtIngreseIpDel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!ingreso)
					txtIngreseIpDel.setText("");
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(22)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
					.addComponent(txtIngreseIpDel, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(167)
					.addComponent(conectarse)
					.addContainerGap(180, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(242, Short.MAX_VALUE)
					.addComponent(reingresoIp, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
					.addGap(57))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(127)
					.addComponent(lblConfiguracionJugador, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(141, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblConfiguracionJugador, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
					.addGap(42)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtIngreseIpDel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(reingresoIp)
					.addPreferredGap(ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
					.addComponent(conectarse)
					.addGap(23))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
