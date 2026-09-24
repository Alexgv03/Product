/*
 Equipo: 
        Gallardo Valdez Brayan Alexis
        Torres Miguel Emiliano
*/

package com.product.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // entidad bd
@Table(name = "category") // nombre de la tabla en la bd
public class Category {

    // id de la categoria
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("category_id")
    @Column(name = "category_id")
    private Integer categoryId;

    // nombre de la categoria
    @JsonProperty("category")
    @Column(name = "category")
    private String category;

    // etiqueta asociada con la categoria
    @JsonProperty("tag")
    @Column(name = "tag")
    private String tag;

    // id de subcategoria
    @JsonProperty("parentCategoryId")
    @Column(name = "parent_category_id")
    private Integer parentCategoryId;

    // estado de la categoria 
    @JsonProperty("status")
    @Column(name = "status")
    private Integer status;

    // constructor vacío para poder instanciar la clase al leer la bd
    public Category() {}

    //constructor
    public Category(String category, String tag, Integer parentCategoryId) {
        this.category = category;
        this.tag = tag;
        this.parentCategoryId = parentCategoryId;
        this.status = 1; 
    }

    // los getters y setters
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public Integer getParentCategoryId() { return parentCategoryId; }
    public void setParentCategoryId(Integer parentCategoryId) { this.parentCategoryId = parentCategoryId; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}