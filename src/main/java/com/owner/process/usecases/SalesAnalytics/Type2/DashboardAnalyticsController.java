package com.owner.process.usecases.SalesAnalytics.SalesAnalytics.Type2;

import com.owner.process.helpers.common.results.ResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${spring.base.path}/dashboardAnalytics")
public class DashboardAnalyticsController
{

    @Autowired
    DashboardAnalyticsDao dashboardAnalyticsDao;
    @GetMapping("/averageBookPerSale")
    public ResponseEntity<?> getAverageBookPerSale(@RequestParam String plant,
                                                   @RequestParam(required = false) String outlet,
                                                   @RequestParam(required = false) String terminal)
    {
        ResultDao resultDao=new ResultDao();

        try {
            Double averageBookPerSale = dashboardAnalyticsDao.getAverageBookPerSale(plant,outlet, terminal);
            if (averageBookPerSale != null && averageBookPerSale>0) {
                resultDao.setResults(averageBookPerSale);
                resultDao.setStatusCode(HttpStatus.OK.value());
                resultDao.setMessage("Average Book per sale");
                return new ResponseEntity<>(resultDao, HttpStatus.OK);
            } else {
                resultDao.setResults("NOT OK");
                resultDao.setStatusCode(HttpStatus.NOT_FOUND.value());
                resultDao.setMessage("No data found");
                return new ResponseEntity<>(resultDao, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resultDao.setResults("NOT OK");
            resultDao.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultDao.setMessage("Internal Server Error");
            return new ResponseEntity<>(resultDao, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/averageOrder")
    public ResponseEntity<?> getAverageOrderValue(@RequestParam String plant,
                                                  @RequestParam(required = false) String outlet,
                                                  @RequestParam(required = false) String terminal)
    {
        ResultDao resultDao=new ResultDao();

        try {
            Double averageOrderValue = dashboardAnalyticsDao.getAverageOrderValue(plant,outlet, terminal);
            if (averageOrderValue != null && averageOrderValue>0) {
                resultDao.setResults(averageOrderValue);
                resultDao.setStatusCode(HttpStatus.OK.value());
                resultDao.setMessage("Average Order Value");
                return new ResponseEntity<>(resultDao, HttpStatus.OK);
            } else {
                resultDao.setResults("NOT OK");
                resultDao.setStatusCode(HttpStatus.NOT_FOUND.value());
                resultDao.setMessage("No data found");
                return new ResponseEntity<>(resultDao, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resultDao.setResults("NOT OK");
            resultDao.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultDao.setMessage("Internal Server Error");
            return new ResponseEntity<>(resultDao, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/averageSellingPrice")
    public ResponseEntity<?> getAverageSellingPrice(@RequestParam String plant,
                                                    @RequestParam(required = false) String outlet,
                                                    @RequestParam(required = false) String terminal)
    {
        ResultDao resultDao=new ResultDao();

        try {
            Double averageSellingPrice = dashboardAnalyticsDao.getAverageSellingPrice(plant,outlet, terminal);
            if (averageSellingPrice != null && averageSellingPrice>0) {
                resultDao.setResults(averageSellingPrice);
                resultDao.setStatusCode(HttpStatus.OK.value());
                resultDao.setMessage("Average Selling Price");
                return new ResponseEntity<>(resultDao, HttpStatus.OK);
            } else {
                resultDao.setResults("NOT OK");
                resultDao.setStatusCode(HttpStatus.NOT_FOUND.value());
                resultDao.setMessage("No data found");
                return new ResponseEntity<>(resultDao, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resultDao.setResults("NOT OK");
            resultDao.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultDao.setMessage("Internal Server Error");
            return new ResponseEntity<>(resultDao, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/salesDensity")
    public ResponseEntity<?> getSalesDensity(@RequestParam String plant,
                                             @RequestParam(required = false) String outlet,
                                             @RequestParam(required = false) String terminal)
    {
        ResultDao resultDao=new ResultDao();

        try {
            Double salesDensity = dashboardAnalyticsDao.getSalesDensity(plant,outlet, terminal);
            if (salesDensity != null && salesDensity>0) {
                resultDao.setResults(salesDensity);
                resultDao.setStatusCode(HttpStatus.OK.value());
                resultDao.setMessage("Sales Density");
                return new ResponseEntity<>(resultDao, HttpStatus.OK);
            } else {
                resultDao.setResults("NOT OK");
                resultDao.setStatusCode(HttpStatus.NOT_FOUND.value());
                resultDao.setMessage("No data found");
                return new ResponseEntity<>(resultDao, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resultDao.setResults("NOT OK");
            resultDao.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultDao.setMessage("Internal Server Error");
            return new ResponseEntity<>(resultDao, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/totalRevenue")
    public ResponseEntity<?> getTotalRevenue(@RequestParam String plant,
                                             @RequestParam(required = false) String outlet,
                                             @RequestParam(required = false) String terminal)
    {
        ResultDao resultDao=new ResultDao();

        try {
            Double totalRevenue = dashboardAnalyticsDao.getTotalRevenue(plant, outlet, terminal);
            if (totalRevenue != null && totalRevenue>0) {
                resultDao.setResults(totalRevenue);
                resultDao.setStatusCode(HttpStatus.OK.value());
                resultDao.setMessage("Total Revenue");
                return new ResponseEntity<>(resultDao, HttpStatus.OK);
            } else {
                resultDao.setResults("NOT OK");
                resultDao.setStatusCode(HttpStatus.NOT_FOUND.value());
                resultDao.setMessage("No data found");
                return new ResponseEntity<>(resultDao, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resultDao.setResults("NOT OK");
            resultDao.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultDao.setMessage("Internal Server Error");
            return new ResponseEntity<>(resultDao, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/totalUnitsSold")
    public ResponseEntity<?> getTotalUnitsSold(@RequestParam String plant,
                                               @RequestParam(required = false) String outlet,
                                               @RequestParam(required = false) String terminal)
    {
        ResultDao resultDao=new ResultDao();

        try {
            Double totalUnitsSold = dashboardAnalyticsDao.getTotalUnitsSold(plant,outlet, terminal);
            if (totalUnitsSold != null && totalUnitsSold>0) {
                resultDao.setResults(totalUnitsSold);
                resultDao.setStatusCode(HttpStatus.OK.value());
                resultDao.setMessage("Total Units Sold");
                return new ResponseEntity<>(resultDao, HttpStatus.OK);
            } else {
                resultDao.setResults("NOT OK");
                resultDao.setStatusCode(HttpStatus.NOT_FOUND.value());
                resultDao.setMessage("No data found");
                return new ResponseEntity<>(resultDao, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resultDao.setResults("NOT OK");
            resultDao.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultDao.setMessage("Internal Server Error");
            return new ResponseEntity<>(resultDao, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/grossMargin")
    public ResponseEntity<?> getGrossMargin(@RequestParam String plant,
                                            @RequestParam(required = false) String outlet,
                                            @RequestParam(required = false) String terminal)
    {
        ResultDao resultDao=new ResultDao();

        try {
            Double grossMargin = dashboardAnalyticsDao.getGrossMargin(plant, outlet, terminal);
            if (grossMargin != null && grossMargin>0) {
                resultDao.setResults(grossMargin+"%");
                resultDao.setStatusCode(HttpStatus.OK.value());
                resultDao.setMessage("Gross Margin");
                return new ResponseEntity<>(resultDao, HttpStatus.OK);
            } else {
                resultDao.setResults("NOT OK");
                resultDao.setStatusCode(HttpStatus.NOT_FOUND.value());
                resultDao.setMessage("No data found");
                return new ResponseEntity<>(resultDao, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resultDao.setResults("NOT OK");
            resultDao.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultDao.setMessage("Internal Server Error");
            return new ResponseEntity<>(resultDao, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/avgSalesPerEmployee")
    public ResponseEntity<?> getAverageSalesPerEmployee(@RequestParam String plant,
                                                        @RequestParam(required = false) String outlet)
    {
        ResultDao resultDao=new ResultDao();

        try {
            Double avg = dashboardAnalyticsDao.getAverageSalesPerEmployee(plant,outlet);
            if (avg != null && avg>0) {
                resultDao.setResults(avg);
                resultDao.setStatusCode(HttpStatus.OK.value());
                resultDao.setMessage("Average sales per Employee");
                return new ResponseEntity<>(resultDao, HttpStatus.OK);
            } else {
                resultDao.setResults("NOT OK");
                resultDao.setStatusCode(HttpStatus.NOT_FOUND.value());
                resultDao.setMessage("No data found");
                return new ResponseEntity<>(resultDao, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resultDao.setResults("NOT OK");
            resultDao.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultDao.setMessage("Internal Server Error");
            return new ResponseEntity<>(resultDao, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/salesGrowthRate")
    public ResponseEntity<?> getSalesGrowthRate(@RequestParam String plant, @RequestParam String fromDate,
                                                @RequestParam String toDate, @RequestParam(required = false) Integer periodType,
                                                @RequestParam(required = false) String outlet,
                                                @RequestParam(required = false) String terminal)
    {
        ResultDao resultDao=new ResultDao();

        try {
            Double salesGrowthRate = dashboardAnalyticsDao.getSalesGrowthRate(plant, fromDate, toDate, outlet,terminal,periodType);
            if (salesGrowthRate != null && salesGrowthRate>0) {
                resultDao.setResults(salesGrowthRate+"%");
                resultDao.setStatusCode(HttpStatus.OK.value());
                resultDao.setMessage("Sales Growth Rate");
                return new ResponseEntity<>(resultDao, HttpStatus.OK);
            } else {
                resultDao.setResults("NOT OK");
                resultDao.setStatusCode(HttpStatus.NOT_FOUND.value());
                resultDao.setMessage("No data found");
                return new ResponseEntity<>(resultDao, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resultDao.setResults("NOT OK");
            resultDao.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultDao.setMessage("Internal Server Error");
            return new ResponseEntity<>(resultDao, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/netProfitMargin")
    public ResponseEntity<?> getNetProfitMargin(@RequestParam String plant, @RequestParam String fromDate,
                                                @RequestParam String toDate,
                                                @RequestParam(required = false) String outlet,
                                                @RequestParam(required = false) String terminal)
    {
        ResultDao resultDao=new ResultDao();

        try {
            Double netProfitMargin = dashboardAnalyticsDao.getNetProfitMargin(plant, fromDate, toDate, outlet,terminal);
            if (netProfitMargin != null && netProfitMargin>0) {
                resultDao.setResults(netProfitMargin+"%");
                resultDao.setStatusCode(HttpStatus.OK.value());
                resultDao.setMessage("Net Profit Margin");
                return new ResponseEntity<>(resultDao, HttpStatus.OK);
            } else {
                resultDao.setResults("NOT OK");
                resultDao.setStatusCode(HttpStatus.NOT_FOUND.value());
                resultDao.setMessage("No data found");
                return new ResponseEntity<>(resultDao, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resultDao.setResults("NOT OK");
            resultDao.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultDao.setMessage("Internal Server Error");
            return new ResponseEntity<>(resultDao, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/operatingExpensesRatio")
    public ResponseEntity<?> getOperatingExpensesRatio(@RequestParam String plant,
                                                       @RequestParam String fromDate,
                                                       @RequestParam String toDate,
                                                       @RequestParam(required = false) String outlet,
                                                       @RequestParam(required = false) String terminal)
    {
        ResultDao resultDao=new ResultDao();

        try {
            Double operatingExpensesRatio = dashboardAnalyticsDao.getOperatingExpensesRatio(plant,fromDate,toDate,outlet);
            if (operatingExpensesRatio!=null && operatingExpensesRatio>0) {
                resultDao.setResults(operatingExpensesRatio+"%");
                resultDao.setStatusCode(HttpStatus.OK.value());
                resultDao.setMessage("Operating Expenses Ratio");
                return new ResponseEntity<>(resultDao, HttpStatus.OK);
            } else {
                resultDao.setResults("NOT OK");
                resultDao.setStatusCode(HttpStatus.NOT_FOUND.value());
                resultDao.setMessage("No data found");
                return new ResponseEntity<>(resultDao, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resultDao.setResults("NOT OK");
            resultDao.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultDao.setMessage("Internal Server Error");
            return new ResponseEntity<>(resultDao, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/bestSelling")
    public ResponseEntity<?> getBestSellingItems(@RequestParam String plant,
                                                 @RequestParam(required = false) String outlet,
                                                 @RequestParam(required = false) String terminal,
                                                 @RequestParam int count)
    {
        ResultDao resultDao=new ResultDao();

        try {
            List<Map<String,Object>> bestSellingItems = dashboardAnalyticsDao.getBestSellingItems(plant,outlet,terminal,count);
            if (!bestSellingItems.isEmpty()) {
                resultDao.setResults(bestSellingItems);
                resultDao.setStatusCode(HttpStatus.OK.value());
                resultDao.setMessage("Best Selling Items");
                return new ResponseEntity<>(resultDao, HttpStatus.OK);
            } else {
                resultDao.setResults("NOT OK");
                resultDao.setStatusCode(HttpStatus.NOT_FOUND.value());
                resultDao.setMessage("No data found");
                return new ResponseEntity<>(resultDao, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resultDao.setResults("NOT OK");
            resultDao.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultDao.setMessage("Internal Server Error");
            return new ResponseEntity<>(resultDao, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
