package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import comandos.AvisarIngreso;
import comandos.ComandosServer;
import comandos.ComenzarPartida;
import comandos.CrearSala;
import comandos.Default;
import comandos.DentroDeJuego;
import comandos.ExpulsarAtodos;
import comandos.IngresarSala;
import comandos.Refrescar;
import comandos.RefreshJugadores;
import comandos.Salir;
import comandos.VolverLobby;

public class HiloAtencionCliente extends Thread {

	private Socket cliente;
	private Paquete paquete;
	private Date inicioConexion;
	private ComandosServer comanSer;
	private ObjectOutputStream salidaObj;
	private ObjectInputStream entradaObj;

	public HiloAtencionCliente(Socket socket) {
		this.cliente = socket;
		this.inicioConexion = new Date();
		try {
			this.salidaObj = new ObjectOutputStream(cliente.getOutputStream());
			this.salidaObj.flush();
			this.entradaObj = new ObjectInputStream(cliente.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			String msj, resultComando;
			ChainOfResposability();
			enviarSalas();
			String nickName = (String) entradaObj.readObject();
			if (nickName.equals("-/-1"))
				cerrarConexion();
			else {
				while(nickRepetido(nickName))
				{	
					salidaObj.writeObject("Repetido");
					nickName = (String) entradaObj.readObject();
				}
				salidaObj.writeObject("noRepetido");
				
				paquete = new Paquete(inicioConexion, cliente, nickName, entradaObj, salidaObj);
				do {
					do {
						
						msj = (String) entradaObj.readObject();
					} while ((resultComando = comanSer.procesar(paquete, msj)).equals("y"));

					if (!resultComando.equals("Salir")) {
						do {
							msj = (String) entradaObj.readObject();
						} while (!(resultComando = comanSer.procesar(paquete, msj)).equals("--VolverAlLobby"));
					}
				} while (!resultComando.equals("Salir"));
			}
		} catch (IOException ex) {
			ex.getStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void ChainOfResposability() {
		ComandosServer crearSala = new CrearSala();
		ComandosServer ingresoSala = new IngresarSala();
		ComandosServer comDefault = new Default();
		ComandosServer volverAllobby = new VolverLobby();
		ComandosServer refrescar = new Refrescar();
		ComandosServer refrescarPlayer = new RefreshJugadores();
		ComandosServer avisarIngreso = new AvisarIngreso();
		ComandosServer expulsaAtodos = new ExpulsarAtodos();
		ComandosServer comenzarPartida = new ComenzarPartida();
		ComandosServer dentroDeJuego = new DentroDeJuego();

		this.comanSer = new Salir();
		comanSer.establecerSiguiente(crearSala);
		crearSala.establecerSiguiente(ingresoSala);
		ingresoSala.establecerSiguiente(volverAllobby);
		volverAllobby.establecerSiguiente(refrescar);
		refrescar.establecerSiguiente(refrescarPlayer);
		refrescarPlayer.establecerSiguiente(avisarIngreso);
		avisarIngreso.establecerSiguiente(expulsaAtodos);
		expulsaAtodos.establecerSiguiente(comenzarPartida);
		comenzarPartida.establecerSiguiente(dentroDeJuego);
		dentroDeJuego.establecerSiguiente(comDefault);
	}
	
	public boolean nickRepetido(String nick)
	{	
		boolean repetido=false;
		for (Map.Entry<String, ArrayList<Paquete>> entry : Servidor.getSalas().entrySet()) {
			ArrayList<Paquete> paquetes = entry.getValue();
			for (Paquete clientes : paquetes) {
				if(clientes.getNick().equals(nick))
					repetido = true;
			}
		}
		return repetido;
	}

	public void enviarSalas() {
		ArrayList<SalaSerealizable> envioSalas = new ArrayList<>();
		for (Map.Entry<String, ArrayList<Paquete>> entry : Servidor.getSalas().entrySet()) {
			SettingsPartida setPart = Servidor.getMaxSalas().get(entry.getKey());
			envioSalas.add(new SalaSerealizable(entry.getValue().size(), setPart));
		}
		try {
			salidaObj.writeObject(envioSalas);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void enviarJugadores() {
		ArrayList<String> nickPlayers = new ArrayList<>();
		for (Paquete item : Servidor.getSalas().get(paquete.getSala())) {
			nickPlayers.add(item.getNick());
		}
		try {
			salidaObj.writeObject(nickPlayers);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cerrarConexion() {
		try {
			salidaObj.close();
			entradaObj.close();
			cliente.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
