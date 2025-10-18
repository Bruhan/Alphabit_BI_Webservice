package com.owner.process.usecases.SalesAnalytics.SalesAnalytics.Type1;

import java.util.List;
import java.util.Map;

public interface SalesAnalyticsDao {
    Double getAverageBookPerSale(String plant,String fromDate, String toDate, String outlet, String terminal, String orderType);
    Double getAverageOrderValue(String plant,String fromDate, String toDate, String outlet, String terminal, String orderType);
    Double getAverageSellingPrice(String plant,String fromDate, String toDate, String outlet, String terminal, String orderType);
    Double getSalesDensity(String plant,String fromDate, String toDate, String outlet, String terminal, String orderType);
    Double getTotalRevenue(String plant,String fromDate, String toDate, String outlet, String terminal, String orderType);
    Double getTotalUnitsSold(String plant,String fromDate, String toDate, String outlet, String terminal, String orderType);
    Double getGrossMargin(String plant,String fromDate, String toDate, String outlet, String terminal, String orderType);
    Double getAverageSalesPerEmployee(String plant,String fromDate, String toDate, String outlet, String orderType);
    Double getSalesGrowthRate(String plant, String fromDate, String toDate, String outlet, String terminal, String orderType,Integer periodType);
    Double getNetProfitMargin(String plant,String fromDate,String toDate, String outlet,String terminal, String orderType);
    Double getOperatingExpensesRatio(String plant,String fromDate,String toDate, String outlet, String orderType);
    List<Map<String, Object>> getBestSellingItems(String plant, String fromDate, String toDate, String outlet, String terminal, String orderType, int count);



}
