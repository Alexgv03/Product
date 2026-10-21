package com.product.api.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

@Service
public class SvcCategoryImp implements SvcCategory {

    final RepoCategory repo;

    SvcCategoryImp(RepoCategory repo) {
        this.repo = repo;
    }

    private void validateId(Integer id) {
        if (repo.findById(id).isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "El id de la categoría no existe");
        }
    }

    private void validateCategory(DtoCategoryIn dto, Integer currentId) {
        Category category = repo.findByCategory(dto.getCategory());
        if (category != null && (currentId == null || !category.getCategoryId().equals(currentId))) {
            throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está en uso");
        }

        Category tag = repo.findByTag(dto.getTag());
        if (tag != null && (currentId == null || !tag.getCategoryId().equals(currentId))) {
            throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está en uso");
        }

        if (dto.getParentCategoryId() != null) {
            if (dto.getParentCategoryId().equals(currentId)) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Una categoría no puede ser padre de sí misma");
            }
            Category parent = repo.findById(dto.getParentCategoryId()).orElse(null);
            if (parent == null || parent.getStatus() == 0) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "La categoría padre no existe o está inactiva");
            }
        }
    }

    @Override
    public ResponseEntity<List<Category>> findAll() {
        try {
            return new ResponseEntity<>(repo.getCategories(), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<List<Category>> findActive() {
        try {
            return new ResponseEntity<>(repo.findByStatusOrderByCategory(1), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<List<Category>> findChilds(Integer id) {
        try {
            return new ResponseEntity<>(repo.findByParentCategoryId(id), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public void create(DtoCategoryIn dto) {
        validateCategory(dto, null);
        try {
            repo.create(dto.getCategory(), dto.getTag(), dto.getParentCategoryId());
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al crear la categoría");
        }
    }

    @Override
    public void update(DtoCategoryIn dto, Integer id) {
        validateId(id);
        validateCategory(dto, id);
        try {
            repo.update(id, dto.getCategory(), dto.getTag(), dto.getParentCategoryId());
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al actualizar la categoría");
        }
    }

    @Override
    public void enable(Integer id) {
        validateId(id);
        try {
            repo.updateStatus(id, 1);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al activar la categoría");
        }
    }

    @Override
    public void disable(Integer id) {
        validateId(id);
        
        List<Category> childs = repo.findByParentCategoryId(id);
        for (Category c : childs) {
            if (c.getStatus() == 1) {
                throw new ApiException(HttpStatus.CONFLICT, "La categoría tiene subcategorías activas");
            }
        }

        try {
            repo.updateStatus(id, 0);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al desactivar la categoría");
        }
    }
}