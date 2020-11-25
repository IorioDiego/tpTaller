package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Paquete implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2585930666645442349L;
	private static SimpleDateFormat formatFecha = new SimpleDateFormat(" [dd/MM/yyyy HH:mm:ss] ");
	private Socket cliente;
	private String salaActiva;
	private boolean enChatPrivado = false;
	private Date inicioConexion;
	private String sala = "..";
	private String nick;
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	
	public Paquete(Date inicioConexion,Socket cliente,String nick,ObjectInputStream entrada,ObjectOutputStream salida) {
		this.inicioConexion=inicioConexion;
		this.cliente = cliente;
		this.nick = nick;
		this.entrada = entrada;
		this.salida = salida;
	}
	
	
	public boolean isEnChatPrivado() {
		return enChatPrivado;
	}
	
	
	public void dejarSala(String sala)
	{
		this.sala = "..";
	}
	
	public void setEnChatPrivado(boolean enChatPrivado) {
		this.enChatPrivado = enChatPrivado;
	}


	public Date getInicioConexion() {
		return inicioConexion;
	}
	
	public String getSalaActiva() {
		return salaActiva;
	}

	public void setSalaActiva(String salaActiva) {
		this.salaActiva = salaActiva;
	}
	
	public Paquete()
	{
		
	}
	public String getTiempoConexion()
	{
        Date tiempoActual = new Date();
        long diffInMillies = Math.abs(inicioConexion.getTime() - tiempoActual.getTime());
        long diff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        int dias = (int)diff/86400;
        int horas = (int)(diff%86400)/3600;
        int minutos = (int)diff%3600/60;
        int segundos = (int)diff%60;
        return String.format("Tiempo activo de %s: %02d dias %02d hh %02d mm %02d ss",nick,dias,horas,minutos,segundos);
	}
	
	@Override
	public String toString() {
		
		return formatFecha.format(new Date()) + "Mensaje de " + nick + " : " ;
	}
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Socket getCliente() {
		return cliente;
	}
	
//	public int cantidadSalas()
//	{
//		return salas.size(); 
//	}
	
//	public ArrayList<String> getSala() {
//		return salas;
//	}
	
	public String getSala()
	{
		return sala;
	}

	public void setSala(String sala) {
		this.sala=sala;
	}

	public ObjectInputStream getEntrada() {
		return entrada;
	}

	public void setEntrada(ObjectInputStream entrada) {
		this.entrada = entrada;
	}

	public ObjectOutputStream getSalida() {
		return salida;
	}

	public void setSalida(ObjectOutputStream salida) {
		this.salida = salida;
	}

	public void setCliente(Socket cliente) {
		this.cliente = cliente;
	}	
	
}
