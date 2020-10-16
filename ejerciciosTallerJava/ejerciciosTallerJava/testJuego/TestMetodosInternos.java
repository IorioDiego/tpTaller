package testJuego;

import letterLove.*;

import static org.junit.Assert.*;

import org.junit.Test;

import game.Jugador;
import game.Partida;

public class TestMetodosInternos {

	@Test
	public void testFinDeMazo() {
		Mazo mazo = new Mazo();
		mazo.register(new Partida());
		Jugador jugador =new Jugador("Lucardo", 2);
		for (int i = 0; i < 17; i++) {
			mazo.darCarta(jugador);
		}	
	}
}