package game;

import java.util.ArrayList;

import cartas.Baron;
import cartas.Carta;
import cartas.Condesa;
import cartas.Mucama;
import cartas.Princesa;
import cartas.Principe;
import cartas.Rey;
import cartas.Sacerdote;
import estados.Eliminado;
import estados.Protegido;

public class Partida extends Observer {
	private int afecto;
	private int cantJugadores;
	private ArrayList<Jugador> jugadores;
	private ArrayList<Carta> listaCartas = new ArrayList<Carta>();
	private Mazo mazo;
	private int jugadoresActivos;
	private boolean reinicio=false;
	private boolean finalizoPartida;

	public boolean getReinicio() {
		return reinicio;
	}

	public void setReinicio(boolean reinicio) {
		this.reinicio = reinicio;
	}

	public Partida() {

	}

	public Partida(int afecto, int cantJugadores, ArrayList<Jugador> jugadores) {
		this.afecto = afecto;
		this.cantJugadores = cantJugadores;
		this.jugadores = jugadores;
		this.jugadoresActivos = cantJugadores;
		this.reinicio = false;
		this.finalizoPartida = false;
		///////////////////////
		listaCartas.add(new Sacerdote());
		listaCartas.add(new Baron());
		listaCartas.add(new Mucama());
		listaCartas.add(new Principe());
		listaCartas.add(new Rey());
		listaCartas.add(new Condesa());
		listaCartas.add(new Princesa());

	}
	
	public void iniciarPartida() {

		observarJugadores();
		iniciarRonda();
		mazo.register(this);
	}

	public void iniciarRonda() {
		for (Jugador jugador : jugadores) {
			jugador.seReiniciaRonda();
//			jugador.vaciarMano(); ahora se hace en seReiniciaRonda			
		}
		
		reinicio=false;
		
		mazo = new Mazo();
		mazo.mezclar();
		mazo.eliminarPrimeraCarta();
		
		cantJugadores = jugadoresActivos;

		for (Jugador jugador : jugadores) {
			mazo.darCarta(jugador);
		}

	}
	
	
	public void reiniciarJuego() {
		for (Jugador jugador : jugadores) {
			jugador.seReiniciaRonda();
//			jugador.vaciarMano(); ahora se hace en seReiniciaRonda			
		}
		
		reinicio=true;
		
		mazo = new Mazo();
		mazo.mezclar();
		mazo.eliminarPrimeraCarta();
		
		cantJugadores = jugadoresActivos;
		finalizoPartida=false;
		for (Jugador jugador : jugadores) {
			mazo.darCarta(jugador);
			jugador.setAfectosConseguidos(0);
		}

	}
	
	public Jugador getGanador() {
		Jugador ganador = new Jugador();
		for (Jugador jugador : jugadores) {
			if(!jugador.getEstado().equals(new Eliminado()))
				ganador=jugador;	
		}
		return ganador;
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
			for(int i = 0; i < jugadoresActivos; i++) {
				if(!jugadores.get(i).getEstado().equals(new Eliminado()))
					jugadores.get(i).ganarRonda(afecto);
			}
			iniciarRonda();
		}
	}

	public void notificarseEndGame() {
		for (int i = 0; i < jugadores.size(); i++) {
			if (jugadores.get(i).getAfectosConseguidos() == afecto)
				finalizarPartida(i);
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
		fuerza = jugadores.get(primeroValido).getMano(0).getFuerza();

		for (int i = primeroValido + 1; i < jugadores.size(); i++) {
			if (!jugadores.get(i).getEstado().equals(new Eliminado())) {

				if (jugadores.get(i).getMano(0).getFuerza() > fuerza) {

					fuerza = jugadores.get(i).getMano(0).getFuerza();
					empate = 0;
					ganador = i;
					jEmpatados.clear();

				} else if (jugadores.get(i).getMano(0).getFuerza() == fuerza) {
					// jugEmpatados[i]=i;
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
					fuerza = jugadores.get(jEmpatados.get(i)).getMano(0).getFuerza();
					ganador = i;
				}
			}
		}
		jugadores.get(ganador).ganarRonda(afecto);
		iniciarRonda();
	}

	public Jugador proximoTurnoJugador(Jugador jugadorActivo) {
		for (Jugador jugador : jugadores) {
			if (!jugador.getEstado().equals(new Eliminado())
					&& !jugador.getNombre().equals(jugadorActivo.getNombre()) && !jugador.isPasoTurno()) {
				return jugador;
			}
		}
		for (Jugador jugador : jugadores) {
			jugador.setPasoTurno(false);
		}
		return proximoTurnoJugador(jugadorActivo);
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
		// TODO evento de seleecion
		// int i = 0;
		return listaCartas.get(index);
	}

	public int finalizarPartida(int i) {
		finalizoPartida=true;
		//System.out.println("El jugador " + jugadores.get(i) + "gano la partida");
		return i;
	}

	public int getJugadoresActivos() {
		return jugadoresActivos;
	}
}
