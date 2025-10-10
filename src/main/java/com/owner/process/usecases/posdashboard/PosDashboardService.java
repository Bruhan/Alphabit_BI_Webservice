package com.owner.process.usecases.posdashboard;

import com.owner.process.persistence.models.*;
import com.owner.process.usecases.posdashboard.dto.InventoryDTO;
import com.owner.process.usecases.posdashboard.dto.InventoryQuantityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PosDashboardService {

    @Autowired
    PosDashboardDao posDashboardDao;

    public List<PosSalesReport> getPosSalesReport(String plant, String fromdate, String todate) throws Exception {
        List<PosSalesReport> posSalesReport;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }

            if(fromdate.length() == 0 && todate.length() == 0 ){
                posSalesReport = posDashboardDao.getpsosalesreport(plant);
            }else if(fromdate.length() > 0 && todate.length() == 0 ){
                posSalesReport = posDashboardDao.getpsosalesreportbyfate(plant,fromdate);
            }else if(fromdate.length() == 0 && todate.length() > 0 ){
                posSalesReport = posDashboardDao.getpsosalesreportbytodate(plant,todate);
            }else{
                posSalesReport = posDashboardDao.getpsosalesreportbydate(plant,fromdate,todate);
            }

        } catch (Exception e) {
            throw new Exception(e);
        }
        return posSalesReport;
    }

    public List<PosSalesReportOutTerm> getPosSalesReportOutTerm(String plant, String fromdate, String todate, String Outlet, String Terminal) throws Exception {
        List<PosSalesReportOutTerm> posSalesReport;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }
            if(Outlet == null) {
                Outlet = "";
            }
            if(Terminal == null) {
                Terminal = "";
            }

            if(fromdate.length() == 0 && todate.length() == 0 ){
                posSalesReport = posDashboardDao.getpsosalesreportoutterm(plant,Outlet,Terminal);
            }else if(fromdate.length() > 0 && todate.length() == 0 ){
                posSalesReport = posDashboardDao.getpsosalesreportbyfateoutterm(plant,fromdate,Outlet,Terminal);
            }else if(fromdate.length() == 0 && todate.length() > 0 ){
                posSalesReport = posDashboardDao.getpsosalesreportbytodateoutterm(plant,todate,Outlet,Terminal);
            }else{
                posSalesReport = posDashboardDao.getpsosalesreportbydateoutterm(plant,fromdate,todate,Outlet,Terminal);
            }

        } catch (Exception e) {
            throw new Exception(e);
        }
        return posSalesReport;
    }

    public List<PosSalesReportWithDate> getPosSalesReportwithdate(String plant, String fromdate, String todate) throws Exception {
        List<PosSalesReportWithDate> posSalesReport;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }

            if(fromdate.length() == 0 && todate.length() == 0 ){
                posSalesReport = posDashboardDao.getpsosalesreportwithdate(plant);
            }else if(fromdate.length() > 0 && todate.length() == 0 ){
                posSalesReport = posDashboardDao.getpsosalesreportbyfatewithdate(plant,fromdate);
            }else if(fromdate.length() == 0 && todate.length() > 0 ){
                posSalesReport = posDashboardDao.getpsosalesreportbytodatewithdate(plant,todate);
            }else{
                posSalesReport = posDashboardDao.getpsosalesreportbydatewithdate(plant,fromdate,todate);
            }

        } catch (Exception e) {
            throw new Exception(e);
        }
        return posSalesReport;
    }

    public List<PosSalesReportOutTermWithDate> getPosSalesReportOutTermwithdate(String plant, String fromdate, String todate, String Outlet, String Terminal) throws Exception {
        List<PosSalesReportOutTermWithDate> posSalesReport;
        try {
            if(fromdate == null){
                fromdate = "";
            }
            if(todate == null) {
                todate = "";
            }
            if(Outlet == null) {
                Outlet = "";
            }
            if(Terminal == null) {
                Terminal = "";
            }

            if(fromdate.length() == 0 && todate.length() == 0 ){
                posSalesReport = posDashboardDao.getpsosalesreportouttermwithdate(plant,Outlet,Terminal);
            }else if(fromdate.length() > 0 && todate.length() == 0 ){
                posSalesReport = posDashboardDao.getpsosalesreportbyfateouttermwithdate(plant,fromdate,Outlet,Terminal);
            }else if(fromdate.length() == 0 && todate.length() > 0 ){
                posSalesReport = posDashboardDao.getpsosalesreportbytodateouttermwithdate(plant,todate,Outlet,Terminal);
            }else{
                posSalesReport = posDashboardDao.getpsosalesreportbydateouttermwithdate(plant,fromdate,todate,Outlet,Terminal);
            }

        } catch (Exception e) {
            throw new Exception(e);
        }
        return posSalesReport;
    }

    public List<InventoryDTO> getInventory(String plant, String item, String loc, Integer page, Integer productsCount) {
        return posDashboardDao.getInventory(plant, item, loc, (page - 1) * productsCount, productsCount);

    }

    public Long getInventoryTotalCount(String plant, String item, String loc) {
        return posDashboardDao.countInventory(plant, item, loc);
    }

    public List<InventoryQuantityDTO> getInventoryQuantities(String plant, String item, String loc) {
        return posDashboardDao.getInventoryQuantities(plant, item, loc);
    }
}
