package com.owner.process.usecases.company;

import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.helpers.common.results.ResultsDTO;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.token.ClaimsSet;
import com.owner.process.persistence.models.UserInfo;
import com.owner.process.usecases.auth.AuthRepository;
import com.owner.process.usecases.company.company_info.CompanyInfoDao;
import com.owner.process.usecases.company.company_info.CompanyInfoPojo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("${spring.base.path}")
public class CompanyController {

    @Autowired
    CompanyService companyService;
    @Autowired
    AuthRepository authRepository;
    @Autowired
    ClaimsSet claimsSet;

    @GetMapping("/user-info")
    public ResponseEntity<Object> getUserDetails(HttpServletRequest request) throws Exception {

        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String sub = claimsDao.getSub();

        //UserInfoPojo userInfoPojo = companyService.getUserDetails(sub,plantName);
        UserInfo userinfo = authRepository.findByUserId(sub);
        int count = companyService.getUserCompaniesCount(sub);
        //mapper
        /*ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);*/

       /* UserInfoDao userInfoDao =modelMapper.map(userInfoPojo,
                new TypeToken<UserInfoDao>(){}.getType());
        userInfoDao.setNoOfCompanies(count);*/
        return new ResponseEntity<>(userinfo, HttpStatus.OK);
    }

    @GetMapping(value = "/user-image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> image(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String sub = claimsDao.getSub();

        //UserInfoPojo userInfoPojo = companyService.getUserDetails(sub,plantName);
        UserInfo userinfo = authRepository.findByUserId(sub);

        final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
                userinfo.getImagePath()
        )));
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(inputStream.contentLength())
                .body(inputStream);

    }

    @GetMapping("/companies")
    public ResponseEntity<Object> getListOfCompanies(HttpServletRequest request) throws Exception {

        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        String empId = claimsDao.getEid();
        String sub = claimsDao.getSub();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime= new DateTimeCalc().getTodayDateTime();

        List<CompanyInfoPojo> companyInfoPojo = companyService.getListOfCompanies(sub);
        //mapper
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        List<CompanyInfoDao> companyInfoDao =modelMapper.map(companyInfoPojo,
                new TypeToken<List<CompanyInfoDao>>(){}.getType());
        //result
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults(companyInfoDao);
        resultsDTO.setPageNumber(1);
        resultsDTO.setPageSize(companyInfoDao.size());
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }
}
