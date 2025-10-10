package com.owner.process.usecases.products;


import com.owner.process.helpers.exception.custom.ResourceNotFoundException;
import com.owner.process.usecases.products.cust_last_ordered_products.custLastOrdProductPojo;
import com.owner.process.usecases.products.productDao.productDaoPojo;
import com.owner.process.usecases.products.product_classification.prodClasificationPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

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
}
