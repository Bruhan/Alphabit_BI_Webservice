package com.owner.process.usecases.sales_order;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.owner.process.helpers.configs.LoggerConfig;
import com.owner.process.persistence.models.ShopifyConfig;
import com.owner.process.persistence.models.ShopifyItem;
import com.owner.process.usecases.sales_order.dao.get_shopify_products.Products;
import com.owner.process.usecases.sales_order.dao.get_shopify_products.ShopifyProductsMain;
import com.owner.process.usecases.sales_order.dao.shopify_location.Locations;
import com.owner.process.usecases.sales_order.dao.shopify_location.ShopifyLocationMain;
import com.owner.process.usecases.sales_order.dao.variant_id.VariantIdMain;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalesOrderModel {

    public String getCryptoValue(String value, int type) {
        String cryptoValue;
        if (type == 1) {
            cryptoValue = Base64.getEncoder().encodeToString(value.getBytes());
        } else {
            byte[] decodedBytes = Base64.getDecoder().decode(value);
            cryptoValue = new String(decodedBytes);
        }
        return cryptoValue;
    }

    private HttpHeaders createHttpHeaders(String authorize) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + authorize);
        return headers;
    }

    public String getInventoryIdFromShopifyUrl(String authorize
            , String domainName, String variantId) throws Exception {
        String theUrl = "https://" + domainName + "/admin/api/2020-07/variants/" + variantId + ".json";
        //LoggerConfig.logger.info("getInventoryIdFromShopifyUrl Started");
        LoggerConfig.logger.info("URL Name " + theUrl);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JsonNode> response;
        List<Long> location;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpHeaders headers = createHttpHeaders(authorize);
            HttpEntity<String> entity = new HttpEntity<>("body", headers);
            response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, JsonNode.class);
            //LoggerConfig.logger.info("Response value " + response);
            //System.out.println("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
        } catch (Exception e) {
            LoggerConfig.logger.error(e.getMessage());
            //throw new Exception(e.getMessage());
            return "connection not established";
        }
        // object mapper
        VariantIdMain variantIdMain = objectMapper.treeToValue(response.getBody(), VariantIdMain.class);
        //stream
        //location = shopifyLocation.getLocations().stream().map(id -> id.getId()).collect(Collectors.toList());
        return String.valueOf(variantIdMain.getVariant().getInventoryItemId());
    }

    public String updateSalesOrder(String authorize, ShopifyConfig shopifyConfig
            , ShopifyItem shopifyItem, String quantity) throws Exception {
        String theUrl = "https://" + shopifyConfig.getDomainName() + "/admin/api/2020-07/inventory_levels/set.json";
        //LoggerConfig.logger.info("updateSalesOrder Started");
        LoggerConfig.logger.info("URL Name " + theUrl);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JsonNode> response;
        List<Long> location;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpHeaders headers = createHttpHeaders(authorize);

            Map<String, Long> passParam = new HashMap<>();
            passParam.put("location_id", Long.valueOf(shopifyConfig.getLocation()));
            passParam.put("inventory_item_id", Long.valueOf(shopifyItem.getInventoryId()));
            passParam.put("available", Long.valueOf(quantity));
            HttpEntity<Map<String, Long>> entity = new HttpEntity<>(passParam, headers);
            response = restTemplate.exchange(theUrl, HttpMethod.POST, entity, JsonNode.class);
            //LoggerConfig.logger.info("Response value " + response);
            //String personResultAsJsonStr =restTemplate.postForObject(theUrl, entity, String.class);
            //System.out.println("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
        } catch (Exception e) {
            LoggerConfig.logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        //JsonNode root = response.getBody();
        return String.valueOf(response.getStatusCode());
    }

    public List<Locations> getLocationFromShopifyUrl(String authorize, String domainName) throws Exception {
        String theUrl = "https://" + domainName + "/admin/api/2020-07/locations.json";
        //LoggerConfig.logger.info("getLocationFromShopifyUrl Started");
        LoggerConfig.logger.info("URL Name " + theUrl);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JsonNode> response;
        List<Long> location;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpHeaders headers = createHttpHeaders(authorize);
            HttpEntity<String> entity = new HttpEntity<>("body", headers);
            response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, JsonNode.class);
            //LoggerConfig.logger.info("Response value " + response);
            //System.out.println("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
        } catch (Exception e) {
            LoggerConfig.logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        // object mapper
        ShopifyLocationMain shopifyLocationMain = objectMapper.treeToValue(response.getBody(), ShopifyLocationMain.class);

        //stream
        //location = shopifyLocation.getLocations().stream().map(id -> id.getId()).collect(Collectors.toList());
        return shopifyLocationMain.getLocations();
    }

    public ShopifyProductsMain getProductsFromShopifyUrl(String authorize, String domainName) throws Exception {
        String theUrl = "https://" + domainName + "/admin/api/2020-07/products.json";
        //LoggerConfig.logger.info("getProductFromShopifyUrl Started");
        LoggerConfig.logger.info("URL Name " + theUrl);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JsonNode> response;
        List<Long> location;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpHeaders headers = createHttpHeaders(authorize);
            HttpEntity<String> entity = new HttpEntity<>("body", headers);
            response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, JsonNode.class);
            //LoggerConfig.logger.info("Response value " + response);
            //System.out.println("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
        } catch (Exception e) {
            LoggerConfig.logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        // object mapper
        //ShopifyProductsMain shopifyProductsMain = objectMapper.treeToValue(response.getBody(), ShopifyProductsMain.class);
        //stream
        //location = shopifyLocation.getLocations().stream().map(id -> id.getId()).collect(Collectors.toList());
        return objectMapper.treeToValue(response.getBody(), ShopifyProductsMain.class);
    }

    public Products getProductFromShopifyUrl(String authorize, String domainName
            , String productId) throws Exception {
        String theUrl = "https://" + domainName + "/admin/api/2020-07/products/"+productId+".json";
        //LoggerConfig.logger.info("getProductFromShopifyUrl Started");
        LoggerConfig.logger.info("URL Name " + theUrl);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JsonNode> response;
        List<Long> location;
        ObjectMapper objectMapper = new ObjectMapper();
        Products shopifyProduct;
        try {
            HttpHeaders headers = createHttpHeaders(authorize);
            HttpEntity<String> entity = new HttpEntity<>("body", headers);
            response = restTemplate.exchange(theUrl, HttpMethod.GET, entity, JsonNode.class);
            //LoggerConfig.logger.info("Response value " + response);
            shopifyProduct = objectMapper.treeToValue(response.getBody().get("product"), Products.class);
            //System.out.println("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
        } catch (Exception e) {
            LoggerConfig.logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return shopifyProduct;
    }

}
