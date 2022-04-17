package parte2.mensajes;
import parte2.Constantes;

public class Msg_Lista_Users extends Mensaje{

	public Msg_Lista_Users(String origen, String destino) {
		super(Constantes.MSG_LISTA_USERS, origen, destino);
	}
	
}
