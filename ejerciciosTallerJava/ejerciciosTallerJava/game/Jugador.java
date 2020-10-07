package game;

import java.util.ArrayList;

import letterLove.Condesa;

import letterLove.Rey;
import letterLove.Principe;

import letterLove.Carta;

public class Jugador {

	private String nombre;
	private int id;
	private Carta carta1;
	private Carta cartaTemporal;
	private ArrayList<Carta> descarte = new ArrayList<Carta>();
	private ArrayList<Carta> mano = new ArrayList<Carta>();
	private Estado estado = new Normal();

	public Jugador(String nombre, int id) {
		this.nombre = nombre;
		this.id = id;
	}

	public void mostrarJugador() {
		System.out.println("Nombre: " + nombre + "\nID: " + id);
	}

//		public void cambiarCartaDescarte() {
//			Carta aux = this.carta1;
//			this.carta1 = this.cartaTemporal;
//			this.cartaTemporal = aux;
//		}

	public int elegirCartaParaJugar() {// izquierda 0, derecha 1
		// evento doble click en carta
		return 1;
	}

	public void descartar(Carta cartaJugada) {
		descarte.add(cartaJugada);

		// return mano.remove(i); //retorno una carta y la asigno a una carta
		// y despues la pongo en el descarte del tablero y
		// del jugador

		// cartaTemporal.mostrarCarta();
		// cartaTemporal.activarEfecto();
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

//		if (mano.contains(new Condesa()) && (mano.contains(new Rey()) || mano.contains(new Principe()))) {
//			if (nuevaCarta.equals(new Condesa())) {
//				descartar(nuevaCarta);
//			}	
//			else
//				descartar(mano.get(0));
//		} else
//		mano.add(nuevaCarta);
//		estado = estado.seRoboCarta();
	}

//		public Carta descartar() {
//			
//			Carta aux = cartaTemporal;
//			
//			descarte.push(cartaTemporal);
//			
//			cartaTemporal = null;
//			
//			return aux; 
//			
//		}

	public Carta getMano() {// para cuando tiene uno solo
		return mano.get(0);
	}

	public void mostrarDescarte() {

		System.out.println("\nEl jugador: " + id + "\nNombre: " + nombre + "\ntiene en su descarte: \n");

		for (Carta carta : descarte) {
			System.out.println(carta);
		}
	}

	public int sumarDescarte() {
		int suma = 0;
		for (Carta carta : descarte) {
			suma += carta.getFuerzaCarta();
		}
		return suma;
	}

//		public void tomarCartaDelMazo(Mazo mazo) {
//			
//			if(this.carta1 == null) {
//				this.carta1 = mazo.darCarta(this);
//				this.carta1.mostrarCarta();
//			}
//				
//			else {
//				this.cartaTemporal = mazo.darCarta();
//				this.cartaTemporal.mostrarCarta();
//			}
//				
//		}

	public void seJugoMucama() {
		estado = estado.seJugoMucama();
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void seJugoPrincesa() {
		estado = estado.seJugoPrincesa();

	}

	public void seJugoBaron() {
		estado = estado.seJugoBaron();
	}

	public void seJugoGuardia() {
		estado = estado.seJugoGuardia();
	}

	public Jugador seleccionarJugador(Partida partida) {
		/// TODO evento de seleccion de jugador
		int i = 1;
		return partida.elegirJugador(i);
	}

	public Carta sacarCartaDeMano(int i) {
		return mano.remove(i);
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

	public boolean tengoLaCarta(Carta carta) {
		return this.mano.contains(carta);
	}

	public void intercabiarMano(Jugador oponente) {
		ArrayList<Carta> cambioDemano = new ArrayList<>(this.mano);
		this.mano.clear();
		this.mano.addAll(oponente.mano);
		oponente.mano.clear();
		oponente.mano.addAll(cambioDemano);

	}

	public void mostrarMano() {
		for (Carta carta : descarte) {
			System.out.println(carta);
		}
	}

	@Override
	public String toString() {
		return "Jugador [nombre=" + nombre + ", id=" + id + ", carta1=" + carta1 + ", cartaTemporal=" + cartaTemporal
				+ ", descarte=" + descarte + ", mano=" + mano + ", estado=" + estado + "]";
	}

}
