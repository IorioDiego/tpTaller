package servidor;

import java.io.Serializable;

public class SettingsPartida implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer cantJugadores;
	private String nombreSala;
	private Integer prendasAmor;
	
//	public SettingsPartida(Integer cantJugadores, String nombreSala, Integer prendasAmor) {
//		this.cantJugadores = cantJugadores;
//		this.nombreSala = nombreSala;
//		this.prendasAmor = prendasAmor;
//	}
	
	
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



	public Integer getCantJugadores() {
		return cantJugadores;
	}

	public String getNombreSala() {
		return nombreSala;
	}

	public Integer getPrendasAmor() {
		return prendasAmor;
	}



	@Override
	public String toString() {
		return "SettingsPartida [cantJugadores=" + cantJugadores + ", nombreSala=" + nombreSala + ", prendasAmor="
				+ prendasAmor + "]";
	}
	
	

	
}
