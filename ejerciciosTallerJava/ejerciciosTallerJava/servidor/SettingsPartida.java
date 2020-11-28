package servidor;

import java.io.Serializable;

public class SettingsPartida implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer cantJugadores;
	private String nombreSala;
	private Integer prendasAmor;
	private static String jugadorIncial;
	private static String orden;
	
	public SettingsPartida(Integer cantJugadores, String nombreSala, Integer prendasAmor) {
		this.cantJugadores = cantJugadores;
		this.nombreSala = nombreSala;
		this.prendasAmor = prendasAmor;
	}
	
	public SettingsPartida(SettingsPartida other) {
		cantJugadores = other.cantJugadores;
		nombreSala = other.nombreSala;
		prendasAmor = other.prendasAmor;
	}
	
	public SettingsPartida() {
		
	}
	

	public void setCantJugadores(Integer cantJugadores) {
		this.cantJugadores = cantJugadores;
	}



	public void setNombreSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}



	public void setPrendasAmor(Integer prendasAmor) {
		this.prendasAmor = prendasAmor;
	}

	public static  void setOrden(String o) {
		orden = o;
	}

	public static void setJugadorIncial(String jI) {
		jugadorIncial = jI;
	}

	
	public Integer getCantJugadores() {
		return cantJugadores;
	}

	public String getNombreSala() {
		return nombreSala;
	}
	
	public Integer getPrendasAmor() {
		return prendasAmor;
	}



	public String getJugadorIncial() {
		return jugadorIncial;
	}

	
	public String getOrden() {
		return orden;
	}

	
	@Override
	public String toString() {
		return "SettingsPartida [cantJugadores=" + cantJugadores + ", nombreSala=" + nombreSala + ", prendasAmor="
				+ prendasAmor + "]";
	}
	
	

	
}
