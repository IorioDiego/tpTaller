package cartas;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JDialog;

import game.Jugador;
import game.Partida;

public class Condesa extends Carta{
	
	public Condesa() {
		super(7, "Condesa", "Si un jugador tiene esta carta y el Rey o el Príncipe, \n "
				+ "esta carta debe ser jugada inmediatamente");
		
	}
	
	public int getCantidadCartasPersonaje() {
		return 1;
	}

	@Override
	public int getFuerzaCarta() {
		return 7;
	}

	@Override
	public void activarEfecto(Jugador jugador, Partida partida,JDialog lista,JDialog listaCartas) {
		//TODO
		
	}


}
