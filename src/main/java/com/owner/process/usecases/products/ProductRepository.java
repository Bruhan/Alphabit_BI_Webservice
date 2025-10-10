package com.owner.process.usecases.products;


import com.owner.process.persistence.models.ItemMst;
import com.owner.process.usecases.products.cust_last_ordered_products.custLastOrdProductPojo;
import com.owner.process.usecases.products.productDao.productDaoPojo;
import com.owner.process.usecases.products.product_classification.prodClasificationPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ItemMst,Long> {

    @Query(value="SELECT IT.ID AS id,IT.PLANT AS plant,ISNULL(IT.ITEM,'') AS item,ISNULL(IT.ITEMDESC,'') AS itemDesc," +
            "ISNULL(IT.PRD_CLS_ID,'') AS category,ISNULL(IT.PRD_DEPT_ID,'') AS department,0 As orderQty,0 As maxOrderQty," +
            "ISNULL(IT.PRD_BRAND_ID,'') AS brand,ISNULL(IT.ITEMTYPE,'') AS subCategory,ISNULL(IT.SALESUOM,'') AS salesUom," +
            "ISNULL((SELECT SUM(QTY) FROM ##plant##INVMST AS IV WHERE IV.ITEM = IT.ITEM GROUP BY IV.ITEM ),0) AS stockQty " +
            "FROM ##plant##ITEMMST AS IT WHERE IT.PLANT = ?5 AND ISNULL(IT.PRD_CLS_ID,'') LIKE ?1% " +
            "AND ISNULL(IT.PRD_DEPT_ID,'') LIKE ?4% AND " +
            "ISNULL(PRD_BRAND_ID,'') LIKE ?3% AND ISNULL(ITEMTYPE,'') LIKE ?2% AND (ISNULL(IT.ITEM,'') LIKE %?6% " +
            "OR ISNULL(IT.ITEMDESC,'') LIKE %?6%)",nativeQuery = true)
    List<productDaoPojo> getListOfProducts(String cat, String subCat, String brand, String dept, String plant, String search);

    @Query(value="SELECT DT.ITEM AS item,SUM(DT.QTYOR) AS orderQty FROM ##plant##DOHDR AS DH " +
            "JOIN ##plant##DODET AS DT ON DH.DONO = DT.DONO WHERE DH.ID=(SELECT MAX(ID) " +
            "FROM ##plant##DOHDR WHERE CustCode = ?1) GROUP BY DT.ITEM",nativeQuery = true)
    List<custLastOrdProductPojo> getLastOrderedProducts(String cust);

    @Query(value="SELECT PRD_DEPT_ID AS id ,ISNULL(PRD_DEPT_DESC,'') AS prodClassification FROM ##plant##PRD_DEPT_MST " +
            "WHERE PLANT = ?1 AND ISNULL(PRD_DEPT_DESC,'') != ''",nativeQuery = true)
    List<prodClasificationPojo> getAllDepartments(String plant);

    @Query(value="SELECT PRD_CLS_ID AS id ,ISNULL(PRD_CLS_DESC,'') AS prodClassification FROM ##plant##PRD_CLASS_MST " +
            "WHERE PLANT = ?1 AND ISNULL(PRD_CLS_DESC,'') != ''",nativeQuery = true)
    List<prodClasificationPojo> getAllCategory(String plant);

    @Query(value="SELECT ISNULL(IT.PRD_DEPT_ID,'') AS id,ISNULL(D.PRD_DEPT_DESC,'') AS prodClassification " +
            "FROM ##plant##ITEMMST AS IT LEFT JOIN ##plant##PRD_DEPT_MST AS D ON D.PRD_DEPT_ID = IT.PRD_DEPT_ID " +
            "WHERE IT.PLANT = ?4 AND ISNULL(IT.PRD_CLS_ID,'') LIKE ?1% " +
            "AND ISNULL(IT.PRD_DEPT_ID,'') != '' AND " +
            "ISNULL(PRD_BRAND_ID,'') LIKE ?3% AND ISNULL(ITEMTYPE,'') LIKE ?2% GROUP BY IT.PRD_DEPT_ID,D.PRD_DEPT_DESC",nativeQuery = true)
    List<prodClasificationPojo> getDepartments(String cat, String subCat, String brand, String plant);

    @Query(value="SELECT ISNULL(IT.PRD_CLS_ID,'') AS id,ISNULL(C.PRD_CLS_DESC,'') AS prodClassification FROM ##plant##ITEMMST AS IT LEFT JOIN ##plant##PRD_CLASS_MST AS C ON C.PRD_CLS_ID = IT.PRD_CLS_ID " +
            "WHERE IT.PLANT = ?4 AND ISNULL(IT.PRD_CLS_ID,'') != '' " +
            "AND ISNULL(IT.PRD_DEPT_ID,'') LIKE ?3% AND " +
            "ISNULL(PRD_BRAND_ID,'') LIKE ?2% AND ISNULL(ITEMTYPE,'') LIKE ?1% GROUP BY IT.PRD_CLS_ID,C.PRD_CLS_DESC",nativeQuery = true)
    List<prodClasificationPojo> getCategory(String subCat, String brand, String dept, String plant);

    @Query(value="SELECT ISNULL(IT.ITEMTYPE,'')  AS id,ISNULL(S.PRD_TYPE_DESC,'') AS prodClassification FROM ##plant##ITEMMST AS IT LEFT JOIN ##plant##PRD_TYPE_MST AS S ON S.PRD_TYPE_ID = IT.ITEMTYPE " +
            "WHERE IT.PLANT = ?4 AND ISNULL(IT.PRD_CLS_ID,'') LIKE ?1% " +
            "AND ISNULL(IT.PRD_DEPT_ID,'') LIKE ?3% AND " +
            "ISNULL(PRD_BRAND_ID,'') LIKE ?2% AND ISNULL(ITEMTYPE,'') != '' GROUP BY IT.ITEMTYPE,S.PRD_TYPE_DESC",nativeQuery = true)
    List<prodClasificationPojo> getSubCategory(String cat, String brand, String dept, String plant);

    @Query(value="SELECT ISNULL(IT.PRD_BRAND_ID,'') AS id,ISNULL(B.PRD_BRAND_DESC,'') AS prodClassification FROM ##plant##ITEMMST AS IT LEFT JOIN ##plant##PRD_BRAND_MST AS B ON B.PRD_BRAND_ID = IT.PRD_BRAND_ID " +
            "WHERE IT.PLANT = ?4 AND ISNULL(IT.PRD_CLS_ID,'') LIKE ?1% " +
            "AND ISNULL(IT.PRD_DEPT_ID,'') LIKE ?3% AND " +
            "ISNULL(IT.PRD_BRAND_ID,'') != '' AND ISNULL(ITEMTYPE,'') LIKE ?2% GROUP BY IT.PRD_BRAND_ID,B.PRD_BRAND_DESC",nativeQuery = true)
    List<prodClasificationPojo> getBrand(String cat, String subCat, String dept, String plant);


}
