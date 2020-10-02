package ejerciciosTallerJava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Mazo {
	
	private int cantCartas = 16;
	private ArrayList<Carta> mazo = new ArrayList<Carta>();
	
	
	public Mazo() {
		
		mazo.add(new Guardia());
		mazo.add(new Guardia());
		mazo.add(new Guardia());
		mazo.add(new Guardia());
		mazo.add(new Guardia());
		
		mazo.add(new Sacerdote());
		mazo.add(new Sacerdote());
		
		mazo.add(new Baron());
		mazo.add(new Baron());
		
		mazo.add(new Mucama());
		mazo.add(new Mucama());
		
		mazo.add(new Principe());
		mazo.add(new Principe());
		
		mazo.add(new Rey());

		mazo.add(new Condesa());
		
		mazo.add(new Princesa());
	}
	
	public Carta eliminarPrimeraCarta() {
		return mazo.remove(0);
	}
	
	public boolean mazoVacio() {
		return cantCartas == 0;
	}
	
	public void mezclar() {
		Collections.shuffle(mazo);
		Collections.shuffle(mazo);
	}
	
	public void darCarta(Jugador jugador) throws IndexOutOfBoundsException {
		
		//mazo.peek().mostrarCarta();
		try {
			if(cantCartas == 1)
				int flag=1;
			jugador.tomarCarta(mazo.remove(0));
			cantCartas--;
		} catch (IndexOutOfBoundsException e) {
			e.getStackTrace();
			//finalizar partida
			// TODO: handle exception
		}
	
	}
	

//	public void mostrarMazo() {
//		
//		for (Carta x : mazo) {
//			x.mostrarCarta();
//		}	
//		
//	}
	
}	
