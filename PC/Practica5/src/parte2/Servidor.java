package parte2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	
	private static ServerSocket listen;
	private static volatile Data datos = new Data();

	public static void main(String[] args) {
		System.out.println("Servidor creado");
		// Los datos del server
		
		try {
			listen = new ServerSocket(999);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true) {
			datos = datos; // Actualizamos los datos por si ha habido alguna modificacion
			try {
				Socket s = listen.accept();
				(new OyenteCliente(s, datos)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
