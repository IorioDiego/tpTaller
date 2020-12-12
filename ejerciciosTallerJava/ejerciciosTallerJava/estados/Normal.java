package estados;

public class Normal extends Estado {
	
	private static final long serialVersionUID = 1L;

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
