package letterLove;

public class ultimaCartaException extends Exception {
	private static final long serialVersionUID = -495059613251522648L;
	
	public ultimaCartaException(String mensaje) {
		super(mensaje);
	}
	
	public int mazoSinCartas()
	{
		return 1;
	}
}
