package parte2.mensajes;

import parte2.Constantes;

public class Msg_Emitir_Fichero extends Mensaje{
	private static final long serialVersionUID = 1L;
	private String filename; // El fichero que se tiene que emitir
	private int puerto; // El puerto en el que el cliente que emite el fichero estará esperando
	
	public Msg_Emitir_Fichero(String origen, String destino, String filename, int puerto) {
		super(Constantes.MSG_EMITIR_FICHERO, origen, destino);
		this.puerto = puerto;
	}
	
	public String getFilename() {
		return filename;
	}

	public int getPuerto() {
		return puerto;
	}
}
