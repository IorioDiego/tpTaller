package ejerciciosTallerJava;

public class Baron extends Carta{
	
	public Baron() {
		super(3, "Baron", "Elige otro jugador y se revelan las cartas de forma privada. "
				+ "El jugador que posee la carta con menos fuerza es eliminado de la ronda");
	}
	
	public int getCantidadCartasPersonaje() {
		return 2;
	}

	@Override
	public int getFuerzaCarta() {
		return 3;
	}
}
