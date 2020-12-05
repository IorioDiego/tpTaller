package servidor;

import java.io.Serializable;

import game.Mazo;

public class SettingsPartida implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer cantJugadores;
	private String nombreSala;
	private Integer prendasAmor;
	private String jugadorIncial;
	private String orden;
	private Mazo mazo;
	
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
	

	public Mazo getMazo() {
		return mazo;
	}

	public void setMazo(Mazo mazo) {
		this.mazo = mazo;
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

	public   void setOrden(String o) {
		orden = o;
	}

	public  void setJugadorIncial(String jI) {
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
