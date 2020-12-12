package game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import InterfaceGrafica.Salas;
import cartas.Baron;
import cartas.Carta;
import cartas.Condesa;
import cartas.Mucama;
import cartas.Princesa;
import cartas.Principe;
import cartas.Rey;
import cartas.Sacerdote;
import comandosJuego.JugarCartas;
import estados.Eliminado;
import estados.Protegido;
import servidor.Paquete;
import servidor.Servidor;

public class Partida extends Observer implements Serializable {
	private int afecto;
	// private ArrayList<Paquete> jugadores; ---> setearlo con el server
	private int cantJugadores;
	private ArrayList<Jugador> jugadores;
	private ArrayList<Carta> listaCartas = new ArrayList<Carta>();
	private Mazo mazo;
	private int jugadoresActivos;
	private ObjectOutputStream dos;
	private ObjectInputStream dis;
	private boolean reinicio = false;
	private boolean finalizoPartida;
	private Jugador ganadoRonda = new Jugador();
	private int nroRonda;
	private String jInicial;
	private String nombreSala;
	private Paquete paqueteActivo;
	private Carta cartaEliminda;
	private boolean huboEliminacion = false;
	// baron
	private boolean eliminoOpBaron = false;
	private boolean eliminoActBaron = false;

	public String getjInicial() {
		return jInicial;
	}

	public void setjInicial(String jInicial) {
		this.jInicial = jInicial;
	}

	public Carta getCartaEliminda() {
		return cartaEliminda;
	}

	public void setCartaEliminda(Carta cartaEliminda) {
		this.cartaEliminda = cartaEliminda;
	}

	public int getNroRonda() {
		return nroRonda;
	}

	public ObjectOutputStream getDos() {
		return dos;
	}

	public void setDos(ObjectOutputStream dos) {
		this.dos = dos;
	}

	public ObjectInputStream getDis() {
		return dis;
	}

	public void setDis(ObjectInputStream dis) {
		this.dis = dis;
	}

	public void setNroRonda(int nroRonda) {
		this.nroRonda = nroRonda;
	}

	public boolean getReinicio() {
		return reinicio;
	}

	public void setReinicio(boolean reinicio) {
		this.reinicio = reinicio;
	}

	public Partida() {

	}

	public Partida(int afecto, int cantJugadores, ArrayList<Jugador> jugadores, String jInicial, String orden,
			String nSala) {
		this.afecto = afecto;
		this.cantJugadores = cantJugadores;
		this.jugadores = jugadores;
		this.jugadoresActivos = cantJugadores;
		this.reinicio = false;
		this.finalizoPartida = false;
		this.nroRonda = 0;
		this.jInicial = jInicial;
		this.nombreSala = nSala;
		///////////////////////
		listaCartas.add(new Sacerdote());
		listaCartas.add(new Baron());
		listaCartas.add(new Mucama());
		listaCartas.add(new Principe());
		listaCartas.add(new Rey());
		listaCartas.add(new Condesa());
		listaCartas.add(new Princesa());
		if (orden.equals("Izquierda"))
			Collections.reverse(this.jugadores);
	}

	public boolean isEliminoOpBaron() {
		return eliminoOpBaron;
	}

	public void setEliminoOpBaron(boolean eliminoOpBaron) {
		this.eliminoOpBaron = eliminoOpBaron;
	}

	public boolean isEliminoActBaron() {
		return eliminoActBaron;
	}

	public void setEliminoActBaron(boolean eliminoActBaron) {
		this.eliminoActBaron = eliminoActBaron;
	}

	public Jugador getGanadoRonda() {
		return ganadoRonda;
	}

	public void setGanadoRonda(Jugador ganadoRonda) {
		this.ganadoRonda = ganadoRonda;
	}

	public boolean isHuboEliminacion() {
		return huboEliminacion;
	}

	public void setHuboEliminacion(boolean huboEliminacion) {
		this.huboEliminacion = huboEliminacion;
	}

	public void iniciarPartida() {

		observarJugadores();
		iniciarRonda();

	}

	public void iniciarRonda() {
		Paquete primerJ = null;
		nroRonda++;
		for (Jugador jugador : jugadores) {
			jugador.seReiniciaRonda();

		}

		if (nroRonda > 1)
			reinicio = true;

		huboEliminacion = false;

		// enviarMsj(dos, "1");
		// mazo = (Mazo) leerMsj(dis);
		mazo = new Mazo();
		// mazo.register(this);
		mazo.mezclar();
		cartaEliminda = mazo.eliminarPrimeraCarta();

		cantJugadores = jugadoresActivos;

//		for (Jugador jugador : jugadores) {
//			mazo.darCarta(jugador);
//		}
		ArrayList<Paquete> p = Servidor.darClientesDeSala(nombreSala);

		try {

			for (Paquete paqueteCliente : p) {
				for (Jugador jugadorCliente : jugadores) {
					if (paqueteCliente.getNick().equals(jugadorCliente.getNombre())) {
						Carta cartaDada = mazo.darCarta(jugadorCliente);
						paqueteCliente.getSalida().writeObject(cartaDada);
						paqueteCliente.getSalida().writeObject(paqueteCliente.getNick());
						for (Jugador jug : jugadores) {
							paqueteCliente.getSalida().writeObject(jug.getAfectosConseguidos());
						}
					}
				}
			}

			for (Paquete paquete : p) {
				if (paquete.getNick().equals(jInicial)) {
					paquete.getSalida().writeObject("tuTurno");
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void reiniciarJuego() {
		for (Jugador jugador : jugadores) {
			jugador.seReiniciaRonda();

		}

		mazo = new Mazo();
		mazo.mezclar();
		cartaEliminda = mazo.eliminarPrimeraCarta();

		cantJugadores = jugadoresActivos;
		finalizoPartida = false;
		for (Jugador jugador : jugadores) {
			mazo.darCarta(jugador);
			jugador.setAfectosConseguidos(0);
		}

	}

	public boolean isFinalizoPartida() {
		return finalizoPartida;
	}

	public void setFinalizoPartida(boolean finalizoPartida) {
		this.finalizoPartida = finalizoPartida;
	}

	public void observarJugadores() {
		for (Jugador jugador : jugadores) {
			jugador.register(this);
		}
	}

	@Override
	public void notificarseEstadoEliminado() {
		cantJugadores--;
		if (cantJugadores == 1) {
			for (int i = 0; i < jugadores.size(); i++) {
				if (!jugadores.get(i).getEstado().equals(new Eliminado()))
					jugadores.get(i).ganarRonda(afecto, this);
			}
			avisarFinRonda();
		}
	}

	public void avisarFinRonda() {
		if (!finalizoPartida) {
			for (Paquete clientes : Servidor.darClientesDeSala(nombreSala)) {
				enviarMsj(clientes.getSalida(), "finDeRonda");
				enviarMsj(clientes.getSalida(), "NofinPartida");
				enviarMsj(clientes.getSalida(), ganadoRonda.getNombre());
			}
			Servidor.darConfigSalas(nombreSala).setReinicioRonda("Reinicio");
			iniciarRonda();
		}
	}

	public void notificarseEndGame() {
		finalizarPartida(0);
		Servidor.darConfigSalas(nombreSala).setFinalizoPartida("finPartida");
		for (Paquete clientes : Servidor.darClientesDeSala(nombreSala)) {
			enviarMsj(clientes.getSalida(), "NofinDeRonda");
			enviarMsj(clientes.getSalida(), "finPartida");
			enviarMsj(clientes.getSalida(), ganadoRonda.getNombre());
		}
	}

	@Override
	public void notificarseFinMazo() {
		int fuerza = 0, ganador = 0, empate = 0;
		int primeroValido = 0;
		ArrayList<Integer> jEmpatados = new ArrayList<Integer>();

		while (jugadores.get(primeroValido).getEstado().equals(new Eliminado())) {
			primeroValido++;
		}
		jEmpatados.add(primeroValido);
		ganador = primeroValido;
		fuerza = jugadores.get(primeroValido).getMano(0).getFuerza();

		for (int i = primeroValido + 1; i < jugadores.size(); i++) {
			if (!jugadores.get(i).getEstado().equals(new Eliminado())) {

				if (jugadores.get(i).getMano(0).getFuerza() > fuerza) {

					fuerza = jugadores.get(i).getMano(0).getFuerza();
					empate = 0;
					ganador = i;
					jEmpatados.clear();

				} else if (jugadores.get(i).getMano(0).getFuerza() == fuerza) {

					jEmpatados.add(i);
					empate = 1;
				}

			}
		}
		if (empate == 1) {
			ganador = jEmpatados.get(0);
			fuerza = jugadores.get(jEmpatados.get(0)).sumarDescarte();
			for (int i = 1; i < jEmpatados.size(); i++) {
				if (jugadores.get(jEmpatados.get(i)).sumarDescarte() > fuerza) {

					fuerza = jugadores.get(jEmpatados.get(i)).sumarDescarte();
					ganador = i;
				}
			}
			ganador = jEmpatados.get(ganador);
		}
		
		jugadores.get(ganador).ganarRonda(afecto, this);
		ganadoRonda = jugadores.get(ganador);
		avisarFinRonda();
	}

	public Jugador proximoTurnoJugador(Jugador jugadorActivo) {
		int index = jugadores.indexOf(jugadorActivo);

		for (int i = index + 1; i < jugadores.size(); i++) {
			Jugador j = jugadores.get(i);
			if (!j.getEstado().equals(new Eliminado()) && !j.isPasoTurno()) {
				return j;
			}
		}

		for (int i = 0; i < index; i++) {
			Jugador j = jugadores.get(i);
			if (!j.getEstado().equals(new Eliminado()) && !j.isPasoTurno())
				return j;
		}

//		for (Jugador jugador : jugadores) {
//			if (!jugador.getEstado().equals(new Eliminado()) && !jugador.getNombre().equals(jugadorActivo.getNombre())
//					&& !jugador.isPasoTurno()) {
//				return jugador;
//			}
//		}
		for (Jugador jugador : jugadores) {
			jugador.setPasoTurno(false);
		}
		return proximoTurnoJugador(jugadorActivo);
	}

	public int getCantJugadores() {
		return cantJugadores;
	}

	public void setCantJugadores(int cantJugadores) {
		this.cantJugadores = cantJugadores;
	}

	public int getAfecto() {
		return afecto;
	}

	public void setAfecto(int afecto) {
		this.afecto = afecto;
	}

	public Mazo getMazo() {
		return mazo;
	}

	public void setMazo(Mazo mazo) {
		this.mazo = mazo;
	}

	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}

	public Jugador elegirJugador(int i) {
		return jugadores.get(i);
	}

	public Carta seleccionarCarta(int index) {

		return listaCartas.get(index);
	}

	public int finalizarPartida(int i) {
		finalizoPartida = true;
		return i;
	}

	public int getJugadoresActivos() {
		return jugadoresActivos;
	}

	public void enviarMsj(ObjectOutputStream dosObject, Object msj) {
		try {
			dosObject.writeObject(msj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object leerMsj(ObjectInputStream disObject) {
		try {
			return disObject.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Paquete getPaqueteActivo() {
		return paqueteActivo;
	}

	public void setPaqueteActivo(Paquete paqueteActivo) {
		this.paqueteActivo = paqueteActivo;
	}

}
