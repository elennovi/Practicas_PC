package parte2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import parte2.mensajes.*;

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
		System.out.println("Introduzca el nombre de los ficheros (FIN para acabar): ");
		List<String> filenames = new ArrayList<String>();
		String filename = scanner.nextLine();
		while (filename != "FIN") {
			filenames.add(filename); // Lo añadimos a la lista
			filename = scanner.nextLine(); // Leemos el siguiente filename
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
		Semaphore sem = new Semaphore(0); // Un semáforo para generar oden a la hora de pedir al usuario una opción
		// y mostrar la solución
		OyenteServidor oyenteServer = null;
		ObjectInputStream fin = null;
		ObjectOutputStream fout = null;
		Usuario user = null;
		try {
			fin = new ObjectInputStream(s.getInputStream());
			fout = new ObjectOutputStream(s.getOutputStream());
			user = new Usuario(name, ficheros, ipv4);
			oyenteServer = new OyenteServidor(s, fin, fout, user, sem);
		} catch (IOException e) {
			e.printStackTrace();
		}
		oyenteServer.start();
		
		// Lo primero que hay que hacer es mandar un mensaje de conexion con el servidor
		try {
			fout.writeObject(new Msg_Conexion(name, "servidor", user));
			fout.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Menú user
		try {
			sem.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(Constantes.MENU);
		// Pedimos al usuario la opcion que quiere ejecutar
		System.out.print("Introduzca el numero de la opcion: ");
		String op = scanner.nextLine();
		try {
			switch(op) {
			case Constantes.OP_CONSULTAR_USUARIOS:
				fout.writeObject(new Msg_Lista_Users(name, "servidor"));
				break;
			case Constantes.OP_PEDIR_FICHERO:
				// Le pide al usuario el nombre del fichero que quiere conseguir
				System.out.print("Nombre del fichero deseado: ");
				String fichero = scanner.nextLine();
				fout.writeObject(new Msg_Pedir_Fichero(name, "servidor", fichero));
				break;
			case Constantes.OP_CERRAR_SESION:
				fout.writeObject(new Msg_Cerrar_Conexion(name, "servidor"));
				break;
			default: // Ha seleccionado una opcion no valida
				System.out.println("Esa opción no existe.");
				sem.release();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			scanner.close();
			return;
		}
		
	}

	private static Map<String, String> recogeInfoFicheros(List<String> filenames) throws IOException {
		Map<String, String> ficheros = new HashMap<String, String>();
		for (String fn: filenames) {
			File file = new File(fn);
	        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String text = "";
				String ln;
				while ((ln = br.readLine()) != null)
					text += ln + "\n";
				ficheros.put(fn, text); // Guardamos en el mapa el fichero con su contenido
			}
		}
		return ficheros;
	}
}
