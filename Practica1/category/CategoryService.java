package category;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

public class CategoryService {

    private Integer currentId = 1;
    private HashSet<String> categories;
    private HashSet<String> tags;
    public HashMap<Integer, Category> catalogoCategorias;

    /**
     * Constructor vacío
     */
    public CategoryService() {
        this.categories = new HashSet<String>();
        this.tags = new HashSet<String>();
        this.catalogoCategorias = new HashMap<>();
    }

    /* ------- FUNCIONALIDADES PARA CATEGORY ------- */

    /**
     * Obtiene todas las categorías registradas
     * 
     * @return arreglo de las categorías
     */
    public ArrayList<Category> getCategories() {
        return new ArrayList<Category>(this.catalogoCategorias.values());
    }

    /**
     * Obtiene las categorías hijas de una categoría
     * 
     * @param category_id
     * @return lista de categorías hijas
     */
    public ArrayList<Category> getChildCategories(Integer category_id) {

        ArrayList<Category> childs = new ArrayList<Category>();

        for (Category category : this.catalogoCategorias.values())
            if (category_id.equals(category.getParentCategory_id()))
                childs.add(category);

        return childs;
    }

    /**
     * Agrega una categoría al catálogo
     * 
     * @param categoria
     */
    public void createCategory(Category categoria) {

        if (validateCategory(categoria)) {

            categoria.setCategory_id(currentId++);
            categoria.setStatus(1);

            this.catalogoCategorias.put(categoria.getCategory_id(), categoria);

            this.categories.add(categoria.getCategory());
            this.tags.add(categoria.getTag());

            return;
        }
        System.err.println("Los datos ingresados son inválidos, el Nombre o el Tag está repetido, o no existe la categoría padre");
    }

    /**
     * Cambia de status a la categoría por medio de un id
     * 
     * @param category_id
     */
    public void deleteCategory(Integer category_id) {

        Category categoria = this.catalogoCategorias.get(category_id);

        if (categoria == null) {
            System.err.println("No existe una categoría con el ID " + category_id);
            return;
        }

        if (categoria.getStatus() == 0) {
            System.err.println("La categoría ya se encuentra inactiva");
            return;
        }

        ArrayList<Category> childs = getChildCategories(category_id);

        if (childs.size() == 0 || hijosInactivos(childs)) {
            this.catalogoCategorias.get(category_id).setStatus(0);
        } else {
            System.err.println("No se pudo elminar categoría, tiene al menos un hijo activo");
        }
    }

    /* VALIDACIONES */

    private boolean validateCategory(Category c) {

        Integer parentCategoryId = c.getParentCategory_id();

        // Verifica categoría única
        if (this.categories.contains(c.getCategory()))
            return false;

        // Verifica tag único
        if (this.tags.contains(c.getTag()))
            return false;

        // Verifica que el id del padre sea un entero
        if (parentCategoryId != null && !(parentCategoryId instanceof Integer))
            return false;

        // Verifica que la categoria padre exista
        if (parentCategoryId != null && !existePadre(parentCategoryId))
            return false;

        // Verifica que no sea padre de si mismo
        if (parentCategoryId != null && parentCategoryId.equals(c.getCategory_id()))
            return false;

        // Verifica que su padre esté activo
        if (parentCategoryId != null && !statusActivoPadre(parentCategoryId))
            return false;

        return true;
    }

    private boolean statusActivoPadre(Integer parentCategoryId) {
        if (this.catalogoCategorias.get(parentCategoryId).getStatus() == 1)
            return true;
        return false;
    }

    private boolean existePadre(Integer parentCategory_id) {
        if (this.catalogoCategorias.get(parentCategory_id) instanceof Category)
            return true;
        return false;
    }

    private boolean hijosInactivos(ArrayList<Category> childs) {

        for (Category category : childs)
            if (category.getStatus() == 1)
                return false;

        return true;
    }

}