// ELENA NOVILLO LUCEÑO
// ESTIBALIZ ZUBIMENDI SOLAGUREN
package parte2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	
	private static ServerSocket listen;
	private static Data datos = new Data();

	public static void main(String[] args) {
		System.out.println("Servidor creado");
		try {
			// Escuchamos por el puerto indicado
			listen = new ServerSocket(999);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(true) {
			try {
				// Esperamos a que un cliente quiera comunicarse con nosotros
				Socket s = listen.accept();
				// Generamos un oyenteCliente para le nuevo cliente
				(new OyenteCliente(s, datos)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
