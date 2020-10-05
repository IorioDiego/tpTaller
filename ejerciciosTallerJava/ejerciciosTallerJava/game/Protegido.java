package game;

public class Protegido extends Estado{

	@Override
	public Estado seJugoMucama() {
		return new Protegido();		
	}
}
