package testJuego;

import java.awt.EventQueue;
import java.util.ArrayList;

import InterfaceGrafica.ComenzarRonda;
import InterfaceGrafica.Tablero;
import game.Jugador;
import game.Partida;

public class MainTests {

	public static void main(String[] args) {
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ComenzarRonda frame = new ComenzarRonda();
					frame.setVisible(true);
					frame.init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		

	}

}
