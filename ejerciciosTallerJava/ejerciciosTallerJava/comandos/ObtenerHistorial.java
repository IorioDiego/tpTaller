package comandos;

import java.io.IOException;
import java.util.ArrayList;

import servidor.Paquete;
import servidor.Servidor;

public class ObtenerHistorial implements ComandosServer {
	
	private ComandosServer siguiente;
	
	@Override
	public void establecerSiguiente(ComandosServer siguiente) {
		this.siguiente = siguiente;
		
	}

	@Override
	public String procesar(Paquete paquete, String msj) {
		String rta = "n";
		if(msj.equals("9"))
		{	
			try {
				paquete.getSalida().writeUTF("--------------------HISTORIAL------------------" + "\n");
				ArrayList<String> historialSala=Servidor.mostrarHistorial(paquete.getSalaActiva());
				for (String historialMsj : historialSala) 
					paquete.getSalida().writeUTF(historialMsj + "\n");
				
				paquete.getSalida().writeUTF("------------------FIN HISTORIAL----------------" + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return rta;
		}
		else
			return siguiente.procesar(paquete,msj);
	}

}
