package game;

public abstract class Estado {
	
	// guardia -baron- princesa (eliminado) - mucama(protegido)
	public  Estado seJugoMucama() {
		return this;
	}
	
	public  Estado seJugoPrincesa() {
		return this;
	}
	
	public  Estado seJugoBaron() {
		return this;
	}
	
	public  Estado seJugoGuardia() {
		return this;
	}
	
	public  Estado seRoboCarta() {
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj.getClass().getName().equals(this.getClass().getName());
	}
}
