// ELENA NOVILLO LUCEÑO
// ESTIBALIZ ZUBIMENDI SOLAGUREN
package parte2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import parte2.mensajes.*;

public class Cliente {
	private static Scanner scanner = new Scanner(System.in);
	private static String name; // El nombre del usuario

	public static void main(String[] args) {
		// Leemos el nombre del usuario por teclado
		System.out.print("Introduzca su nombre de usuario: ");
		name = scanner.nextLine();
		System.out.println();
		
		// Leemos los ficheros que aporta el cliente por teclado
		System.out.println("Introduzca el nombre de los ficheros (FIN para acabar): ");
		List<String> filenames = new ArrayList<String>();
		String filename = scanner.nextLine();
		while (!filename.equals("FIN")) {
			filenames.add(filename); // Lo añadimos a la lista
			filename = scanner.nextLine(); // Leemos el siguiente filename
		}
		System.out.println();
		
		// Funcion que dados unos nombres de ficheros genera un mapa: key = filename, value = text
		Map<String, String> ficheros = null;
		try {
			 ficheros = recogeInfoFicheros(filenames);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// Creamos los sockets con el servidor
		Socket s = null;
		try {
			// Suponemos que tanto servidor como cliente están ejecutandose desde la misma máquina
			s = new Socket("localhost", 999); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Un semáforo para generar orden a la hora de pedir al usuario una opción y mostrar la respuesta del servidor
		Semaphore sem = new Semaphore(0); 
		// Creamos un OyenteServidor y los flujos para la comunicación
		OyenteServidor oyenteServer = null;
		ObjectInputStream fin = null;
		ObjectOutputStream fout = null;
		Usuario user = null;
		try {
			fout = new ObjectOutputStream(s.getOutputStream());
			fin = new ObjectInputStream(s.getInputStream());
			user = new Usuario(name, ficheros, "localhost");
			oyenteServer = new OyenteServidor(s, fin, fout, user, sem);
		} catch (IOException e) {
			e.printStackTrace();
		}
		oyenteServer.start();
		
		// Lo primero que hay que hacer es mandar un mensaje de conexion con el servidor
		try {
			// Esperaremos a que el OyenteServidor nos indique que ya está escuchando
			sem.acquire(); 
			fout.writeObject((Object) new Msg_Conexion(name, "servidor", user));
			fout.flush();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		// Mostramos el menú de usuario hasta que se cierre la conexion
		boolean acaba = false;
		while(!acaba) {
			try {
				// Esperamos a que el OyenteServidor responda a las peticiones correspondientes (si las hubiera)
				sem.acquire();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			System.out.println(Constantes.MENU);
			// Pedimos al usuario la opcion que quiere ejecutar
			System.out.print("Introduzca el numero de la opcion: ");
			String op = scanner.nextLine();
			try {
				switch(op) {
				// Le pide al servidor una lista con los usuarios y sus correspondientes ficheros aportados
				case Constantes.OP_CONSULTAR_USUARIOS:
					fout.reset();
					fout.writeObject(new Msg_Lista_Users(name, "servidor"));
					fout.flush();
					break;
				// Le pide al servidor el contenido de un fichero
				case Constantes.OP_PEDIR_FICHERO:
					// Pedimos al usuario el nombre del fichero en cuestión
					System.out.print("Nombre del fichero deseado: ");
					String fichero = scanner.nextLine();
					fout.reset();
					fout.writeObject(new Msg_Pedir_Fichero(name, "servidor", fichero));
					fout.flush();
					break;
				// Le pide al servidor confirmación para cerrar conexión
				case Constantes.OP_CERRAR_SESION:
					fout.reset();
					fout.writeObject(new Msg_Cerrar_Conexion(name, "servidor"));
					fout.flush();
					acaba = true;
					break;
				// En cualquier otro caso, no existe esa opción
				default: 
					System.out.println("Esa opción no existe.");
					sem.release();
				}
			} catch (IOException e) {
				e.printStackTrace();
				scanner.close();
				return;
			}
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
				// Guardamos en el mapa el fichero con su contenido
				ficheros.put(fn, text); 
			}
		}
		return ficheros;
	}
}
