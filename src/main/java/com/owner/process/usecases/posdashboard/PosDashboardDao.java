package com.owner.process.usecases.posdashboard;

import com.owner.process.persistence.models.PosSalesReport;
import com.owner.process.persistence.models.PosSalesReportOutTerm;
import com.owner.process.persistence.models.PosSalesReportOutTermWithDate;
import com.owner.process.persistence.models.PosSalesReportWithDate;
import com.owner.process.usecases.posdashboard.dto.InventoryDTO;
import com.owner.process.usecases.posdashboard.dto.InventoryQuantityDTO;

import java.util.List;

public interface PosDashboardDao {
    List<PosSalesReport> getpsosalesreport(String plant);
    List<PosSalesReport> getpsosalesreportbyfate(String plant, String fromdate);
    List<PosSalesReport> getpsosalesreportbytodate(String plant, String todate);
    List<PosSalesReport> getpsosalesreportbydate(String plant, String fromdate, String todate);
    List<PosSalesReportOutTerm> getpsosalesreportoutterm(String plant, String Outlet, String Terminal);
    List<PosSalesReportOutTerm> getpsosalesreportbyfateoutterm(String plant, String fromdate,String Outlet,String Terminal);
    List<PosSalesReportOutTerm> getpsosalesreportbytodateoutterm(String plant, String todate,String Outlet,String Terminal);
    List<PosSalesReportOutTerm> getpsosalesreportbydateoutterm(String plant, String fromdate, String todate,String Outlet,String Terminal);

    List<PosSalesReportWithDate> getpsosalesreportwithdate(String plant);

    List<PosSalesReportWithDate> getpsosalesreportbyfatewithdate(String plant, String fromdate);

    List<PosSalesReportWithDate> getpsosalesreportbytodatewithdate(String plant, String todate);

    List<PosSalesReportWithDate> getpsosalesreportbydatewithdate(String plant, String fromdate, String todate);

    List<PosSalesReportOutTermWithDate> getpsosalesreportouttermwithdate(String plant, String Outlet, String Terminal);

    List<PosSalesReportOutTermWithDate> getpsosalesreportbyfateouttermwithdate(String plant, String fromdate, String Outlet, String Terminal);

    List<PosSalesReportOutTermWithDate> getpsosalesreportbytodateouttermwithdate(String plant, String todate, String Outlet, String Terminal);

    List<PosSalesReportOutTermWithDate> getpsosalesreportbydateouttermwithdate(String plant, String fromdate, String todate, String Outlet, String Terminal);

    List<InventoryDTO> getInventory(String plant, String item, String loc, int offset, Integer productsCount);

    Long countInventory(String plant, String item, String loc);

    List<InventoryQuantityDTO> getInventoryQuantities(String plant, String item, String loc);
}
