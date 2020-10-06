package game;

public class Protegido extends Estado{
	
	@Override
	public Estado seRoboCarta() {
		return new Normal();
	}
}
