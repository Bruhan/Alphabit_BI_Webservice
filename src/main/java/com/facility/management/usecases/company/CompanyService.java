package com.facility.management.usecases.company;

import com.facility.management.helpers.exception.custom.ResourceNotFoundException;
import com.facility.management.usecases.company.company_info.CompanyInfoPojo;
import com.facility.management.usecases.company.user_info.UserInfoPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public List<CompanyInfoPojo> getListOfCompanies(String sub) throws Exception {
        List<CompanyInfoPojo> val;
        try {
            val = companyRepository.getListOfCompanies(sub);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }
    public UserInfoPojo getUserDetails(String sub,String plant) throws Exception {
        UserInfoPojo val;
        try {
            val = companyRepository.getUserDetails(sub,plant);
            if (val == null) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }
    public int getUserCompaniesCount(String sub) throws Exception {
        int count;
        try {
            count = companyRepository.getUserCompaniesCount(sub);
            if (count == 0) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return count;
    }
    public String getBaseCurrency(String plant) throws Exception {
        String currencyType;
        try {
            currencyType = companyRepository.getBaseCurrency(plant);
            if (currencyType == null)
                return "SGD";
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return currencyType;
    }

    public String getAutoLocation(String plant) throws Exception {
        String location;
        try {
            location = companyRepository.getAutoLocation(plant);
            if (location == null)
                return "";
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return location;
    }

    public String getTaxPercentage(String plant,String ConfigKey, String taxtype) throws Exception {
        String taxPercentage;
        try {
            taxPercentage = companyRepository.getTaxPercentage(plant,ConfigKey,taxtype);
            if (taxPercentage == null)
                return "0";
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return taxPercentage;
    }

    public int getSalesTaxId(String plt) throws Exception {
        int id;
        try {
            id = companyRepository.getSalesTaxId(plt);
            if (id == 0) {
                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return id;
    }

    public int getShowStockQtyOnApp(String plt) throws Exception {
        int ShowAtockQtyOnApp=0;
        try {
            ShowAtockQtyOnApp = companyRepository.getShowStockQtyOnApp(plt);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return ShowAtockQtyOnApp;
    }

    public int getsalesTaxinclusive(String plant) throws Exception {
        int taxin = 0;
        try {
            taxin = companyRepository.getsalesTaxinclusive(plant);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return taxin;
    }
}
