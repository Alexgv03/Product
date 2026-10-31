package com.product.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProduct;
import com.product.api.repository.RepoProductImage;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

@Service
public class SvcProductImageImp implements SvcProductImage {

    @Autowired
    RepoProductImage repo;

    @Autowired
    RepoProduct repoProduct;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.upload.images}")
    private String uploadImages;

    private void validateProductId(Integer id) {
        if (repoProduct.findById(id).isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
        }
    }

    private void validateImageId(Integer id) {
        if (repo.findById(id).isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "La imagen del producto no existe");
        }
    }

    @Override
    public ResponseEntity<List<ProductImage>> getProductImages(Integer productId) {
        validateProductId(productId);
        
        try {
            List<ProductImage> allImages = repo.findAll();
            List<ProductImage> productImages = allImages.stream()
                .filter(img -> img.getProductId().equals(productId) && img.getStatus() == 1)
                .toList();
            
            return new ResponseEntity<>(productImages, HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<String> createProductImage(DtoProductImageIn in) {
        validateProductId(in.getProductId());

        try {
            if (in.getImage().startsWith("data:image")) {
                int commaIndex = in.getImage().indexOf(",");
                if (commaIndex != -1) {
                    in.setImage(in.getImage().substring(commaIndex + 1));
                }
            }

            byte[] imageBytes = Base64.getDecoder().decode(in.getImage());
            
            String fileName = UUID.randomUUID().toString() + ".png";
            
            Path imagePath = Paths.get(uploadDir, uploadImages, "product", fileName);
            
            Files.createDirectories(imagePath.getParent());
            
            Files.write(imagePath, imageBytes);

            ProductImage productImage = new ProductImage();
            productImage.setProductId(in.getProductId());
            productImage.setImage("/product/" + fileName);
            productImage.setStatus(1);
            
            repo.save(productImage);

            return new ResponseEntity<>("La imagen ha sido registrada", HttpStatus.OK);

        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el archivo");
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<String> deleteProductImage(Integer id) {
        validateImageId(id);
        
        try {
            ProductImage productImage = repo.findById(id).get();
            productImage.setStatus(0);
            repo.save(productImage);
            
            return new ResponseEntity<>("La imagen ha sido eliminada", HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }
}