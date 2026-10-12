/*
 Equipo: 
        Gallardo Valdez Brayan Alexis
        Torres Miguel Emiliano
*/

package com.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.product.api.entity.Category;

@Repository
public interface RepoCategory extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT * FROM category ORDER BY category", nativeQuery = true)
    List<Category> getCategories();

    List<Category> findByStatusOrderByCategory(@Param("status") Integer status);

    List<Category> findByParentCategoryId(@Param("parent_category_id") Integer parentCategoryId);

    @Modifying(clearAutomatically=true, flushAutomatically=true)
    @Transactional
    @Query(value="INSERT INTO category(category, tag, status, parent_category_id) VALUES(:category, :tag, 1, :parent_category_id)", nativeQuery=true)
    void create(@Param("category") String category, @Param("tag") String tag, @Param("parent_category_id") Integer parentCategoryId);

    @Modifying(clearAutomatically=true, flushAutomatically=true)
    @Transactional
    @Query(value="UPDATE category SET category = :category, tag = :tag, parent_category_id = :parent_category_id WHERE category_id = :category_id", nativeQuery=true)
    void update(@Param("category_id") Integer categoryId, @Param("category") String category, @Param("tag") String tag, @Param("parent_category_id") Integer parentCategoryId);

    @Modifying(clearAutomatically=true, flushAutomatically=true)
    @Transactional
    @Query(value="UPDATE category SET status = :status WHERE category_id = :category_id", nativeQuery = true)
    void updateStatus(@Param("category_id") Integer categoryId, @Param("status") Integer status);
}