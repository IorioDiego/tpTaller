package cartas;

import java.io.IOException;

import javax.swing.JDialog;

import InterfaceGrafica.Tablero;
import game.Jugador;
import game.Partida;
import servidor.Paquete;
import servidor.Servidor;

public class Rey extends Carta{
	
	public Rey() {
		super(6, "Rey", "descripciones/reyDescrip.png");
		
	}
	
	public int getCantidadCartasPersonaje() {
		return 1;
	}

	@Override
	public int getFuerzaCarta() {
		return 6;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida,Paquete paquete) {
		int jElegido = 0;
		try {
			jElegido = (int) paquete.getEntrada().readObject();
			Jugador oponente = partida.getJugadores().get(jElegido);
		
			Carta miMano = jugador.getMano(0);
			Carta opMano = oponente.getMano(0);
			
			paquete.getSalida().writeObject(opMano);
			for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
				if (paqueteCliente.getCliente().isConnected()&& paquete.getNick().equals(oponente.getNombre())) {
					paqueteCliente.getSalida().writeObject(miMano);
				}
			}
			jugador.intercabiarMano(partida.getJugadores().get(jElegido));
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
//		lista.setVisible(true);
//		jugador.intercabiarMano(partida.getJugadores().get(Tablero.getJugadorElegido()));
	}
}
