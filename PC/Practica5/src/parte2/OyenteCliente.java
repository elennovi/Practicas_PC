package parte2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import parte2.mensajes.Mensaje;
import parte2.mensajes.Msg_Conexion;
import parte2.mensajes.Msg_Conf_Conexion;
import parte2.mensajes.Msg_Conf_Lista_Users;
import parte2.mensajes.Msg_Lista_Users;

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
					datos.addCliente(mc.getNameUser(), new InfoCliente(mc.getNameUser(), fin, fout), mc.getInfoUser());
					// Envío por el fout de msg_conf_conexion
					fout.writeObject(new Msg_Conf_Conexion("servidor", "cliente"));
					fout.flush();
				case Constantes.MSG_LISTA_USERS:
					// Recopilar info, meter en una ED para mandarla
					// Envío msg_conf_lista_usr con info
					fout.writeObject(new Msg_Conf_Lista_Users(m.getOrigen(), m.getDestino(), datos.getListaUsers()));
					fout.flush();
				case Constantes.MSG_PEDIR_FICHERO:
				case Constantes.MSG_PREPARADO_CLIENTE_SERVIDOR:
				case Constantes.MSG_CERRAR_CONEXION:
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
