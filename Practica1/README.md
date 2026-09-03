# Practica 1: Introducción 

Para esta práctica se realizó una implementación de un semi CRUD, ya que aún no se pueden modificar los objetos creados durante el tiempo de ejecución y además no se guardan persistentemente.

Se trabajó con un modelo `Category` que nos permite hacer las siguientes acciones:
- Crear una nueva categoría
- Ver todas las categorías
- Filtrar por categorias hijas 
- Eliminar (cambiar de status) a una categoría

Se dividió en 3 clases la implementación:

**1. Category:** atributos, getters y setters.

**2. CategoryService:** lógica del negocio (acciones y restricciones).

**3. Main:** CLI para interactuar con las funcionalidades del CRUD implementadas en `CategoryService`.

## Ejecución.
Para compilar y ejecutar hay que estar dentro del directorio raíz de la entrega y ejecutar los siguientes comandos:
```shell
javac -d bin category/*.java
java -cp bin category.Main
```

Después de ejecutar, se pueden borrar los binarios con el siguiente comando:

```
rm -r bin
```

## Sobre el uso de IA
Para la realización de la práctica sólo se realizó un prompt para la elaboración del la clase `Main` con el fin de adaptar la interfaz de usuario a una Command Line Interface, las clases `Category` y `CategoryService` fueron realizadas íntegramente sin el uso de ninguna IA. El prompt utilizado se anexa en la entrega de la práctica.

Al final se adaptó manualmente por un menú de opciones, aunque se conservaron partes del código proporcionado.

