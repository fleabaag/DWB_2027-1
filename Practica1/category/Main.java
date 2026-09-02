package category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    private static final String PROMPT = "$> ";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        CategoryService categoryService = new CategoryService();

        mostrarBienvenida();

        boolean ejecutar = true;

        while (ejecutar) {

            System.out.print(PROMPT);

            String entrada = sc.nextLine().trim();

            if (entrada.isEmpty()) {
                continue;
            }

            String[] partes = entrada.split("\\s+");

            String comando = partes[0].toLowerCase();

            switch (comando) {

                case "mostrar_categorias":
                    if (partes.length != 1) {
                        errorUso("mostrar_categorias");
                    } else {
                        mostrarCategorias(categoryService.getCategories());
                    }
                    break;

                case "obtener_hijos":
                    if (partes.length != 2) {
                        errorUso("obtener_hijos [N]");
                    } else {
                        Integer id = convertirId(partes[1]);

                        if (id != null) {
                            ArrayList<Category> hijos = categoryService.getChildCategories(id);

                            if (hijos.isEmpty()) {
                                System.out.println(
                                        "La categoría no tiene hijos registrados.");
                            } else {
                                mostrarCategorias(hijos);
                            }
                        }
                    }
                    break;

                case "crear_categoria":
                    if (partes.length != 4) {
                        errorUso(
                                "crear_categoria [NombreCategoria] [Tag] [id_padre]");
                    } else {
                        crearCategoria(
                                categoryService,
                                partes[1],
                                partes[2],
                                partes[3]);
                    }
                    break;

                case "borrar_categoria":
                    if (partes.length != 2) {
                        errorUso("borrar_categoria [N]");
                    } else {
                        Integer id = convertirId(partes[1]);

                        if (id != null) {
                            categoryService.deleteCategory(id);
                        }
                    }
                    break;

                case "help":
                    if (partes.length != 1) {
                        errorUso("help");
                    } else {
                        mostrarHelp();
                    }
                    break;

                case "salir":
                    if (partes.length != 1) {
                        errorUso("salir");
                    } else {
                        ejecutar = false;
                        System.out.println("Hasta luego.");
                    }
                    break;

                default:
                    System.err.println(
                            "Error: comando no reconocido: " + partes[0]);
                    System.out.println(
                            "Escribe 'help' para consultar los comandos disponibles.");
                    break;
            }
        }

        sc.close();
    }

    /**
     * Muestra el mensaje inicial del programa.
     */
    private static void mostrarBienvenida() {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("       SISTEMA DE CATEGORÍAS");
        System.out.println("==========================================");
        System.out.println();
        System.out.println("Bienvenido al sistema de categorías.");
        System.out.println();
        System.out.println("Comandos disponibles:");
        System.out.println("  mostrar_categorias");
        System.out.println("  obtener_hijos");
        System.out.println("  crear_categoria");
        System.out.println("  borrar_categoria");
        System.out.println("  salir");
        System.out.println("  help");
        System.out.println();
        System.out.println(
                "Escribe 'help' para obtener información sobre el uso de los comandos.");
        System.out.println();
    }

    /**
     * Muestra la ayuda de todos los comandos.
     */
    private static void mostrarHelp() {

        System.out.println();
        System.out.println("COMANDOS DISPONIBLES");
        System.out.println();

        System.out.println(
                "mostrar_categorias");
        System.out.println(
                "    Muestra todas las categorías registradas durante la ejecución.");
        System.out.println();

        System.out.println(
                "obtener_hijos [N]");
        System.out.println(
                "    Muestra las categorías hijas de la categoría con ID N.");
        System.out.println();

        System.out.println(
                "crear_categoria [NombreCategoria] [Tag] [id_padre]");
        System.out.println(
                "    Crea una categoría con nombre, tag e ID de categoría padre.");
        System.out.println(
                "    Para crear una categoría raíz, utiliza 'null' como id_padre.");
        System.out.println();

        System.out.println(
                "borrar_categoria [N]");
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

        if (!idPadreTexto.equalsIgnoreCase("null")) {

            try {
                idPadre = Integer.parseInt(idPadreTexto);
            } catch (NumberFormatException e) {
                System.err.println(
                        "Error: id_padre debe ser un número entero o 'null'.");
                return;
            }
        }

        Category categoria = new Category(nombre, tag, idPadre);

        categoryService.createCategory(categoria);

        /**
         * CategoryService no devuelve un boolean indicando si la operación
         * fue exitosa. Por eso comprobamos si la categoría fue registrada.
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
                    "Error: el ID debe ser un número entero.");

            return null;
        }
    }

    /**
     * Imprime una lista de categorías en formato de tabla.
     */
    private static void mostrarCategorias(ArrayList<Category> categorias) {

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

    /**
     * Muestra un error de uso de un comando.
     */
    private static void errorUso(String uso) {

        System.err.println(
                "Error: uso incorrecto del comando.");

        System.err.println(
                "Uso: " + uso);
    }
}