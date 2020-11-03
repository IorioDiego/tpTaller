package game;

import java.util.ArrayList;

import cartas.Baron;
import cartas.Condesa;
import cartas.Mucama;
import cartas.Princesa;
import cartas.Principe;
import cartas.Rey;
import cartas.Sacerdote;
import estados.Eliminado;

public class Partida extends Observer  {
	private int afecto;
	private int cantJugadores;
	private ArrayList<Jugador> jugadores;
	private ArrayList<Carta> listaCartas = new ArrayList<Carta>();
	private Mazo mazo;
	private int jugadoresActivos;

	public Partida() {

	}

	public Partida(int afecto, int cantJugadores, ArrayList<Jugador> jugadores) {
		this.afecto = afecto;
		this.cantJugadores = cantJugadores;
		this.jugadores = jugadores;
		this.jugadoresActivos= cantJugadores;

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
			jugador.vaciarMano();
		}
		mazo= new Mazo();
		mazo.mezclar();
		mazo.eliminarPrimeraCarta();

		for (Jugador jugador : jugadores) {
			mazo.darCarta(jugador);
		}
		
	}
	
	
	public void observarJugadores() {
		for (Jugador jugador : jugadores) {
			jugador.register(this);
			
		}
	}
	
	@Override
	public void notificarseEstadoEliminado() {
		cantJugadores--;
		if(cantJugadores == 1) {
			int i=0;
			while (jugadores.get(i).getEstado().equals(new Eliminado())) {
				i++;
			jugadores.get(i).ganarRonda(afecto);
			}
			
		}
	}
	
	public void notificarseEndGame() {
		for (int i = 0; i < jugadores.size(); i++) {		
			if(jugadores.get(i).getAfectosConseguidos() == afecto )
				finalizarPartida(i);
		}

	}
	
	
	@Override
	public void notificarseFinMazo() {
		int fuerza = 0, ganador=0,empate=0;	
		int primeroValido=0;
		ArrayList<Integer> jEmpatados= new ArrayList<Integer>();
		while (jugadores.get(primeroValido).getEstado().equals(new Eliminado())) {
			primeroValido++;
		}
		jEmpatados.add(primeroValido);
		fuerza = jugadores.get(primeroValido).getMano().getFuerza();
		
		for (int i = primeroValido+1 ; i < jugadores.size(); i++) {
			if (!jugadores.get(i).getEstado().equals(new Eliminado())) {
		
				if (jugadores.get(i).getMano().getFuerza() > fuerza) {
				
					fuerza = jugadores.get(i).getMano().getFuerza();
					empate=0;
					ganador=i;
					jEmpatados.clear();
				
				}else if(jugadores.get(i).getMano().getFuerza() == fuerza)
				{	
					//jugEmpatados[i]=i;
					jEmpatados.add(i);
					empate=1;
				}
				
			}
		}
		
		if( empate == 1) {
			ganador= jEmpatados.get(0);
			fuerza = jugadores.get(jEmpatados.get(0)).sumarDescarte();
			for (int i = 1; i < jEmpatados.size(); i++) {
				if(jugadores.get(jEmpatados.get(i)).sumarDescarte()> fuerza) {
					fuerza=jugadores.get(jEmpatados.get(i)).getMano().getFuerza();
					ganador=i;
				}	
			}		
		}		
		jugadores.get(ganador).ganarRonda(afecto);
		iniciarRonda();		
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

	
	public boolean finalizarPartida(int i) {
		System.out.println("El jugador "+ jugadores.get(i)+"gano la partida");
		return true;
	}
	
	public int getJugadoresActivos() {
		return jugadoresActivos;
	}
}
