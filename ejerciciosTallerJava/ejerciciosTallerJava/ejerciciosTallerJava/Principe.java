package ejerciciosTallerJava;

public class Principe extends Carta{
	
	public Principe() {
		super(5, "Principe", " elige otro jugador (incluso a s� mismo) para descartar su mano y robar una "
				+ "carta nueva. Si la Princesa es descartada de esta manera, el jugador que la descart� "
				+ "es eliminado de la ronda");
		
	}
	
	public int getCantidadCartasPersonaje() {
		return 2;
	}

	@Override
	public int getFuerzaCarta() {
		return 5;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida) {
		// TODO Auto-generated method stub
		
	}

}
