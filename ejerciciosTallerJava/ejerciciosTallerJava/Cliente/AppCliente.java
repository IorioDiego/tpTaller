package Cliente;
import java.io.IOException;


public class AppCliente {
	public static void main(String[] args) {
		try {
			Cliente client = new Cliente("localhost", 20000);
			client.start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		//192.168.2.38
	}
}
