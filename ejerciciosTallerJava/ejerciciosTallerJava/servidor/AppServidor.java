package servidor;

import java.io.IOException;

public class AppServidor {
	
	public static void main(String[] args) {

		try {
			Servidor server = new Servidor(20000);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
