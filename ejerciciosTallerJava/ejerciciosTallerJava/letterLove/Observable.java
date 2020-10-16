package letterLove;

import java.util.LinkedList;

import game.Observer;

public class Observable {
	private LinkedList<Observer> observadores = new LinkedList<Observer>();
	
	public void register(Observer observador)
	{
		this.observadores.add(observador);
	}
	
	protected void notificar() {
		for (Observer observer : observadores) {
			observer.notificarse();
		}
	}
}
