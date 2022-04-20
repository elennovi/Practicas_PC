package parte2.mensajes;

import parte2.Constantes;

public class Msg_Emitir_Fichero extends Mensaje{
	private String filename; // El fichero que se tiene que emitir
	
	public Msg_Emitir_Fichero(String origen, String destino, String filename) {
		super(Constantes.MSG_EMITIR_FICHERO, origen, destino);
	}
	
	public String getFilename() {
		return filename;
	}

}
