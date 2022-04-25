package parte2.mensajes;

import parte2.Constantes;

public class Msg_Preparado_Servidor_Cliente extends Mensaje {
	private static final long serialVersionUID = 1L;
	private String ip;
	private int puerto;

	public Msg_Preparado_Servidor_Cliente(String origen, String destino, String ip, int puerto) {
		super(Constantes.MSG_PREPARADO_SERVIDOR_CLIENTE, origen, destino);
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
