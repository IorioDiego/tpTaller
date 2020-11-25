package comandos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import servidor.Paquete;
import servidor.SalaSerealizable;
import servidor.Servidor;

public class Refrescar implements ComandosServer {

	private ComandosServer siguiente;

	@Override
	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;

	}

	@Override
	public String procesar(Paquete paquete, String msj) {
		if (msj.equals("11")) {
			try {
				ArrayList<SalaSerealizable> envioSalas = new ArrayList<>();
				for (Map.Entry<String, ArrayList<Paquete>> entry : Servidor.getSalas().entrySet()) {
					envioSalas.add(new SalaSerealizable(entry.getValue().size(), entry.getKey()));
				}
				paquete.getSalida().writeObject(envioSalas);

			} catch (IOException e) {
				e.printStackTrace();
			}
			return "y";
		} else
			return siguiente.procesar(paquete, msj);
	}

}
