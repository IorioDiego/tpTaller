package game;

public class Normal extends Estado {
	
	@Override
	public Estado seJugoMucama() {
		return new Protegido();		
	}
	
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
