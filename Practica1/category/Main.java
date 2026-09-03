package category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        CategoryService categoryService = new CategoryService();

        boolean ejecutar = true;

        while (ejecutar) {
            mostrarMenu();
            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1":
                    mostrarCategorias(categoryService.getCategories());
                    break;
                case "2":
                    obtenerHijos(sc, categoryService);
                    break;
                case "3":
                    crearCategoria(sc, categoryService);
                    break;
                case "4":
                    borrarCategoria(sc, categoryService);
                    break;
                case "5":
                    ejecutar = false;
                    System.out.println("Hasta luego.");
                    break;
                case "help":
                    mostrarHelp();
                    break;
                default:
                    System.err.println("Error: selecciona una opción disponible");
            }
        }

        sc.close();
    }

    /**
     * Muestra el menu del sistema
     */
    private static void mostrarMenu() {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("       SISTEMA DE CATEGORÍAS");
        System.out.println("==========================================");
        System.out.println();
        System.out.println("Selecciona una opción:");
        System.out.println("  1. Mostrar categorías");
        System.out.println("  2. Obtener categorías hijas");
        System.out.println("  3. Crear categoría");
        System.out.println("  4. Borrar categoría");
        System.out.println("  5. Salir");
        System.out.println("Escribe 'help' si necesitas información sobre los comandos");
        System.out.print("Ingresa una opción: ");
    }

    private static void obtenerHijos(Scanner sc, CategoryService categoryService) {
        System.out.println();
        System.out.println("OBTENER CATEGORIAS HIJAS");
        System.out.println();
        System.out.println("Indica el ID de la categoría padre.");
        System.out.print("ID de la categoría: ");
        Integer id = convertirId(sc.nextLine().trim());
        System.out.println();

        if (id != null) {

            if (categoryService.catalogoCategorias.get(id) != null) {

                ArrayList<Category> hijos = categoryService.getChildCategories(id);

                if (hijos.isEmpty())
                    System.err.println("Error: La categoría no tiene hijos registrados.");
                else
                    mostrarCategorias(hijos);

            } else {
                System.err.println("Error: No existe categoría alguna con el Id proporcionado.");
            }

        }
    }

    private static void crearCategoria(Scanner sc, CategoryService categoryService) {
        System.out.println();
        System.out.println("CREAR CATEGORIA NUEVA");
        System.out.println();
        System.out.println("Ingresa los datos de la nueva categoría.");
        System.out.print("Nombre de la categoría: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Tag de la categoría: ");
        String tag = sc.nextLine().trim();
        System.out.print("ID de la categoría padre [SIN CATEGORÍA PADRE PRESIONA ENTER]: ");
        String idPadreTexto = sc.nextLine().trim();
        System.out.println();

        if (nombre.isEmpty() || tag.isEmpty()) {
            System.err.println("Error: el nombre y el tag no pueden estar vacíos.");
            return;
        }
        crearCategoria(categoryService, nombre, tag, idPadreTexto);
    }

    private static void borrarCategoria(Scanner sc, CategoryService categoryService) {
        System.out.println();
        System.out.println("BORRAR CATEGORIA");
        System.out.println();
        System.out.println("Ingresa el ID de la categoría que deseas borrar.");
        System.out.print("ID de la categoría: ");
        Integer id = convertirId(sc.nextLine().trim());
        System.out.println();

        if (id != null && categoryService.deleteCategory(id))
            System.out.println("Categoría con id: " + id + " borrada con éxito.");

    }

    /**
     * Muestra la ayuda de todos los comandos.
     */
    private static void mostrarHelp() {

        System.out.println();
        System.out.println("FUNCIONES DISPONIBLES");
        System.out.println();

        System.out.println(
                "Mostrar categorias");
        System.out.println(
                "    Muestra todas las categorías registradas durante la ejecución.");
        System.out.println();

        System.out.println(
                "Obtener hijos [N]");
        System.out.println(
                "    Muestra las categorías hijas de la categoría con ID N.");
        System.out.println();

        System.out.println(
                "Crear categoria ([NombreCategoria] [Tag] [id_padre])");
        System.out.println(
                "    Crea una categoría con nombre, tag e ID de categoría padre.");
        System.out.println(
                "    Para crear una categoría raíz, utiliza 'null' como id_padre.");
        System.out.println();

        System.out.println(
                "Borrar categoria [N]");
        System.out.println(
                "    Desactiva la categoría con ID N.");
        System.out.println(
                "    No puede desactivarse si tiene hijos activos.");
        System.out.println();

        System.out.println(
                "salir");
        System.out.println(
                "    Termina la ejecución del programa.");
        System.out.println();

        System.out.println(
                "help");
        System.out.println(
                "    Muestra esta ayuda.");
        System.out.println();
    }

    /**
     * Crea una categoría a partir de los argumentos proporcionados.
     */
    private static void crearCategoria(CategoryService categoryService, String nombre, String tag,
            String idPadreTexto) {

        Integer idPadre = null;

        if (!idPadreTexto.equalsIgnoreCase("")) {

            try {
                idPadre = Integer.parseInt(idPadreTexto);
            } catch (NumberFormatException e) {
                System.err.println(
                        "Error: id_padre debe ser un número entero");
                return;
            }
        }

        Category categoria = new Category(nombre, tag, idPadre);

        categoryService.createCategory(categoria);

        /**
         * Comprobamos si la categoría fue registrada.
         */
        if (categoria.getCategory_id() != null) {
            System.out.println(
                    "Categoría creada correctamente con ID "
                            + categoria.getCategory_id());
        }
    }

    /**
     * Convierte un texto a Integer.
     *
     * @return el ID convertido o null si el valor no es válido.
     */
    private static Integer convertirId(String texto) {

        try {
            return Integer.parseInt(texto);

        } catch (NumberFormatException e) {

            System.err.println(
                    "\nError: el ID debe ser un número entero.");

            return null;
        }
    }

    /**
     * Imprime una lista de categorías en formato de tabla.
     */
    private static void mostrarCategorias(ArrayList<Category> categorias) {
        System.out.println();
        System.out.println("CATEGORIAS DISPONIBLES");
        System.out.println();

        if (categorias.isEmpty()) {
            System.out.println("No existen categorías registradas.");
            return;
        }

        /**
         * Ordenamos por ID para que la salida sea determinista.
         */
        categorias.sort(Comparator.comparing(Category::getCategory_id));

        String separador = "+----+----------------------+------------+----------+-----------+";

        System.out.println();
        System.out.println(separador);

        System.out.printf(
                "| %-2s | %-20s | %-10s | %-8s | %-9s |%n",
                "ID",
                "Nombre",
                "Tag",
                "ID_Padre",
                "Status");

        System.out.println(separador);

        for (Category categoria : categorias) {

            String idPadre = categoria.getParentCategory_id() == null
                    ? "-"
                    : categoria.getParentCategory_id().toString();

            String status = categoria.getStatus() == 1
                    ? "Activo"
                    : "Inactivo";

            System.out.printf(
                    "| %-2d | %-20s | %-10s | %-8s | %-9s |%n",
                    categoria.getCategory_id(),
                    categoria.getCategory(),
                    categoria.getTag(),
                    idPadre,
                    status);
        }

        System.out.println(separador);
        System.out.println();
    }

}