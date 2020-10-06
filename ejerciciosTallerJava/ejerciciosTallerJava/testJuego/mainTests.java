package testJuego;

import java.util.ArrayList;

import org.junit.Before;

import game.Jugador;
import game.Partida;
import letterLove.Mazo;

public class mainTests {

	public static void main(String[] args) {
		
		Partida partida;
		ArrayList<Jugador> jugadores;
		jugadores = new ArrayList<>();
		jugadores.add(new Jugador("Dieguien", 3));
		jugadores.add(new Jugador("Lucardo", 2));
		jugadores.add(new Jugador("Tomarson", 1));
		jugadores.add(new Jugador("Leandro", 4));
		//partida = new Partida(3, 4, jugadores);
		//partida.iniciarPartida();
		
		System.out.println(jugadores.get(0));
//		System.out.println(jugadores.get(1).getMano());
//		System.out.println(jugadores.get(2).getMano());
//		System.out.println(jugadores.get(3).getMano());
	}

}
