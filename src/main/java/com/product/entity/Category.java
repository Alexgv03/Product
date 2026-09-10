package com.product.entity;

/*
/  Equipo:
/         Gallardo Valdez Brayan Alexis
/         Torres Miguel Emiliano        
*/

public class Category {
    private Integer category_id;
    private String category;
    private String tag;
    private Integer parentCategoryId;
    private Integer status;


    // el constructor
    public Category(String category, String tag, Integer parentCategoryId) {
        this.category = category;
        this.tag = tag;
        this.parentCategoryId = parentCategoryId;
        this.status = 1; 
    }

    // getters y setters
    public Integer getCategory_id() { return category_id; }
    public void setCategory_id(Integer category_id) { this.category_id = category_id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public Integer getParentCategoryId() { return parentCategoryId; }
    public void setParentCategoryId(Integer parentCategoryId) { this.parentCategoryId = parentCategoryId; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    // Para que se sobreescriba el método toString y que al momento de imprimirse se vea idéntico al de los ejemplos

    @Override
    public String toString() {
        return "{" + category_id + ", \"" + category + "\", \"" + tag + "\", " + 
               (parentCategoryId == null ? "null" : parentCategoryId) + ", " + status + "}";
    }
}
