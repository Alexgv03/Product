/*
 Equipo: 
        Gallardo Valdez Brayan Alexis
        Torres Miguel Emiliano
*/

package com.product.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;

@RestController //es controlador
@RequestMapping("/category") //ruta base para los metodos de esta clase
public class CtrlCategory {

    final SvcCategory svc;

    CtrlCategory(SvcCategory svc) {
        this.svc = svc;
    }

    // mapea solicitud http get
    @GetMapping
    public List<Category> getCategories() {
        // regresa la lista con las categorias desde la bd
        return svc.getCategories();
    }
}