package servidor;

import java.io.Serializable;

public class SalaSerealizable implements Serializable{

	int cantJugadores;
	String nombreSala;
	
	public SalaSerealizable(int CJug, String nombre ) {
		cantJugadores=CJug;
		nombreSala=nombre;
	}

	@Override
	public String toString() {
		return  String.format("%-115s%02d",nombreSala,cantJugadores);
	}
	
}
