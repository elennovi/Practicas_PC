package parte2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import parte2.mensajes.*;

public class OyenteServidor extends Thread{
	private Socket s;
	private Usuario user;
	private ObjectInputStream fin;
	private ObjectOutputStream fout;
	private Semaphore sem;
	
	public OyenteServidor(Socket s, ObjectInputStream fin, ObjectOutputStream fout, Usuario user, Semaphore sem) {
		this.s = s;
		this.user = user;
		this.fin = fin;
		this.fout = fout;
		this.sem = sem;
	}
	
	public void run() {
		while(true) {
			try {
				Mensaje m = (Mensaje)fin.readObject();
				switch(m.getTipo()) {
				case Constantes.MSG_CONF_CONEXION:
					// Mostramos un mensaje indicando que ya ha sido confirmada la conexion
					System.out.println("El usuario " + m.getDestino() + " se ha conectado a " + m.getOrigen());
					sem.release();
					break;
				case Constantes.MSG_CONF_LISTA_USERS:
					// Mostramos la lista de usuarios devuelta por el servidor
					Msg_Conf_Lista_Users mclu = (Msg_Conf_Lista_Users) m;
					System.out.println("La lista de usuarios que ha mandado el " + m.getOrigen() + ": \n" 
							+ mclu.getListaUsers().toString());
					sem.release();
					break;
				case Constantes.MSG_PREPARADO_SERVIDOR_CLIENTE:
					// Obtenemos el cliente, el puerto y la IP
					Msg_Preparado_Servidor_Cliente mpsc = (Msg_Preparado_Servidor_Cliente) m;
					String ip = mpsc.getIP();
					int puerto = mpsc.getPuerto();
					String cliente = mpsc.getOrigen();
					// Creamos el socket con esta información
					Socket s = new Socket(ip, puerto);
					// Creamos un nuevo thread receptor
					Receptor receptor = new Receptor(s, cliente);
					receptor.start();
					receptor.join(); // Esperamos a que el receptor haya acabado de recibir la informacion
					sem.release();
					s.close();
					break;
				case Constantes.MSG_CONF_CERRAR_CONEXION:
					// Cerrar el socket
					(this.s).close();
					break;
				case Constantes.MSG_EMITIR_FICHERO:
					Msg_Emitir_Fichero mef = (Msg_Emitir_Fichero) m;
					// Conseguimos el nombre del fichero que debemos emitir y el puerto
					String filename = mef.getFilename();
					int port = mef.getPuerto();
					// Crear emisor  (el server socket y el accept)
					ServerSocket ss = new ServerSocket(port);
					Socket s1 = ss.accept(); // Esperamos a que el receptor esté listo
					Emisor emisor = new Emisor(s1, user.getInfoFile(filename));
					emisor.start();
					emisor.join(); // Esperamos a que acabe de emitir el fichero
					ss.close(); // Cerramos el server socket
					// Envio mensaje indicando que se ha preparado cliente servidor
					fout.writeObject(new Msg_Preparado_Cliente_Servidor(m.getDestino(), m.getOrigen(), user.getIP(), port));
					fout.flush();
					break;
				case Constantes.MSG_ERROR:
					Msg_Error me = (Msg_Error) m;
					// Obtenemos el mensaje de error obtenido y lo mostramos
					System.out.println(me.getMensajeError());
					System.out.println("Pruebe a conectarse más tarde.");
					(this.s).close();
					sem.release();
					return;
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
