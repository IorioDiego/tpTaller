package game;

public class Normal extends Estado {
	
	@Override
	public Estado seRoboCarta() {
		return new Normal();
	}

}
