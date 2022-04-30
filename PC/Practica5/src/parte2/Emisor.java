package parte2;

import java.io.IOException;
import java.io.ObjectInputStream;
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
			new ObjectInputStream((this.s).getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.textFile = textFile;
	}
	
	public void run() {
		// Mandamos el contenido del fichero al cliente receptor
		try {
			fout.reset();
			fout.writeObject(textFile);
			fout.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
