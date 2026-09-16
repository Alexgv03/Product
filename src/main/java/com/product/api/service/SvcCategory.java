/*
 Equipo: 
        Gallardo Valdez Brayan Alexis
        Torres Miguel Emiliano
*/

package com.product.api.service;

import java.util.List;

import com.product.api.entity.Category;

public interface SvcCategory {
    List<Category> getCategories();
    List<Category> getActiveCategories();
}