package com.facility.management.usecases.products;


import com.facility.management.helpers.exception.custom.ResourceNotFoundException;
import com.facility.management.usecases.products.cust_last_ordered_products.custLastOrdProductPojo;
import com.facility.management.usecases.products.dao.ProductDao;
import com.facility.management.usecases.products.dto.ProductCategoryDTO;
import com.facility.management.usecases.products.dto.ProductDTO;
import com.facility.management.usecases.products.dto.ProductSubCategoryDTO;
import com.facility.management.usecases.products.enums.ProductCategory;
import com.facility.management.usecases.products.enums.ProductSubCategory;
import com.facility.management.usecases.products.productDao.productDaoPojo;
import com.facility.management.usecases.products.product_classification.prodClasificationPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductDao productDao;

    public List<productDaoPojo> getListOfProducts(String cat, String subcat, String brand, String dept, String plant, String search) throws Exception {
        List<productDaoPojo> val;
        try {
            if(cat == null){
                cat="";
            }
            if(subcat == null){
                subcat="";
            }
            if(brand == null){
                brand="";
            }
            if(dept == null){
                dept="";
            }
            if(search == null){
                search="";
            }
            val = productRepository.getListOfProducts(cat,subcat,brand,dept,plant,search);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<custLastOrdProductPojo> getLastOrderedProducts(String cust) throws Exception {
        List<custLastOrdProductPojo> val;
        try {
            val = productRepository.getLastOrderedProducts(cust);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<prodClasificationPojo> getAllDepartments(String plant) throws Exception {
        List<prodClasificationPojo> val;
        try {
            val = productRepository.getAllDepartments(plant);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<prodClasificationPojo> getAllCategory(String plant) throws Exception {
        List<prodClasificationPojo> val;
        try {
            val = productRepository.getAllCategory(plant);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<prodClasificationPojo> getDepartments(String cat, String subCat, String brand, String plant) throws Exception {
        List<prodClasificationPojo> val;
        try {
            if(cat == null){
                cat="";
            }
            if(subCat == null){
                subCat="";
            }
            if(brand == null){
                brand="";
            }
            val = productRepository.getDepartments(cat,subCat,brand,plant);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<prodClasificationPojo> getCategory(String subCat, String brand, String dept, String plant) throws Exception {
        List<prodClasificationPojo> val;
        try {
            if(subCat == null){
                subCat="";
            }
            if(brand == null){
                brand="";
            }
            if(dept == null){
                dept="";
            }
            val = productRepository.getCategory(subCat, brand, dept, plant);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<prodClasificationPojo> getSubCategory(String cat, String brand, String dept, String plant) throws Exception {
        List<prodClasificationPojo> val;
        try {
            if(cat == null){
                cat="";
            }
            if(brand == null){
                brand="";
            }
            if(dept == null){
                dept="";
            }
            val = productRepository.getSubCategory(cat, brand, dept, plant);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<prodClasificationPojo> getBrand(String cat, String subCat, String dept, String plant) throws Exception {
        List<prodClasificationPojo> val;
        try {
            if(cat == null){
                cat="";
            }
            if(subCat == null){
                subCat="";
            }
            if(dept == null){
                dept="";
            }
            val = productRepository.getBrand(cat, subCat, dept, plant);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<productDaoPojo> getListOfProductsbysupplier(String suppliercode) throws Exception {
        List<productDaoPojo> val;
        try {
            val = productRepository.getListOfProductsbysupplier(suppliercode);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<ProductDTO> getProductRequestProducts(String plant, String subCategoryCode) throws Exception {
        List<ProductDTO> productDTOList = null;
        try {

            productDTOList = productDao.getProductsByCategory(plant, ProductCategory.PC003.name(), subCategoryCode);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return productDTOList;
    }

    public List<ProductDTO> getInorganicWastageProducts(String plant, String subCategoryCode) throws Exception {
        List<ProductDTO> productDTOList = null;
        try {
            productDTOList = productDao.getProductsByCategory(plant, ProductCategory.PC004.name(), subCategoryCode);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return productDTOList;
    }

    public List<ProductDTO> getOWCProducts(String plant) throws Exception {
        List<ProductDTO> productDTOList = null;
        try {
            productDTOList = productDao.getProductsBySubCategory(plant, ProductCategory.PC003.name(), ProductSubCategory.PT003.name());
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return productDTOList;
    }

    public List<ProductDTO> getOWCOutcomeProducts(String plant) throws Exception {
        List<ProductDTO> productDTOList = null;
        try {
            productDTOList = productDao.getProductsBySubCategory(plant, ProductCategory.PC005.name(), ProductSubCategory.PT011.name());
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return productDTOList;
    }

    public List<ProductCategoryDTO> getAllCategories(String plant) throws Exception {
        List<ProductCategoryDTO> productCategoryDTOList = null;
        try {
            productCategoryDTOList = productDao.getAllCategories(plant);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }

        return productCategoryDTOList;
    }

    public List<ProductSubCategoryDTO> getProductRequestSubCategory(String plant) throws Exception {
        List<ProductSubCategoryDTO> productSubCategoryDTOList = null;
        try {
            productSubCategoryDTOList = productDao.getSubCategoryByCategory(plant, ProductCategory.PC003.name());
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }

        return productSubCategoryDTOList;
    }

    public List<ProductSubCategoryDTO> getInorganicSubCategory(String plant) throws Exception {
        List<ProductSubCategoryDTO> productSubCategoryDTOList = null;
        try {
            productSubCategoryDTOList = productDao.getSubCategoryByCategory(plant, ProductCategory.PC004.name());
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }

        return productSubCategoryDTOList;
    }


}
