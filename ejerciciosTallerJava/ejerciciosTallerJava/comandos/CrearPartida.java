//package comandos;
//
//import java.io.IOException;
//
//import servidor.Paquete;
//import servidor.Servidor;
//
//public class CrearPartida implements ComandosServer{
//
//	
//	private ComandosServer siguiente;
//
//	public void establecerSiguiente(ComandosServer siguiente) {
//		this.siguiente = siguiente;
//
//	}
//	
//	public String procesar(Paquete paquete, String msj) {
//		String crearPartida = "--crearPartida";
//		if (msj.equals("16")) {
//			try {
//				
//					for (Paquete paqueteCliente : Servidor.darClientesDeSala(paquete.getSala())) {
//						
//						if (	paqueteCliente.getCliente().isConnected() &&
//								paqueteCliente.getSala().equals(paquete.getSala())) {
//							paqueteCliente.getSalida().writeObject(comenzarPartida);
//						}
//					}
//				
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return comenzarPartida;
//		} else
//			return siguiente.procesar(paquete, msj);
//	}
//}
