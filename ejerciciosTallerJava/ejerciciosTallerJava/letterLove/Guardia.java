package letterLove;

import java.util.Scanner;

import game.Estado;
import game.Jugador;
import game.Partida;

public class Guardia extends Carta{
	
	public Guardia() {
		super(1, "Guardia", "Elige otro jugador oponente y nombra un tipo de carta (excepto \"Guardia\"). "
				+ "Si el oponente tiene en su mano una carta de ese tipo, "
				+ "el oponente es eliminado de la ronda");
		
	}
	
	public int getCantidadCartasPersonaje() {
		return 5;
	}
	
	@Override
	public int getFuerzaCarta(){
		return 1;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida) {
		Scanner entrada = new Scanner(System.in);
		Jugador oponente = jugador.seleccionarJugador(partida);
		partida.mostrarLista();
		System.out.print("Ingrese opcion: ");
	    int opcion=Integer.parseInt(entrada.nextLine());
		if(oponente.tengoLaCarta(partida.seleccionarCarta(opcion)))
			oponente.seJugoGuardia();
	}
}
