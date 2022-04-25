package parte1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class Servidor {
	// Base de archivos
	static Map<String, String> archivos = new HashMap<String, String>();
	
	public static void main(String[] args) throws IOException {
		inserta_archivos();
		ServerSocket listen = null;
		Socket ss = null;
		try {
			listen = new ServerSocket(5000);
			ss = listen.accept();
			ObjectOutputStream fout = new ObjectOutputStream(ss.getOutputStream());
			ObjectInputStream fin = new ObjectInputStream(ss.getInputStream());
			// Recibe el nombre del fichero de texto
			String nombre =(String) fin.readObject();
			// Busca el archivo en la base
			String contenido = archivos.get(nombre);
			fout.writeObject((Object) contenido);
			fout.flush();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		listen.close();
		ss.close();
	}
	
	public static void inserta_archivos() {
		archivos.put("p1.txt", "primer archivo");
		archivos.put("p2.txt", "segundo archivo");
		archivos.put("p3.txt", "tercer archivo");
	}
}
