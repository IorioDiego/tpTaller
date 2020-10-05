package game;

import java.util.ArrayList;
import java.util.Stack;

import letterLove.Carta;

public class Jugador {

		private String nombre;
		private int id;
		private Carta carta1;
		private Carta cartaTemporal;
		private ArrayList<Carta> descarte = new ArrayList<Carta>();
		private ArrayList<Carta> mano= new ArrayList<Carta>();
		
		public Jugador(String nombre, int id) {
			this.nombre = nombre;
			this.id = id;
		}
		
		public void mostrarJugador() {
			System.out.println("Nombre: " + nombre + "\nID: " + id);
		}
		
		public void cambiarCartaDescarte() {
			Carta aux = this.carta1;
			this.carta1 = this.cartaTemporal;
			this.cartaTemporal = aux;
		}
		
		public void descartar(int i) {
			
			descarte.add(mano.remove(i));
			
			//return mano.remove(i); //retorno una carta y la asigno a una carta
									// y despues la pongo en el descarte del tablero y 
									// del jugador
			
			//cartaTemporal.mostrarCarta();
			//cartaTemporal.activarEfecto();
		}
		
		public void tomarCarta(Carta nuevaCarta) {
			mano.add(nuevaCarta);
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
		
		public void mostrarDescarte(){
			
			System.out.println("\nEl jugador: " + id 
					 			+ "\nNombre: " + nombre + "\ntiene en su descarte: \n");
			
			for (Carta carta : descarte) {
				carta.mostrarCarta();
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

}
