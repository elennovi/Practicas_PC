package parte2.mensajes;

import java.util.List;
import java.util.Map;

import parte2.Constantes;

public class Msg_Conf_Lista_Users extends Mensaje{
	
	private Map<String, List<String>> mapa;

	public Msg_Conf_Lista_Users(String origen, String destino, Map<String, List<String>> mapa) {
		super(Constantes.MSG_CONF_LISTA_USERS, origen, destino);
		this.mapa = mapa;
	}
	
	public Map<String, List<String>> getListaUsers(){
		return mapa;
	}

}
