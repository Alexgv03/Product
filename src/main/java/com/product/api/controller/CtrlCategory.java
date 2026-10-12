package com.product.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CtrlCategory {

    final SvcCategory svc;

    CtrlCategory(SvcCategory svc) {
        this.svc = svc;
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return svc.findAll();
    }

    @GetMapping("/active")
    public ResponseEntity<List<Category>> findActive() {
        return svc.findActive();
    }

    @GetMapping("/{id}/childs")
    public ResponseEntity<List<Category>> findChilds(@PathVariable Integer id) {
        return svc.findChilds(id);
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody DtoCategoryIn dto){
        svc.create(dto);
        return ResponseEntity.ok().body("La categoría ha sido registrada");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody DtoCategoryIn dto, @PathVariable Integer id) {
        svc.update(dto, id);
        return ResponseEntity.ok().body("La categoría ha sido actualizada");
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<String> enable(@PathVariable Integer id){
         svc.enable(id);
         return ResponseEntity.ok().body("La categoría ha sido activada");
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<String> disable(@PathVariable Integer id){
         svc.disable(id);
         return ResponseEntity.ok().body("La categoría ha sido desactivada");
    }
}