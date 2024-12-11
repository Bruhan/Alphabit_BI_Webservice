package com.facility.management.usecases.company;

import com.facility.management.persistence.models.PlantMst;
import com.facility.management.usecases.company.company_info.CompanyInfoPojo;
import com.facility.management.usecases.company.user_info.UserInfoPojo;
import com.facility.management.usecases.company.company_info.CompanyInfoPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<PlantMst,Long> {

    @Query(value="select plt.plant,plt.PLNTDESC as plantDesc " +
            " from PLNTMST plt left join user_Info usr "+
            "on usr.DEPT = plt.PLANT "+
            "where usr.USER_ID =:sub",nativeQuery = true)
    List<CompanyInfoPojo> getListOfCompanies(String sub);

    @Query(value="select UI_PKEY as userId,USER_NAME as userName, "+
            "EMAIL as email from user_Info "+
            "where USER_ID = :sub and dept = :dept",nativeQuery = true)
    UserInfoPojo getUserDetails(String sub,String dept);

    @Query(value="select count(USER_ID) as noOfCompanies from user_Info "+
            "where USER_ID = :sub ",nativeQuery = true)
    int getUserCompaniesCount(String sub);

    @Query(value = "select TOP 1 BASE_CURRENCY as baseCurrency from PLNTMST " +
            "where plant =?1 ", nativeQuery = true)
    String getBaseCurrency(String plt);

    @Query(value = "select TOP 1 LOC as location from ##plant##API_INVENTORY_LOC " +
            "where PLANT =?1 ", nativeQuery = true)
    String getAutoLocation(String plt);

    @Query(value = "select TOP 1 NUMBEROFDECIMAL as numberOfDecimal from PLNTMST " +
            "where plant =?1 ", nativeQuery = true)
    String getNumberOfDecimal(String plt);

    @Query(value = "select TOP 1 gstpercentage as taxpercentage from ##plant##COMPANY_CONFIG " +
            "where plant =?1 AND configkey =?2 AND gsttype=?3", nativeQuery = true)
    String getTaxPercentage(String plant,String ConfigKey, String taxtype);

    @Query(value="SELECT ID FROM FINCOUNTRYTAXTYPE WHERE CONFIGKEY='OUTBOUND' AND TAXTYPE='STANDARD RATED' AND " +
            "COUNTRY_CODE = (SELECT TOP 1 C.COUNTRY_CODE FROM COUNTRYMASTER AS C JOIN PLNTMST AS P " +
            "ON P.COUNTY = C.COUNTRYNAME WHERE P.PLANT=?1)",nativeQuery = true)
    int getSalesTaxId(String plt);

    @Query(value = "select TOP 1 ISNULL(SHOW_STOCKQTY_ONAPP,0) as ShowAtockQtyOnApp from PLNTMST " +
            "where plant =?1 ", nativeQuery = true)
    int getShowStockQtyOnApp(String plt);

    @Query(value = "select TOP 1 ISNULL(ISSALESAPP_TAXINCLUSIVE,0) as TAXINC from PLNTMST " +
            "where plant =?1 ", nativeQuery = true)
    int getsalesTaxinclusive(String plt);

}
