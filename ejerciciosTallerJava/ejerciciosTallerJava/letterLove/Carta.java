package letterLove;

import game.Jugador;
import game.Partida;

public abstract class Carta {
	

	private int fuerza;
	private String nombre;
	private String descEfecto;
	
	public Carta(int fuerza, String nombre, String descEfecto) {
			this.fuerza = fuerza;
			this.nombre = nombre;
			this.descEfecto = descEfecto;
	}

	
	public void mostrarCarta() {
		System.out.println("Valor: " + fuerza + "\nNombre: " + nombre + 
									"\nDescripcion Efecto: " + descEfecto);
	}
	
	
	public abstract int getCantidadCartasPersonaje();
	public abstract int getFuerzaCarta();
	public abstract void activarEfecto(Jugador jugador,Partida partida);
}
