package parte1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) throws IOException {
		Socket s = null;
		try {
			s = new Socket("localhost", 5000);
			ObjectOutputStream fout = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream fin = new ObjectInputStream(s.getInputStream());
			// Input del usuario por consola
			String name = scanner.nextLine();
			fout.writeObject((Object)name);
			fout.flush();
			String contenido = (String) fin.readObject();
			System.out.println("El contenido del fichero es:\n" + contenido + "\n");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		s.close();
	}
}
