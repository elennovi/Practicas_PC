package parte2.mensajes;

import parte2.Constantes;

public class Msg_Cerrar_Conexion extends Mensaje{
	private static final long serialVersionUID = 1L;

	public Msg_Cerrar_Conexion(String origen, String destino) {
		super(Constantes.MSG_CERRAR_CONEXION, origen, destino);
	}

}
