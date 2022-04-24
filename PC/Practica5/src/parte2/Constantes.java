package parte2;

public final class Constantes {
	public static final int MSG_CONEXION = 1;
	public static final int MSG_LISTA_USERS = 2;
	public static final int MSG_PEDIR_FICHERO = 3;
	public static final int MSG_PREPARADO_CLIENTE_SERVIDOR = 4;
	public static final int MSG_CERRAR_CONEXION = 5;
	public static final int MSG_CONF_CONEXION = 6;
	public static final int MSG_CONF_LISTA_USERS = 7;
	public static final int MSG_PREPARADO_SERVIDOR_CLIENTE = 8;
	public static final int MSG_CONF_CERRAR_CONEXION = 9;
	public static final int MSG_EMITIR_FICHERO = 10;
	public static final int MSG_ERROR = 11;
	public static final int NUM_MAX_PUERTOS = 100;
	public static final int INI_PUERTO = 5000;
	public static final String ERROR_CERRAR_CONEXION = "Primero debe establecer la conexión con el servidor";
	public static final String ERROR_PEDIR_FICHERO = "El fichero que ha pedido no existe";
	public static final String MENU = "__________      _________\n"
									+ "__________ MENU _________\n"
									+ "1. Consultar usuarios.\n"
									+ "2. Pedir fichero.\n"
									+ "3. Cerrar sesion.";
	
	public static final String OP_CONSULTAR_USUARIOS = "1";
	public static final String OP_PEDIR_FICHERO = "2";
	public static final String OP_CERRAR_SESION = "3";
	
}