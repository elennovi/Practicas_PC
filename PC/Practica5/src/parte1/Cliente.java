package parte1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		try {
			Socket s = new Socket("localhost", 999);
			BufferedReader fin = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter fout = new PrintWriter(s.getOutputStream());
			// Input del usuario por consola
			String name = scanner.nextLine();
			fout.println(name);
			fout.flush();
			String contenido = fin.readLine();
			System.out.println("El contenido del fichero es:\n" + contenido + "\n");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
