package parte1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class Servidor {
	// Base de archivos
	static Map<String, String> archivos = new HashMap<String, String>();
	
	public static void main(String[] args) {
		inserta_archivos();
		try {
			ServerSocket listen = new ServerSocket(999);
			Socket ss = listen.accept();
			BufferedReader fin = new BufferedReader(new InputStreamReader(ss.getInputStream()));
			PrintWriter fout = new PrintWriter(ss.getOutputStream());
			// Recibe el nombre del fichero de texto
			String nombre = fin.readLine();
			// Busca el archivo en la base
			String contenido = archivos.get(nombre);
			fout.println(contenido);
			fout.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void inserta_archivos() {
		archivos.put("p1.txt", "primer archivo");
		archivos.put("p2.txt", "segundo archivo");
		archivos.put("p3.txt", "tercer archivo");
	}
}
