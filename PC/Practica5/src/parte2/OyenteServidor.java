package parte2;

import java.net.Socket;

public class OyenteServidor extends Thread{
	private Socket s;
	
	public OyenteServidor(Socket s) {
		this.s = s;
	}
	
	public void run() {
		while(true) {
			
		}
	}
}
