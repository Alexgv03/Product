package com.product.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.entity.Category;

// Indicamos que es un controlador REST y la ruta principal
@RestController
@RequestMapping("/category") 
public class CtrlProduct {

    // Maneja las peticiones GET a /category
    @GetMapping
    public List<Category> getCategories() {
        
        // Creamos la lista en memoria (tiempo de ejecución)
        List<Category> categories = new ArrayList<>();
        
        // Primera categoría padre
        Category ropa = new Category("Ropa", "RP", null);
        ropa.setCategory_id(1); // Forzamos el ID para que coincida con el ejemplo
        
        // Segunda categoría (hija de ropa)
        Category calzado = new Category("Calzado", "CLZD", 1);
        calzado.setCategory_id(2);
        
        categories.add(ropa);
        categories.add(calzado);
        
        // Spring Web convertirá automáticamente esta lista a JSON (Jackson)
        return categories; 
    }
}