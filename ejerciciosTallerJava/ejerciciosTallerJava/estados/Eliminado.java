package estados;

public class Eliminado extends Estado{
	
	@Override
	public Estado seReiniciaRonda() {
		return new Normal();
	}
}