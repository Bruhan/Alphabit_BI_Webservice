package com.owner.process.usecases.SalesAnalytics.SalesAnalytics.Type1;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("SalesAnalyticsDao")
public class SalesAnalyticsDaoImpl implements SalesAnalyticsDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Double getAverageBookPerSale(String plant, String fromDate, String toDate, String outlet, String terminal, String orderType) {
        Session session = sessionFactory.openSession();
        double avgBookPerSale = 0.0;

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ISNULL(SUM(d.QTYOR),0) as totalBooksSales,COUNT(DISTINCT h.DONO) as billCount")
                    .append(" FROM ").append(plant).append("_DODET d ")
                    .append(" LEFT JOIN ").append(plant).append("_DOHDR h on d.DONO=h.DONO ")
                    .append("WHERE 1=1 ");

            if (fromDate != null && !fromDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date,h.ORDDATE,103)>=CONVERT(date,:fromDate,103)");
            }

            if (toDate != null && !toDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date,h.ORDDATE,103)<=CONVERT(date,:toDate,103)");
            }

            if (outlet != null && !outlet.trim().isEmpty()) {
                sql.append(" AND h.OUTLET=:outlet");
            }

            if (terminal != null && !terminal.trim().isEmpty()) {
                sql.append(" AND h.TERMINAL=:terminal");
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                sql.append(" AND h.ORDERTYPE=:orderType");
            }

            Query query = session.createSQLQuery(sql.toString());

            // Set parameters
            if (fromDate != null && !fromDate.trim().isEmpty()) {
                query.setParameter("fromDate", fromDate);
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                query.setParameter("toDate", toDate);
            }
            if (outlet != null && !outlet.trim().isEmpty()) {
                query.setParameter("outlet", outlet);
            }
            if (terminal != null && !terminal.trim().isEmpty()) {
                query.setParameter("terminal", terminal);
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                query.setParameter("orderType", orderType);
            }
            System.out.println("SQL Query: " + sql);
            Object[] result = (Object[]) query.uniqueResult();

            double totalBooks = ((Number) result[0]).doubleValue();
            long totalBills = ((Number) result[1]).longValue();
            avgBookPerSale = totalBills > 0 ? totalBooks / totalBills : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to calculate average book per sale", e);
        } finally {
            session.close();
        }
        return avgBookPerSale;
    }

    @Override
    public Double getAverageOrderValue(String plant, String fromDate, String toDate, String outlet, String terminal, String orderType) {
        Session session = sessionFactory.openSession();
        double avgOrder = 0.0;

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ISNULL(SUM(d.UNITPRICE*d.QTYOR),0) AS totalSalesAmount,COUNT(DISTINCT h.DONO) as billCount FROM ")
                    .append(plant).append("_DODET d").append(" LEFT JOIN ")
                    .append(plant).append("_DOHDR h ").append(" ON d.DONO=h.DONO WHERE 1=1 ");

            if (fromDate != null && !fromDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date,h.ORDDATE,103)>=CONVERT(date,:fromDate,103)");
            }

            if (toDate != null && !toDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date,h.ORDDATE,103)<=CONVERT(date,:toDate,103)");

            }

            if (outlet != null && !outlet.trim().isEmpty()) {
                sql.append(" AND h.OUTLET=:outlet");
            }

            if (terminal != null && !terminal.trim().isEmpty()) {
                sql.append(" AND h.TERMINAL=:terminal");
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                sql.append(" AND h.ORDERTYPE=:orderType");
            }

            Query query = session.createSQLQuery(sql.toString());

            // Set parameters
            if (fromDate != null && !fromDate.trim().isEmpty()) {
                query.setParameter("fromDate", fromDate);
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                query.setParameter("toDate", toDate);
            }
            if (outlet != null && !outlet.trim().isEmpty()) {
                query.setParameter("outlet", outlet);
            }
            if (terminal != null && !terminal.trim().isEmpty()) {
                query.setParameter("terminal", terminal);
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                query.setParameter("orderType", orderType);
            }

            System.out.println("SQL Query: " + sql);

            Object[] result = (Object[]) query.uniqueResult();

            double totalSales = ((Number) result[0]).doubleValue();
            double billsCount = ((Number) result[1]).doubleValue();

            avgOrder = billsCount > 0 ? totalSales / billsCount : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to calculate the average order value");
        } finally {
            session.close();
        }
        return avgOrder;

    }

    @Override
    public Double getAverageSellingPrice(String plant, String fromDate, String toDate, String outlet, String terminal, String orderType) {
        Session session = sessionFactory.openSession();
        Double avgSellingPrice = 0.0;

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ISNULL(SUM(d.UNITPRICE*d.QTYOR),0) as totalSalesAmount,ISNULL(SUM(d.QTYOR),0) as booksQty FROM ")
                    .append(plant).append("_DODET d ").append(" LEFT JOIN ")
                    .append(plant).append("_DOHDR h ").append(" ON d.DONO=h.DONO WHERE 1=1 ");

            if (fromDate != null && !fromDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date,h.ORDDATE,103)>=CONVERT(date,:fromDate,103)");
            }

            if (toDate != null && !toDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date,h.ORDDATE,103)<=CONVERT(date,:toDate,103)");

            }

            if (outlet != null && !outlet.trim().isEmpty()) {
                sql.append(" AND h.OUTLET=:outlet");
            }

            if (terminal != null && !terminal.trim().isEmpty()) {
                sql.append(" AND h.TERMINAL=:terminal");
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                sql.append(" AND h.ORDERTYPE=:orderType");
            }

            Query query = session.createSQLQuery(sql.toString());

            // Set parameters
            if (fromDate != null && !fromDate.trim().isEmpty()) {
                query.setParameter("fromDate", fromDate);
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                query.setParameter("toDate", toDate);
            }
            if (outlet != null && !outlet.trim().isEmpty()) {
                query.setParameter("outlet", outlet);
            }
            if (terminal != null && !terminal.trim().isEmpty()) {
                query.setParameter("terminal", terminal);
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                query.setParameter("orderType", orderType);
            }

            System.out.println("SQL Query: " + sql);

            Object[] result = (Object[]) query.uniqueResult();

            double totalSales = ((Number) result[0]).doubleValue();
            double totalBooks = ((Number) result[1]).doubleValue();

            avgSellingPrice = totalBooks > 0 ? totalSales / totalBooks : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to calculate the average selling price");
        } finally {
            session.close();
        }
        return avgSellingPrice;
    }

    @Override
    public Double getSalesDensity(String plant, String fromDate, String toDate, String outlet, String terminal, String orderType) {
        Session session = sessionFactory.openSession();
        double salesDensity = 0.0;

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT (ISNULL(SUM(d.TAXTOTALAMOUNT),0)) as totalRetailsSales FROM ")
                    .append(plant).append("_DODET d").append(" LEFT JOIN ")
                    .append(plant).append("_DOHDR h ").append(" ON d.DONO=h.DONO WHERE 1=1 ");

            if (fromDate != null && !fromDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date,h.ORDDATE,103)>=CONVERT(date,:fromDate,103)");
            }

            if (toDate != null && !toDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date,h.ORDDATE,103)<=CONVERT(date,:toDate,103)");

            }

            if (outlet != null && !outlet.trim().isEmpty()) {
                sql.append(" AND h.OUTLET=:outlet");
            }

            if (terminal != null && !terminal.trim().isEmpty()) {
                sql.append(" AND h.TERMINAL=:terminal");
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                sql.append(" AND h.ORDERTYPE=:orderType");
            }

            Query query = session.createSQLQuery(sql.toString());

            // Set parameters
            if (fromDate != null && !fromDate.trim().isEmpty()) {
                query.setParameter("fromDate", fromDate);
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                query.setParameter("toDate", toDate);
            }
            if (outlet != null && !outlet.trim().isEmpty()) {
                query.setParameter("outlet", outlet);
            }
            if (terminal != null && !terminal.trim().isEmpty()) {
                query.setParameter("terminal", terminal);
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                query.setParameter("orderType", orderType);
            }

            System.out.println("SQL Query: " + sql);
            Object result = query.uniqueResult();
            salesDensity = result != null ? Math.round(((Number) result).doubleValue() / 100) : 0;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to calculate the sales density");
        } finally {
            session.close();
        }
        return salesDensity;

    }

    @Override
    public Double getTotalRevenue(String plant, String fromDate, String toDate, String outlet, String terminal, String orderType) {
        Session session = sessionFactory.openSession();
        Double totalRevenue = 0.0;

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ISNULL(SUM(d.UNITPRICE*d.QTYOR),0) as totalPrice FROM ")
                    .append(plant).append("_DODET d").append(" LEFT JOIN ")
                    .append(plant).append("_DOHDR h ").append(" ON d.DONO=h.DONO WHERE 1=1 ");

            if (fromDate != null && !fromDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date,h.ORDDATE,103)>=CONVERT(date,:fromDate,103)");
            }

            if (toDate != null && !toDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date,h.ORDDATE,103)<=CONVERT(date,:toDate,103)");

            }

            if (outlet != null && !outlet.trim().isEmpty()) {
                sql.append(" AND h.OUTLET=:outlet");
            }

            if (terminal != null && !terminal.trim().isEmpty()) {
                sql.append(" AND h.TERMINAL=:terminal");
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                sql.append(" AND h.ORDERTYPE=:orderType");
            }

            Query query = session.createSQLQuery(sql.toString());

            // Set parameters
            if (fromDate != null && !fromDate.trim().isEmpty()) {
                query.setParameter("fromDate", fromDate);
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                query.setParameter("toDate", toDate);
            }
            if (outlet != null && !outlet.trim().isEmpty()) {
                query.setParameter("outlet", outlet);
            }
            if (terminal != null && !terminal.trim().isEmpty()) {
                query.setParameter("terminal", terminal);
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                query.setParameter("orderType", orderType);
            }

            System.out.println("SQL Query: " + sql);
            Object result = query.uniqueResult();
            totalRevenue = result != null ? ((Number) result).doubleValue() : 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to calculate the total revenue");
        } finally {
            session.close();
        }
        return totalRevenue;
    }

    @Override
    public Double getTotalUnitsSold(String plant, String fromDate, String toDate, String outlet, String terminal, String orderType) {
        Session session = sessionFactory.openSession();
        double totalUnitsSold = 0.0;

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ISNULL(SUM(d.QTYOR),0) as totalUnitsSold FROM ")
                    .append(plant).append("_DODET d").append(" LEFT JOIN ")
                    .append(plant).append("_DOHDR h ").append(" ON d.DONO=h.DONO WHERE 1=1 ");

            if (fromDate != null && !fromDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date,h.ORDDATE,103)>=CONVERT(date,:fromDate,103)");
            }

            if (toDate != null && !toDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date,h.ORDDATE,103)<=CONVERT(date,:toDate,103)");

            }

            if (outlet != null && !outlet.trim().isEmpty()) {
                sql.append(" AND h.OUTLET=:outlet");
            }

            if (terminal != null && !terminal.trim().isEmpty()) {
                sql.append(" AND h.TERMINAL=:terminal");
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                sql.append(" AND h.ORDERTYPE=:orderType");
            }


            Query query = session.createSQLQuery(sql.toString());

            // Set parameters
            if (fromDate != null && !fromDate.trim().isEmpty()) {
                query.setParameter("fromDate", fromDate);
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                query.setParameter("toDate", toDate);
            }
            if (outlet != null && !outlet.trim().isEmpty()) {
                query.setParameter("outlet", outlet);
            }
            if (terminal != null && !terminal.trim().isEmpty()) {
                query.setParameter("terminal", terminal);
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                query.setParameter("orderType", orderType);
            }


            System.out.println("SQL Query: " + sql);
            Object result = query.uniqueResult();
            totalUnitsSold = result != null ? ((Number) result).doubleValue() : 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to calculate the total units sold");
        } finally {
            session.close();
        }
        return totalUnitsSold;
    }

    @Override
    public Double getGrossMargin(String plant, String fromDate, String toDate, String outlet, String terminal, String orderType) {
        Session session = sessionFactory.openSession();
        double grossMargin = 0.0;

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ISNULL(SUM(d.UNITPRICE*d.QTYOR),0) as totalRevenue, ")
                    .append("ISNULL(SUM(d.UNITCOST*d.QTYOR),0) as costOfGoodsSold FROM ")
                    .append(plant).append("_DODET d").append(" LEFT JOIN ")
                    .append(plant).append("_DOHDR h ").append(" ON d.DONO=h.DONO WHERE 1=1 ");

            if (fromDate != null && !fromDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date,h.ORDDATE,103)>=CONVERT(date,:fromDate,103)");
            }

            if (toDate != null && !toDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date,h.ORDDATE,103)<=CONVERT(date,:toDate,103)");

            }

            if (outlet != null && !outlet.trim().isEmpty()) {
                sql.append(" AND h.OUTLET=:outlet");
            }

            if (terminal != null && !terminal.trim().isEmpty()) {
                sql.append(" AND h.TERMINAL=:terminal");
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                sql.append(" AND h.ORDERTYPE=:orderType");
            }

            Query query = session.createSQLQuery(sql.toString());

            // Set parameters
            if (fromDate != null && !fromDate.trim().isEmpty()) {
                query.setParameter("fromDate", fromDate);
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                query.setParameter("toDate", toDate);
            }
            if (outlet != null && !outlet.trim().isEmpty()) {
                query.setParameter("outlet", outlet);
            }
            if (terminal != null && !terminal.trim().isEmpty()) {
                query.setParameter("terminal", terminal);
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                query.setParameter("orderType", orderType);
            }

            System.out.println("SQL Query: " + sql);
            Object[] result = (Object[]) query.uniqueResult();
            double totalRevenue = ((Number) result[0]).doubleValue();
            double totalCostOfGoodsSold = ((Number) result[1]).doubleValue();
            grossMargin = totalRevenue > 0 ? Math.round(((totalRevenue - totalCostOfGoodsSold) / totalRevenue) * 100) : 0.0;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to calculate the gross margin");
        } finally {
            session.close();
        }
        return grossMargin;
    }



    @Override
    public Double getAverageSalesPerEmployee(String plant, String fromDate, String toDate, String outlet, String orderType) {

        Session session = sessionFactory.openSession();
        double avgSalesPerEmployee = 0.0;

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ISNULL(SUM(d.UNITPRICE*d.QTYOR),0) AS totalRevenue, ")
                    .append("(SELECT COUNT(EMPNO) FROM ").append(plant).append("_EMP_MST e WHERE 1=1 ");

            if (outlet != null && !outlet.trim().isEmpty()) {
                sql.append(" AND e.OUTLET=:outlet");
            }
            sql.append(") AS employeeCount ")
                    .append("FROM ").append(plant).append("_DODET d ")
                    .append("LEFT JOIN ").append(plant).append("_DOHDR h ON d.DONO=h.DONO ")
                    .append("WHERE 1=1 ");

            if (fromDate != null && !fromDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date,h.ORDDATE,103) >= CONVERT(date,:fromDate,103)");
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date,h.ORDDATE,103) <= CONVERT(date,:toDate,103)");
            }
            if (outlet != null && !outlet.trim().isEmpty()) {
                sql.append(" AND h.OUTLET=:outlet");
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                sql.append(" AND h.ORDERTYPE=:orderType");
            }

            Query query = session.createSQLQuery(sql.toString());

            // Set parameters
            if (fromDate != null && !fromDate.trim().isEmpty()) {
                query.setParameter("fromDate", fromDate);
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                query.setParameter("toDate", toDate);
            }
            if (outlet != null && !outlet.trim().isEmpty()) {
                query.setParameter("outlet", outlet);
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                query.setParameter("orderType", orderType);
            }

            System.out.println("SQL Query: " + sql);
            Object[] result = (Object[]) query.uniqueResult();
            double totalRevenue = ((Number) result[0]).doubleValue();
            double empCount = ((Number) result[1]).doubleValue();

            avgSalesPerEmployee = empCount > 0 ? totalRevenue / empCount : 0.0;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to calculate the average sales per employee");
        } finally {
            session.close();
        }
        return avgSalesPerEmployee;
    }


    @Override
    public Double getSalesGrowthRate(String plant, String fromDate, String toDate, String outlet, String terminal, String orderType, Integer periodType) {

        Session session = sessionFactory.openSession();
        double salesGrowthRate = 0.0;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate from = LocalDate.parse(fromDate, formatter);
            LocalDate to = LocalDate.parse(toDate, formatter);

            LocalDate prevTo = from.minusDays(1);
            LocalDate prevFrom;

            switch (periodType != null ? periodType : 0) {
                /* case values to calculate based on given period type=days/week/month/year
                1=days or week,
                2=month,
                3=year
                default=days
                */
                case 1:
                    long days = ChronoUnit.DAYS.between(from, to) + 1;
                    prevFrom = prevTo.minusDays(days - 1);
                    break;
                case 2:
                    prevFrom = from.minusMonths(1);
                    prevTo = to.minusMonths(1);
                    break;
                case 3:
                    prevFrom = from.minusYears(1);
                    prevTo = to.minusYears(1);
                    break;
                default:
                    long daysDefault = ChronoUnit.DAYS.between(from, to) + 1;
                    prevFrom = prevTo.minusDays(daysDefault - 1);
                    break;
            }

            String prevFromStr = prevFrom.format(formatter);
            String prevToStr = prevTo.format(formatter);


            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ")
                    .append("ISNULL(SUM(CASE WHEN CONVERT(date,h.ORDDATE,103) BETWEEN CONVERT(date,:fromDate,103) AND CONVERT(date,:toDate,103) ")
                    .append("THEN d.UNITPRICE*d.QTYOR ELSE 0 END),0) AS currentSales, ")
                    .append("ISNULL(SUM(CASE WHEN CONVERT(date,h.ORDDATE,103) BETWEEN CONVERT(date,:prevFromDate,103) AND CONVERT(date,:prevToDate,103) ")
                    .append("THEN d.UNITPRICE*d.QTYOR ELSE 0 END),0) AS previousSales ")
                    .append("FROM ").append(plant).append("_DODET d ")
                    .append("LEFT JOIN ").append(plant).append("_DOHDR h ON d.DONO=h.DONO ")
                    .append("WHERE 1=1 ");

            if (outlet != null && !outlet.trim().isEmpty()) {
                sql.append(" AND h.OUTLET=:outlet");
            }
            if (terminal != null && !terminal.trim().isEmpty()) {
                sql.append(" AND h.TERMINAL=:terminal");
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                sql.append(" AND h.ORDERTYPE=:orderType");
            }

            Query query = session.createSQLQuery(sql.toString());

            // Set parameters
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            query.setParameter("prevFromDate", prevFromStr);
            query.setParameter("prevToDate", prevToStr);

            if (outlet != null && !outlet.trim().isEmpty()) {
                query.setParameter("outlet", outlet);
            }
            if (terminal != null && !terminal.trim().isEmpty()) {
                query.setParameter("terminal", terminal);
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                query.setParameter("orderType", orderType);
            }

            System.out.println("SQL Query: " + sql);

            Object[] result = (Object[]) query.uniqueResult();
            double currentSales = ((Number) result[0]).doubleValue();
            double previousSales = ((Number) result[1]).doubleValue();

            if (previousSales > 0) {
                salesGrowthRate = Math.round(((currentSales - previousSales) / previousSales) * 100);
            } else {
                salesGrowthRate = 0.0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to calculate Sales Growth Rate");
        } finally {
            session.close();
        }

        return salesGrowthRate;
    }

    @Override
    public Double getNetProfitMargin(String plant, String fromDate, String toDate, String outlet, String terminal, String orderType) {
        Session session = sessionFactory.openSession();
        double netProfitMargin = 0.0;

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ")
                    .append("ISNULL(SUM(d.UNITPRICE * d.QTYOR), 0) AS totalRevenue, ")
                    .append("ISNULL(SUM(d.UNITCOST * d.QTYOR), 0) AS COGS, ")
                    .append("(SELECT ISNULL(SUM(e.TOTAL_AMOUNT), 0) ")
                    .append(" FROM ").append(plant).append("_FINEXPENSESHDR e WHERE 1=1 ");

            if (fromDate != null && !fromDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date, e.EXPENSES_DATE, 103) >= CONVERT(date, :fromDate, 103)");
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date, e.EXPENSES_DATE, 103) <= CONVERT(date, :toDate, 103)");
            }

            sql.append(") AS totalExpenses ")
                    .append("FROM ").append(plant).append("_DODET d ")
                    .append("LEFT JOIN ").append(plant).append("_DOHDR h ON d.DONO = h.DONO ")
                    .append("WHERE 1=1 ");

            // Add filters
            if (fromDate != null && !fromDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date, h.ORDDATE, 103) >= CONVERT(date, :fromDate, 103)");
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date, h.ORDDATE, 103) <= CONVERT(date, :toDate, 103)");
            }
            if (outlet != null && !outlet.trim().isEmpty()) {
                sql.append(" AND h.OUTLET = :outlet");
            }
            if (terminal != null && !terminal.trim().isEmpty()) {
                sql.append(" AND h.TERMINAL = :terminal");
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                sql.append(" AND h.ORDERTYPE = :orderType");
            }

            Query query = session.createSQLQuery(sql.toString());

            // Set parameters
            if (fromDate != null && !fromDate.trim().isEmpty()) {
                query.setParameter("fromDate", fromDate);
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                query.setParameter("toDate", toDate);
            }
            if (outlet != null && !outlet.trim().isEmpty()) {
                query.setParameter("outlet", outlet);
            }
            if (terminal != null && !terminal.trim().isEmpty()) {
                query.setParameter("terminal", terminal);
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                query.setParameter("orderType", orderType);
            }

            // Execute query
            Object[] result = (Object[]) query.uniqueResult();

            double totalRevenue = ((Number) result[0]).doubleValue();
            double cogs = ((Number) result[1]).doubleValue();
            double totalExpenses = ((Number) result[2]).doubleValue();

            double netIncome = totalRevenue - (cogs + totalExpenses);

            netProfitMargin=totalRevenue > 0? Math.round((netIncome / totalRevenue) * 100):0.0;

            System.out.println("SQL Query: " + sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to calculate Net Profit Margin");
        } finally {
            session.close();
        }

        return netProfitMargin;
    }



    @Override
    public Double getOperatingExpensesRatio(String plant, String fromDate, String toDate, String outlet, String orderType) {
        Session session = sessionFactory.openSession();
        double operatingExpensesRatio = 0.0;

        try {

            StringBuilder expenseSql = new StringBuilder();
            expenseSql.append("SELECT ISNULL(SUM(e.TOTAL_AMOUNT),0) AS operatingExpenses ");
            expenseSql.append("FROM ");
            expenseSql.append(plant);
            expenseSql.append("_FINEXPENSESHDR  e WHERE 1=1");

            if (fromDate != null && !fromDate.trim().isEmpty()) {
                expenseSql.append(" AND CONVERT(date,e.EXPENSES_DATE,103) >= CONVERT(date,:fromDate,103)");
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                expenseSql.append(" AND CONVERT(date,e.EXPENSES_DATE,103) <= CONVERT(date,:toDate,103)");
            }

            //set parameters to expense query
            Query expenseQuery = session.createSQLQuery(expenseSql.toString());
            if (fromDate != null && !fromDate.trim().isEmpty()) {
                expenseQuery.setParameter("fromDate", fromDate);
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                expenseQuery.setParameter("toDate", toDate);
            }

            double operatingExpenses = ((Number) expenseQuery.uniqueResult()).doubleValue();

            StringBuilder revenueSql = new StringBuilder();
            revenueSql.append("SELECT ISNULL(SUM(d.UNITPRICE * d.QTYOR),0) AS totalRevenue ")
                    .append("FROM ").append(plant).append("_DODET d ")
                    .append("LEFT JOIN ").append(plant).append("_DOHDR h ON d.DONO = h.DONO ")
                    .append("WHERE 1=1 ");

            if (fromDate != null && !fromDate.trim().isEmpty()) {
                revenueSql.append(" AND CONVERT(date,h.ORDDATE,103) >= CONVERT(date,:fromDate,103)");
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                revenueSql.append(" AND CONVERT(date,h.ORDDATE,103) <= CONVERT(date,:toDate,103)");
            }
            if (outlet != null && !outlet.trim().isEmpty()) {
                revenueSql.append(" AND h.OUTLET=:outlet");
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                revenueSql.append(" AND h.ORDERTYPE=:orderType");
            }

            Query revenueQuery = session.createSQLQuery(revenueSql.toString());

            // Set parameters
            if (fromDate != null && !fromDate.trim().isEmpty()) {
                revenueQuery.setParameter("fromDate", fromDate);
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                revenueQuery.setParameter("toDate", toDate);
            }
            if (outlet != null && !outlet.trim().isEmpty()) {
                revenueQuery.setParameter("outlet", outlet);
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                revenueQuery.setParameter("orderType", orderType);
            }

            double totalRevenue = ((Number) revenueQuery.uniqueResult()).doubleValue();

            operatingExpensesRatio = totalRevenue > 0 ? Math.round((operatingExpenses / totalRevenue)*100) : 0.0;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to calculate the operating expenses ratio");
        } finally {
            session.close();
        }
        return operatingExpensesRatio;
    }

    @Override
    public List<Map<String, Object>> getBestSellingItems(String plant, String fromDate, String toDate, String outlet, String terminal, String orderType, int count) {
        Session session = sessionFactory.openSession();
        List<Map<String, Object>> bestSellingItems = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT TOP(:count) A.* FROM ( ")
            .append("SELECT d.ITEM,d.ITEMDESC,SUM(d.QTYOR) AS QTY ")
            .append("FROM ").append(plant).append("_DODET d ")
            .append("LEFT JOIN ").append(plant)
            .append("_DOHDR h ON d.DONO=h.DONO WHERE 1=1 ");

            //optional filters
            if (fromDate != null && !fromDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date,h.ORDDATE,103) >= CONVERT(date,:fromDate,103)");
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                sql.append(" AND CONVERT(date,h.ORDDATE,103) <= CONVERT(date,:toDate,103)");
            }
            if (outlet != null && !outlet.trim().isEmpty()) {
                sql.append(" AND h.OUTLET=:outlet");
            }
            if (terminal != null && !terminal.trim().isEmpty()) {
                sql.append(" AND h.TERMINAL=:terminal");
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                sql.append(" AND h.ORDERTYPE=:orderType");
            }

            sql.append(" GROUP BY d.ITEM, d.ItemDesc) AS A ");
            sql.append("ORDER BY A.QTY DESC");

            Query query = session.createSQLQuery(sql.toString());
            query.setParameter("count",count);

            // Set parameters
            if (fromDate != null && !fromDate.trim().isEmpty()) {
                query.setParameter("fromDate", fromDate);
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                query.setParameter("toDate", toDate);
            }
            if (outlet != null && !outlet.trim().isEmpty()) {
                query.setParameter("outlet", outlet);
            }
            if (terminal != null && !terminal.trim().isEmpty()) {
                query.setParameter("terminal", terminal);
            }
            if (orderType != null && !orderType.trim().isEmpty()) {
                query.setParameter("orderType", orderType);
            }

            System.out.println("SQL Query: " + sql);
            List<Object[]> rows = query.list();

            for (Object[] row : rows) {
                Map<String, Object> map = new HashMap<>();
                map.put("ITEM", row[0]);
                map.put("ITEMDESC", row[1]);
                map.put("QTY", ((Number) row[2]).intValue());
                bestSellingItems.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch Best Selling Items");
        } finally {
            session.close();
        }

        return bestSellingItems;
    }


}




