package category;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;


public class CategoryService {

    private Integer currentId = 0;
    private HashSet<String> categories;
    private HashSet<String> tags;
    private HashMap<Integer, Category> catalogoCategorias;

    public CategoryService() {
        this.tags = new HashSet<String>();
        this.catalogoCategorias = new HashMap<>();
    }

    public void getCategories() {
        System.out.println(catalogoCategorias);
    }

    public void getChildCategories(Integer category_id) {

        ArrayList<Category> childs = new ArrayList<Category>();

        // for (Category category : catalogoCategorias)
        //     if (category_id.equals(category.getParentCategory_id()))
        //         childs.add(category);

        System.out.println(childs);

    }

    public void createCategory(Category categoria) {

        if (validateCategory(categoria)) {
            this.catalogoCategorias.put(categoria.getCategory_id(), categoria);
            return;
        }
        System.err.println("Los datos ingresados no son válidos");
    }

    public void deletecategory(Integer category_id) {

    }


    public boolean validateCategory(Category c){

        
        // Verifica que exista category_id
        if (!(c.getCategory_id() instanceof Integer))
            return false;

        // Verifica categoría única
        if(categories.contains(c.getCategory()))
            return false;

        // Verifica tag único
        if(tags.contains(c.getTag()))
            return false;

        // Verifica que su padre esté activo        
        if(c.getParentCategory_id() != null && !statusActivoPadre(currentId)){
            return false;
        }

        // Verifica que no sea padre de si mismo
        if(c.getParentCategory_id() != null && c.getParentCategory_id().equals(c.getCategory_id()))
            return false;

        return true;
    }

    public boolean statusActivoPadre(Integer parentCategoryId){

        Category padre = this.catalogoCategorias.get(parentCategoryId);
        if(padre.getStatus().intValue() == 1)
            return true;
        return false;
    }
}