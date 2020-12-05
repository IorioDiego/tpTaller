package servidor;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import game.Jugador;
import game.Partida;

public class Servidor {

	private static Map<String, SettingsPartida> maxSalas = new HashMap<String, SettingsPartida>();
	private static Map<String, ArrayList<Paquete>> salas = new HashMap<String, ArrayList<Paquete>>();

	public Servidor(int puerto) throws IOException {
		ServerSocket servidor = new ServerSocket(puerto);
		System.out.println("Server inicializando...");
		for (int i = 1; i <= 200; i++) {
			Socket cliente = servidor.accept();

			HiloAtencionCliente hc = new HiloAtencionCliente(cliente);
			hc.start();
		}

		System.out.println("Server Finalizado");
		servidor.close();
	}

	static public void eliminarClienteDeSalas(Paquete paqueteClient) {

		ArrayList<Paquete> listaClientSala = salas.get(paqueteClient.getSala());
		for (Iterator<Paquete> iterator = listaClientSala.iterator(); iterator.hasNext();) {
			Paquete paquete = (Paquete) iterator.next();
			if (paqueteClient.getCliente().equals(paquete.getCliente()))
				iterator.remove();
		}
	}

	public static boolean agregarClienteSala(Paquete paqueteClient, String salaAingresar) {
		boolean existiaSala = false;
		if (salas.containsKey(salaAingresar)) {
			salas.get(salaAingresar).add(paqueteClient);
			existiaSala = true;
		}
		return existiaSala;
	}

	static public void eliminarClienteDeSala(Paquete paqueteClient, String sala) {
		for (Iterator<Paquete> iterator = salas.get(sala).iterator(); iterator.hasNext();) {
			Paquete paquete = (Paquete) iterator.next();
			if (paqueteClient.getCliente().equals(paquete.getCliente()))
				iterator.remove();
		}
		if (salas.get(sala).size() == 0) {
			salas.remove(sala);
			maxSalas.remove(sala);
		}
	}

	public static boolean existeSala(String sala) {
		return salas.containsKey(sala);
	}

	public static void crearSala(Paquete paqueteClient, String salaAcrear, SettingsPartida setPart) {
		ArrayList<Paquete> client = new ArrayList<>();
		client.add(paqueteClient);
		if (!salas.containsKey(salaAcrear)) {
			salas.put(salaAcrear, client);
			maxSalas.put(salaAcrear, setPart);
		} else
			agregarClienteSala(paqueteClient, salaAcrear);
	}

	public static Map<String, ArrayList<Paquete>> getSalas() {
		return salas;
	}

	public static ArrayList<Paquete> darClientesDeSala(String sala) {
		return salas.get(sala);
	}

	public static Map<String, SettingsPartida> getMaxSalas() {
		return maxSalas;
	}
	
	public static SettingsPartida darConfigSalas(String sala) {
		return maxSalas.get(sala);
	}


	public static void setMaxSalas(Map<String, SettingsPartida> maxSalas) {
		Servidor.maxSalas = maxSalas;
	}

//	public static Partida generarPartida(Paquete host) {
//		int id = 0;
//		ArrayList<Jugador> jugadores = new ArrayList<>();
//		for (Paquete paqueteCliente : darClientesDeSala(host.getSala())) {
//			jugadores.add(new Jugador(paqueteCliente.getNick(), id));
//
//		}
//
//		return new Partida(maxSalas.get(host.getSala()).getPrendasAmor(),
//				maxSalas.get(host.getSala()).getCantJugadores(), jugadores,host.getNick());
//	}
}
