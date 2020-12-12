package Cliente;

import java.awt.EventQueue;

import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


import InterfaceGrafica.Salas;
import servidor.SalaSerealizable;


public class Cliente {

	private String ip;
	private int puerto;
	private ObjectInputStream disObj = null;
	private ObjectOutputStream dosObj = null;
	private Socket socket;
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
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void activarInterfaz() {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sala.init(disObj, dosObj,socket);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
