package parte2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
	
	private static ServerSocket listen;
	private static List<Socket> sockets = new ArrayList<Socket>();

	public static void main(String[] args) {
		// Los datos del server
		Data datos = new Data();
		while(true) {
			try {
				listen = new ServerSocket(999);
				sockets.add(listen.accept());
				(new OyenteCliente(sockets.get(sockets.size() - 1), datos)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
