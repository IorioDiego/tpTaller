package Cliente;

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

import InterfaceGrafica.Salas;
import servidor.Paquete;
import servidor.SalaSerealizable;
import servidor.Servidor;

public class Cliente implements Serializable {

	private String ip;
	private int puerto;
	private BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
	private DataInputStream dis;
	private DataOutputStream dos;
	private ObjectInputStream disObj = null;
	private ObjectOutputStream dosObj = null;
	private Socket socket;
//	private  Map<String, ArrayList<Paquete>> salas ;
	private ArrayList<SalaSerealizable> salas;

	public Cliente(String ip, int puerto) throws UnknownHostException, IOException {
		this.puerto = puerto;
		this.ip = ip;
		conectarse();
	}

	public void conectarse() {
		try {
			Salas sala = new Salas();
			socket = new Socket(ip, puerto);
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			dosObj = new ObjectOutputStream(socket.getOutputStream());
			dosObj.flush();
			disObj = new ObjectInputStream(socket.getInputStream());
//			String s = (String)disObj.readObject();
//			System.out.println(s);

//			salas = (Map<String, ArrayList<Paquete>>) disObj.readObject();
			salas = (ArrayList<SalaSerealizable>) disObj.readObject();
			for (SalaSerealizable sal : salas) {
				sala.agregarSalas(sal);

			}
			sala.init();
			dos.writeUTF(obtenerSala());
			

//			for (Map.Entry<String, ArrayList<Paquete>> entry : salas.entrySet()) {
//			System.out.println(
//					"Nombre Sala: " + entry.getKey() + " Usuarios conetados:" + entry.getValue().size());
//			
//		}
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

	//public static synchronized void addDato(Object dato) {
	//	
	//}

		public synchronized String obtenerSala() {
		
			while(!Salas.tocoIngreso())
				try {
					Thread.sleep(5);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
		return String.valueOf(Salas.getSala());
	}
}
