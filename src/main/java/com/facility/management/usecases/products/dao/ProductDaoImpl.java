package com.facility.management.usecases.products.dao;

import com.facility.management.usecases.products.dto.ProductCategoryDTO;
import com.facility.management.usecases.products.dto.ProductDTO;
import com.facility.management.usecases.products.dto.ProductSubCategoryDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository("ProductDao")
public class ProductDaoImpl implements ProductDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<ProductDTO> getProductsByCategory(String plant, String category, String subCategoryCode) {
        Session session = sessionFactory.openSession();
        List<ProductDTO> productDTOList = null;
        String sql = null;
        try {
            if(subCategoryCode.toLowerCase().equals("all")) {
                sql = "SELECT ID, PLANT, ITEM, ITEMDESC, PRD_CLS_ID, ITEMTYPE, STKUOM FROM " + plant + "_ITEMMST " +
                        "WHERE PLANT = :plant AND PRD_CLS_ID = :category";

                Query query = session.createSQLQuery(sql);

                query.setParameter("plant", plant);
                query.setParameter("category", category);

                List<Object[]> rows = query.list();

                productDTOList = new ArrayList<>();
                for(Object[] row: rows) {
                    ProductDTO productDTO = new ProductDTO();

                    productDTO.setId((int) row[0]);
                    productDTO.setPlant((String) row[1]);
                    productDTO.setItem((String) row[2]);
                    productDTO.setItemDesc((String) row[3]);
                    productDTO.setCategory((String) row[4]);
                    productDTO.setSubCategory((String) row[5]);
                    productDTO.setStkUom((String) row[6]);

                    productDTOList.add(productDTO);
                }
            } else {
                sql = "SELECT ID, PLANT, ITEM, ITEMDESC, PRD_CLS_ID, ITEMTYPE, STKUOM FROM " + plant + "_ITEMMST " +
                        "WHERE PLANT = :plant AND PRD_CLS_ID = :category AND ITEMTYPE = :subCategory";

                Query query = session.createSQLQuery(sql);

                query.setParameter("plant", plant);
                query.setParameter("category", category);
                query.setParameter("subCategory", subCategoryCode);

                List<Object[]> rows = query.list();

                productDTOList = new ArrayList<>();
                for(Object[] row: rows) {
                    ProductDTO productDTO = new ProductDTO();

                    productDTO.setId((int) row[0]);
                    productDTO.setPlant((String) row[1]);
                    productDTO.setItem((String) row[2]);
                    productDTO.setItemDesc((String) row[3]);
                    productDTO.setCategory((String) row[4]);
                    productDTO.setSubCategory((String) row[5]);
                    productDTO.setStkUom((String) row[6]);

                    productDTOList.add(productDTO);
                }
            }


        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }

        return productDTOList;
    }

    @Override
    public List<ProductDTO> getProductsBySubCategory(String plant, String category, String subCategory) {
        Session session = sessionFactory.openSession();
        List<ProductDTO> productDTOList = null;
        try {
            String sql = "SELECT ID, PLANT, ITEM, ITEMDESC, PRD_CLS_ID, ITEMTYPE, STKUOM FROM " + plant + "_ITEMMST " +
                    "WHERE PLANT = :plant AND PRD_CLS_ID = :category AND ITEMTYPE = :subCategory";

            Query query = session.createSQLQuery(sql);

            query.setParameter("plant", plant);
            query.setParameter("category", category);
            query.setParameter("subCategory", subCategory);

            List<Object[]> rows = query.list();

            productDTOList = new ArrayList<>();
            for(Object[] row: rows) {
                ProductDTO productDTO = new ProductDTO();

                productDTO.setId((int) row[0]);
                productDTO.setPlant((String) row[1]);
                productDTO.setItem((String) row[2]);
                productDTO.setItemDesc((String) row[3]);
                productDTO.setCategory((String) row[4]);
                productDTO.setSubCategory((String) row[5]);
                productDTO.setStkUom((String) row[6]);

                productDTOList.add(productDTO);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }

        return productDTOList;
    }

    @Override
    public List<ProductCategoryDTO> getAllCategories(String plant) {
        Session session = sessionFactory.openSession();
        List<ProductCategoryDTO> productCategoryDTOList = null;
        try {
            String sql = "SELECT PRD_CLS_ID, PRD_CLS_DESC FROM " + plant + "_PRD_CLASS_MST WHERE PLANT = :plant";

            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);

            List<Object[]> rows = query.list();
            productCategoryDTOList = new ArrayList<>();

            for(Object[] row: rows) {
                ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
                productCategoryDTO.setCategoryCode((String) row[0]);
                productCategoryDTO.setCategoryName((String) row[1]);

                productCategoryDTOList.add(productCategoryDTO);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }

        return productCategoryDTOList;
    }

    @Override
    public List<ProductSubCategoryDTO> getSubCategoryByCategory(String plant, String category) {
        Session session = sessionFactory.openSession();
        List<ProductSubCategoryDTO> productSubCategoryDTOList = null;
        try {
            String sql = "SELECT ITEMTYPE AS SUBCATEGORYCODE, (SELECT PRD_TYPE_DESC FROM " + plant + "_PRD_TYPE_MST WHERE " +
                    "PRD_TYPE_ID = ITEMTYPE) AS SUBCATEGORYNAME FROM " + plant + "_ITEMMST WHERE PRD_CLS_ID = :category AND PLANT = :plant GROUP BY " +
                    "ITEMTYPE";

            Query query = session.createSQLQuery(sql);
            query.setParameter("plant", plant);
            query.setParameter("category", category);

            List<Object[]> rows = query.list();
            productSubCategoryDTOList = new ArrayList<>();

            for(Object[] row: rows) {
                ProductSubCategoryDTO productSubCategoryDTO = new ProductSubCategoryDTO();
                productSubCategoryDTO.setSubCategoryCode((String) row[0]);
                productSubCategoryDTO.setSubCategoryName((String) row[1]);

                productSubCategoryDTOList.add(productSubCategoryDTO);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }

        return productSubCategoryDTOList;
    }
}
