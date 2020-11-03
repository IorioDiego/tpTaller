package game;

public abstract class Carta {

	private int fuerza;
	private String nombre;
	private String descEfecto;

	public Carta(int fuerza, String nombre, String descEfecto) {
		this.fuerza = fuerza;
		this.nombre = nombre;
		this.descEfecto = descEfecto;
	}

	public abstract int getCantidadCartasPersonaje();

	public abstract int getFuerzaCarta();

	public abstract void activarEfecto(Jugador jugador, Partida partida);

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carta other = (Carta) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Valor: " + fuerza + "\nNombre: " + nombre + "\nDescripcion Efecto: " + descEfecto;
	}
	
	public int getFuerza() {
		return fuerza;
	}
	
	
	public String getNombre() {
		return nombre;
	}
	
	
//	@Override
//	public String toString() {
//		return "Valor: " + fuerza + "\nNombre: " + nombre ;
//	}

	
}
