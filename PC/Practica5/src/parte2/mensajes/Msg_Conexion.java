package parte2.mensajes;
import java.util.List;

import parte2.Constantes;
import parte2.Usuario;

public class Msg_Conexion extends Mensaje{
	
	private Usuario user;

	public Msg_Conexion(String origen, String destino, Usuario user) {
		super(Constantes.MSG_CONEXION, origen, destino);
		this.user = user;
	}
	
	public List<String> getFilenames(){
		return user.getFilenames();
	}
	
	public String getNameUser() {
		return user.getName();
	}
	
	public String getIP() {
		return user.getIP();
	}
	
}