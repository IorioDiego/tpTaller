package letterLove;

import java.util.ArrayList;

public class Descarte {
	
	private ArrayList<Carta> cartasJugadas = new ArrayList<Carta>();
	
	public void mostrarDescarte(){
			for (Carta carta : cartasJugadas) {
				System.out.println(carta);

			}
	}

}
