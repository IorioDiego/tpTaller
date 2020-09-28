package ejerciciosTallerJava;

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
}
