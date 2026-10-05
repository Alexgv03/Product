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

@Service
public class SvcCategoryImp implements SvcCategory {

    final RepoCategory repo;

    SvcCategoryImp(RepoCategory repo) {
        this.repo = repo;
    }

    @Override
    public ResponseEntity<List<Category>> findAll() {
        return new ResponseEntity<>(repo.getCategories(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Category>> findActive() {
        return new ResponseEntity<>(repo.findByStatusOrderByCategory(1), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Category>> findChilds(Integer id) {
        return new ResponseEntity<>(repo.findByParentCategoryId(id), HttpStatus.OK);
    }

    @Override
    public void create(DtoCategoryIn dto) {
        try {
            repo.create(dto.getCategory(), dto.getTag(), dto.getParentCategoryId());
        } catch (DataAccessException e) {
            Throwable root = e.getRootCause();
            String msg = (root != null) ? root.getMessage() : e.getMessage();
            
            if (msg != null && msg.contains("ux_category")) {
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está en uso");
            }
            if (msg != null && msg.contains("ux_tag")) {
                throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está en uso");
            }
            if (msg != null && msg.contains("La categoría padre no existe o está inactiva")) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "La categoría padre no existe o está inactiva");
            }
            if (msg != null && msg.contains("Una categoría no puede ser padre de sí misma")) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Una categoría no puede ser padre de sí misma");
            }
            
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al crear la categoría");
        }
    }

    @Override
    public void update(DtoCategoryIn dto, Integer id) {
        try {
            repo.update(id, dto.getCategory(), dto.getTag(), dto.getParentCategoryId());
        } catch (DataAccessException e) {
            Throwable root = e.getRootCause();
            String msg = (root != null) ? root.getMessage() : e.getMessage();
            
            if (msg != null && msg.contains("ux_category")) {
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está en uso");
            }
            if (msg != null && msg.contains("ux_tag")) {
                throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está en uso");
            }
            if (msg != null && msg.contains("La categoría padre no existe o está inactiva")) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "La categoría padre no existe o está inactiva");
            }
            if (msg != null && msg.contains("Una categoría no puede ser padre de sí misma")) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Una categoría no puede ser padre de sí misma");
            }
            
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al actualizar la categoría");
        }
    }

    @Override
    public void enable(Integer id) {
        try {
            repo.updateStatus(id, 1);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al activar la categoría");
        }
    }

    @Override
    public void disable(Integer id) {
        try {
            repo.updateStatus(id, 0);
        } catch (DataAccessException e) {
            Throwable root = e.getRootCause();
            String msg = (root != null) ? root.getMessage() : e.getMessage();
            
            if (msg != null && msg.contains("No es posible eliminar una categoría si tiene categorías hijas")) {
                throw new ApiException(HttpStatus.CONFLICT, "La categoría tiene subcategorías activas");
            }
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al desactivar la categoría");
        }
    }
}