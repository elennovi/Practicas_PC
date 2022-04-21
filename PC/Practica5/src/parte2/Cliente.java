package parte2;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Scanner;

public class Cliente {
	
	// Info static del cliente
	private static Scanner scanner = new Scanner(System.in);
	private static String name;

	public static void main(String[] args) {
		// Leer nombre del teclado
		System.out.print("Introduzca su nombre de usuario: ");
		name = scanner.nextLine();
		// Crear Sockets con el server
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		// Crear un thread OyenteServidor
		// Envía un msg de conexión
		// Menú user
	}

}
