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
import comandos.ObtenerHistorial;
import comandos.Salir;
import comandos.SalirDeSala;
import comandos.VerTiempoConexion;
import comandos.VolverLobby;

public class HiloAtencionCliente extends Thread implements Serializable {

	private Socket cliente;
	private DataInputStream entrada;
	private Date inicioConexion;
	private DataOutputStream salida;
	private ComandosServer comanSer;
	private ObjectOutputStream salidaObj;
	private ObjectInputStream entradaObj;
	
	private final String opcionesSala = "Ingrese Comando: " + "\n" + 
										"1)-Salir" + "\n" + 
										"2)-Crear sala" + "\n" +
										"3)-Ingresar a sala";

	private final String opcionesComandos = "Ingrese Comando: " + "\n" + 
											"5)--Chat general" + "\n" + 
											"6)--Chat privado"+ "\n" + 
											"7)--ver tiempo de conexion" + "\n" + 
											"8)--volver al lobby" + "\n" + 
											"9)--obtener historial" + "\n"+ 
											"1)--Salir";

	public HiloAtencionCliente(Socket socket) {
		this.cliente = socket;
		this.inicioConexion = new Date();
		try {
//			this.entrada = new DataInputStream(cliente.getInputStream());
//			this.salida = new DataOutputStream(cliente.getOutputStream());
			this.salidaObj= new ObjectOutputStream(cliente.getOutputStream());
			this.salidaObj.flush();
			this.entradaObj= new ObjectInputStream(cliente.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
		this.comanSer = new Salir();
		ComandosServer crearSala = new CrearSala();
		ComandosServer ingresoSala = new IngresarSala();
		ComandosServer chatGeneral = new EnviarMsjAllSala();
		ComandosServer chatPrivado = new ChatPrivado();
		ComandosServer chatComDefault = new Default();
		ComandosServer chatVerTiempoConexion = new VerTiempoConexion();
		ComandosServer volverAllobby = new VolverLobby();
		ComandosServer salirDeSala = new SalirDeSala();
		ComandosServer obtenerHistorial = new ObtenerHistorial();

		comanSer.establecerSiguiente(crearSala);
		crearSala.establecerSiguiente(ingresoSala);
		ingresoSala.establecerSiguiente(chatGeneral);
		chatGeneral.establecerSiguiente(chatPrivado);
		chatPrivado.establecerSiguiente(chatVerTiempoConexion);
		chatVerTiempoConexion.establecerSiguiente(volverAllobby);
		volverAllobby.establecerSiguiente(salirDeSala);
		salirDeSala.establecerSiguiente(obtenerHistorial);
		obtenerHistorial.establecerSiguiente(chatComDefault);
	}

	@Override
	public void run() {
		try {
			String msj, resultComando;
			boolean existeSala = true;
//			salida.writeUTF("Ingrese su nombre de usuario: ");
//			String nick = entrada.readUTF();
			String nick  = "diego";
			Paquete paquete = new Paquete(inicioConexion, cliente, nick, entrada, salida);
//			do {
//				do {
////					salida.writeUTF("Lobby" + "\n");
////					for (Map.Entry<String, ArrayList<Paquete>> entry : Servidor.getSalas().entrySet()) {
////						salida.writeUTF(
////								"Nombre Sala: " + entry.getKey() + " Usuarios conetados:" + entry.getValue().size());
////						
////					}
//					
					salidaObj.writeObject(Servidor.getSalas());
//					salida.writeUTF(opcionesSala);
//					if (paquete.cantidadSalas() >= 1)
//						salida.writeUTF("4)-Salir de sala");
//					msj = entrada.readUTF();
//				} while ((resultComando = comanSer.procesar(paquete, msj)).equals("y"));
//
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
		}

	}
	

}
