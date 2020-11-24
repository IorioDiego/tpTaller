package Cliente;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import InterfaceGrafica.Salas;

public class HiloEscuchar extends Thread implements Serializable {
	
	DataInputStream entrada;
//	private ObjectOutputStream;
	public HiloEscuchar(DataInputStream entrada,Salas salas) {
	 this.entrada = entrada;
	}
	
	@Override
	public void run()
	{	
		try {
			String msj;
			while(!(msj = entrada.readUTF()).equals("--Salir"))
			{	
				System.out.println(msj);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}