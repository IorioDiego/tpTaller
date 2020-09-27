package ejerciciosTallerJava;

public class Mucama extends Carta{
	
	public Mucama() {
		super(4, "Mucama", "El jugador está protegido y no puede ser afectado por cartas "
				+ "de otros jugadores hasta su siguiente turno");
		
	}
	
	public int getCantidadCartasPersonaje() {
		return 2;
	}

	@Override
	public int getFuerzaCarta() {
		return 4;
	}
}
