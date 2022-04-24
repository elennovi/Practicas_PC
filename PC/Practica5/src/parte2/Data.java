package parte2;

import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
	// Utilizamos dos mapas para añadir concurrencia
	// Mapa = {key : cliente, values : InfoCliente}
	private volatile Map<String, InfoCliente> infoClientes;
	// Mapa = {key : cliente, values : lista de ficheros}
	private volatile Map<String, List<String>> ficherosClientes;
	// Un monitor para controlar los accesos a cada uno de los mapas
	private MiMonitor moniInfoClientes;
	private MiMonitor moniFicherosClientes;
	
	
	public Data() {
		infoClientes = new HashMap<String, InfoCliente>();
		ficherosClientes = new HashMap<String, List<String>>();
		moniInfoClientes = new MiMonitor();
		moniFicherosClientes = new MiMonitor();
	}
	
	// msg_conexión
	public void addCliente(String name, InfoCliente ic, List<String> ficheros) {
		moniInfoClientes.requestWrite();
		infoClientes.put(name, ic);
		infoClientes = infoClientes;
		moniInfoClientes.releaseWrite();
		moniFicherosClientes.releaseWrite();
		ficherosClientes.put(name, ficheros);
		ficherosClientes = ficherosClientes;
		moniFicherosClientes.releaseWrite();
	}
	
	// msg_lista_users
	public Map<String, List<String>> getListaUsers() {
		moniFicherosClientes.requestRead();
		Map<String, List<String>> mapaFicheros = ficherosClientes;
		moniFicherosClientes.releaseRead();
		return mapaFicheros;
	}
	
	// msg_pedir_fichero
	public String getFicheroCliente(String fichero){
		String cl = null; // El nombre del cliente
		moniFicherosClientes.requestRead();
		for(String s : ficherosClientes.keySet())
			if(ficherosClientes.get(s).contains(fichero)) {
				cl = s;
				break;
			}
		moniFicherosClientes.releaseRead();
		return cl;
	}
	
	// devuelve el fout del cliente indicado por parametros
	public ObjectOutputStream getFoutOf(String idClient) {
		moniInfoClientes.requestRead();
		ObjectOutputStream fout = infoClientes.get(idClient).getFout();
		moniInfoClientes.releaseRead();
		return fout;
	}

	public boolean eliminarCliente(String idClient) {
		boolean value = true;
		moniInfoClientes.requestRead();
		if (!infoClientes.containsKey(idClient))
			value = false; // Si no existe el cliente
		moniInfoClientes.releaseRead();
		// Eliminamos al cliente del mapa que contiene los flujos de entrada y de salida
		moniInfoClientes.requestWrite();
		infoClientes.remove(idClient);
		infoClientes = infoClientes;
		moniInfoClientes.releaseWrite();
		// Eliminamos al cliente del mapa que contiene los archivos que esta ofreciendo
		moniFicherosClientes.requestWrite();
		ficherosClientes.remove(idClient);
		ficherosClientes = ficherosClientes;
		moniFicherosClientes.releaseWrite();
		return value; // Hemos podido borrar el cliente porque ya existía
	}
	
}