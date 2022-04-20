package parte2.mensajes;

import parte2.Constantes;

public class Msg_Preparado_Cliente_Servidor extends Mensaje {

	public Msg_Preparado_Cliente_Servidor(String origen, String destino) {
		super(Constantes.MSG_PREPARADO_CLIENTE_SERVIDOR, origen, destino);
	}
}
