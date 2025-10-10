package com.owner.process.usecases.company;

import com.owner.process.helpers.exception.custom.ResourceNotFoundException;
import com.owner.process.usecases.company.company_info.CompanyInfoPojo;
import com.owner.process.usecases.company.user_info.UserInfoPojo;
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

    public String getNumberOfDecimal(String plant) throws Exception {
        String numberOfDecimal;
        try {
            numberOfDecimal = companyRepository.getNumberOfDecimal(plant);
            if (numberOfDecimal == null)
                return "SGD";
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return numberOfDecimal;
    }

    public String getCompanyNmae(String plant) throws Exception {
        String companyname;
        try {
            companyname = companyRepository.getCompanyNmae(plant);
            if (companyname == null)
                return "";
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return companyname;
    }
}
