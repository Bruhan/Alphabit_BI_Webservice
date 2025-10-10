package com.owner.process.usecases.reports;

import com.owner.process.helpers.common.results.ResultsDTO;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.token.ClaimsSet;
import com.owner.process.helpers.configs.LoggerConfig;
import com.owner.process.persistence.models.*;
import com.owner.process.usecases.company.CompanyService;
import com.owner.process.usecases.hq_process.prod_bom_mst.ProdBOMMstService;
import com.owner.process.usecases.recv_det.ReceiptDetService;
import com.owner.process.usecases.sales_order.inventory_master.InvMstService;
import com.owner.process.usecases.sales_order.item_mst.ItemMstService;
import com.owner.process.usecases.sales_order.shipment_history.ShipHisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${spring.base.path}")
public class ReportController {
    @Autowired
    ItemMstService itemMstService;
    @Autowired
    ClaimsSet claimsSet;
    @Autowired
    ReceiptDetService receiptDetService;
    @Autowired
    ShipHisService shipHisService;
    @Autowired
    InvMstService invMstService;
    @Autowired
    ProdBOMMstService prodBOMMstService;
    @Autowired
    CompanyService companyService;

    @Autowired
    ReportService reportService;

    @GetMapping("/opening-closing-stock/report")
    public ResponseEntity<?> getSalesOrderByAppStatus(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        List<OpenCloseStockReportPojo>  ocsrpojolist =  new ArrayList<OpenCloseStockReportPojo>();
        List<ItemQty> receivedQtyList = receiptDetService.getItemQty(FromDate,ToDate);
        List<ItemQty> issuedQtyList = shipHisService.getItemQty(FromDate,ToDate);
        List<ItemQty> receivedQtyFromdateList = receiptDetService.getItemQtyFromDate(ToDate);
        List<ItemQty> receivedQtyToDateList = receiptDetService.getItemQtyToDate(FromDate);
        List<ItemQty> issuedQtyFromDateList = shipHisService.getItemQtyFromDate(ToDate);
        List<ItemQty> issuedQtyToDateList = shipHisService.getItemQtyToDate(FromDate);

        List<ItemQty> receivedQtyFromdateList2 = receiptDetService.getItemQtyFromDate(FromDate);
        List<ItemQty> receivedQtyToDateList2 = receiptDetService.getItemQtyToDate(ToDate);
        List<ItemQty> issuedQtyFromDateList2 = shipHisService.getItemQtyFromDate(FromDate);
        List<ItemQty> issuedQtyToDateList2 = shipHisService.getItemQtyToDate(ToDate);

        List<ItemMst> itemMstlist = itemMstService.getItemMstall();
        for (ItemMst item:itemMstlist) {
            System.out.println(item.getItem());
            if(item.getItem().equalsIgnoreCase("CHICKEN_CURRY")){
                System.out.println(item.getItem());
            }
            OpenCloseStockReportPojo ocsrpojo = new OpenCloseStockReportPojo();
            ocsrpojo.setPlant(plant);
            ocsrpojo.setItem(item.getItem());
            ocsrpojo.setItemDesc(item.getItemDescription());
            ocsrpojo.setCategory(item.getProductClassId());
            ocsrpojo.setSubCategory(item.getItemType());
            ocsrpojo.setBrand(item.getProductBrandId());
            ocsrpojo.setDepartment(item.getPrdDeptId());
            ocsrpojo.setUom(item.getInventoryUom());
            float recqty = 0.0f;
            float issqty = 0.0f;
            float orecqty = 0.0f;
            float oissqty = 0.0f;
            float crecqty = 0.0f;
            float cissqty = 0.0f;

            List<ItemQty> rqty = receivedQtyList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(rqty.size() > 0){
                ocsrpojo.setTotalReceivedQty(rqty.get(0).getQty());
                recqty = rqty.get(0).getQty();
            }else{
                ocsrpojo.setTotalReceivedQty(0.0f);
            }

            List<ItemQty> iqty = issuedQtyList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(iqty.size() > 0){
                ocsrpojo.setTotalIssuedQty(iqty.get(0).getQty());
                issqty  = iqty.get(0).getQty();
            }else{
                ocsrpojo.setTotalIssuedQty(0.0f);
            }

            List<ItemQty> orqty = receivedQtyToDateList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(orqty.size() > 0){
                orecqty  = orqty.get(0).getQty();
            }
            List<ItemQty> oiqty = issuedQtyToDateList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(oiqty.size() > 0){
                oissqty  = oiqty.get(0).getQty();
            }

            ocsrpojo.setOpeningStockQty(orecqty-oissqty);

            List<ItemQty> crqty = receivedQtyToDateList2.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(crqty.size() > 0){
                crecqty  = crqty.get(0).getQty();
            }
            List<ItemQty> ciqty = issuedQtyToDateList2.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(ciqty.size() > 0){
                cissqty  = ciqty.get(0).getQty();
            }

            ocsrpojo.setClosingStock(crecqty-cissqty);

            float price =0.0f;
            String avgcost ="0.0";
            if(item.getIsComPro() == null) {
                avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                    price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                } else {
                    price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                }
            }else{
                if(item.getIsComPro() == 1) {
                    List<ProdBOMMst> probommstlist = prodBOMMstService.getbyPitemAndBomType(item.getItem(), "kit");
                    float itemprice = 0;
                    for (ProdBOMMst prodBOMMst : probommstlist) {
                            String cavgcost = itemMstService.getAvgCost(prodBOMMst.getCitem(), prodBOMMst.getChildUom());
                            itemprice = itemprice + (Float.valueOf(cavgcost) * prodBOMMst.getQty().floatValue());
                    }
                    avgcost = String.valueOf(itemprice);
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(itemprice) + ((Float.valueOf(itemprice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(itemprice) + Float.valueOf(item.getIncPrice());
                    }
                }else{
                    avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                    }
                }
            }


            ocsrpojo.setAverageCost(Float.valueOf(avgcost));
            ocsrpojo.setPrice(price);

            ocsrpojo.setTotalCost(Float.valueOf(avgcost)*recqty);
            ocsrpojo.setTotalPrice(price*issqty);

            List<ItemQty> lastrqty = receivedQtyFromdateList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(lastrqty.size() > 0){
                ocsrpojo.setLaterReceveivedQty(lastrqty.get(0).getQty());
            }else{
                ocsrpojo.setLaterReceveivedQty(0.0f);
            }

            List<ItemQty> lastiqty = issuedQtyFromDateList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(lastiqty.size() > 0){
                ocsrpojo.setLaterIssuedQty(lastiqty.get(0).getQty());
            }else{
                ocsrpojo.setLaterIssuedQty(0.0f);
            }

            double sih = invMstService.getTQtyByItem(item.getItem());
            System.out.println(sih);
            ocsrpojo.setStockOnHand(sih);

            ocsrpojolist.add(ocsrpojo);
        }

        //result
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults(ocsrpojolist);
        resultsDTO.setPageNumber(1);
        resultsDTO.setPageSize(1);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @GetMapping("/opening-stock/sumbak")
    public ResponseEntity<?> getopeningstockbak(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        List<ItemQty> receivedQtyToDateList = receiptDetService.getItemQtyToDate(FromDate);
        List<ItemQty> issuedQtyToDateList = shipHisService.getItemQtyToDate(FromDate);
        List<ItemMst> itemMstlist = itemMstService.getItemMstall();
        float totalAmount =0.0f;
        for (ItemMst item:itemMstlist) {
            float orecqty = 0.0f;
            float oissqty = 0.0f;
            List<ItemQty> orqty = receivedQtyToDateList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(orqty.size() > 0){
                orecqty  = orqty.get(0).getQty();
            }
            List<ItemQty> oiqty = issuedQtyToDateList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(oiqty.size() > 0){
                oissqty  = oiqty.get(0).getQty();
            }

            /*float price =0.0f;
            String avgcost ="0.0";
            if(item.getIsComPro() == null) {
                avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                    price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                } else {
                    price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                }
            }else{
                if(item.getIsComPro() == 1) {
                    List<ProdBOMMst> probommstlist = prodBOMMstService.getbyPitemAndBomType(item.getItem(), "kit");
                    float itemprice = 0;
                    for (ProdBOMMst prodBOMMst : probommstlist) {
                        String cavgcost = itemMstService.getAvgCost(prodBOMMst.getCitem(), prodBOMMst.getChildUom());
                        itemprice = itemprice + (Float.valueOf(cavgcost) * prodBOMMst.getQty().floatValue());
                    }
                    avgcost = String.valueOf(itemprice);
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(itemprice) + ((Float.valueOf(itemprice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(itemprice) + Float.valueOf(item.getIncPrice());
                    }
                }else{
                    avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                    }
                }
            }*/

            String UnitPrice = "0";
            float UnitCost = 0;
            float itemUnitPrice = 0;
            int SalesPriceCodition = itemMstService.getSalesPriceCodition(plant, "Outbound Order");
            if (item.getIsComPro() == null) {
                if (SalesPriceCodition == 1) {
                    UnitPrice = itemMstService.getorderPricOfItem(item.getItem());
                    UnitCost = Float.valueOf(itemMstService.getorderCostOfItem(item.getItem()));
                    itemUnitPrice = Float.valueOf(UnitPrice);
                } else if (SalesPriceCodition == 2) {
                    UnitPrice = itemMstService.getListPriceOfItem(item.getItem());
                    UnitCost = Float.valueOf(itemMstService.getListCostOfItem(item.getItem()));
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        itemUnitPrice = Float.valueOf(UnitPrice) + ((Float.valueOf(UnitPrice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        itemUnitPrice = Float.valueOf(UnitPrice) + Float.valueOf(item.getIncPrice());
                    }
                } else {
                    UnitPrice = itemMstService.getAvgCost(item.getItem(), item.getPurchaseUom());
                    UnitCost = Float.valueOf(itemMstService.getAvgCost(item.getItem(), item.getPurchaseUom()));
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        itemUnitPrice = Float.valueOf(UnitPrice) + ((Float.valueOf(UnitPrice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        itemUnitPrice = Float.valueOf(UnitPrice) + Float.valueOf(item.getIncPrice());
                    }
                }
            } else {
                if (item.getIsComPro() == 1) {
                    List<ProdBOMMst> probommstlist = prodBOMMstService.getbyPitemAndBomType(item.getItem(), "kit");
                    for (ProdBOMMst prodBOMMst : probommstlist) {
                        ItemMst BOMitemmst = itemMstService.getItemMstPk(prodBOMMst.getCitem());
                        if (BOMitemmst.getNonStackFlag().equalsIgnoreCase("Y")) {
                            String listcost = itemMstService.getListPriceOfItem(prodBOMMst.getCitem());
                            UnitCost = UnitCost + Float.valueOf(itemMstService.getListCostOfItem(item.getItem()));
                            itemUnitPrice = itemUnitPrice + (Float.valueOf(listcost) * prodBOMMst.getQty().floatValue());
                        } else {


                            if (SalesPriceCodition == 1) {
                                String ordercost = itemMstService.getorderPricOfItem(prodBOMMst.getCitem());
                                UnitCost = UnitCost + Float.valueOf(itemMstService.getorderCostOfItem(item.getItem()));
                                itemUnitPrice = itemUnitPrice + (Float.valueOf(ordercost) * prodBOMMst.getQty().floatValue());
                            } else if (SalesPriceCodition == 2) {
                                String listcost = itemMstService.getListPriceOfItem(prodBOMMst.getCitem());
                                UnitCost = UnitCost + Float.valueOf(itemMstService.getListCostOfItem(item.getItem()));
                                itemUnitPrice = itemUnitPrice + (Float.valueOf(listcost) * prodBOMMst.getQty().floatValue());

                                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                                    itemUnitPrice = Float.valueOf(itemUnitPrice) + ((Float.valueOf(itemUnitPrice) / 100) * Float.valueOf(item.getIncPrice()));
                                } else {
                                    itemUnitPrice = Float.valueOf(itemUnitPrice) + Float.valueOf(item.getIncPrice());
                                }
                            } else {
                                String avgcost = itemMstService.getAvgCost(prodBOMMst.getCitem(), prodBOMMst.getChildUom());
                                UnitCost = UnitCost + Float.valueOf(avgcost);
                                itemUnitPrice = itemUnitPrice + (Float.valueOf(avgcost) * prodBOMMst.getQty().floatValue());

                                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                                    itemUnitPrice = Float.valueOf(itemUnitPrice) + ((Float.valueOf(itemUnitPrice) / 100) * Float.valueOf(item.getIncPrice()));
                                } else {
                                    itemUnitPrice = Float.valueOf(itemUnitPrice) + Float.valueOf(item.getIncPrice());
                                }
                            }


                        }
                    }


                }else{
                    if (SalesPriceCodition == 1) {
                        UnitPrice = itemMstService.getorderPricOfItem(item.getItem());
                        UnitCost = Float.valueOf(itemMstService.getorderCostOfItem(item.getItem()));
                        itemUnitPrice = Float.valueOf(UnitPrice);
                    } else if (SalesPriceCodition == 2) {
                        UnitPrice = itemMstService.getListPriceOfItem(item.getItem());
                        UnitCost = Float.valueOf(itemMstService.getListCostOfItem(item.getItem()));
                        if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                            itemUnitPrice = Float.valueOf(UnitPrice) + ((Float.valueOf(UnitPrice) / 100) * Float.valueOf(item.getIncPrice()));
                        } else {
                            itemUnitPrice = Float.valueOf(UnitPrice) + Float.valueOf(item.getIncPrice());
                        }
                    } else {
                        UnitPrice = itemMstService.getAvgCost(item.getItem(), item.getPurchaseUom());
                        UnitCost = Float.valueOf(itemMstService.getAvgCost(item.getItem(), item.getPurchaseUom()));
                        if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                            itemUnitPrice = Float.valueOf(UnitPrice) + ((Float.valueOf(UnitPrice) / 100) * Float.valueOf(item.getIncPrice()));
                        } else {
                            itemUnitPrice = Float.valueOf(UnitPrice) + Float.valueOf(item.getIncPrice());
                        }
                    }
                }
            }

            totalAmount = totalAmount+((orecqty-oissqty)*UnitCost);
        }

        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/opening-stock/sum")
    public ResponseEntity<?> getopeningstock(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        double totalAmount = reportService.getOpeningbalancetotal(plant,FromDate);
        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/closing-stock/sumbak")
    public ResponseEntity<?> getClosingStockbak(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();

        String dt = ToDate;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dt));
        c.add(Calendar.DATE, 1);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date
        List<ItemQty> receivedQtyToDateList2 = receiptDetService.getItemQtyToDate(dt);
        List<ItemQty> issuedQtyToDateList2 = shipHisService.getItemQtyToDate(dt);
        float totalAmount =0.0f;
        List<ItemMst> itemMstlist = itemMstService.getItemMstall();
        for (ItemMst item:itemMstlist) {
            float crecqty = 0.0f;
            float cissqty = 0.0f;


            List<ItemQty> crqty = receivedQtyToDateList2.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(crqty.size() > 0){
                crecqty  = crqty.get(0).getQty();
            }
            List<ItemQty> ciqty = issuedQtyToDateList2.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(ciqty.size() > 0){
                cissqty  = ciqty.get(0).getQty();
            }

            /*float price =0.0f;
            String avgcost ="0.0";
            if(item.getIsComPro() == null) {
                avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                    price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                } else {
                    price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                }
            }else{
                if(item.getIsComPro() == 1) {
                    List<ProdBOMMst> probommstlist = prodBOMMstService.getbyPitemAndBomType(item.getItem(), "kit");
                    float itemprice = 0;
                    for (ProdBOMMst prodBOMMst : probommstlist) {
                        String cavgcost = itemMstService.getAvgCost(prodBOMMst.getCitem(), prodBOMMst.getChildUom());
                        itemprice = itemprice + (Float.valueOf(cavgcost) * prodBOMMst.getQty().floatValue());
                    }
                    avgcost = String.valueOf(itemprice);
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(itemprice) + ((Float.valueOf(itemprice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(itemprice) + Float.valueOf(item.getIncPrice());
                    }
                }else{
                    avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                    }
                }
            }*/

            String UnitPrice = "0";
            float UnitCost = 0;
            float itemUnitPrice = 0;
            int SalesPriceCodition = itemMstService.getSalesPriceCodition(plant, "Outbound Order");
            if (item.getIsComPro() == null) {
                if (SalesPriceCodition == 1) {
                    UnitPrice = itemMstService.getorderPricOfItem(item.getItem());
                    UnitCost = Float.valueOf(itemMstService.getorderCostOfItem(item.getItem()));
                    itemUnitPrice = Float.valueOf(UnitPrice);
                } else if (SalesPriceCodition == 2) {
                    UnitPrice = itemMstService.getListPriceOfItem(item.getItem());
                    UnitCost = Float.valueOf(itemMstService.getListCostOfItem(item.getItem()));
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        itemUnitPrice = Float.valueOf(UnitPrice) + ((Float.valueOf(UnitPrice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        itemUnitPrice = Float.valueOf(UnitPrice) + Float.valueOf(item.getIncPrice());
                    }
                } else {
                    UnitPrice = itemMstService.getAvgCost(item.getItem(), item.getPurchaseUom());
                    UnitCost = Float.valueOf(itemMstService.getAvgCost(item.getItem(), item.getPurchaseUom()));
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        itemUnitPrice = Float.valueOf(UnitPrice) + ((Float.valueOf(UnitPrice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        itemUnitPrice = Float.valueOf(UnitPrice) + Float.valueOf(item.getIncPrice());
                    }
                }
            } else {
                if (item.getIsComPro() == 1) {
                    List<ProdBOMMst> probommstlist = prodBOMMstService.getbyPitemAndBomType(item.getItem(), "kit");
                    for (ProdBOMMst prodBOMMst : probommstlist) {
                        ItemMst BOMitemmst = itemMstService.getItemMstPk(prodBOMMst.getCitem());
                        if (BOMitemmst.getNonStackFlag().equalsIgnoreCase("Y")) {
                            String listcost = itemMstService.getListPriceOfItem(prodBOMMst.getCitem());
                            UnitCost = UnitCost + Float.valueOf(itemMstService.getListCostOfItem(item.getItem()));
                            itemUnitPrice = itemUnitPrice + (Float.valueOf(listcost) * prodBOMMst.getQty().floatValue());
                        } else {


                            if (SalesPriceCodition == 1) {
                                String ordercost = itemMstService.getorderPricOfItem(prodBOMMst.getCitem());
                                UnitCost = UnitCost + Float.valueOf(itemMstService.getorderCostOfItem(item.getItem()));
                                itemUnitPrice = itemUnitPrice + (Float.valueOf(ordercost) * prodBOMMst.getQty().floatValue());
                            } else if (SalesPriceCodition == 2) {
                                String listcost = itemMstService.getListPriceOfItem(prodBOMMst.getCitem());
                                UnitCost = UnitCost + Float.valueOf(itemMstService.getListCostOfItem(item.getItem()));
                                itemUnitPrice = itemUnitPrice + (Float.valueOf(listcost) * prodBOMMst.getQty().floatValue());

                                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                                    itemUnitPrice = Float.valueOf(itemUnitPrice) + ((Float.valueOf(itemUnitPrice) / 100) * Float.valueOf(item.getIncPrice()));
                                } else {
                                    itemUnitPrice = Float.valueOf(itemUnitPrice) + Float.valueOf(item.getIncPrice());
                                }
                            } else {
                                String avgcost = itemMstService.getAvgCost(prodBOMMst.getCitem(), prodBOMMst.getChildUom());
                                UnitCost = UnitCost + Float.valueOf(avgcost);
                                itemUnitPrice = itemUnitPrice + (Float.valueOf(avgcost) * prodBOMMst.getQty().floatValue());

                                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                                    itemUnitPrice = Float.valueOf(itemUnitPrice) + ((Float.valueOf(itemUnitPrice) / 100) * Float.valueOf(item.getIncPrice()));
                                } else {
                                    itemUnitPrice = Float.valueOf(itemUnitPrice) + Float.valueOf(item.getIncPrice());
                                }
                            }


                        }
                    }


                }else{
                    if (SalesPriceCodition == 1) {
                        UnitPrice = itemMstService.getorderPricOfItem(item.getItem());
                        UnitCost = Float.valueOf(itemMstService.getorderCostOfItem(item.getItem()));
                        itemUnitPrice = Float.valueOf(UnitPrice);
                    } else if (SalesPriceCodition == 2) {
                        UnitPrice = itemMstService.getListPriceOfItem(item.getItem());
                        UnitCost = Float.valueOf(itemMstService.getListCostOfItem(item.getItem()));
                        if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                            itemUnitPrice = Float.valueOf(UnitPrice) + ((Float.valueOf(UnitPrice) / 100) * Float.valueOf(item.getIncPrice()));
                        } else {
                            itemUnitPrice = Float.valueOf(UnitPrice) + Float.valueOf(item.getIncPrice());
                        }
                    } else {
                        UnitPrice = itemMstService.getAvgCost(item.getItem(), item.getPurchaseUom());
                        UnitCost = Float.valueOf(itemMstService.getAvgCost(item.getItem(), item.getPurchaseUom()));
                        if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                            itemUnitPrice = Float.valueOf(UnitPrice) + ((Float.valueOf(UnitPrice) / 100) * Float.valueOf(item.getIncPrice()));
                        } else {
                            itemUnitPrice = Float.valueOf(UnitPrice) + Float.valueOf(item.getIncPrice());
                        }
                    }
                }
            }

            totalAmount = totalAmount+((crecqty-cissqty)*UnitCost);

        }

        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/closing-stock/sum")
    public ResponseEntity<?> getClosingStock(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();

        String dt = ToDate;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dt));
        c.add(Calendar.DATE, 1);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date
        double totalAmount = reportService.getOpeningbalancetotal(plant,dt);

        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/issued-qty/sum")
    public ResponseEntity<?> getIssuedQty(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate, @RequestParam(value = "customerId", required = false) String customerId) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        List<ItemQtyPrice> issuedQtyList = shipHisService.getItemQtyWithoutKit(FromDate,ToDate);
        if(customerId == null){

        }else{
            if(customerId.length() > 0){
                issuedQtyList = shipHisService.getItemQtyByCustId(FromDate,ToDate,customerId);
            }
        }
        List<ItemMst> itemMstlist = itemMstService.getItemMstall();
        float totalAmount = 0.0f;
        for (ItemMst item:itemMstlist) {
            float issqty = 0.0f;
            float price =0.0f;
            List<ItemQtyPrice> iqty = issuedQtyList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(iqty.size() > 0){
                issqty  = iqty.get(0).getQty();
                price = iqty.get(0).getUnitPrice();
            }


           /* float price =0.0f;
            String avgcost ="0.0";
            if(item.getIsComPro() == null) {
                avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                    price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                } else {
                    price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                }
            }else{
                if(item.getIsComPro() == 1) {
                    List<ProdBOMMst> probommstlist = prodBOMMstService.getbyPitemAndBomType(item.getItem(), "kit");
                    float itemprice = 0;
                    for (ProdBOMMst prodBOMMst : probommstlist) {
                        String cavgcost = itemMstService.getAvgCost(prodBOMMst.getCitem(), prodBOMMst.getChildUom());
                        itemprice = itemprice + (Float.valueOf(cavgcost) * prodBOMMst.getQty().floatValue());
                    }
                    avgcost = String.valueOf(itemprice);
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(itemprice) + ((Float.valueOf(itemprice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(itemprice) + Float.valueOf(item.getIncPrice());
                    }
                }else{
                    avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                    }
                }
            }*/




           /* if(issqty > 0) {
                System.out.println("--------------");
                System.out.println(item.getItem());
                System.out.println(totalAmount);
                System.out.println(price);
                System.out.println("--------------");
            }*/

            /*totalAmount = totalAmount + (price*issqty);*/
            totalAmount = totalAmount + (price);
        }

        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/issued-qty/report")
    public ResponseEntity<?> getIssuedQtyCustReport(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate, @RequestParam(value = "customerId", required = false) String customerId) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        String numberOfDecimal = companyService.getNumberOfDecimal(plant);
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        List<OutletIssuedReportPojo>  ocsrpojolist =  new ArrayList<OutletIssuedReportPojo>();
        List<ItemQtyPrice> issuedQtyList = shipHisService.getItemQtyWithoutKit(FromDate,ToDate);
        if(customerId == null){

        }else{
            if(customerId.length() > 0){
                issuedQtyList = shipHisService.getItemQtyByCustId(FromDate,ToDate,customerId);
            }
        }
        //List<ItemMst> itemMstlist = itemMstService.getItemMstall();
        for (ItemQtyPrice iqty:issuedQtyList) {
            ItemMst item =  itemMstService.getItemMstPk(iqty.getItem());
            OutletIssuedReportPojo ocsrpojo = new OutletIssuedReportPojo();
            ocsrpojo.setPlant(plant);
            ocsrpojo.setItem(item.getItem());
            ocsrpojo.setItemDesc(item.getItemDescription());
            ocsrpojo.setCategory(item.getProductClassId());
            ocsrpojo.setSubCategory(item.getItemType());
            ocsrpojo.setBrand(item.getProductBrandId());
            ocsrpojo.setDepartment(item.getPrdDeptId());
            ocsrpojo.setUom(item.getInventoryUom());
            float issqty = 0.0f;

            //List<ItemQty> iqty = issuedQtyList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            //if(iqty.size() > 0){
            ocsrpojo.setTotalIssuedQty(iqty.getQty());
            issqty  = iqty.getQty();
           /* }else{
                ocsrpojo.setTotalIssuedQty(0.0f);
            }*/

            float price =0.0f;
            String avgcost ="0.0";
            if(item.getIsComPro() == null) {
                avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                    price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                } else {
                    price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                }
            }else{
                if(item.getIsComPro() == 1) {
                    List<ProdBOMMst> probommstlist = prodBOMMstService.getbyPitemAndBomType(item.getItem(), "kit");
                    float itemprice = 0;
                    for (ProdBOMMst prodBOMMst : probommstlist) {
                        String cavgcost = itemMstService.getAvgCost(prodBOMMst.getCitem(), prodBOMMst.getChildUom());
                        itemprice = itemprice + (Float.valueOf(cavgcost) * prodBOMMst.getQty().floatValue());
                    }
                    avgcost = String.valueOf(itemprice);
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(itemprice) + ((Float.valueOf(itemprice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(itemprice) + Float.valueOf(item.getIncPrice());
                    }
                }else{
                    avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                    }
                }
            }

            ocsrpojo.setPrice((float)roundAvoid(price,Integer.valueOf(numberOfDecimal)));
            ocsrpojo.setTotalPrice((float)roundAvoid((price*issqty),Integer.valueOf(numberOfDecimal)));
            ocsrpojolist.add(ocsrpojo);
        }

        //result
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults(ocsrpojolist);
        resultsDTO.setPageNumber(1);
        resultsDTO.setPageSize(1);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @GetMapping("/recieved-qty/sum")
    public ResponseEntity<?> getRecivedQty(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate, @RequestParam(value = "SupplierName", required = false) String SupplierName) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        List<ItemQtyPrice> recivedQtyList = receiptDetService.getItemQtyCost(FromDate,ToDate);
        if(SupplierName == null){

        }else{
            if(SupplierName.length() > 0){
                recivedQtyList = receiptDetService.getItemQtyBySupId(FromDate,ToDate,SupplierName);
            }
        }
        List<ItemMst> itemMstlist = itemMstService.getItemMstall();
        float totalAmount = 0.0f;
        for (ItemMst item:itemMstlist) {
            float issqty = 0.0f;
            float cost =0.0f;
            List<ItemQtyPrice> iqty = recivedQtyList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(iqty.size() > 0){
                issqty  = iqty.get(0).getQty();
                cost = iqty.get(0).getUnitPrice();
            }

           /* float price =0.0f;
            String avgcost ="0.0";
            if(item.getIsComPro() == null) {
                avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                    price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                } else {
                    price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                }
            }else{
                if(item.getIsComPro() == 1) {
                    List<ProdBOMMst> probommstlist = prodBOMMstService.getbyPitemAndBomType(item.getItem(), "kit");
                    float itemprice = 0;
                    for (ProdBOMMst prodBOMMst : probommstlist) {
                        String cavgcost = itemMstService.getAvgCost(prodBOMMst.getCitem(), prodBOMMst.getChildUom());
                        itemprice = itemprice + (Float.valueOf(cavgcost) * prodBOMMst.getQty().floatValue());
                    }
                    avgcost = String.valueOf(itemprice);
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(itemprice) + ((Float.valueOf(itemprice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(itemprice) + Float.valueOf(item.getIncPrice());
                    }
                }else{
                    avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                    }
                }
            }


            totalAmount = totalAmount + (Float.valueOf(avgcost)*issqty);*/
            totalAmount = totalAmount + (cost);
        }

        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/recieved-qty/report")
    public ResponseEntity<?> getRecievedQtyCustReport(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate, @RequestParam(value = "suppliername", required = false) String supplierName) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        String numberOfDecimal = companyService.getNumberOfDecimal(plant);
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        List<OutletIssuedReportPojo>  ocsrpojolist =  new ArrayList<OutletIssuedReportPojo>();
        List<ItemQtyPrice> recivedQtyList = receiptDetService.getItemQtyCost(FromDate,ToDate);
        if(supplierName == null){

        }else{
            if(supplierName.length() > 0){
                recivedQtyList = receiptDetService.getItemQtyBySupId(FromDate,ToDate,supplierName);
            }
        }
        //List<ItemMst> itemMstlist = itemMstService.getItemMstall();
        for (ItemQtyPrice iqty:recivedQtyList) {
            ItemMst item =  itemMstService.getItemMstPk(iqty.getItem());
            OutletIssuedReportPojo ocsrpojo = new OutletIssuedReportPojo();
            ocsrpojo.setPlant(plant);
            ocsrpojo.setItem(item.getItem());
            ocsrpojo.setItemDesc(item.getItemDescription());
            ocsrpojo.setCategory(item.getProductClassId());
            ocsrpojo.setSubCategory(item.getItemType());
            ocsrpojo.setBrand(item.getProductBrandId());
            ocsrpojo.setDepartment(item.getPrdDeptId());
            ocsrpojo.setUom(item.getInventoryUom());
            float issqty = 0.0f;

            //List<ItemQty> iqty = issuedQtyList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            //if(iqty.size() > 0){
            ocsrpojo.setTotalIssuedQty(iqty.getQty());
            issqty  = iqty.getQty();
           /* }else{
                ocsrpojo.setTotalIssuedQty(0.0f);
            }*/

            float price =0.0f;
            String avgcost ="0.0";
            if(item.getIsComPro() == null) {
                avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                    price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                } else {
                    price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                }
            }else{
                if(item.getIsComPro() == 1) {
                    List<ProdBOMMst> probommstlist = prodBOMMstService.getbyPitemAndBomType(item.getItem(), "kit");
                    float itemprice = 0;
                    for (ProdBOMMst prodBOMMst : probommstlist) {
                        String cavgcost = itemMstService.getAvgCost(prodBOMMst.getCitem(), prodBOMMst.getChildUom());
                        itemprice = itemprice + (Float.valueOf(cavgcost) * prodBOMMst.getQty().floatValue());
                    }
                    avgcost = String.valueOf(itemprice);
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(itemprice) + ((Float.valueOf(itemprice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(itemprice) + Float.valueOf(item.getIncPrice());
                    }
                }else{
                    avgcost = itemMstService.getAvgCostpurchase(item.getItem(), item.getInventoryUom());

                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                    }
                }
            }

            ocsrpojo.setPrice((float)roundAvoid(Float.valueOf(avgcost),Integer.valueOf(numberOfDecimal)));
            ocsrpojo.setTotalPrice((float)roundAvoid((Float.valueOf(avgcost)*issqty),Integer.valueOf(numberOfDecimal)));
            ocsrpojolist.add(ocsrpojo);
        }

        //result
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults(ocsrpojolist);
        resultsDTO.setPageNumber(1);
        resultsDTO.setPageSize(1);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }


    @GetMapping("/recieved-qty/sum-with-tax")
    public ResponseEntity<?> getRecivedQtySumWithTax(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        String numberOfDecimal = companyService.getNumberOfDecimal(plant);
        String totalpurchase = receiptDetService.getTotalPurchaseCostWithTax(FromDate,ToDate);
        double totalAmount=roundAvoid(Double.valueOf(totalpurchase),Integer.valueOf(numberOfDecimal));
        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/recieved-qty/supplir-sum-with-tax")
    public ResponseEntity<?> getRecivedSupQtySumWithTax(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate, @RequestParam(value = "suppliername") String supplierName) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        String numberOfDecimal = companyService.getNumberOfDecimal(plant);
        String totalpurchase = receiptDetService.getTotalPurCostOfSupplierWithTax(FromDate,ToDate,supplierName);
        double totalAmount=roundAvoid(Double.valueOf(totalpurchase),Integer.valueOf(numberOfDecimal));
        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/recieved-qty/supplier-report-with-tax")
    public ResponseEntity<?> getRecievedQtyCustReporttax(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate, @RequestParam(value = "suppliername") String supplierName) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        String numberOfDecimal = companyService.getNumberOfDecimal(plant);
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        List<PurchaseSummaryWithTax> recivedQtyList = receiptDetService.getPurCostOfSupplierWithTax(FromDate,ToDate,supplierName);
        String totalsubtotal = receiptDetService.getTotalSubOfSupplierWithTax(FromDate,ToDate,supplierName);
        String totaltax = receiptDetService.getTotalTaxOfSupplierWithTax(FromDate,ToDate,supplierName);
        String totalamount = receiptDetService.getTotalPurCostOfSupplierWithTax(FromDate,ToDate,supplierName);
        //result
        ResultsDTO resultsDTO = new ResultsDTO();
        Map<String, Object> result = new HashMap<>();
        result.put("total subtotal", totalsubtotal);
        result.put("total tax", totaltax);
        result.put("total amount", totalamount);
        result.put("list", recivedQtyList);
        resultsDTO.setResults(result);
        resultsDTO.setPageNumber(1);
        resultsDTO.setPageSize(1);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @GetMapping("/issued-qty/sum-with-tax")
    public ResponseEntity<?> getIssuedQtySumWithTax(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        String numberOfDecimal = companyService.getNumberOfDecimal(plant);
        String totalsales = shipHisService.getTotalSaleswithtax(FromDate,ToDate);
        double totalAmount=roundAvoid(Double.valueOf(totalsales),Integer.valueOf(numberOfDecimal));
        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/issued-qty/customer-sum-with-tax")
    public ResponseEntity<?> getIssuedSupQtySumWithTax(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate, @RequestParam(value = "customerId") String customerId) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        String numberOfDecimal = companyService.getNumberOfDecimal(plant);
        String totalsales = shipHisService.getTotalCustSalesWithTax(FromDate,ToDate,customerId);
        double totalAmount=roundAvoid(Double.valueOf(totalsales),Integer.valueOf(numberOfDecimal));
        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/issued-qty/customer-report-with-tax")
    public ResponseEntity<?> getIssuedQtyCustReporttax(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate, @RequestParam(value = "customerId") String customerId) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        String numberOfDecimal = companyService.getNumberOfDecimal(plant);
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        List<SalesSummaryWithTax> issuedQtyList = shipHisService.getSalesDetailWithTax(FromDate,ToDate,customerId);
        String totalsubtotal = shipHisService.getTotalSalesSubTotalWithTax(FromDate,ToDate,customerId);
        String totaltax = shipHisService.getTotalSalesTaxTotalWithTax(FromDate,ToDate,customerId);
        String totalamount = shipHisService.getTotalCustSalesWithTax(FromDate,ToDate,customerId);
        //result
        ResultsDTO resultsDTO = new ResultsDTO();
        Map<String, Object> result = new HashMap<>();
        result.put("total subtotal", totalsubtotal);
        result.put("total tax", totaltax);
        result.put("total amount", totalamount);
        result.put("list", issuedQtyList);
        resultsDTO.setResults(result);
        resultsDTO.setPageNumber(1);
        resultsDTO.setPageSize(1);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    public static double roundAvoid(double value, int places) {


        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    @GetMapping("/pos-sales/sum")
    public ResponseEntity<?> getPosSalesSum(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        List<ItemQtyPrice> issuedQtyList = shipHisService.getItemQtyWithoutKitpos(FromDate,ToDate);
       /* if(terminal == null){

        }else{
            if(terminal.length() > 0){
                issuedQtyList = shipHisService.getItemQtyByterminalIdpos(FromDate,ToDate,terminal);
            }
        }*/
        List<ItemMst> itemMstlist = itemMstService.getItemMstall();
        float totalAmount = 0.0f;
        for (ItemMst item:itemMstlist) {
            float issqty = 0.0f;
            float price =0.0f;
            List<ItemQtyPrice> iqty = issuedQtyList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(iqty.size() > 0){
                issqty  = iqty.get(0).getQty();
                price = iqty.get(0).getUnitPrice();
            }


           /* float price =0.0f;
            String avgcost ="0.0";
            if(item.getIsComPro() == null) {
                avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                    price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                } else {
                    price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                }
            }else{
                if(item.getIsComPro() == 1) {
                    List<ProdBOMMst> probommstlist = prodBOMMstService.getbyPitemAndBomType(item.getItem(), "kit");
                    float itemprice = 0;
                    for (ProdBOMMst prodBOMMst : probommstlist) {
                        String cavgcost = itemMstService.getAvgCost(prodBOMMst.getCitem(), prodBOMMst.getChildUom());
                        itemprice = itemprice + (Float.valueOf(cavgcost) * prodBOMMst.getQty().floatValue());
                    }
                    avgcost = String.valueOf(itemprice);
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(itemprice) + ((Float.valueOf(itemprice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(itemprice) + Float.valueOf(item.getIncPrice());
                    }
                }else{
                    avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                    }
                }
            }*/




           /* if(issqty > 0) {
                System.out.println("--------------");
                System.out.println(item.getItem());
                System.out.println(totalAmount);
                System.out.println(price);
                System.out.println("--------------");
            }*/

            /*totalAmount = totalAmount + (price*issqty);*/
            totalAmount = totalAmount + (price);
        }

        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/pos-sales-outlet/sum")
    public ResponseEntity<?> getPosSalesOutletSum(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate,@RequestParam String outlet) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
       // List<ItemQtyPrice> issuedQtyList = shipHisService.getItemQtyWithoutKitpos(FromDate,ToDate);
       /* if(terminal == null){

        }else{
            if(terminal.length() > 0){
                issuedQtyList = shipHisService.getItemQtyByterminalIdpos(FromDate,ToDate,terminal);
            }
        }*/
        List<ItemQtyPrice> issuedQtyList = shipHisService.getItemQtyByOutletIdpos(FromDate,ToDate,outlet);
        List<ItemMst> itemMstlist = itemMstService.getItemMstall();
        float totalAmount = 0.0f;
        for (ItemMst item:itemMstlist) {
            float issqty = 0.0f;
            float price =0.0f;
            List<ItemQtyPrice> iqty = issuedQtyList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(iqty.size() > 0){
                issqty  = iqty.get(0).getQty();
                price = iqty.get(0).getUnitPrice();
            }


           /* float price =0.0f;
            String avgcost ="0.0";
            if(item.getIsComPro() == null) {
                avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                    price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                } else {
                    price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                }
            }else{
                if(item.getIsComPro() == 1) {
                    List<ProdBOMMst> probommstlist = prodBOMMstService.getbyPitemAndBomType(item.getItem(), "kit");
                    float itemprice = 0;
                    for (ProdBOMMst prodBOMMst : probommstlist) {
                        String cavgcost = itemMstService.getAvgCost(prodBOMMst.getCitem(), prodBOMMst.getChildUom());
                        itemprice = itemprice + (Float.valueOf(cavgcost) * prodBOMMst.getQty().floatValue());
                    }
                    avgcost = String.valueOf(itemprice);
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(itemprice) + ((Float.valueOf(itemprice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(itemprice) + Float.valueOf(item.getIncPrice());
                    }
                }else{
                    avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                    }
                }
            }*/




           /* if(issqty > 0) {
                System.out.println("--------------");
                System.out.println(item.getItem());
                System.out.println(totalAmount);
                System.out.println(price);
                System.out.println("--------------");
            }*/

            /*totalAmount = totalAmount + (price*issqty);*/
            totalAmount = totalAmount + (price);
        }

        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/pos-sales-outlet-terminal/sum")
    public ResponseEntity<?> getPosSalesOutletterminalSum(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate,@RequestParam String outlet,@RequestParam String terminal) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        // List<ItemQtyPrice> issuedQtyList = shipHisService.getItemQtyWithoutKitpos(FromDate,ToDate);
       /* if(terminal == null){

        }else{
            if(terminal.length() > 0){
                issuedQtyList = shipHisService.getItemQtyByterminalIdpos(FromDate,ToDate,terminal);
            }
        }*/
        List<ItemQtyPrice> issuedQtyList = shipHisService.getItemQtyByOutletterminalIdpos(FromDate,ToDate,outlet,terminal);
        List<ItemMst> itemMstlist = itemMstService.getItemMstall();
        float totalAmount = 0.0f;
        for (ItemMst item:itemMstlist) {
            float issqty = 0.0f;
            float price =0.0f;
            List<ItemQtyPrice> iqty = issuedQtyList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(iqty.size() > 0){
                issqty  = iqty.get(0).getQty();
                price = iqty.get(0).getUnitPrice();
            }


           /* float price =0.0f;
            String avgcost ="0.0";
            if(item.getIsComPro() == null) {
                avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                    price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                } else {
                    price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                }
            }else{
                if(item.getIsComPro() == 1) {
                    List<ProdBOMMst> probommstlist = prodBOMMstService.getbyPitemAndBomType(item.getItem(), "kit");
                    float itemprice = 0;
                    for (ProdBOMMst prodBOMMst : probommstlist) {
                        String cavgcost = itemMstService.getAvgCost(prodBOMMst.getCitem(), prodBOMMst.getChildUom());
                        itemprice = itemprice + (Float.valueOf(cavgcost) * prodBOMMst.getQty().floatValue());
                    }
                    avgcost = String.valueOf(itemprice);
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(itemprice) + ((Float.valueOf(itemprice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(itemprice) + Float.valueOf(item.getIncPrice());
                    }
                }else{
                    avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                    }
                }
            }*/




           /* if(issqty > 0) {
                System.out.println("--------------");
                System.out.println(item.getItem());
                System.out.println(totalAmount);
                System.out.println(price);
                System.out.println("--------------");
            }*/

            /*totalAmount = totalAmount + (price*issqty);*/
            totalAmount = totalAmount + (price);
        }

        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/pos-sales/report")
    public ResponseEntity<?> getPosSalesReport(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate,@RequestParam String outlet,@RequestParam String terminal ) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        String numberOfDecimal = companyService.getNumberOfDecimal(plant);
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        List<OutletIssuedReportPojo>  ocsrpojolist =  new ArrayList<OutletIssuedReportPojo>();
        List<ItemQtyPrice> issuedQtyList = shipHisService.getItemQtyByOutletterminalIdpos(FromDate,ToDate,outlet,terminal);
       /* if(terminal == null){

        }else{
            if(terminal.length() > 0){
                issuedQtyList = shipHisService.getItemQtyByterminalIdpos(FromDate,ToDate,terminal);
            }
        }*/
        //List<ItemMst> itemMstlist = itemMstService.getItemMstall();
        for (ItemQtyPrice iqty:issuedQtyList) {
            ItemMst item =  itemMstService.getItemMstPk(iqty.getItem());
            OutletIssuedReportPojo ocsrpojo = new OutletIssuedReportPojo();
            ocsrpojo.setPlant(plant);
            ocsrpojo.setItem(item.getItem());
            ocsrpojo.setItemDesc(item.getItemDescription());
            ocsrpojo.setCategory(item.getProductClassId());
            ocsrpojo.setSubCategory(item.getItemType());
            ocsrpojo.setBrand(item.getProductBrandId());
            ocsrpojo.setDepartment(item.getPrdDeptId());
            ocsrpojo.setUom(item.getInventoryUom());
            float issqty = 0.0f;

            //List<ItemQty> iqty = issuedQtyList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            //if(iqty.size() > 0){
            ocsrpojo.setTotalIssuedQty(iqty.getQty());
            issqty  = iqty.getQty();
           /* }else{
                ocsrpojo.setTotalIssuedQty(0.0f);
            }*/

            float price =0.0f;
            String avgcost ="0.0";
            if(item.getIsComPro() == null) {
                avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                    price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                } else {
                    price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                }
            }else{
                if(item.getIsComPro() == 1) {
                    List<ProdBOMMst> probommstlist = prodBOMMstService.getbyPitemAndBomType(item.getItem(), "kit");
                    float itemprice = 0;
                    for (ProdBOMMst prodBOMMst : probommstlist) {
                        String cavgcost = itemMstService.getAvgCost(prodBOMMst.getCitem(), prodBOMMst.getChildUom());
                        itemprice = itemprice + (Float.valueOf(cavgcost) * prodBOMMst.getQty().floatValue());
                    }
                    avgcost = String.valueOf(itemprice);
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(itemprice) + ((Float.valueOf(itemprice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(itemprice) + Float.valueOf(item.getIncPrice());
                    }
                }else{
                    avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                    }
                }
            }

            ocsrpojo.setPrice((float)roundAvoid(price,Integer.valueOf(numberOfDecimal)));
            ocsrpojo.setTotalPrice((float)roundAvoid((price*issqty),Integer.valueOf(numberOfDecimal)));
            ocsrpojolist.add(ocsrpojo);
        }

        //result
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults(ocsrpojolist);
        resultsDTO.setPageNumber(1);
        resultsDTO.setPageSize(1);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @GetMapping("/erp-sales/sum")
    public ResponseEntity<?> getErpSalesSum(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate, @RequestParam(value = "customerId", required = false) String customerId) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        List<ItemQtyPrice> issuedQtyList = shipHisService.getItemQtyWithoutKiterp(FromDate,ToDate);
        if(customerId == null){

        }else{
            if(customerId.length() > 0){
                issuedQtyList = shipHisService.getItemQtyByCustIderp(FromDate,ToDate,customerId);
            }
        }
        List<ItemMst> itemMstlist = itemMstService.getItemMstall();
        float totalAmount = 0.0f;
        for (ItemMst item:itemMstlist) {
            float issqty = 0.0f;
            float price =0.0f;
            List<ItemQtyPrice> iqty = issuedQtyList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(iqty.size() > 0){
                issqty  = iqty.get(0).getQty();
                price = iqty.get(0).getUnitPrice();
            }


           /* float price =0.0f;
            String avgcost ="0.0";
            if(item.getIsComPro() == null) {
                avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                    price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                } else {
                    price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                }
            }else{
                if(item.getIsComPro() == 1) {
                    List<ProdBOMMst> probommstlist = prodBOMMstService.getbyPitemAndBomType(item.getItem(), "kit");
                    float itemprice = 0;
                    for (ProdBOMMst prodBOMMst : probommstlist) {
                        String cavgcost = itemMstService.getAvgCost(prodBOMMst.getCitem(), prodBOMMst.getChildUom());
                        itemprice = itemprice + (Float.valueOf(cavgcost) * prodBOMMst.getQty().floatValue());
                    }
                    avgcost = String.valueOf(itemprice);
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(itemprice) + ((Float.valueOf(itemprice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(itemprice) + Float.valueOf(item.getIncPrice());
                    }
                }else{
                    avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                    }
                }
            }*/




           /* if(issqty > 0) {
                System.out.println("--------------");
                System.out.println(item.getItem());
                System.out.println(totalAmount);
                System.out.println(price);
                System.out.println("--------------");
            }*/

            /*totalAmount = totalAmount + (price*issqty);*/
            totalAmount = totalAmount + (price);
        }

        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/erp-sales/report")
    public ResponseEntity<?> getErpSalesReport(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String ToDate, @RequestParam(value = "customerId", required = false) String customerId) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        String numberOfDecimal = companyService.getNumberOfDecimal(plant);
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        List<OutletIssuedReportPojo>  ocsrpojolist =  new ArrayList<OutletIssuedReportPojo>();
        List<ItemQtyPrice> issuedQtyList = shipHisService.getItemQtyWithoutKiterp(FromDate,ToDate);
        if(customerId == null){

        }else{
            if(customerId.length() > 0){
                issuedQtyList = shipHisService.getItemQtyByCustIderp(FromDate,ToDate,customerId);
            }
        }
        //List<ItemMst> itemMstlist = itemMstService.getItemMstall();
        for (ItemQtyPrice iqty:issuedQtyList) {
            ItemMst item =  itemMstService.getItemMstPk(iqty.getItem());
            OutletIssuedReportPojo ocsrpojo = new OutletIssuedReportPojo();
            ocsrpojo.setPlant(plant);
            ocsrpojo.setItem(item.getItem());
            ocsrpojo.setItemDesc(item.getItemDescription());
            ocsrpojo.setCategory(item.getProductClassId());
            ocsrpojo.setSubCategory(item.getItemType());
            ocsrpojo.setBrand(item.getProductBrandId());
            ocsrpojo.setDepartment(item.getPrdDeptId());
            ocsrpojo.setUom(item.getInventoryUom());
            float issqty = 0.0f;

            //List<ItemQty> iqty = issuedQtyList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            //if(iqty.size() > 0){
            ocsrpojo.setTotalIssuedQty(iqty.getQty());
            issqty  = iqty.getQty();
           /* }else{
                ocsrpojo.setTotalIssuedQty(0.0f);
            }*/

            float price =0.0f;
            String avgcost ="0.0";
            if(item.getIsComPro() == null) {
                avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                    price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                } else {
                    price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                }
            }else{
                if(item.getIsComPro() == 1) {
                    List<ProdBOMMst> probommstlist = prodBOMMstService.getbyPitemAndBomType(item.getItem(), "kit");
                    float itemprice = 0;
                    for (ProdBOMMst prodBOMMst : probommstlist) {
                        String cavgcost = itemMstService.getAvgCost(prodBOMMst.getCitem(), prodBOMMst.getChildUom());
                        itemprice = itemprice + (Float.valueOf(cavgcost) * prodBOMMst.getQty().floatValue());
                    }
                    avgcost = String.valueOf(itemprice);
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(itemprice) + ((Float.valueOf(itemprice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(itemprice) + Float.valueOf(item.getIncPrice());
                    }
                }else{
                    avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                    }
                }
            }

            ocsrpojo.setPrice((float)roundAvoid(price,Integer.valueOf(numberOfDecimal)));
            ocsrpojo.setTotalPrice((float)roundAvoid((price*issqty),Integer.valueOf(numberOfDecimal)));
            ocsrpojolist.add(ocsrpojo);
        }

        //result
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults(ocsrpojolist);
        resultsDTO.setPageNumber(1);
        resultsDTO.setPageSize(1);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }


    @GetMapping("/opening-stock-TEST/sum")
    public ResponseEntity<?> getopeningstockTEST(HttpServletRequest request, @RequestParam String FromDate, @RequestParam String itemcode) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("sales-order AppStatus get started for " + plant);
        long start = System.currentTimeMillis();
        List<ItemQty> receivedQtyToDateList = receiptDetService.getItemQtyToDate(FromDate);
        List<ItemQty> issuedQtyToDateList = shipHisService.getItemQtyToDate(FromDate);
        ItemMst item = itemMstService.getItemMstPk(itemcode);
        float totalAmount =0.0f;

            float orecqty = 0.0f;
            float oissqty = 0.0f;
            List<ItemQty> orqty = receivedQtyToDateList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(orqty.size() > 0){
                orecqty  = orqty.get(0).getQty();
            }
            List<ItemQty> oiqty = issuedQtyToDateList.stream().filter(a->a.getItem().equalsIgnoreCase(item.getItem())).collect(Collectors.toList());
            if(oiqty.size() > 0){
                oissqty  = oiqty.get(0).getQty();
            }

            /*float price =0.0f;
            String avgcost ="0.0";
            if(item.getIsComPro() == null) {
                avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                    price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                } else {
                    price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                }
            }else{
                if(item.getIsComPro() == 1) {
                    List<ProdBOMMst> probommstlist = prodBOMMstService.getbyPitemAndBomType(item.getItem(), "kit");
                    float itemprice = 0;
                    for (ProdBOMMst prodBOMMst : probommstlist) {
                        String cavgcost = itemMstService.getAvgCost(prodBOMMst.getCitem(), prodBOMMst.getChildUom());
                        itemprice = itemprice + (Float.valueOf(cavgcost) * prodBOMMst.getQty().floatValue());
                    }
                    avgcost = String.valueOf(itemprice);
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(itemprice) + ((Float.valueOf(itemprice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(itemprice) + Float.valueOf(item.getIncPrice());
                    }
                }else{
                    avgcost = itemMstService.getAvgCost(item.getItem(), item.getInventoryUom());

                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        price = Float.valueOf(avgcost) + ((Float.valueOf(avgcost) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        price = Float.valueOf(avgcost) + Float.valueOf(item.getIncPrice());
                    }
                }
            }*/

            String UnitPrice = "0";
            float UnitCost = 0;
            float itemUnitPrice = 0;
            int SalesPriceCodition = itemMstService.getSalesPriceCodition(plant, "Outbound Order");
            if (item.getIsComPro() == null) {
                if (SalesPriceCodition == 1) {
                    UnitPrice = itemMstService.getorderPricOfItem(item.getItem());
                    UnitCost = Float.valueOf(itemMstService.getorderCostOfItem(item.getItem()));
                    itemUnitPrice = Float.valueOf(UnitPrice);
                } else if (SalesPriceCodition == 2) {
                    UnitPrice = itemMstService.getListPriceOfItem(item.getItem());
                    UnitCost = Float.valueOf(itemMstService.getListCostOfItem(item.getItem()));
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        itemUnitPrice = Float.valueOf(UnitPrice) + ((Float.valueOf(UnitPrice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        itemUnitPrice = Float.valueOf(UnitPrice) + Float.valueOf(item.getIncPrice());
                    }
                } else {
                    UnitPrice = itemMstService.getAvgCost(item.getItem(), item.getPurchaseUom());
                    UnitCost = Float.valueOf(itemMstService.getAvgCost(item.getItem(), item.getPurchaseUom()));
                    if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                        itemUnitPrice = Float.valueOf(UnitPrice) + ((Float.valueOf(UnitPrice) / 100) * Float.valueOf(item.getIncPrice()));
                    } else {
                        itemUnitPrice = Float.valueOf(UnitPrice) + Float.valueOf(item.getIncPrice());
                    }
                }
            } else {
                if (item.getIsComPro() == 1) {
                    List<ProdBOMMst> probommstlist = prodBOMMstService.getbyPitemAndBomType(item.getItem(), "kit");
                    for (ProdBOMMst prodBOMMst : probommstlist) {
                        ItemMst BOMitemmst = itemMstService.getItemMstPk(prodBOMMst.getCitem());
                        if (BOMitemmst.getNonStackFlag().equalsIgnoreCase("Y")) {
                            String listcost = itemMstService.getListPriceOfItem(prodBOMMst.getCitem());
                            UnitCost = UnitCost + Float.valueOf(itemMstService.getListCostOfItem(item.getItem()));
                            itemUnitPrice = itemUnitPrice + (Float.valueOf(listcost) * prodBOMMst.getQty().floatValue());
                        } else {


                            if (SalesPriceCodition == 1) {
                                String ordercost = itemMstService.getorderPricOfItem(prodBOMMst.getCitem());
                                UnitCost = UnitCost + Float.valueOf(itemMstService.getorderCostOfItem(item.getItem()));
                                itemUnitPrice = itemUnitPrice + (Float.valueOf(ordercost) * prodBOMMst.getQty().floatValue());
                            } else if (SalesPriceCodition == 2) {
                                String listcost = itemMstService.getListPriceOfItem(prodBOMMst.getCitem());
                                UnitCost = UnitCost + Float.valueOf(itemMstService.getListCostOfItem(item.getItem()));
                                itemUnitPrice = itemUnitPrice + (Float.valueOf(listcost) * prodBOMMst.getQty().floatValue());

                                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                                    itemUnitPrice = Float.valueOf(itemUnitPrice) + ((Float.valueOf(itemUnitPrice) / 100) * Float.valueOf(item.getIncPrice()));
                                } else {
                                    itemUnitPrice = Float.valueOf(itemUnitPrice) + Float.valueOf(item.getIncPrice());
                                }
                            } else {
                                String avgcost = itemMstService.getAvgCost(prodBOMMst.getCitem(), prodBOMMst.getChildUom());
                                UnitCost = UnitCost + Float.valueOf(avgcost);
                                itemUnitPrice = itemUnitPrice + (Float.valueOf(avgcost) * prodBOMMst.getQty().floatValue());

                                if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                                    itemUnitPrice = Float.valueOf(itemUnitPrice) + ((Float.valueOf(itemUnitPrice) / 100) * Float.valueOf(item.getIncPrice()));
                                } else {
                                    itemUnitPrice = Float.valueOf(itemUnitPrice) + Float.valueOf(item.getIncPrice());
                                }
                            }


                        }
                    }


                }else{
                    if (SalesPriceCodition == 1) {
                        UnitPrice = itemMstService.getorderPricOfItem(item.getItem());
                        UnitCost = Float.valueOf(itemMstService.getorderCostOfItem(item.getItem()));
                        itemUnitPrice = Float.valueOf(UnitPrice);
                    } else if (SalesPriceCodition == 2) {
                        UnitPrice = itemMstService.getListPriceOfItem(item.getItem());
                        UnitCost = Float.valueOf(itemMstService.getListCostOfItem(item.getItem()));
                        if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                            itemUnitPrice = Float.valueOf(UnitPrice) + ((Float.valueOf(UnitPrice) / 100) * Float.valueOf(item.getIncPrice()));
                        } else {
                            itemUnitPrice = Float.valueOf(UnitPrice) + Float.valueOf(item.getIncPrice());
                        }
                    } else {
                        UnitPrice = itemMstService.getAvgCost(item.getItem(), item.getPurchaseUom());
                        UnitCost = Float.valueOf(itemMstService.getAvgCost(item.getItem(), item.getPurchaseUom()));
                        if (item.getIncPriceUnit().equalsIgnoreCase("%")) {
                            itemUnitPrice = Float.valueOf(UnitPrice) + ((Float.valueOf(UnitPrice) / 100) * Float.valueOf(item.getIncPrice()));
                        } else {
                            itemUnitPrice = Float.valueOf(UnitPrice) + Float.valueOf(item.getIncPrice());
                        }
                    }
                }
            }

            totalAmount = totalAmount+((orecqty-oissqty)*UnitCost);
            System.out.println("getItem------"+item.getItem());
        System.out.println("orecqty------"+orecqty);
        System.out.println("oissqty------"+oissqty);
        System.out.println("UnitCost------"+UnitCost);


        //map
        Map<String, Object> result = new HashMap<>();
        result.put("totalAmount", totalAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}