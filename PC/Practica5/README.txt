README.txt

- Elena Novillo Luceño
- Estibaliz Zubimendi Solaguren

Instrucciones de la práctica 5.

* Cliente: al acceder se deberá poner el nombre de usuario (NO debe utilizarse un mismo nombre de
usuario para dos clientes diferentes). Para escribir los ficheros que se desean aportar, se pondrá
uno en cada línea y se escribirá "FIN" para acabar. Ha de tenerse en cuenta que los ficheros deben
estar alojados en la carpeta del proyecto para que sean reconocidos, si no, deberá utilizarse como
nombre de fichero, la ruta de acceso al fichero. Para ejecutar las opciones se deberá poner el número
de opción solamente.

Como se pedía en la práctica, hemos hecho uso de las herramientas vistas a lo largo del curso.
En este fichero explicamos exactamente dónde y por qué usamos dichas herramientas.

* Lock:
Como necesitamos asignar puertos diferentes a todos los clientes que se quieran conectar
peer-to-peer, utilizamos una instrucción fetch-and-add. Para asegurar la sección crítica,
utilizamos el LockTicket desarrollado en la práctica 2.

* Semáforos:
Cuando los usuarios quieran hacer peticiones al servidor, deberán esperar a que su correspondiente
OyenteServidor muestre la respuesta del servidor a peticiones que se hayan podido hacer con anterioridad.

* Monitor:
Nuestra aplicación se basa en mandar ficheros de texto entre los clientes por lo que tenemos
que mantener un registro de los usuarios y de sus respectivos ficheros. Para asegurar que todos
los usuarios comparten estos datos, utilizamos un monitor que implementa las operaciones
del esquema escritores-lectores (requestWrite, releaseWrite, requestRead y releaseRead) visto en clase.