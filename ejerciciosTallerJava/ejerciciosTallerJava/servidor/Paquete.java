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

public class Paquete{
	/**
	 * 
	 */
	private boolean esHost=false;
	private Socket cliente;
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
	
	public void setHostSala(boolean host)
	{
		esHost = host;
	}
	
	public boolean esHost()
	{
		return esHost;
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
	
	
	public Paquete()
	{
		
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
