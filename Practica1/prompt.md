Quiero hacer un programa interactivo simulando el comportamiento de una terminal que tenga los siguientes comandos:

$> mostrar_categorias --> Muestra en pantalla todas las categorías registradas durante tiempo de ejecución.

$> obtener_hijos [N] --> Muestra en pantalla todos los hijos asociados a la categoría con id N (integer)

$> crear_categoria [NombreCategoria] [Tag] [id_padre] --> Crea una categoria que recibe como parámetros el nombre (string), un tag (string) y un id de su categoria padre (integer), en CategoryService realiza varias validaciones pero no especifica la razón del fallo

$> borrar_categoria [N] --> Cambia de status a la categoría cumpliendo con la condición de que ningún hijo debe estar activo con [N] (integer) el id de la categoria.

$> salir --> Termina la ejecución del programa

$> help --> muestra los comandos disponibles y su uso

Debe mostrar un saludo inicial y mostrar los comandos disponibles sin descripción, sugerir usar help para más información, así como mensajes de error y correcto uso. 

Para comandos que regresan listas de categorias formatear en una tabla con las columnas: ID, Nombre, Tag, ID_Padre, Status. Si ID_Padre es null, entonces la tabla imprime '-', si el Status es 0 entonces imprime 'Inactivo', si Status es 1, imprime 'Activo'

Adjunto las clases Category y CategoryService: 
