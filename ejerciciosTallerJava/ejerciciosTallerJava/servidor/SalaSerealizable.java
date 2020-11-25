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
		return  String.format("%-90sconectados:%02d",nombreSala,cantJugadores);
	}

	public String getNombreSala() {
		return nombreSala;
	}

	public void setNombreSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}
	
	
	
}
