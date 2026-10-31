package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoProductIn;
import com.product.api.dto.out.DtoProductListOut;
import com.product.api.dto.out.DtoProductOut;
import com.product.api.entity.Product;
import com.product.api.repository.RepoCategory;
import com.product.api.repository.RepoProduct;
import com.product.common.mapper.MapperProduct;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

@Service
public class SvcProductImp implements SvcProduct {

    @Autowired
    RepoProduct repo;

    @Autowired
    RepoCategory repoCategory;

    @Autowired
    MapperProduct mapper;

    private void validateProductId(Integer id) {
        if (repo.findById(id).isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
        }
    }

    private void validateProduct(DtoProductIn dto, Integer currentId) {
        List<Product> gtins = repo.findByGtin(dto.getGtin());
        if (!gtins.isEmpty()) {
            Product p = gtins.get(0);
            if (currentId == null || !p.getProduct_id().equals(currentId)) {
                throw new ApiException(HttpStatus.CONFLICT, "El gtin del producto ya está registrado");
            }
        }

        List<Product> products = repo.findByProduct(dto.getProduct());
        if (!products.isEmpty()) {
            Product p = products.get(0);
            if (currentId == null || !p.getProduct_id().equals(currentId)) {
                throw new ApiException(HttpStatus.CONFLICT, "El nombre del producto ya está registrado");
            }
        }

        if (repoCategory.findById(dto.getCategory_id()).isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");
        }
    }

    @Override
    public ResponseEntity<List<DtoProductListOut>> getProducts() {
        try {
            List<Product> products = repo.findAll();
            return new ResponseEntity<>(mapper.fromProductList(products), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<DtoProductOut> getProduct(Integer id) {
        validateProductId(id);
        try {
            Product product = repo.findById(id).get();
            return new ResponseEntity<>(mapper.toDtoOut(product), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<String> createProduct(DtoProductIn in) {
        validateProduct(in, null);
        try {
            Product product = mapper.fromDto(in);
            repo.save(product);
            return new ResponseEntity<>("El producto ha sido registrado", HttpStatus.CREATED);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<String> updateProduct(Integer id, DtoProductIn in) {
        validateProductId(id);
        validateProduct(in, id);
        try {
            Product product = mapper.fromDto(id, in);
            repo.save(product);
            return new ResponseEntity<>("El producto ha sido actualizado", HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<String> enableProduct(Integer id) {
        validateProductId(id);
        try {
            Product product = repo.findById(id).get();
            product.setStatus(1);
            repo.save(product);
            return new ResponseEntity<>("El producto ha sido activado", HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<String> disableProduct(Integer id) {
        validateProductId(id);
        try {
            Product product = repo.findById(id).get();
            product.setStatus(0);
            repo.save(product);
            return new ResponseEntity<>("El producto ha sido desactivado", HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }
}
