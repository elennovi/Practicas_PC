package parte2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

import parte2.mensajes.Msg_Conexion;

public class OyenteServidor extends Thread{
	private Socket s;
	private Usuario user;
	private ObjectInputStream fin;
	private ObjectOutputStream fout;
	
	public OyenteServidor(Socket s, Usuario user) {
		this.s = s;
		this.user = user;
		try {
			fin = new ObjectInputStream(s.getInputStream());
			fout = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		// Lo primero que hay que hacer es mandar un mensaje de conexion con el servidor
		try {
			fout.writeObject(new Msg_Conexion(user.getName(), "servidor", user));
			fout.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true) {
			
		}
	}
}
