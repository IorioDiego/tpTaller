package InterfaceGrafica;

import java.awt.Graphics2D;

import cartas.Carta;



public class DibujoCarta  {
	
	private int ejeX;
	private  int ejeY;
	private Carta cartaDib; 
	
	public  DibujoCarta(Carta carta, int x, int y) {
		cartaDib = carta;
		ejeX=x;
		ejeY=y;
	}

	public int getEjeX() {
		return ejeX;
	}

	public void setEjeX(int ejeX) {
		this.ejeX = ejeX;
	}

	public int getEjeY() {
		return ejeY;
	}

	public void setEjeY(int ejeY) {
		this.ejeY = ejeY;
	}

	public Carta getCartaDib() {
		return cartaDib;
	}

	public void setCartaDib(Carta cartaDib) {
		this.cartaDib = cartaDib;
	}



}
