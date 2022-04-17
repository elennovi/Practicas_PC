package parte2.mensajes;
import parte2.Constantes;

public class Msg_Conf_Conexion extends Mensaje{

	public Msg_Conf_Conexion(String origen, String destino) {
		super(Constantes.MSG_CONF_CONEXION, origen, destino);
	}

}
