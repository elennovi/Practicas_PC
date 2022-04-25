package parte2.mensajes;
import parte2.Constantes;

public class Msg_Conf_Conexion extends Mensaje{
	private static final long serialVersionUID = 1L;

	public Msg_Conf_Conexion(String origen, String destino) {
		super(Constantes.MSG_CONF_CONEXION, origen, destino);
	}

}
