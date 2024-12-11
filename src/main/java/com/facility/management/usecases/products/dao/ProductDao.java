package com.facility.management.usecases.products.dao;

import com.facility.management.usecases.products.dto.ProductCategoryDTO;
import com.facility.management.usecases.products.dto.ProductDTO;
import com.facility.management.usecases.products.dto.ProductSubCategoryDTO;

import java.util.List;

public interface ProductDao {
    public List<ProductDTO> getProductsByCategory(String plant, String category, String subCategoryCode);

    List<ProductDTO> getProductsBySubCategory(String plant, String category, String subCategory);

    List<ProductCategoryDTO> getAllCategories(String plant);

    List<ProductSubCategoryDTO> getSubCategoryByCategory(String plant, String category);
}
