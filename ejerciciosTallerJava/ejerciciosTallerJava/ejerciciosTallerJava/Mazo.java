package ejerciciosTallerJava;

import java.util.Collections;
import java.util.Stack;

public class Mazo {
	
	private int cantCartas = 16;
	private Stack<Carta> mazo = new Stack<Carta>();
	
	
	public Mazo() {
		
		mazo.push(new Guardia());
		mazo.push(new Guardia());
		mazo.push(new Guardia());
		mazo.push(new Guardia());
		mazo.push(new Guardia());
		
		mazo.push(new Sacerdote());
		mazo.push(new Sacerdote());
		
		mazo.push(new Baron());
		mazo.push(new Baron());
		
		mazo.push(new Mucama());
		mazo.push(new Mucama());
		
		mazo.push(new Principe());
		mazo.push(new Principe());
		
		mazo.push(new Rey());

		mazo.push(new Condesa());
		
		mazo.push(new Princesa());
	}
	
	public boolean mazoVacio() {
		return cantCartas == 0;
	}
	
	public void mezclar() {
		Collections.shuffle(mazo);
		Collections.shuffle(mazo);
	}
	
	public Carta darCarta() {
		cantCartas--;
		//mazo.peek().mostrarCarta();
		return mazo.pop();
	}
	
//	public void mostrarMazo() {
//		
//		for (Carta x : mazo) {
//			x.mostrarCarta();
//		}	
//		
//	}
	
}	
