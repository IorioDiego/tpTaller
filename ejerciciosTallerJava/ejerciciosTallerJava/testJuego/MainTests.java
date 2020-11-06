package testJuego;

import java.awt.EventQueue;
import java.util.ArrayList;

import InterfaceGrafica.Tablero;
import game.Jugador;
import game.Partida;

public class MainTests {

	public static void main(String[] args) {
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArrayList<Jugador> jugadores = new ArrayList<>();
					jugadores.add(new Jugador("Dieguien", 3));
					jugadores.add(new Jugador("Lucardo", 2));
					jugadores.add(new Jugador("Tomarson", 1));
					jugadores.add(new Jugador("Leandrovich", 4));
					Partida partida = new Partida(3, 4, jugadores);
					partida.iniciarPartida();
					Tablero frame = new Tablero();
					frame.setVisible(true);
					frame.init(jugadores);
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
