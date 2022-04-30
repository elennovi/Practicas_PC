package parte2;

import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
	// Utilizamos dos mapas para aumentar la concurrencia
	// Almacena para cada cliente su IP y sus flujos
	private volatile Map<String, InfoCliente> infoClientes;
	// Almacena para cada cliente los nombres de ficheros
	private volatile Map<String, List<String>> ficherosClientes;
	
	// Un monitor para controlar los accesos a cada uno de los mapas
	private MiMonitor moniInfoClientes;
	private MiMonitor moniFicherosClientes;
	
	// El numero de puerto que asignamos a cada uno de los clientes que se quieran comunicar p2p
	private volatile int numPuerto = Constantes.INI_PUERTO;
	// Un lock ticket para la seccion critica del nuevoPuerto()
	private LockTicket lock;
	
	public Data() {
		infoClientes = new HashMap<String, InfoCliente>();
		ficherosClientes = new HashMap<String, List<String>>();
		
		moniInfoClientes = new MiMonitor();
		moniFicherosClientes = new MiMonitor();
		// Como mucho habrá NUM_MAX_PUERTOS queriendo comunicarse entre ellos
		lock = new LockTicket();
	}
	
	// Msg_Conexion
	public void addCliente(String name, InfoCliente ic, List<String> ficheros) {
		moniInfoClientes.requestWrite();
		infoClientes.put(name, ic);
		infoClientes = infoClientes;
		moniInfoClientes.releaseWrite();
		moniFicherosClientes.requestWrite();
		ficherosClientes.put(name, ficheros);
		ficherosClientes = ficherosClientes;
		moniFicherosClientes.releaseWrite();
	}
	
	// Msg_Lista_Users
	public Map<String, List<String>> getListaUsers() {
		moniFicherosClientes.requestRead();
		Map<String, List<String>> mapaFicheros = ficherosClientes;
		moniFicherosClientes.releaseRead();
		return mapaFicheros;
	}
	
	// Msg_Pedir_Fechiro
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
	
	// Es un fetch-and-add que devuelve un puerto nuevo
	public int nuevoPuerto() {
		// Cogemos el lock para que nadie utilice el mismo puerto que nosotros
		lock.takeLock();
		int port = numPuerto;
		numPuerto++; 
		// Ya pueden entrar otros a coger el puerto
		lock.releaseLock();
		return port;
	}
}