/*
 Equipo: 
        Gallardo Valdez Brayan Alexis
        Torres Miguel Emiliano
*/

package com.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.api.entity.Category;

@Repository // indica que este componente interactua con la bd
public interface RepoCategory extends JpaRepository<Category, Integer> {

    // consulta sql
    @Query(value = "SELECT * FROM category ORDER BY category", nativeQuery = true)
    List<Category> getCategories();

    // lee el nombre del metodo y crea una consulta automaticamente 
    List<Category> findByStatusOrderByCategory(@Param("status") Integer status);
}