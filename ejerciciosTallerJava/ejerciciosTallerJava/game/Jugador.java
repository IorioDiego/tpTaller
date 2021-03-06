package game;

import java.io.Serializable;
import java.util.ArrayList;

import cartas.Carta;
import cartas.Condesa;
import cartas.Principe;
import cartas.Rey;
import estados.Eliminado;
import estados.Estado;
import estados.Normal;
import estados.Protegido;
import servidor.Paquete;

public class Jugador extends Observable implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombre;
	private int afectosConseguidos;
	private ArrayList<Carta> descarte = new ArrayList<Carta>();
	private ArrayList<Carta> mano = new ArrayList<Carta>();
	private Estado estado = new Normal();
	private boolean pasoTurno = false;

	public Jugador() {
	};

	public Jugador(String nombre) {
		this.nombre = nombre;
		this.afectosConseguidos = 0;
	}

	public void setAfectosConseguidos(int afectosConseguidos) {
		this.afectosConseguidos = afectosConseguidos;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void seJugoMucama() {
		estado = estado.seJugoMucama();
	}

	public void seReiniciaRonda() {
		estado = estado.seReiniciaRonda();
		descarte.clear();
		vaciarMano();
	}

	public boolean isPasoTurno() {
		return pasoTurno;
	}

	public void setPasoTurno(boolean pasoTurno) {
		this.pasoTurno = pasoTurno;
	}

	public void seJugoBaron() {
		estado = estado.seJugoBaron();
		notificarEstadoEliminado();
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public void seJugoGuardia() {
		estado = estado.seJugoGuardia();
		notificarEstadoEliminado();
	}

	public void seJugoPrincesa() {
		estado = estado.seJugoPrincesa();
		notificarEstadoEliminado();
	}

	public void ganarRonda(int afectos, Partida partida) {
		partida.setGanadoRonda(this);
		if (++afectosConseguidos == afectos)
			notificarEndGame();
	}

	public Carta tomarCarta(Carta nuevaCarta) {
		Carta tomada = nuevaCarta;
		if (nuevaCarta.equals(new Condesa()) && (mano.contains(new Rey()) || mano.contains(new Principe())))
			descartar(nuevaCarta);
		else {
			if (mano.contains(new Condesa()) && (nuevaCarta.equals(new Rey()) || nuevaCarta.equals(new Principe()))) {
				descartar(sacarCartaDeMano(0));
				tomada = nuevaCarta;
			}
			mano.add(nuevaCarta);

		}
		estado = estado.seRoboCarta();
		return tomada;
	}

	public void jugarCarta(Partida partida, int index, Paquete paquete) {
		Carta cartaElegida = sacarCartaDeMano(index);
		cartaElegida.activarEfecto(this, partida, paquete);
		descartar(cartaElegida);
		pasoTurno = true;
	}

	public int compararMano(Jugador oponente) {
		return this.mano.get(0).getFuerzaCarta() - oponente.mano.get(0).getFuerzaCarta();
	}

	public void intercabiarMano(Jugador oponente) {
		ArrayList<Carta> cambioDemano = new ArrayList<>(this.mano);
		this.mano.clear();
		this.mano.addAll(oponente.mano);
		oponente.mano.clear();
		oponente.mano.addAll(cambioDemano);

	}

	public ArrayList<Carta> getManoCompleta() {
		return mano;
	}

	public boolean tengoLaCarta(Carta carta) {
		return this.mano.contains(carta);
	}

	public Carta getDescarte(int index) {
		return descarte.get(index);
	}

	public Carta sacarCartaDeMano(int i) {
		return mano.remove(i);
	}

	public void mostrarMano() {
		for (Carta carta : mano) {
			System.out.println(carta);
		}
	}

	@Override
	public String toString() {
		return "Jugador [nombre=" + nombre + ", afectos=" + afectosConseguidos + ", estado=" + estado + "]";
	}

	public void vaciarMano() {
		mano.clear();
	}

	public int getTamanioMano() {
		return mano.size();
	}

	public int elegirCartaParaJugar(int i) {
		return i;
	}

	public Carta descartar(Carta cartaJugada) {
		descarte.add(cartaJugada);
		return cartaJugada;
	}

	public Jugador seleccionarJugador(Partida partida) {
		int i = 1;
		return partida.elegirJugador(i);
	}

	public int sumarDescarte() {
		int suma = 0;
		for (Carta carta : descarte) {
			suma += carta.getFuerzaCarta();
		}
		return suma;
	}

	public Carta getMano(int i) {
		return mano.get(i);
	}

	public Carta getCarta(int i) {
		return mano.get(i);
	}

	public Carta ultimaCartaDescarte() {
		return descarte.get(descarte.size() - 1);
	}

	public String getNombre() {
		return nombre;
	}

	public int getAfectosConseguidos() {
		return afectosConseguidos;
	}

	public boolean isBlockedOrDelete() {
		return getEstado().equals(new Eliminado()) || getEstado().equals(new Protegido());
	}

	public Carta getUltimaDescartada() {
		if (descarte.size() > 1 && descarte.get(descarte.size() - 1).equals(new Principe()))
			return descarte.get(descarte.size() - 2);
		else if (!descarte.isEmpty())
			return descarte.get(descarte.size() - 1);
		return null;
	}
}
