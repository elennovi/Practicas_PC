package parte2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Cliente {
	
	// Info static del cliente
	private static Scanner scanner = new Scanner(System.in);
	private static String name; // El nombre del usuario
	private static String ipv4; // La ip del cliente actual

	public static void main(String[] args) {
		// Leer nombre del teclado
		System.out.print("Introduzca su nombre de usuario: ");
		name = scanner.nextLine();
		System.out.println();
		
		// Leer el nombre del servidor al que se quiere conectar
		System.out.print("Introduzca el nombre del servidor: ");
		String serverName = scanner.nextLine();
		System.out.println();
		
		// Leer los ficheros que aporta el cliente
		System.out.print("Introduzca el nombre de los ficheros (FIN para acabar): ");
		List<String> filenames = new ArrayList<String>();
		String filename = scanner.next();
		while (filename != "FIN") {
			filenames.add(filename); // Lo añadimos a la lista
			filename = scanner.next(); // Leemos el siguiente filename
		}
		System.out.println();
		
		// Funcion que dados unos nombres de ficheros genera un mapa: key = filename, value = text
		Map<String, String> ficheros = null;
		try {
			 ficheros = recogeInfoFicheros(filenames);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Obtenemos su ip para utilizarla más adelante en el intercambio de ficheros
		try {
			// Obtenemos las interfaces
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			// Le pedimos al usuario que introduzca la interfaz en la que está
			System.out.print("Interfaces asociadas: " + interfaces);
			String selectedI = scanner.nextLine();
			// Obtenemos esa interfaz y buscamos una dirección ip version 4
			NetworkInterface i = NetworkInterface.getByName(selectedI);
			Enumeration<InetAddress> addressesI = i.getInetAddresses();
	        InetAddress ip = addressesI.nextElement();
	        // Extraemos la primera direcccion del tipo ipv4
	        while (ip instanceof Inet4Address)
	            ip = addressesI.nextElement();
	        ipv4 = ip.toString().substring(1, ip.toString().length());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Crear Sockets con el server
		Socket s = null;
		try {
			s = new Socket(serverName, 999);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Crear un thread OyenteServidor
		(new OyenteServidor(s, new Usuario(name, ficheros, ipv4))).start();
		
		// Menú user
		
	}

	private static Map<String, String> recogeInfoFicheros(List<String> filenames) throws IOException {
		Map<String, String> ficheros = new HashMap<String, String>();
		for (String fn: filenames) {
			File file = new File(fn);
	        BufferedReader br = new BufferedReader(new FileReader(file));
	        String text = "";
	        String ln;
	        while ((ln = br.readLine()) != null)
	        	text += ln + "\n";
	        ficheros.put(fn, text); // Guardamos en el mapa el fichero con su contenido
		}
		return ficheros;
	}

}
