package servidor;

import java.io.IOException;
import java.io.Serializable;

public class AppServidor implements Serializable {
	
	public static void main(String[] args) {

		try {
			new Servidor(20000);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}