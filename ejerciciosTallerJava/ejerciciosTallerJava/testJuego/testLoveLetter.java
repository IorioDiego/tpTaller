package testJuego;

import letterLove.*;
import game.*;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;



public class testLoveLetter {
	
	private Partida partida;
	private ArrayList<Jugador> jugadores;
	
	@Before
	public void setUp()
	{	
		jugadores = new ArrayList<>();
		jugadores.add(new Jugador("Dieguien",3));
		jugadores.add(new Jugador("Lucardo",2));
		jugadores.add(new Jugador("Tomarson",1));
		jugadores.add(new Jugador("Leandro",4));
		partida = new Partida(3, 4,jugadores);
		partida.iniciarPartida();
	}
	
	@Test
	public void DescartarRey()
	{	
		Jugador jugador=jugadores.get(0);
		Jugador oponente=jugadores.get(1);
		jugador.tomarCarta(new Rey());
		
		Carta cartaJugador=jugador.getMano();
		Carta cartaOponente=oponente.getMano();
		jugador.jugarCarta(partida);

		assertEquals(cartaJugador, oponente.getMano());
		assertEquals(cartaOponente, jugador.getMano());
		
		///VER COMO SE ELIGE EL INDICE DE JUGADOR
	}

	@Test
	public void DescartarMucama()
	{	
		Jugador jugador=jugadores.get(0);
		
		jugador.tomarCarta(new Mucama());
		jugador.jugarCarta(partida);
		jugador.seJugoMucama();
		
		assertEquals(new Protegido(),jugador.getEstado());
	}
}
