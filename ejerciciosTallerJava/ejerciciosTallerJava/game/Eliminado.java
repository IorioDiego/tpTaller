package game;

public class Eliminado extends Estado{



	@Override
	public Estado seJugoPrincesa() {
		return new Eliminado();
	}

	@Override
	public Estado seJugoBaron() {
		return new Eliminado();
	}

	@Override
	public Estado seJugoGuardia() {
		return new Eliminado();
	}

}
