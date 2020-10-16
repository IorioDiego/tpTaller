package game;

import java.util.ArrayList;

import letterLove.Baron;
import letterLove.Condesa;
import letterLove.Mucama;
import letterLove.Princesa;
import letterLove.Rey;
import letterLove.Principe;
import letterLove.Sacerdote;
import letterLove.ultimaCartaException;
import letterLove.Carta;
import letterLove.Mazo;

public class Partida extends Observer {
	private int afecto;
	private int cantJugadores;
	private ArrayList<Jugador> jugadores;
	private ArrayList<Carta> listaCartas = new ArrayList<Carta>();
	private Mazo mazo;

	public Partida() {

	}

	public Partida(int afecto, int cantJugadores, ArrayList<Jugador> jugadores) {
		this.afecto = afecto;
		this.cantJugadores = cantJugadores;
		this.jugadores = jugadores;

		///////////////////////
		listaCartas.add(new Sacerdote());
		listaCartas.add(new Baron());
		listaCartas.add(new Mucama());
		listaCartas.add(new Principe());
		listaCartas.add(new Rey());
		listaCartas.add(new Condesa());
		listaCartas.add(new Princesa());

	}

	@Override
	public void notificarse() {
		int fuerza = 0, ganador=0,empate=0;
		
		//int [] jugEmpatados = new int[jugadores.size()];
		//jugEmpatados[0]=0;
		int primeroValido=0;
		ArrayList<Integer> jEmpatados= new ArrayList();
		
		
		
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
				
				}if(jugadores.get(i).getMano().getFuerza() == fuerza)
				{	
					//jugEmpatados[i]=i;
					jEmpatados.add(i);
					empate=1;
				}
				
			}
		}
		
		if( empate == 1) {
			ganador=0;
			fuerza = jugadores.get(jEmpatados.get(0)).sumarDescarte();
			for (int i = 1; i < jEmpatados.size(); i++) {
				if(jugadores.get(jEmpatados.get(i)).sumarDescarte()> fuerza) {
					fuerza=jugadores.get(jEmpatados.get(i)).getMano().getFuerza();
					ganador=i;
				}	
			}
			
		}
		
		jugadores.get(ganador).ganarRonda();
			
	}
	

	public Mazo getMazo() {
		return mazo;
	}

	public void setMazo(Mazo mazo) {
		this.mazo = mazo;
	}

	public void iniciarPartida() {
		Mazo mazo = new Mazo();
		mazo.mezclar();
		mazo.eliminarPrimeraCarta();

		for (Jugador jugador : jugadores) {
			mazo.darCarta(jugador);
		}
		/// POR CADA RONDA TIENE QUE PASAR ESTO--->TAL VEZ PUEDE IR EN TABLERO
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

}
