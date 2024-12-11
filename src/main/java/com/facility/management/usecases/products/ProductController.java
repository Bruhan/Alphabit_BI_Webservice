package com.facility.management.usecases.products;

import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.helpers.common.results.ResultDao;
import com.facility.management.helpers.common.results.ResultsDao;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.helpers.utils.JwtUtil;
import com.facility.management.persistence.models.ItemMst;
import com.facility.management.persistence.models.ProdBOMMst;
import com.facility.management.usecases.products.dto.ProductCategoryDTO;
import com.facility.management.usecases.products.dto.ProductDTO;
import com.facility.management.usecases.products.dto.ProductSubCategoryDTO;
import com.facility.management.usecases.products.productDao.productDao;
import com.facility.management.usecases.products.productDao.productDaoPojo;
import com.facility.management.usecases.products.product_classification.prodClasification;
import com.facility.management.usecases.products.product_classification.prodClasificationPojo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("${spring.base.path}")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    ClaimsSet claimsSet;

    @GetMapping("/product-request/products/{subCategoryCode}")
    public ResponseEntity<Object> getProductRequestProducts(HttpServletRequest request, @PathVariable("subCategoryCode") String subCategoryCode) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<ProductDTO> productDTOList = productService.getProductRequestProducts(plant, subCategoryCode);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(productDTOList);
        resultDao.setMessage("SUCCESS");
        resultDao.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/inorganic-wastage/products/{subCategoryCode}")
    public ResponseEntity<Object> getInorganicWastageProducts(HttpServletRequest request, @PathVariable("subCategoryCode") String subCategoryCode) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<ProductDTO> productDTOList = productService.getInorganicWastageProducts(plant, subCategoryCode);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(productDTOList);
        resultDao.setMessage("SUCCESS");
        resultDao.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }


    @GetMapping("/owc/products")
    public ResponseEntity<Object> getOWCProducts(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<ProductDTO> productDTOList = productService.getOWCProducts(plant);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(productDTOList);
        resultDao.setMessage("SUCCESS");
        resultDao.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/owc-outcome/products")
    public ResponseEntity<Object> getOWCOutcomeProducts(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<ProductDTO> productDTOList = productService.getOWCOutcomeProducts(plant);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(productDTOList);
        resultDao.setMessage("SUCCESS");
        resultDao.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<Object> getAllCategories(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<ProductCategoryDTO> productCategoryDTOList = productService.getAllCategories(plant);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(productCategoryDTOList);
        resultDao.setMessage("SUCCESS");
        resultDao.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/product-request/sub-categories")
    public ResponseEntity<Object> getProductRequestSubCategories(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<ProductSubCategoryDTO> productSubCategoryDTOList = productService.getProductRequestSubCategory(plant);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(productSubCategoryDTOList);
        resultDao.setMessage("SUCCESS");
        resultDao.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/inorganic-products/sub-categories")
    public ResponseEntity<Object> getInorganicProductsSubCategories(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<ProductSubCategoryDTO> productSubCategoryDTOList = productService.getInorganicSubCategory(plant);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(productSubCategoryDTOList);
        resultDao.setMessage("SUCCESS");
        resultDao.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<Object> getListOfProducts(HttpServletRequest request, @RequestParam(required=false) String category,
                                                     @RequestParam(required=false) String subCategory, @RequestParam(required=false) String department,
                                                     @RequestParam(required=false) String brand,@RequestParam(required=false) String search) throws Exception {
//        String plant = jwtUtil.plt;

        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime= new DateTimeCalc().getTodayDateTime();

        //get products
        List<productDaoPojo> productList = productService.getListOfProducts(category,subCategory,brand,department,plant,search);

        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<productDao> productDao = modelMapper.map(productList,
                new TypeToken<List<productDao>>() {
                }.getType());

        //result
        ResultsDao resultsDao = new ResultsDao();
        resultsDao.setResults(productDao);
        resultsDao.setPageNumber(1);
        resultsDao.setPageSize(productList.size());
        return new ResponseEntity<>(resultsDao, HttpStatus.OK);
    }

    @GetMapping("/getAllDepartment")
    public ResponseEntity<Object> getAllDepartment(HttpServletRequest request) throws Exception {

        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime= new DateTimeCalc().getTodayDateTime();

        //get all department
        List<prodClasificationPojo> prodClasificPojo = productService.getAllDepartments(plant);

        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<prodClasification> prodClasificationlist = modelMapper.map(prodClasificPojo,
                new TypeToken<List<prodClasification>>() {
                }.getType());

        //result
        ResultsDao resultsDao = new ResultsDao();
        resultsDao.setResults(prodClasificationlist);
        resultsDao.setPageNumber(1);
        resultsDao.setPageSize(prodClasificationlist.size());
        return new ResponseEntity<>(resultsDao, HttpStatus.OK);
    }

    @GetMapping("/getAllCategory")
    public ResponseEntity<Object> getAllCategory(HttpServletRequest request) throws Exception {

        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime= new DateTimeCalc().getTodayDateTime();

        //get all category
        List<prodClasificationPojo> prodClasificPojo = productService.getAllCategory(plant);

        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<prodClasification> prodClasificationlist = modelMapper.map(prodClasificPojo,
                new TypeToken<List<prodClasification>>() {
                }.getType());

        //result
        ResultsDao resultsDao = new ResultsDao();
        resultsDao.setResults(prodClasificationlist);
        resultsDao.setPageNumber(1);
        resultsDao.setPageSize(prodClasificationlist.size());
        return new ResponseEntity<>(resultsDao, HttpStatus.OK);
    }

    @GetMapping("/getDepartments")
    public ResponseEntity<Object> getDepartments(HttpServletRequest request,
                                                 @RequestParam(required=false) String category,
                                                 @RequestParam(required=false) String subCategory,
                                                 @RequestParam(required=false) String brand) throws Exception {

        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime= new DateTimeCalc().getTodayDateTime();

        //get departments
        List<prodClasificationPojo> prodClasificPojo = productService.getDepartments(category,subCategory,brand,plant);

        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<prodClasification> prodClasificationlist = modelMapper.map(prodClasificPojo,
                new TypeToken<List<prodClasification>>() {
                }.getType());


        //result
        ResultsDao resultsDao = new ResultsDao();
        resultsDao.setResults(prodClasificationlist);
        resultsDao.setPageNumber(1);
        resultsDao.setPageSize(prodClasificationlist.size());
        return new ResponseEntity<>(resultsDao, HttpStatus.OK);
    }

    @GetMapping("/getCategory")
    public ResponseEntity<Object> getCategory(HttpServletRequest request,
                                              @RequestParam(required=false) String subCategory,
                                              @RequestParam(required=false) String department,
                                              @RequestParam(required=false) String brand) throws Exception {

        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime= new DateTimeCalc().getTodayDateTime();

        //get category
        List<prodClasificationPojo> prodClasificPojo = productService.getCategory(subCategory,brand,department,plant);

        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<prodClasification> prodClasificationlist = modelMapper.map(prodClasificPojo,
                new TypeToken<List<prodClasification>>() {
                }.getType());


        //result
        ResultsDao resultsDao = new ResultsDao();
        resultsDao.setResults(prodClasificationlist);
        resultsDao.setPageNumber(1);
        resultsDao.setPageSize(prodClasificationlist.size());
        return new ResponseEntity<>(resultsDao, HttpStatus.OK);
    }

    @GetMapping("/getSubCategory")
    public ResponseEntity<Object> getSubCategory(HttpServletRequest request,
                                                 @RequestParam(required=false) String category,
                                                 @RequestParam(required=false) String department,
                                                 @RequestParam(required=false) String brand) throws Exception {

        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime= new DateTimeCalc().getTodayDateTime();

        //get subcategory
        List<prodClasificationPojo> prodClasificPojo = productService.getSubCategory(category,brand,department,plant);

        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<prodClasification> prodClasificationlist = modelMapper.map(prodClasificPojo,
                new TypeToken<List<prodClasification>>() {
                }.getType());


        //result
        ResultsDao resultsDao = new ResultsDao();
        resultsDao.setResults(prodClasificationlist);
        resultsDao.setPageNumber(1);
        resultsDao.setPageSize(prodClasificationlist.size());
        return new ResponseEntity<>(resultsDao, HttpStatus.OK);
    }

    @GetMapping("/getBrand")
    public ResponseEntity<Object> getBrand(HttpServletRequest request,
                                           @RequestParam(required=false) String category,
                                           @RequestParam(required=false) String subCategory,
                                           @RequestParam(required=false) String department) throws Exception {

        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime= new DateTimeCalc().getTodayDateTime();

        //get brand
        List<prodClasificationPojo> prodClasificPojo = productService.getBrand(category,subCategory,department,plant);

        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<prodClasification> prodClasificationlist = modelMapper.map(prodClasificPojo,
                new TypeToken<List<prodClasification>>() {
                }.getType());


        //result
        ResultsDao resultsDao = new ResultsDao();
        resultsDao.setResults(prodClasificationlist);
        resultsDao.setPageNumber(1);
        resultsDao.setPageSize(prodClasificationlist.size());
        return new ResponseEntity<>(resultsDao, HttpStatus.OK);
    }

    @GetMapping("/product/getbysupplier")
    public ResponseEntity<Object> getbysupplier(HttpServletRequest request, @RequestParam String suppliercode) throws Exception {
        String plant = jwtUtil.plt;


        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime= new DateTimeCalc().getTodayDateTime();

        //get products
        List<productDaoPojo> productList = productService.getListOfProductsbysupplier(suppliercode);

        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<productDao> productDao = modelMapper.map(productList,
                new TypeToken<List<productDao>>() {
                }.getType());

        //result
        ResultsDao resultsDao = new ResultsDao();
        resultsDao.setResults(productDao);
        resultsDao.setPageNumber(1);
        resultsDao.setPageSize(productList.size());
        return new ResponseEntity<>(resultsDao, HttpStatus.OK);
    }
}
