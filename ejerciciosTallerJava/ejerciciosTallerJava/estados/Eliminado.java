package estados;

public class Eliminado extends Estado{
	
	private static final long serialVersionUID = 1L;

	@Override
	public Estado seReiniciaRonda() {
		return new Normal();
	}
}
