package parte2.mensajes;

import parte2.Constantes;

public class Msg_Preparado_Cliente_Servidor extends Mensaje {
	private String ip;
	private int puerto;
	
	public Msg_Preparado_Cliente_Servidor(String origen, String destino, String ip, int puerto) {
		super(Constantes.MSG_PREPARADO_CLIENTE_SERVIDOR, origen, destino);
		this.ip = ip;
		this.puerto = puerto;
	}

	public String getIP() {
		return ip;
	}

	public int getPuerto() {
		return puerto;
	}
}
