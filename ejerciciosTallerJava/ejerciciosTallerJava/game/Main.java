package game;

import letterLove.Mazo;

public class Main {
		
	public static void main(String[] args) {
		
		Mazo mazo = new Mazo();
		Jugador jugador1 = new Jugador("Carlos", 1);
//		Jugador jugador2 = new Jugador("Marta", 2);
		
		mazo.mezclar();
		mazo.mezclar();
		
		//jugador1.tomarCartaDelMazo(mazo);
		System.out.println("\n");

		//jugador1.tomarCartaDelMazo(mazo);
		System.out.println("\n");
		
		System.out.println("El jugador 1 descarta: ");
		jugador1.cambiarCartaDescarte();
		jugador1.descartar(0);
		System.out.println("\n");
		
		//jugador1.tomarCartaDelMazo(mazo);
		System.out.println("\n");
		
		System.out.println("El jugador 1 descarta: ");
		jugador1.descartar(1);
		
		System.out.println("\n");
		//jugador1.tomarCartaDelMazo(mazo);
		System.out.println("\n");
		
		System.out.println("El jugador 1 descarta: ");
		jugador1.cambiarCartaDescarte();
		jugador1.descartar(0);

		
		jugador1.mostrarDescarte();
		System.out.println("\nTiene una suma total en el descarte de: " + jugador1.sumarDescarte());

	}

}
