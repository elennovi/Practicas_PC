package parte2;

import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Data {
	// Utilizamos dos mapas para añadir concurrencia
	// Mapa = {key : cliente, values : InfoCliente}
	private Map<String, InfoCliente> infoClientes;
	// Mapa = {key : cliente, values : lista de ficheros}
	private Map<String, List<String>> ficherosClientes;
	// Un monitor para controlar los accesos a cada uno de los mapas
	private MiMonitor moniInfoClientes;
	private MiMonitor moniFicherosClientes;
	// El numero de puerto que asignamos a cada uno de los clientes que se quieran comunicar peet-to-peer
	private volatile int numPuerto = Constantes.INI_PUERTO;
	// Un lock para la seccion critica
	private Lock lock;
	
	
	public Data() {
		infoClientes = new ConcurrentHashMap<String, InfoCliente>();
		ficherosClientes = new ConcurrentHashMap<String, List<String>>();
		moniInfoClientes = new MiMonitor();
		moniFicherosClientes = new MiMonitor();
		lock = new LockTicket(Constantes.NUM_MAX_PUERTOS);
		// lock = new ReentrantLock();
	}
	
	// msg_conexión
	public void addCliente(String name, InfoCliente ic, List<String> ficheros) {
		moniInfoClientes.requestWrite();
		infoClientes.put(name, ic);
		moniInfoClientes.releaseWrite();
		moniFicherosClientes.requestWrite();
		ficherosClientes.put(name, ficheros);
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
		moniInfoClientes.releaseWrite();
		// Eliminamos al cliente del mapa que contiene los archivos que esta ofreciendo
		moniFicherosClientes.requestWrite();
		ficherosClientes.remove(idClient);
		moniFicherosClientes.releaseWrite();
		return value; // Hemos podido borrar el cliente porque ya existía
	}
	
	// Es un fetch-and-add que devuelve un puerto nuevo
	public int nuevoPuerto() {
		// Cogemos el lock para que nadie utilice el mismo puerto que nosotros
		lock.takeLock(numPuerto - Constantes.INI_PUERTO + 1);
		int port = numPuerto;
		numPuerto++; // Fetch and add del puerto actual
		// Ya pueden entrar otros a coger el puerto
		lock.releaseLock(numPuerto - Constantes.INI_PUERTO + 1);
		return port;
	}
}