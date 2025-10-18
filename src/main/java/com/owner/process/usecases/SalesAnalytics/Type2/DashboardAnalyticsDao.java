package com.owner.process.usecases.SalesAnalytics.SalesAnalytics.Type2;

import java.util.List;
import java.util.Map;

public interface DashboardAnalyticsDao {
    Double getAverageBookPerSale(String plant, String outlet, String terminal);
    Double getAverageOrderValue(String plant, String outlet, String terminal);
    Double getAverageSellingPrice(String plant, String outlet, String terminal);
    Double getSalesDensity(String plant, String outlet, String terminal);
    Double getTotalRevenue(String plant, String outlet, String terminal);
    Double getTotalUnitsSold(String plant, String outlet, String terminal);
    Double getGrossMargin(String plant, String outlet, String terminal);
    Double getAverageSalesPerEmployee(String plant,String outlet);
    Double getSalesGrowthRate(String plant, String fromDate, String toDate, String outlet, String terminal, Integer periodType);
    Double getNetProfitMargin(String plant,String fromDate,String toDate, String outlet,String terminal);
    Double getOperatingExpensesRatio(String plant, String fromDate, String toDate, String outlet);
    List<Map<String, Object>> getBestSellingItems(String plant, String outlet,String terminal,int topCount);

}
