package estados;

public class Protegido extends Estado{
	
	private static final long serialVersionUID = 1L;

	@Override
	public Estado seRoboCarta() {
		return new Normal();
	}
	
	@Override
	public Estado seReiniciaRonda() {
		return new Normal();
	}
}
