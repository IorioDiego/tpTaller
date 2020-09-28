package ejerciciosTallerJava;

public class Rey extends Carta{
	
	public Rey() {
		super(6, "Rey", "Elige otro jugador e intercambian sus manos");
		
	}
	
	public int getCantidadCartasPersonaje() {
		return 1;
	}

	@Override
	public int getFuerzaCarta() {
		return 6;
	}
}
