package Cliente;

import java.awt.EventQueue;
import java.awt.List;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

import InterfaceGrafica.ComenzarRonda;
import InterfaceGrafica.Salas;
import servidor.Paquete;
import servidor.SalaSerealizable;
import servidor.Servidor;

public class Cliente {

	private String ip;
	private int puerto;
	private BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
	private DataInputStream dis;
	private DataOutputStream dos;
	private ObjectInputStream disObj = null;
	private ObjectOutputStream dosObj = null;
	private Socket socket;
	private Integer tocoBoton = -1;
//	private  Map<String, ArrayList<Paquete>> salas ;
	private ArrayList<SalaSerealizable> salas;
	private Salas sala = new Salas();

	public Cliente(String ip, int puerto) throws UnknownHostException, IOException {
		this.puerto = puerto;
		this.ip = ip;
		conectarse();
	}

	public void conectarse() {
		try {

			socket = new Socket(ip, puerto);
			dosObj = new ObjectOutputStream(socket.getOutputStream());
			dosObj.flush();
			disObj = new ObjectInputStream(socket.getInputStream());

			salas = (ArrayList<SalaSerealizable>) disObj.readObject();
			for (SalaSerealizable sal : salas) {
				sala.agregarSalas(sal);
			}
			activarInterfaz();

//			HiloEscuchar hiloEscucha = new HiloEscuchar(dis,sala);
//			hiloEscucha.start();
//			while (hiloEscucha.isAlive()) {
//				String msj = keyRead.readLine();
//				dos.writeUTF(msj);
//				Thread.sleep(200);
//			}
//			cerrarConexion();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void activarInterfaz() {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sala.init(disObj, dosObj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void cerrarConexion() {
		try {
			keyRead.close();
			dis.close();
			disObj.close();
			dosObj.close();
			dos.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
