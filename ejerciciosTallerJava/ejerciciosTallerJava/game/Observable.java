package game;

import java.util.LinkedList;

public class Observable {
	private LinkedList<Observer> observadores = new LinkedList<Observer>();
	
	public void register(Observer observador)
	{
		this.observadores.add(observador);
	}
	
	public void notificarFinMazo() {
		for (Observer observer : observadores) {
			observer.notificarseFinMazo();
		}
	}
	
	public void notificarEstadoEliminado() {
		for (Observer observer : observadores) {
			observer.notificarseEstadoEliminado();
		}
	}
	
	public void notificarEndGame() {
		for (Observer observer : observadores) {
			observer.notificarseEndGame();
		}
	}
}
