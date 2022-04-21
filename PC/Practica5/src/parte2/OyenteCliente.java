package parte2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
			fin = new ObjectInputStream(s.getInputStream());
			fout = new ObjectOutputStream(s.getOutputStream());
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
					Msg_Conexion mc = (Msg_Conexion)m;
					// Guardar info user
					// Envío por el fout de msg_conf_conexion
					datos.addCliente(mc.getNameUser(), new InfoCliente(mc.getNameUser(), fin, fout), mc.getFilenames());
					fout.writeObject(new Msg_Conf_Conexion(m.getDestino(), m.getOrigen()));
					fout.flush();
					
				case Constantes.MSG_LISTA_USERS:
					// Recopilar info, meter en una ED para mandarla
					// Envío msg_conf_lista_usr con info
					fout.writeObject(new Msg_Conf_Lista_Users(m.getDestino(), m.getOrigen(), datos.getListaUsers()));
					fout.flush();
					
				case Constantes.MSG_PEDIR_FICHERO:
					Msg_Pedir_Fichero mpf = (Msg_Pedir_Fichero) m;
					// Buscamos al cliente con esa info
					String cliente2 = datos.getFicheroCliente(mpf.getFileName());
					if(cliente2 == null) {
						fout.writeObject(new Msg_Error(m.getDestino(), m.getOrigen(), Constantes.ERROR_PEDIR_FICHERO));
						fout.flush();
					}
					else {
						// Obtenemos el fout del cliente en cuestion
						ObjectOutputStream fout2 = datos.getFoutOf(cliente2);
						// Mandamos un mensaje para emitir fichero por ese fout
						fout2.writeObject(new Msg_Emitir_Fichero(m.getOrigen(), cliente2, mpf.getFileName()));
						fout2.flush();
					}
				
				case Constantes.MSG_PREPARADO_CLIENTE_SERVIDOR:
					// Obtener el fout del cliente al que va dirigido el mensaje
					ObjectOutputStream fout1 = datos.getFoutOf(m.getOrigen());
					// Enviamos mensaje preparado servidor cliente
					fout1.writeObject(new Msg_Preparado_Cliente_Servidor(m.getOrigen(), m.getDestino()));
					fout1.flush();
					
				case Constantes.MSG_CERRAR_CONEXION:
					// Eliminamos al usuario de todas las tablas
					if (!datos.eliminarCliente(m.getOrigen())) {
						// Como no está el cliente le indicamos que ha habido un erorr
						fout.writeObject(new Msg_Error(m.getDestino(), m.getOrigen(), Constantes.ERROR_CERRAR_CONEXION));
						fout.flush();
						return; // No está el cliente por lo que ese oyente cliente acaba
					}
					// Mandamos un mensaje de confirmacion del cierre de la conexion
					fout.writeObject(new Msg_Conf_Cerrar_Conexion("servidor", m.getOrigen()));
					fout.flush();
					break;
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
