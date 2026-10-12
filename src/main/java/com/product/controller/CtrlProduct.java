package com.product.controller;

/*
/  Equipo:
/         Gallardo Valdez Brayan Alexis
/         Torres Miguel Emiliano        
*/

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.entity.Category;

// decimos que es un controlador REST y la ruta principal
@RestController
@RequestMapping("/category") 
public class CtrlProduct {

    // maneja las peticiones GET a /category
    @GetMapping
    public List<Category> getCategories() {
        
        // se crea la lista en tiempo de ejecucion
        List<Category> categories = new ArrayList<>();
        
        // 1ra categoria padre
        Category ropa = new Category("Ropa", "RP", null);
        ropa.setCategory_id(1); // Forzamos el ID para que coincida con el ejemplo
        
        // 2da categoría (hija de ropa)
        Category calzado = new Category("Calzado", "CLZD", 1);
        calzado.setCategory_id(2);
        
        categories.add(ropa);
        categories.add(calzado);
        
        // Spring Web convierte esta lista a json
        return categories; 
    }
}