package com.owner.process.usecases.approval;

import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.helpers.common.results.ResultsDTO;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.token.ClaimsSet;
import com.owner.process.persistence.models.*;
import com.owner.process.usecases.PltApprovalMatrix.PltApprovalMatrixService;
import com.owner.process.usecases.PltApprovalMatrix.pojo.PltApprovalMatrixPojo;
import com.owner.process.usecases.PltApprovalMatrix.pojo.pltApprovalMatrixSummary;
import com.owner.process.usecases.activity_log.ActivityLogModel;
import com.owner.process.usecases.activity_log.ActivityLogService;
import com.owner.process.usecases.purchase_order.PoAttachment.PoAttachmentService;
import com.owner.process.usecases.purchase_order.PoAttachmentApproval.PoAttachmentApprovalService;
import com.owner.process.usecases.purchase_order.PoDetApprovalRemarks.PoDetApprovalRemarksService;
import com.owner.process.usecases.purchase_order.PoDetRemarks.PoDetRemarksService;
import com.owner.process.usecases.purchase_order.poDet.PoDetService;
import com.owner.process.usecases.purchase_order.poDetApproval.PoDetApprovalService;
import com.owner.process.usecases.purchase_order.poHdr.PoHdrService;
import com.owner.process.usecases.purchase_order.poHdrApproval.PoHderApprovalService;
import com.owner.process.usecases.sales_order.DoAttachmentApproval.DoAttachmentApprovalService;
import com.owner.process.usecases.sales_order.DoDetApprovalRemarks.DoDetApprovalRemarksService;
import com.owner.process.usecases.sales_order.doAttachment.DoAttachmentService;
import com.owner.process.usecases.sales_order.doDetApproval.DoDetApprovalService;
import com.owner.process.usecases.sales_order.doHdrApproval.DoHderApprovalService;
import com.owner.process.usecases.sales_order.do_det.DoDetService;
import com.owner.process.usecases.sales_order.do_det_remarks.DoDetRemarksService;
import com.owner.process.usecases.sales_order.do_hdr.DoHdrService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${spring.base.path}")
public class OwnerApprovalsController {

    @Autowired
    ClaimsSet claimsSet;
    @Autowired
    PoHdrService poHdrService;
    @Autowired
    PoDetService poDetService;
    @Autowired
    PoHderApprovalService poHderApprovalService;
    @Autowired
    PoDetApprovalService poDetApprovalService;
    @Autowired
    PoAttachmentService poAttachmentService;
    @Autowired
    PoAttachmentApprovalService poAttachmentApprovalService;
    @Autowired
    PoDetRemarksService poDetRemarksService;
    @Autowired
    PoDetApprovalRemarksService poDetApprovalRemarksService;
    @Autowired
    ActivityLogService activityLogService;
    @Autowired
    DoHdrService doHdrService;
    @Autowired
    DoDetService doDetService;
    @Autowired
    DoDetRemarksService doDetRemarksService;
    @Autowired
    DoAttachmentService doAttachmentService;
    @Autowired
    DoHderApprovalService doHderApprovalService;
    @Autowired
    DoDetApprovalService doDetApprovalService;
    @Autowired
    DoDetApprovalRemarksService doDetApprovalRemarksService;
    @Autowired
    DoAttachmentApprovalService doAttachmentApprovalService;
    @Autowired
    PltApprovalMatrixService pltApprovalMatrixService;

    @RequestMapping(value = "/Approvals/AllPurchase", method = RequestMethod.POST)
    public ResponseEntity<Object> getAllPurchase(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<PoHdr> poHdrList = new ArrayList<PoHdr>();
        List<PoHdr> poHdrListCreate = poHdrService.getPoHdrByApprovalStatus("CREATE APPROVAL PENDING");
        List<PoHdr> poHdrListEdit = poHdrService.getPoHdrByApprovalStatus("EDIT APPROVAL PENDING");
        List<PoHdr> poHdrListDelete = poHdrService.getPoHdrByApprovalStatus("DELETE APPROVAL PENDING");
        poHdrList.addAll(poHdrListCreate);
        poHdrList.addAll(poHdrListEdit);
        poHdrList.addAll(poHdrListDelete);

        ResultsDTO result = new ResultsDTO();
        result.setResults(poHdrList);
        result.setPageSize(poHdrList.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/Approvals/AllPurchase-pagination", method = RequestMethod.POST)
    public ResponseEntity<Object> getAllPurchasePagination(HttpServletRequest request,
                                                           @RequestParam int page,
                                                           @RequestParam int count) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        if(page > 0){
            page = page - 1;
        }

        List<PoHdr> poHdrList = new ArrayList<PoHdr>();
        List<PoHdr> poHdrListCreate = poHdrService.getPoHdrByApprovalStatus("CREATE APPROVAL PENDING");
        List<PoHdr> poHdrListEdit = poHdrService.getPoHdrByApprovalStatus("EDIT APPROVAL PENDING");
        List<PoHdr> poHdrListDelete = poHdrService.getPoHdrByApprovalStatus("DELETE APPROVAL PENDING");
        poHdrList.addAll(poHdrListCreate);
        poHdrList.addAll(poHdrListEdit);
        poHdrList.addAll(poHdrListDelete);

        List<PoHdr> poHdrListpage = new ArrayList<PoHdr>();
        poHdrListpage = poHdrService.getPoHdrByApprovalStatusPage(page,count);

        int divcount = poHdrList.size()/count;
        if(poHdrList.size() - (divcount *count) > 0){
            divcount = divcount + 1;
        }
        //result
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults(poHdrListpage);
        resultsDTO.setPageNumber(page+1);
        resultsDTO.setPageSize(poHdrListpage.size());
        resultsDTO.setTotalCount((long)poHdrList.size());
        resultsDTO.setTotalPageCount(divcount);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/Approvals/PurchaseCreate", method = RequestMethod.POST)
    public ResponseEntity<Object> getAllPurchaseCreate(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        List<PoHdr> poHdrListCreate = poHdrService.getPoHdrByApprovalStatus("CREATE APPROVAL PENDING");
        ResultsDTO result = new ResultsDTO();
        result.setResults(poHdrListCreate);
        result.setPageSize(poHdrListCreate.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/Approvals/PurchaseEdit", method = RequestMethod.POST)
    public ResponseEntity<Object> getAllPurchaseEdit(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        List<PoHdr> poHdrListEdit = poHdrService.getPoHdrByApprovalStatus("EDIT APPROVAL PENDING");
        ResultsDTO result = new ResultsDTO();
        result.setResults(poHdrListEdit);
        result.setPageSize(poHdrListEdit.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/Approvals/PurchaseDelete", method = RequestMethod.POST)
    public ResponseEntity<Object> getAllPurchaseDelete(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        List<PoHdr> poHdrListDelete = poHdrService.getPoHdrByApprovalStatus("DELETE APPROVAL PENDING");
        ResultsDTO result = new ResultsDTO();
        result.setResults(poHdrListDelete);
        result.setPageSize(poHdrListDelete.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/Approvals/GetPurchaseByPoNo")
    public ResponseEntity<Object> getPurchaseByPoNo(HttpServletRequest request, @RequestParam String PoNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        PurchasePojo purchasePojo = new PurchasePojo();
        PoHdr poHdr = poHdrService.getPoHdrByPoNo(PoNo);
        List<PoDet> poDetList = poDetService.getByPoNo(PoNo);
        purchasePojo.setPoHdr(poHdr);
        purchasePojo.setPoDetList(poDetList);
        ResultsDTO result = new ResultsDTO();
        result.setResults(purchasePojo);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*@GetMapping("/Approvals/PurchaseApproveReject")
    public ResponseEntity<Object> getPurchaseApproveReject(HttpServletRequest request,
                                                           @RequestParam int status,
                                                           @RequestParam String orderStatus,
                                                           @RequestParam String remarks,
                                                           @RequestParam String uKey) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        *//*PoHdr poHdr = modelMapper.map(doHdr,
                        new TypeToken<poHdr>() {
                        }.getType());*//*
        PoHdr pohdr = poHdrService.getPoHdrByUkey(uKey);
        List<PoDet> podetlist = poDetService.getByUKey(uKey);
        PoHdrApproval pohdrapproval = poHderApprovalService.getPoHdrAppByUkey(uKey);
        List<PoDetApproval> podetapprovallist = poDetApprovalService.getPoDetApprovalBYUkey(uKey);
        if(status == 1){
            if(orderStatus.equalsIgnoreCase("CREATE APPROVAL PENDING")){
                pohdrapproval.setOrderStatus("CREATE APPROVED");
                poHderApprovalService.savePoHdrApp(pohdrapproval);
                pohdr.setOrderStatus("Open");
                poHdrService.savePoHdr(pohdr);
            }else if(orderStatus.equalsIgnoreCase("EDIT APPROVAL PENDING")){
                if(pohdrapproval.getOrderStatus().equalsIgnoreCase("Draft")){
                    pohdr.setOrderStatus("Open");
                }else{
                    pohdr.setOrderStatus(pohdrapproval.getOrderStatus());
                }
                poHdrService.savePoHdr(pohdr);
                pohdrapproval.setOrderStatus("EDIT APPROVED");
                poHderApprovalService.savePoHdrApp(pohdrapproval);

            }else if(orderStatus.equalsIgnoreCase("DELETE APPROVAL PENDING")){
                pohdrapproval.setOrderStatus("DELETE APPROVED");
                poHderApprovalService.savePoHdrApp(pohdrapproval);
                poHdrService.delete(pohdr);
                poDetService.deleteall(podetlist);
            }
        }else{
            if(orderStatus.equalsIgnoreCase("CREATE APPROVAL PENDING")){
                pohdr.setOrderStatus("REJECTED");
                poHdrService.savePoHdr(pohdr);
                pohdrapproval.setOrderStatus("CREATE REJECTED");
                poHderApprovalService.savePoHdrApp(pohdrapproval);
            }else if(orderStatus.equalsIgnoreCase("EDIT APPROVAL PENDING")){
                PoHdr poHdrukey = modelMapper.map(pohdrapproval, new TypeToken<PoHdr>() {}.getType());
                poHdrukey.setId(pohdr.getId());
                poHdrService.savePoHdr(poHdrukey);
                poDetService.deleteall(podetlist);
                for(PoDetApproval podetapp:podetapprovallist){
                    PoDet poDetukey = modelMapper.map(podetapp, new TypeToken<PoDet>() {}.getType());
                    poDetService.save(poDetukey);
                }
                pohdrapproval.setOrderStatus("EDIT REJECTED");
                poHderApprovalService.savePoHdrApp(pohdrapproval);
            }else if(orderStatus.equalsIgnoreCase("DELETE APPROVAL PENDING")){
                pohdr.setOrderStatus(pohdrapproval.getOrderStatus());
                poHdrService.savePoHdr(pohdr);
                pohdrapproval.setOrderStatus("DELETE REJECTED");
                poHderApprovalService.savePoHdrApp(pohdrapproval);
            }
        }
        ResultsDao result = new ResultsDao();
        result.setResults("OK");
        if(status == 1){
            result.setMessage("APPROVED SUCCESSFULLY");
        }else{
            result.setMessage("REJECTED SUCCESSFULLY");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }*/

    @GetMapping("/Approvals/PurchaseApproveReject")
    public ResponseEntity<Object> getPurchaseApproveReject(HttpServletRequest request,
                                                           @RequestParam int status,
                                                           @RequestParam String approvalStatus,
                                                           @RequestParam String remarks,
                                                           @RequestParam String uKey) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        String tnsDate = new DateTimeCalc().getTodayDateTime().split(" ")[0];
        String crAt = new DateTimeCalc().getUcloTodayDateTime();
        String crBy = claimsDao.getSub();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        ActivityLogModel activityLogModel = new ActivityLogModel();
        PoHdr pohdr = poHdrService.getPoHdrByUkey(uKey);
        List<PoDet> podetlist = poDetService.getByUKey(uKey);
        List<PoDetRemarks> PoDetRemarksList = poDetRemarksService.getByUKey(uKey);
        List<PoAttachment> PoAttachmentList = poAttachmentService.getByUKey(uKey);
        PoHdrApproval pohdrapproval = poHderApprovalService.getPoHdrAppByUkey(uKey);
        List<PoDetApproval> podetapprovallist = poDetApprovalService.getPoDetApprovalBYUkey(uKey);
        List<PoDetApprovalRemarks> PoDetApprovalRemarksList = poDetApprovalRemarksService.getByUKey(uKey);
        List<PoAttachmentApproval> PoAttachmentApprovalList = poAttachmentApprovalService.getByUKey(uKey);
        if(status == 1){
            if(approvalStatus.equalsIgnoreCase("CREATE APPROVAL PENDING")){
                pohdrapproval.setApprovalSataus("CREATE APPROVED");
                poHderApprovalService.savePoHdrApp(pohdrapproval);
                pohdr.setOrderStatus("Open");
                pohdr.setApprovalSataus("CREATE APPROVED");
                poHdrService.savePoHdr(pohdr);

                activityLogService.setActivityLogDetails(
                        activityLogModel.setActivityLogModelForStock(
                                plant, "CREATE_PURCHASE_ORDER_APPROVED", pohdrapproval.getPurchaseNo(), "",
                                0.0, "", tnsDate,
                                crAt, crBy,remarks,"","","",""));
            }else if(approvalStatus.equalsIgnoreCase("EDIT APPROVAL PENDING")){
                if(pohdrapproval.getOrderStatus().equalsIgnoreCase("Draft")){
                    pohdr.setOrderStatus("Open");
                }/*else{
                    pohdr.setOrderStatus(pohdrapproval.getOrderStatus());
                }*/
                pohdr.setApprovalSataus("EDIT APPROVED");
                poHdrService.savePoHdr(pohdr);
                pohdrapproval.setApprovalSataus("EDIT APPROVED");
                poHderApprovalService.savePoHdrApp(pohdrapproval);

                activityLogService.setActivityLogDetails(
                        activityLogModel.setActivityLogModelForStock(
                                plant, "EDIT_PURCHASE_ORDER_APPROVED", pohdrapproval.getPurchaseNo(), "",
                                0.0, "", tnsDate,
                                crAt, crBy,remarks,"","","",""));

            }else if(approvalStatus.equalsIgnoreCase("DELETE APPROVAL PENDING")){
                pohdrapproval.setApprovalSataus("DELETE APPROVED");
                poHderApprovalService.savePoHdrApp(pohdrapproval);
                poHdrService.delete(pohdr);
                poDetService.deleteall(podetlist);
                poDetRemarksService.deleteall(PoDetRemarksList);
                poAttachmentService.deleteall(PoAttachmentList);

                activityLogService.setActivityLogDetails(
                        activityLogModel.setActivityLogModelForStock(
                                plant, "DELETE_PURCHASE_ORDER_APPROVED", pohdrapproval.getPurchaseNo(), "",
                                0.0, "", tnsDate,
                                crAt, crBy,remarks,"","","",""));
            }
        }else{
            if(approvalStatus.equalsIgnoreCase("CREATE APPROVAL PENDING")){
                pohdr.setApprovalSataus("CREATE REJECTED");
                poHdrService.savePoHdr(pohdr);
                pohdrapproval.setApprovalSataus("CREATE REJECTED");
                poHderApprovalService.savePoHdrApp(pohdrapproval);
                activityLogService.setActivityLogDetails(
                        activityLogModel.setActivityLogModelForStock(
                                plant, "CREATE_PURCHASE_ORDER_REJECTED", pohdrapproval.getPurchaseNo(), "",
                                0.0, "", tnsDate,
                                crAt, crBy,remarks,"","","",""));

            }else if(approvalStatus.equalsIgnoreCase("EDIT APPROVAL PENDING")){
                PoHdr poHdrukey = modelMapper.map(pohdrapproval, new TypeToken<PoHdr>() {}.getType());
                poHdrukey.setId(pohdr.getId());
                poHdrukey.setApprovalSataus("EDIT REJECTED");
                poHdrService.savePoHdr(poHdrukey);
                poDetService.deleteall(podetlist);
                for(PoDetApproval podetapp:podetapprovallist){
                    PoDet poDetukey = modelMapper.map(podetapp, new TypeToken<PoDet>() {}.getType());
                    poDetService.save(poDetukey);
                }
                poDetRemarksService.deleteall(PoDetRemarksList);
                if(PoDetApprovalRemarksList.size() > 0) {
                    for (PoDetApprovalRemarks PoDetApprovalRemarks : PoDetApprovalRemarksList) {
                        PoDetRemarks poDetRemarksukey = modelMapper.map(PoDetApprovalRemarks, new TypeToken<PoDetRemarks>() {
                        }.getType());
                        poDetRemarksService.save(poDetRemarksukey);
                    }
                }
                poAttachmentService.deleteall(PoAttachmentList);
                pohdrapproval.setApprovalSataus("EDIT REJECTED");
                poHderApprovalService.savePoHdrApp(pohdrapproval);

                activityLogService.setActivityLogDetails(
                        activityLogModel.setActivityLogModelForStock(
                                plant, "EDIT_PURCHASE_ORDER_REJECTED", pohdrapproval.getPurchaseNo(), "",
                                0.0, "", tnsDate,
                                crAt, crBy,remarks,"","","",""));
            }else if(approvalStatus.equalsIgnoreCase("DELETE APPROVAL PENDING")){
                pohdr.setOrderStatus(pohdrapproval.getOrderStatus());
                pohdr.setApprovalSataus("DELETE REJECTED");
                poHdrService.savePoHdr(pohdr);
                pohdrapproval.setApprovalSataus("DELETE REJECTED");
                poHderApprovalService.savePoHdrApp(pohdrapproval);

                activityLogService.setActivityLogDetails(
                        activityLogModel.setActivityLogModelForStock(
                                plant, "DELETE_PURCHASE_ORDER_REJECTED", pohdrapproval.getPurchaseNo(), "",
                                0.0, "", tnsDate,
                                crAt, crBy,remarks,"","","",""));
            }
        }
        ResultsDTO result = new ResultsDTO();
        result.setResults("OK");
        if(status == 1){
            result.setMessage("APPROVED SUCCESSFULLY");
        }else{
            result.setMessage("REJECTED SUCCESSFULLY");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//SALES BY IMTHI 20-08-2022
    @RequestMapping(value = "/Approvals/AllSales", method = RequestMethod.POST)
    public ResponseEntity<Object> getAllSales(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        List<DoHdr> doHdrList = new ArrayList<DoHdr>();
        List<DoHdr> doHdrListCreate = doHdrService.getDoHdrByAppCustOrderStatus("CREATE APPROVAL PENDING");
        List<DoHdr> doHdrListEdit = doHdrService.getDoHdrByAppCustOrderStatus("EDIT APPROVAL PENDING");
        List<DoHdr> doHdrListDelete = doHdrService.getDoHdrByAppCustOrderStatus("DELETE APPROVAL PENDING");
        doHdrList.addAll(doHdrListCreate);
        doHdrList.addAll(doHdrListEdit);
        doHdrList.addAll(doHdrListDelete);

        ResultsDTO result = new ResultsDTO();
        result.setResults(doHdrList);
        result.setPageSize(doHdrList.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/Approvals/SalesCreate", method = RequestMethod.POST)
    public ResponseEntity<Object> getAllSalesCreate(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        List<DoHdr> doHdrListCreate = doHdrService.getDoHdrByAppCustOrderStatus("CREATE APPROVAL PENDING");

        ResultsDTO result = new ResultsDTO();
        result.setResults(doHdrListCreate);
        result.setPageSize(doHdrListCreate.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/Approvals/SalesEdit", method = RequestMethod.POST)
    public ResponseEntity<Object> getAllSalesEdit(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        List<DoHdr> doHdrListEdit = doHdrService.getDoHdrByAppCustOrderStatus("EDIT APPROVAL PENDING");

        ResultsDTO result = new ResultsDTO();
        result.setResults(doHdrListEdit);
        result.setPageSize(doHdrListEdit.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/Approvals/SalesDelete", method = RequestMethod.POST)
    public ResponseEntity<Object> getAllSalesDelete(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        List<DoHdr> doHdrListDelete = doHdrService.getDoHdrByAppCustOrderStatus("DELETE APPROVAL PENDING");

        ResultsDTO result = new ResultsDTO();
        result.setResults(doHdrListDelete);
        result.setPageSize(doHdrListDelete.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/Approvals/GetSalesByDoNo")
    public ResponseEntity<Object> getSalesByDoNo(HttpServletRequest request, @RequestParam String DoNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        SalesPojo salesPojo = new SalesPojo();
        DoHdr doHdr = doHdrService.getDoHdrByDoNo(DoNo);
        List<DoDet> doDetList = doDetService.getAllDoDetPk(DoNo);
        salesPojo.setDoHdr(doHdr);
        salesPojo.setDoDetList(doDetList);

        ResultsDTO result = new ResultsDTO();
        result.setResults(salesPojo);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/Approvals/SalesApproveReject")
    public ResponseEntity<Object> getSalesApproveReject(HttpServletRequest request,
                                                           @RequestParam int status,
                                                           @RequestParam String approvalStatus,
                                                           @RequestParam String remarks,
                                                           @RequestParam String uKey) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        String tnsDate = new DateTimeCalc().getTodayDateTime().split(" ")[0];
        String crAt = new DateTimeCalc().getUcloTodayDateTime();
        String crBy = claimsDao.getSub();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        ActivityLogModel activityLogModel = new ActivityLogModel();

        /*DoHdr doHdr = doHdrService.getDoHdrByDoNo(DoNo);
        List<DoDet> doDetList = doDetService.getAllDoDetPk(DoNo);*/

        DoHdr dohdr = doHdrService.getDoHdrByUkey(uKey);
        List<DoDet> dodetlist = doDetService.getByUKey(uKey);
        List<DoDetRemarks> DoDetRemarksList = doDetRemarksService.getByUKey(uKey);
        List<DoAttachment> DoAttachmentList = doAttachmentService.getByUKey(uKey);
        DoHdrApproval dohdrapproval = doHderApprovalService.getDoHdrAppByUkey(uKey);
        List<DoDetApproval> dodetapprovallist = doDetApprovalService.getDoDetApprovalBYUkey(uKey);
        List<DoDetApprovalRemarks> DoDetApprovalRemarksList = doDetApprovalRemarksService.getByUKey(uKey);
        List<DoAttachmentApproval> DoAttachmentApprovalList = doAttachmentApprovalService.getByUKey(uKey);

        if(status == 1){
            if(approvalStatus.equalsIgnoreCase("CREATE APPROVAL PENDING")){
                dohdrapproval.setAppCustOrderStatus("CREATE APPROVED");
                doHderApprovalService.saveDoHdrApp(dohdrapproval);
                dohdr.setOrderStatus("Open");
                /*dohdr.setApproveStatus("CREATE APPROVED");*/
                dohdr.setAppCustOrderStatus("CREATE APPROVED");
                doHdrService.saveDoHdr(dohdr);

                activityLogService.setActivityLogDetails(
                        activityLogModel.setActivityLogModelForStock(
                                plant, "CREATE_SALES_ORDER_APPROVED", dohdrapproval.getDoNo(), "",
                                0.0, "", tnsDate,
                                crAt, crBy,remarks,"","","",""));
            }else if(approvalStatus.equalsIgnoreCase("EDIT APPROVAL PENDING")){
                if(dohdrapproval.getOrderStatus().equalsIgnoreCase("Draft")){
                    dohdr.setOrderStatus("Open");
                }
                dohdr.setAppCustOrderStatus("EDIT APPROVED");
                doHdrService.saveDoHdr(dohdr);
                dohdrapproval.setAppCustOrderStatus("EDIT APPROVED");
                doHderApprovalService.saveDoHdrApp(dohdrapproval);

                activityLogService.setActivityLogDetails(
                        activityLogModel.setActivityLogModelForStock(
                                plant, "EDIT_SALES_ORDER_APPROVED", dohdrapproval.getDoNo(), "",
                                0.0, "", tnsDate,
                                crAt, crBy,remarks,"","","",""));

            }else if(approvalStatus.equalsIgnoreCase("DELETE APPROVAL PENDING")){
                dohdrapproval.setAppCustOrderStatus("DELETE APPROVED");
                doHderApprovalService.saveDoHdrApp(dohdrapproval);
                doHdrService.delete(dohdr);
                doDetService.deleteall(dodetlist);
                doDetRemarksService.deleteall(DoDetRemarksList);
                doAttachmentService.deleteall(DoAttachmentList);

                activityLogService.setActivityLogDetails(
                        activityLogModel.setActivityLogModelForStock(
                                plant, "DELETE_SALES_ORDER_APPROVED", dohdrapproval.getDoNo(), "",
                                0.0, "", tnsDate,
                                crAt, crBy,remarks,"","","",""));
            }
        }else{
            if(approvalStatus.equalsIgnoreCase("CREATE APPROVAL PENDING")){
                dohdr.setAppCustOrderStatus("CREATE REJECTED");
                doHdrService.saveDoHdr(dohdr);
                dohdrapproval.setAppCustOrderStatus("CREATE REJECTED");
                doHderApprovalService.saveDoHdrApp(dohdrapproval);
                activityLogService.setActivityLogDetails(
                        activityLogModel.setActivityLogModelForStock(
                                plant, "CREATE_SALES_ORDER_REJECTED", dohdrapproval.getDoNo(), "",
                                0.0, "", tnsDate,
                                crAt, crBy,remarks,"","","",""));

            }else if(approvalStatus.equalsIgnoreCase("EDIT APPROVAL PENDING")){
                DoHdr doHdrukey = modelMapper.map(dohdrapproval, new TypeToken<DoHdr>() {}.getType());
                doHdrukey.setId(dohdr.getId());
                doHdrukey.setAppCustOrderStatus("EDIT REJECTED");
                doHdrService.saveDoHdr(doHdrukey);
                doDetService.deleteall(dodetlist);
                for(DoDetApproval dodetapp:dodetapprovallist){
                    DoDet doDetukey = modelMapper.map(dodetapp, new TypeToken<DoDet>() {}.getType());
                    doDetService.save(doDetukey);
                }
                doDetRemarksService.deleteall(DoDetRemarksList);
                if(DoDetApprovalRemarksList.size() > 0) {
                    for (DoDetApprovalRemarks DoDetApprovalRemarks : DoDetApprovalRemarksList) {
                        DoDetRemarks doDetRemarksukey = modelMapper.map(DoDetApprovalRemarks, new TypeToken<DoDetRemarks>() {
                        }.getType());
                        doDetRemarksService.save(doDetRemarksukey);
                    }
                }
                doAttachmentService.deleteall(DoAttachmentList);
                dohdrapproval.setAppCustOrderStatus("EDIT REJECTED");
                doHderApprovalService.saveDoHdrApp(dohdrapproval);

                activityLogService.setActivityLogDetails(
                        activityLogModel.setActivityLogModelForStock(
                                plant, "EDIT_SALES_ORDER_REJECTED", dohdrapproval.getDoNo(), "",
                                0.0, "", tnsDate,
                                crAt, crBy,remarks,"","","",""));
            }else if(approvalStatus.equalsIgnoreCase("DELETE APPROVAL PENDING")){
                dohdr.setOrderStatus(dohdrapproval.getOrderStatus());
                dohdr.setAppCustOrderStatus("DELETE REJECTED");
                doHdrService.saveDoHdr(dohdr);
                dohdrapproval.setAppCustOrderStatus("DELETE REJECTED");
                doHderApprovalService.saveDoHdrApp(dohdrapproval);

                activityLogService.setActivityLogDetails(
                        activityLogModel.setActivityLogModelForStock(
                                plant, "DELETE_SALES_ORDER_REJECTED", dohdrapproval.getDoNo(), "",
                                0.0, "", tnsDate,
                                crAt, crBy,remarks,"","","",""));
            }
        }
        ResultsDTO result = new ResultsDTO();
        result.setResults("OK");
        if(status == 1){
            result.setMessage("APPROVED SUCCESSFULLY");
        }else{
            result.setMessage("REJECTED SUCCESSFULLY");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/Approvals/AllApprovals")
    public ResponseEntity<?> getApprovalSummary(HttpServletRequest request,@RequestParam(required=false) String Plant,@RequestParam(required=true) String ApprovalType)
            throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));

        String PLANT = claimsDao.getPlt();
        if(Plant == null){
            Plant = PLANT;
        }

        List<PltApprovalMatrixPojo> pltApprovalMatrixPojo = new ArrayList<PltApprovalMatrixPojo>();
        pltApprovalMatrixPojo=pltApprovalMatrixService.getPltSummarybyApprovalType(Plant,ApprovalType);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<pltApprovalMatrixSummary> pltApprovalMatrixSummarylist = modelMapper.map(pltApprovalMatrixPojo,new TypeToken<List<pltApprovalMatrixSummary>>() { }.getType());

        ResultsDTO result = new ResultsDTO();
        result.setResults(pltApprovalMatrixSummarylist);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
