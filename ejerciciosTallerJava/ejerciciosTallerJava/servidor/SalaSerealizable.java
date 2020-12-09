package servidor;

import java.io.Serializable;

public class SalaSerealizable implements Serializable{

	private Integer JugadoresConetados;
	private SettingsPartida setPart;
	
	public SalaSerealizable(Integer jugadoresConetados,SettingsPartida setPart) {
		this.JugadoresConetados = jugadoresConetados;
		this.setPart = setPart;
	}

	@Override
	public String toString() {
		return  String.format("Sala: %-15s [%s]   Prendas: %-17sJugadores: %02d/%02d",setPart.getNombreSala(),setPart.isInGame()?"En Juego":"disponible",setPart.getPrendasAmor(),JugadoresConetados,setPart.getCantJugadores());
	}

	
	public Integer getJugadoresConetados() {
		return JugadoresConetados;
	}

	public void setJugadoresConetados(Integer jugadoresConetados) {
		JugadoresConetados = jugadoresConetados;
	}

	

	public SettingsPartida getSetPart() {
		return setPart;
	}

	public void setSetPart(SettingsPartida setPart) {
		this.setPart = setPart;
	}
	
}
