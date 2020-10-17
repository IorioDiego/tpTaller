package testJuego;

import letterLove.*;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import game.Jugador;
import game.Normal;
import game.Partida;

public class TestRondas {

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
	public void ganoSinDesempate() {

		jugadores.get(0).sacarCartaDeMano(0);
		jugadores.get(1).sacarCartaDeMano(0);
		jugadores.get(2).sacarCartaDeMano(0);
		jugadores.get(3).sacarCartaDeMano(0);

		jugadores.get(0).tomarCarta(new Rey());
		jugadores.get(1).tomarCarta(new Baron());
		jugadores.get(2).tomarCarta(new Princesa());
		jugadores.get(3).tomarCarta(new Sacerdote());

		try {
			for (int i = 0; i < 16; i++) {

				partida.getMazo().eliminarPrimeraCarta();
			}
		} catch (Exception ex) {
			partida.getMazo().notificarFinMazo();
		}

		assertEquals(1, jugadores.get(2).getAfectosConseguidos());
	}

	@Test
	public void ganoConDesempate() {

		jugadores.get(0).sacarCartaDeMano(0);
		jugadores.get(1).sacarCartaDeMano(0);
		jugadores.get(2).sacarCartaDeMano(0);
		jugadores.get(3).sacarCartaDeMano(0);

		jugadores.get(0).descartar(new Princesa());
		jugadores.get(1).descartar(new Guardia());

		jugadores.get(0).tomarCarta(new Principe());
		jugadores.get(1).tomarCarta(new Principe());
		jugadores.get(2).tomarCarta(new Baron());
		jugadores.get(3).tomarCarta(new Sacerdote());

		try {
			for (int i = 0; i < 16; i++) {

				partida.getMazo().eliminarPrimeraCarta();
			}
		} catch (Exception ex) {
			partida.getMazo().notificarFinMazo();
		}

		assertEquals(1, jugadores.get(0).getAfectosConseguidos());
	}

	@Test
	public void ganoSinDesempatePrimeroEliminado() {

		jugadores.get(0).sacarCartaDeMano(0);
		jugadores.get(1).sacarCartaDeMano(0);
		jugadores.get(2).sacarCartaDeMano(0);
		jugadores.get(3).sacarCartaDeMano(0);

		jugadores.get(0).tomarCarta(new Princesa());
		jugadores.get(0).tomarCarta(new Princesa());
		jugadores.get(0).jugarCarta(partida);

		jugadores.get(1).tomarCarta(new Baron());
		jugadores.get(2).tomarCarta(new Rey());
		jugadores.get(3).tomarCarta(new Sacerdote());

		try {
			for (int i = 0; i < 16; i++) {

				partida.getMazo().eliminarPrimeraCarta();
			}
		} catch (Exception ex) {
			partida.getMazo().notificarFinMazo();
		}

		assertEquals(1, jugadores.get(2).getAfectosConseguidos());
	}

	@Test
	public void ganoConDesempatePrimeroYTeceroEliminadO() {

		jugadores.get(0).sacarCartaDeMano(0);
		jugadores.get(1).sacarCartaDeMano(0);
		jugadores.get(2).sacarCartaDeMano(0);
		jugadores.get(3).sacarCartaDeMano(0);

		jugadores.get(0).tomarCarta(new Princesa());
		jugadores.get(0).tomarCarta(new Princesa());
		jugadores.get(0).jugarCarta(partida);

		jugadores.get(2).tomarCarta(new Princesa());
		jugadores.get(2).tomarCarta(new Princesa());
		jugadores.get(2).jugarCarta(partida);

		jugadores.get(1).descartar(new Condesa());
		jugadores.get(3).descartar(new Guardia());

		jugadores.get(1).tomarCarta(new Baron());
		jugadores.get(2).tomarCarta(new Baron());
		jugadores.get(3).tomarCarta(new Baron());

		try {
			for (int i = 0; i < 16; i++) {

				partida.getMazo().eliminarPrimeraCarta();
			}
		} catch (Exception ex) {
			partida.getMazo().notificarFinMazo();
		}

		assertEquals(1, jugadores.get(1).getAfectosConseguidos());
	}

	@Test
	public void reinicioRonda() {

		jugadores.get(0).sacarCartaDeMano(0);
		jugadores.get(1).sacarCartaDeMano(0);
		jugadores.get(2).sacarCartaDeMano(0);
		jugadores.get(3).sacarCartaDeMano(0);

		jugadores.get(0).tomarCarta(new Princesa());
		jugadores.get(0).tomarCarta(new Princesa());
		jugadores.get(0).jugarCarta(partida);

		jugadores.get(2).tomarCarta(new Princesa());
		jugadores.get(2).tomarCarta(new Princesa());
		jugadores.get(2).jugarCarta(partida);

		jugadores.get(1).descartar(new Condesa());
		jugadores.get(3).descartar(new Guardia());

		jugadores.get(1).tomarCarta(new Baron());
		jugadores.get(2).tomarCarta(new Baron());
		jugadores.get(3).tomarCarta(new Baron());

		try {
			for (int i = 0; i < 16; i++) {

				partida.getMazo().eliminarPrimeraCarta();
			}
		} catch (Exception ex) {
			partida.getMazo().notificarFinMazo();
		}

		assertEquals(11, partida.getMazo().getCantCartas());
		for (Jugador jugador : jugadores) {
			assertEquals(new Normal(), jugador.getEstado());
			assertEquals(1, jugador.getTamañoMano());
		}
	}

	@Test
	public void ganadorMazoNoVacio() {

		jugadores.get(0).tomarCarta(new Princesa());
		jugadores.get(0).jugarCarta(partida);

		jugadores.get(1).tomarCarta(new Princesa());
		jugadores.get(1).jugarCarta(partida);

		jugadores.get(2).tomarCarta(new Princesa());
		jugadores.get(2).jugarCarta(partida);

		assertEquals(1, jugadores.get(3).getAfectosConseguidos());
	}

}
