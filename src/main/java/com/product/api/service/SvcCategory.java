package com.product.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;

public interface SvcCategory {
    ResponseEntity<List<Category>> findAll();
    ResponseEntity<List<Category>> findActive();
    ResponseEntity<List<Category>> findChilds(Integer id);
    void create(DtoCategoryIn dto);
    void update(DtoCategoryIn dto, Integer id);
    void enable(Integer id);
    void disable(Integer id);
}