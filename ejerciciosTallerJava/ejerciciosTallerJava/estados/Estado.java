package estados;

public abstract class Estado {
	
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
	
	public Estado seReiniciaRonda()
	{
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj.getClass().getName().equals(this.getClass().getName());
	}
	
	@Override
	public String toString() {
		return this.getClass().getName();
	}
	
}
