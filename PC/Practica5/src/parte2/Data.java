package parte2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
	// Mapa = {key : cliente, values : InfoCliente}
	private volatile Map<String, InfoCliente> infoClientes;
	// Mapa = {key : cliente, values : lista de ficheros}
	private volatile Map<String, List<String>> ficherosClientes;
	
	public Data() {
		infoClientes = new HashMap<String, InfoCliente>();
		ficherosClientes = new HashMap<String, List<String>>();
	}
	
	// msg_conexi�n
	public void addCliente(String name, InfoCliente ic, List<String> ficheros) {
		infoClientes.put(name, ic);
		ficherosClientes.put(name, ficheros);
	}
	
	// msg_lista_users
	public Map<String, List<String>> getListaUsers() {
		return ficherosClientes;
	}
	
	// msg_pedir_fichero
	public String getFicheroCliente(String fichero){
		for(String s : ficherosClientes.keySet())
			if(ficherosClientes.get(s).contains(fichero))
				return s;
		// no existe el fichero en la base de datos
		return null;
	}
	
}