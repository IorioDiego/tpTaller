package game;

import java.util.ArrayList;

import cartas.Condesa;
import cartas.Principe;
import cartas.Rey;
import estados.Estado;
import estados.Normal;

public class Jugador extends Observable {

	private String nombre;
	private int id;
	private int afectosConseguidos;
	private ArrayList<Carta> descarte = new ArrayList<Carta>();
	private ArrayList<Carta> mano = new ArrayList<Carta>();
	private Estado estado = new Normal();

	public Jugador(String nombre, int id) {
		this.nombre = nombre;
		this.id = id;
		this.afectosConseguidos = 0;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void seJugoMucama() {
		estado = estado.seJugoMucama();
	}

	public void seReiniciaRonda() {
		estado = estado.seReiniciaRonda();

	}

	public void seJugoBaron() {
		estado = estado.seJugoBaron();
		notificarEstadoEliminado();
	}

	public void seJugoGuardia() {
		estado = estado.seJugoGuardia();
		notificarEstadoEliminado();
	}

	public void seJugoPrincesa() {
		estado = estado.seJugoPrincesa();
		notificarEstadoEliminado();
	}
	
	public void ganarRonda(int afectos) {
		if (++afectosConseguidos == afectos)
			notificarEndGame();
	}
	
	public void tomarCarta(Carta nuevaCarta) {

		if (nuevaCarta.equals(new Condesa()) && (mano.contains(new Rey()) || mano.contains(new Principe())))
			descartar(nuevaCarta);
		else {
			if (mano.contains(new Condesa()) && (nuevaCarta.equals(new Rey()) || nuevaCarta.equals(new Principe())))
				descartar(sacarCartaDeMano(0));
			mano.add(nuevaCarta);
		}
		estado = estado.seRoboCarta();
	}


	public void jugarCarta(Partida partida) {// recive indice del evento
		int indiceAux = this.elegirCartaParaJugar();
		Carta cartaElegida = sacarCartaDeMano(indiceAux);
		cartaElegida.activarEfecto(this, partida);
		descartar(cartaElegida);
	}

	public int compararMano(Jugador oponente) {// si devuleve negativo perdi, si es 0 empatamos
												// si es positivo gane
		return this.mano.get(0).getFuerzaCarta() - oponente.mano.get(0).getFuerzaCarta();
	}

	
	public void intercabiarMano(Jugador oponente) {
		ArrayList<Carta> cambioDemano = new ArrayList<>(this.mano);
		this.mano.clear();
		this.mano.addAll(oponente.mano);
		oponente.mano.clear();
		oponente.mano.addAll(cambioDemano);

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

	public int getTamaņoMano() {
		return mano.size();
	}

	
	public int elegirCartaParaJugar() {// izquierda 0, derecha 1
		// evento doble click en carta
		return 1;
	}

	public void descartar(Carta cartaJugada) {
		descarte.add(cartaJugada);
	}
	
	public Jugador seleccionarJugador(Partida partida) {
		/// evento de seleccion de jugador
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
	
	public Carta getMano() {// para cuando tiene uno solo
		return mano.get(0);
	}

	public void mostrarDescarte() {

		System.out.println("\nEl jugador: " + id + "\nNombre: " + nombre + "\ntiene en su descarte: \n");

		for (Carta carta : descarte) {
			System.out.println(carta);
		}
	}
	
	public int getAfectosConseguidos() {
		return afectosConseguidos;
	}

}
