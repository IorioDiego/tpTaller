package servidor;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import comandos.ChatPrivado;
import comandos.ComandosServer;
import comandos.CrearSala;
import comandos.Default;
import comandos.EnviarMsjAllSala;
import comandos.IngresarSala;
import comandos.Refrescar;
import comandos.Salir;
import comandos.SalirDeSala;
import comandos.VolverLobby;

public class HiloAtencionCliente extends Thread {

	private Socket cliente;
	private DataInputStream entrada;
	private Date inicioConexion;
	private DataOutputStream salida;
	private ComandosServer comanSer;
	private ObjectOutputStream salidaObj;
	private ObjectInputStream entradaObj;

	public HiloAtencionCliente(Socket socket) {
		this.cliente = socket;
		this.inicioConexion = new Date();
		try {
//			this.entrada = new DataInputStream(cliente.getInputStream());
//			this.salida = new DataOutputStream(cliente.getOutputStream());
			this.salidaObj = new ObjectOutputStream(cliente.getOutputStream());
			this.salidaObj.flush();
			this.entradaObj = new ObjectInputStream(cliente.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
		this.comanSer = new Salir();
		ComandosServer crearSala = new CrearSala();
		ComandosServer ingresoSala = new IngresarSala();
		ComandosServer chatGeneral = new EnviarMsjAllSala();
		ComandosServer chatPrivado = new ChatPrivado();
		ComandosServer chatComDefault = new Default();
		ComandosServer volverAllobby = new VolverLobby();
		ComandosServer salirDeSala = new SalirDeSala();
		ComandosServer refrescar = new Refrescar();

		comanSer.establecerSiguiente(crearSala);
		crearSala.establecerSiguiente(ingresoSala);
		ingresoSala.establecerSiguiente(chatGeneral);
		chatGeneral.establecerSiguiente(chatPrivado);
		chatPrivado.establecerSiguiente(volverAllobby);
		volverAllobby.establecerSiguiente(salirDeSala);
		salirDeSala.establecerSiguiente(refrescar);
		refrescar.establecerSiguiente(chatComDefault);
	}

	@Override
	public void run() {
		try {
			String msj, resultComando;
			boolean existeSala = true;
			enviarSalas();
			String nickName = (String) entradaObj.readObject();
			Paquete paquete = new Paquete(inicioConexion, cliente, nickName, entradaObj, salidaObj);
//			do {
			do {
				// String salaElegida = (String)entradaObj.readObject();
				// System.out.println("la sala elegida fue: " + salaElegida);
				// SettingsPartida setPart = (SettingsPartida)entradaObj.readObject();
				// System.out.println(setPart);

				// salida.writeUTF(opcionesSala);
				// if (paquete.cantidadSalas() >= 1)
				// salida.writeUTF("4)-Salir de sala");
				msj = (String) entradaObj.readObject();
			} while ((resultComando = comanSer.procesar(paquete, msj)).equals("y"));
			
			Thread.sleep(4000);
///------>			Dentro de sala

//				if (!resultComando.equals("Salir")) {
//					do {
//						String sala;
//						if (paquete.cantidadSalas() > 1) {
//							do {
//								salida.writeUTF("\n" + "--ElegirSalaChat");
//								for (String salas : paquete.getSala())
//									salida.writeUTF("SALAS" + "\n" + salas);
//								sala = entrada.readUTF();
//								if (!(existeSala = Servidor.existeSala(sala)))
//									salida.writeUTF("Error,Sala invalida");
//							} while (!existeSala);
//							salida.writeUTF("!Listo para chatear");
//						} else
//							sala = paquete.getSala().get(0);
//						paquete.setSalaActiva(sala);
//
//						salida.writeUTF(opcionesComandos);
//						msj = entrada.readUTF();
//					} while (!(resultComando = comanSer.procesar(paquete, msj)).equals("--VolverAlLobby"));
//				}
//			} while (!resultComando.equals("Salir"));

		} catch (IOException ex) {
			ex.getStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void enviarSalas() {
		ArrayList<SalaSerealizable> envioSalas = new ArrayList<>();
		for (Map.Entry<String, ArrayList<Paquete>> entry : Servidor.getSalas().entrySet()) {
			SettingsPartida setPart=Servidor.getMaxSalas().get(entry.getKey());
			envioSalas.add(new SalaSerealizable(entry.getValue().size(),setPart));
		}
		try {
			salidaObj.writeObject(envioSalas);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
