/*
 Equipo: 
        Gallardo Valdez Brayan Alexis
        Torres Miguel Emiliano
*/

package com.product.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;

@Service // marca la clase como un componente de servicio
public class SvcCategoryImp implements SvcCategory {

    
    final RepoCategory repo;
    // inyeccion dependencias por constructor
    SvcCategoryImp(RepoCategory repo) {
        this.repo = repo;
    }

    @Override
    public List<Category> getCategories() {
        return repo.getCategories();
    }

    @Override
    public List<Category> getActiveCategories() {
        return repo.findByStatusOrderByCategory(1);
    }
}