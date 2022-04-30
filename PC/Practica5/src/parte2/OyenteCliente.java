package parte2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import parte2.mensajes.*;

public class OyenteCliente extends Thread{	
	private Socket s;
	private Data datos; 
	private ObjectInputStream fin;
	private ObjectOutputStream fout;
	
	
	public OyenteCliente(Socket s, Data datos) {
		this.s = s;
		this.datos = datos;
		try {
			fout = new ObjectOutputStream((this.s).getOutputStream());
			fin = new ObjectInputStream((this.s).getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
			try {
				Mensaje m = (Mensaje)fin.readObject();
				switch(m.getTipo()) {
				case Constantes.MSG_CONEXION:
					System.out.println("Al servidor le llega un mensaje de conexion");
					Msg_Conexion mc = (Msg_Conexion)m;
					// Guardamos la información del usuario
					datos.addCliente(mc.getNameUser(), new InfoCliente(mc.getNameUser(), mc.getIP(), fin, fout), mc.getFilenames());
					// Enviamos por el fout el Msg_Conf_Conexion
					fout.reset();
					fout.writeObject(new Msg_Conf_Conexion(m.getDestino(), m.getOrigen()));
					fout.flush();
					System.out.println("El servidor ha mandado el mensaje de confirmacion de conexion");
					break;
				case Constantes.MSG_LISTA_USERS:
					// Recopilamos la información en un Map
					Map<String, List<String>> mapaActual =  datos.getListaUsers();
					fout.reset();
					// Enviamos un Msg_Conf_Lista_Users con la información pedida
					fout.writeObject(new Msg_Conf_Lista_Users(m.getDestino(), m.getOrigen(), mapaActual));
					fout.flush();
					break;
				case Constantes.MSG_PEDIR_FICHERO:
					Msg_Pedir_Fichero mpf = (Msg_Pedir_Fichero) m;
					// Buscamos al cliente que tiene ese fichero
					String cliente2 = datos.getFicheroCliente(mpf.getFileName());
					// Si no lo hemos encontrado mandamos un mensaje de eror
					if(cliente2 == null) {
						fout.reset();
						fout.writeObject(new Msg_Error(m.getDestino(), m.getOrigen(), Constantes.ERROR_PEDIR_FICHERO));
						fout.flush();
					}
					// Si lo hemos encontrado debemos pedirle al cliente encontrado que emita el fichero
					else {
						// Obtenemos el fout del cliente en cuestion
						ObjectOutputStream fout2 = datos.getFoutOf(cliente2);
						// Mandamos un Msg_Emitir_Fichero por ese fout
						fout2.reset();
						// Conseguimos el nuevo puerto para la comunicacion entre ambos clientes
						int nuevoPuerto = datos.nuevoPuerto();
						fout2.writeObject(new Msg_Emitir_Fichero(m.getOrigen(), cliente2, mpf.getFileName(), nuevoPuerto));
						fout2.flush();
					}
					break;
				case Constantes.MSG_PREPARADO_CLIENTE_SERVIDOR:
					Msg_Preparado_Cliente_Servidor mpcs = (Msg_Preparado_Cliente_Servidor) m;
					// Obtenemos el fout del cliente al que va dirigido el mensaje
					ObjectOutputStream fout1 = datos.getFoutOf(m.getDestino());
					// Enviamos un Msg_Preparado_Servidor_Cliente
					fout1.reset();
					fout1.writeObject(new Msg_Preparado_Servidor_Cliente(m.getOrigen(), m.getDestino(), mpcs.getIP(), mpcs.getPuerto()));
					fout1.flush();
					break;
				case Constantes.MSG_CERRAR_CONEXION:
					// Eliminamos al usuario de todas las tablas
					// Si no hemos podido eliminarlo
					if (!datos.eliminarCliente(m.getOrigen())) {
						fout.reset();
						fout.writeObject(new Msg_Error(m.getDestino(), m.getOrigen(), Constantes.ERROR_CERRAR_CONEXION));
						fout.flush();
						return;
					}
					// Mandamos un Msg_Conf_Cerrar_Conexion
					fout.reset();
					fout.writeObject(new Msg_Conf_Cerrar_Conexion(m.getDestino(), m.getOrigen()));
					fout.flush();
					return; 
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
