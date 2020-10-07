package testJuego;

import letterLove.*;
import game.*;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.sun.org.apache.xpath.internal.compiler.OpCodes;

public class testLoveLetter {

	private Partida partida;
	private ArrayList<Jugador> jugadores;

	@Before
	public void setUp() {
		jugadores = new ArrayList<>();
		jugadores.add(new Jugador("Dieguien", 3));
		jugadores.add(new Jugador("Lucardo", 2));
		jugadores.add(new Jugador("Tomarson", 1));
		jugadores.add(new Jugador("Leandro", 4));
		partida = new Partida(3, 4, jugadores);
		partida.iniciarPartida();
	}

	@Test
	public void DescartarRey() {
		Jugador jugador = jugadores.get(0);
		Jugador oponente = jugadores.get(1);
		jugador.tomarCarta(new Rey());

		Carta cartaJugador = jugador.getMano();
		Carta cartaOponente = oponente.getMano();
		jugador.jugarCarta(partida);

		assertEquals(cartaJugador, oponente.getMano());
		assertEquals(cartaOponente, jugador.getMano());

		/// VER COMO SE ELIGE EL INDICE DE JUGADOR
	}

	@Test
	public void DescartarMucama() {
		Jugador jugador = jugadores.get(0);

		jugador.tomarCarta(new Mucama());
		jugador.jugarCarta(partida);

		assertEquals(new Protegido(), jugador.getEstado());
	}

	@Test
	public void DescartarBaronPierdo() {
		Jugador jugador = jugadores.get(0);
		Jugador oponente = jugadores.get(1);
		jugador.sacarCartaDeMano(0);
		oponente.sacarCartaDeMano(0);
		jugador.tomarCarta(new Mucama());
		oponente.tomarCarta(new Condesa());
		int comp = jugador.getMano().getFuerza() - oponente.getMano().getFuerza();
		jugador.tomarCarta(new Baron());
		jugador.jugarCarta(partida);

		assertEquals(new Eliminado(), jugador.getEstado());
	}

	@Test
	public void DescartarBaronGano() {
		Jugador jugador = jugadores.get(0);
		Jugador oponente = jugadores.get(1);
		jugador.sacarCartaDeMano(0);
		oponente.sacarCartaDeMano(0);
		jugador.tomarCarta(new Condesa());
		oponente.tomarCarta(new Mucama());
		int comp = jugador.getMano().getFuerza() - oponente.getMano().getFuerza();
		jugador.tomarCarta(new Baron());
		jugador.jugarCarta(partida);

		assertEquals(new Eliminado(), oponente.getEstado());

	}

	@Test
	public void DescartarBaronEmpate() {
		Jugador jugador = jugadores.get(0);
		Jugador oponente = jugadores.get(1);
		jugador.sacarCartaDeMano(0);
		oponente.sacarCartaDeMano(0);
		jugador.tomarCarta(new Guardia());
		oponente.tomarCarta(new Guardia());
		int comp = jugador.getMano().getFuerza() - oponente.getMano().getFuerza();
		jugador.tomarCarta(new Baron());
		jugador.jugarCarta(partida);

		assertEquals(new Normal(), oponente.getEstado());
		assertEquals(new Normal(), jugador.getEstado());
	}
	
	@Test
	public void DescartarGuardiaNoElimino() {
		Jugador jugador = jugadores.get(0);
		Jugador oponente = jugadores.get(1);
		oponente.sacarCartaDeMano(0);
		oponente.tomarCarta(new Principe());
		jugador.tomarCarta(new Guardia());
		jugador.jugarCarta(partida);
		assertEquals(new Normal(), oponente.getEstado());
	}
	
	@Test
	public void DescartarGuardiaEliminoOponente() {
		Jugador jugador = jugadores.get(0);
		Jugador oponente = jugadores.get(1);
		oponente.sacarCartaDeMano(0);
		oponente.tomarCarta(new Mucama());
		jugador.tomarCarta(new Guardia());
		jugador.jugarCarta(partida);
		assertEquals(new Eliminado(), oponente.getEstado());
	}
	
	@Test
	public void tomoCondesaConReyEnMano() {
		Jugador jugador = jugadores.get(0);
		jugador.sacarCartaDeMano(0);
		jugador.tomarCarta(new Rey());
		jugador.tomarCarta(new Condesa());
		assertEquals(new Rey(), jugador.getMano());
	}
	
	@Test
	public void tomoReyConCondesaEnMano() {
		Jugador jugador = jugadores.get(0);
		jugador.sacarCartaDeMano(0);
		jugador.tomarCarta(new Condesa());
		jugador.tomarCarta(new Rey());
		assertEquals(new Rey(), jugador.getMano());
	}
	
	@Test
	public void tomoCondesaNoPrincipeNoRey() {
		Jugador jugador = jugadores.get(0);
		jugador.sacarCartaDeMano(0);
		jugador.tomarCarta(new Condesa());
		jugador.tomarCarta(new Guardia());
		assertEquals(new Condesa(), jugador.getMano());
	}
	
	@Test
	public void sinPrincipeNiReyTomoCondesa() {
		Jugador jugador = jugadores.get(0);
		jugador.sacarCartaDeMano(0);
		jugador.tomarCarta(new Guardia());
		jugador.tomarCarta(new Condesa());
		assertEquals(new Guardia(), jugador.getMano());
	}
	
	@Test
	public void DescartoPrincesa() {
		Jugador jugador = jugadores.get(0);
		jugador.tomarCarta(new Princesa());
		jugador.jugarCarta(partida);
		assertEquals(new Eliminado(), jugador.getEstado());
	}

}
