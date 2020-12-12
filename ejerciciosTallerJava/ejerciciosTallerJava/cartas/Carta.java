package cartas;

import java.io.Serializable;

import game.Jugador;
import game.Partida;
import servidor.Paquete;

public abstract class Carta implements Serializable {

	private static final long serialVersionUID = 1L;
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

	public abstract void activarEfecto(Jugador jugador, Partida partida, Paquete paquete);

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

		return descEfecto;
	}

	public int getFuerza() {
		return fuerza;
	}

	public String getNombre() {
		return nombre;
	}

}
