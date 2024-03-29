package parte2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Receptor extends Thread {
	private Socket s;
	private ObjectInputStream fin;
	
	// El receptor recibe el socket para generar los flujos y el nombre del usuario con el que se va a comunicar
	public Receptor(Socket s, String emisor) {
		try {
			this.s = s;
			new ObjectOutputStream((this.s).getOutputStream());
			fin = new ObjectInputStream((this.s).getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			// Esperamos a que el emisor nos mande el contenido del fichero
			String contenido = (String) fin.readObject(); 
			// Lo mostramos
			System.out.println(contenido);
			// Cerramos la conexion
			s.close(); 
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
}
