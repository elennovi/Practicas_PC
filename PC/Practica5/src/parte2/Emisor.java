package parte2;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Emisor extends Thread {
	private Socket s;
	private String textFile;
	private ObjectOutputStream fout;
	
	public Emisor(Socket s1, String textFile) {
		try {
			this.s = s1;
			fout = new ObjectOutputStream((this.s).getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.textFile = textFile;
	}
	
	public void run() {
		// Debemos mandar el contenido por el 
		try {
			fout.writeObject(textFile);
			fout.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
