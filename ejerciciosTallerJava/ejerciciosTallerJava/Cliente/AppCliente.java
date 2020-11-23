package Cliente;
import java.io.IOException;


public class AppCliente {
	public static void main(String[] args) {
		try {
			new Cliente("localhost", 20000);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
