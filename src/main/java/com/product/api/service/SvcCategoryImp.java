package com.product.api.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.DBAccessException;

@Service
public class SvcCategoryImp implements SvcCategory {

    final RepoCategory repo;

    SvcCategoryImp(RepoCategory repo) {
        this.repo = repo;
    }

    @Override
    public ResponseEntity<List<Category>> getCategories() {
        try {
            List<Category> categories = repo.getCategories();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (DataAccessException e) {
            // Lanza la excepción personalizada si la base de datos falla
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<List<Category>> getActiveCategories() {
        try {
            List<Category> categories = repo.findByStatusOrderByCategory(1);
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }
}