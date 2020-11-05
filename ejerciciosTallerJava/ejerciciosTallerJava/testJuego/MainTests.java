package testJuego;

import java.awt.EventQueue;
import java.util.ArrayList;

import game.Jugador;
import game.Partida;
import game.Tablero;

public class MainTests {

	public static void main(String[] args) {
		ArrayList<Jugador> jugadores;
		jugadores = new ArrayList<>();
		jugadores.add(new Jugador("Dieguen", 3));
		jugadores.add(new Jugador("Lucardo", 2));
		Partida partida = new Partida(3, 2, jugadores);
		partida.iniciarPartida();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tablero frame = new Tablero(jugadores);
					frame.setVisible(true);
					frame.init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		

//
//		jugadores.get(0).ganarRonda(partida.getAfecto());
//		jugadores.get(0).ganarRonda(partida.getAfecto());
//		jugadores.get(0).ganarRonda(partida.getAfecto());
//		jugadores.get(0).ganarRonda(partida.getAfecto());

//		System.out.println(jugadores.get(0));
//		partida.getMazo().darCarta(jugadores.get(0));
//		jugadores.get(0).mostrarMano();
//		jugadores.get(0).jugarCarta(partida);

		/// SE JUGO CARTA CONTRA EL JUGADOR 1
//		System.out.println();
//		System.out.println(jugadores.get(1));
//		System.out.println(jugadores.get(1).getMano());
//		System.out.println();
//		System.out.println(jugadores.get(0));

	}

}
