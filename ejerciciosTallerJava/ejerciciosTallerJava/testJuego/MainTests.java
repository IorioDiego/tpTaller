package testJuego;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import InterfaceGrafica.ComenzarRonda;
import InterfaceGrafica.Tablero;
import game.Jugador;
import game.Partida;

public class MainTests {

	public static void main(String[] args) throws MalformedURLException, LineUnavailableException, IOException, UnsupportedAudioFileException {
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ComenzarRonda frame = new ComenzarRonda();
					frame.setVisible(true);
					frame.init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
		});
		Clip clip = AudioSystem.getClip();
		clip.open(AudioSystem.getAudioInputStream(new File("sounds/music.wav")));
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		
	}

}
