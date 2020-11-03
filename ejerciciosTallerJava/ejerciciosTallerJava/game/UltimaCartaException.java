package game;

public class UltimaCartaException extends Exception {
	private static final long serialVersionUID = -495059613251522648L;
	
	public UltimaCartaException(String mensaje) {
		super(mensaje);
	}
	
	public int mazoSinCartas()
	{
		return 1;
	}
}
