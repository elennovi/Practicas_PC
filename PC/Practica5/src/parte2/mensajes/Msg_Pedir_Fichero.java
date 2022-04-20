package parte2.mensajes;

import parte2.Constantes;

public class Msg_Pedir_Fichero extends Mensaje {
	// El nombre del fichero que se tiene que emitir
	private String fichero;
	
	public Msg_Pedir_Fichero(String origen, String destino, String fichero) {
		super(Constantes.MSG_PEDIR_FICHERO, origen, destino);
		this.fichero = fichero;
	}
	
	public String getFileName() {
		return fichero;
	}
}
