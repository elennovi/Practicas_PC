package parte2.mensajes;

import parte2.Constantes;

public class Msg_Error extends Mensaje {
	private String mensajeError;
	
	public Msg_Error(String origen, String destino, String error) {
		super(Constantes.MSG_ERROR, origen, destino);
		mensajeError = error;
	}

	public String getMensajeError() {
		return mensajeError;
	}
	
}
