package com.facility.management.usecases.auth;


import com.facility.management.helpers.common.results.ResultDao;
import com.facility.management.helpers.common.results.ResultsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.helpers.exception.custom.ResourceNotFoundException;
import com.facility.management.helpers.utils.CryptoAlgoUtil;
import com.facility.management.helpers.utils.JwtUtil;
import com.facility.management.persistence.models.*;
import com.facility.management.usecases.auth.dao.AuthDao;
import com.facility.management.usecases.auth.dto.AuthRequestDTO;
import com.facility.management.usecases.auth.dto.AuthTokensDTO;
import com.facility.management.usecases.auth.exceptions.WrongCredentialsException;
import com.facility.management.usecases.auth.userlocation.UserLocationService;
import com.facility.management.usecases.company.CompanyService;
import com.facility.management.usecases.plant.dao.PlantDao;
import com.facility.management.usecases.plant.dto.PlantDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    AuthRepository authRepository;

    @Autowired
    AuthDao authDao;

    @Autowired
    CompanyService companyService;
    @Autowired
    UserLocationService userLocationService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtTokenUtil;

    @Value("${spring.crypto.key-path}")
    private String pathName;

    @Autowired
    PlantDao plantDao;

    public AuthService() {
    }

//    public void setPlant(String plant) {
//        this.plant = plant;
//    }

    String plant;

    //used in Jwtrequestfilter
    @Override
    public UserDetails loadUserByUsername(String empUserName) throws UsernameNotFoundException {


//        UserInfo userDao = authDao.findByUserId(empUserName);

//        if (userDao == null) {
//            throw new BadCredentialsException("Username wrong");
//            throw new WrongCredentialsException("Invalid Credentials");
//        }
//        return new User(userDao.getUserId(), userDao.getPassword(),
//                new ArrayList<>());

//        if(plant == null) {
//            throw new RuntimeException("No Plant Found");
//        }

//        EmployeeMaster employeeMaster = authDao.findByEmployeeLoginId(empUserName, plant);

        HREmpUserInfo hrEmpUserInfo = authDao.findByEmployeeLoginId2(empUserName);

        if (hrEmpUserInfo == null) {
            throw new WrongCredentialsException("Invalid Credentials");
        }
        return new User(hrEmpUserInfo.getEmpUserId(), hrEmpUserInfo.getPassword(),
                new ArrayList<>());

    }

    public AuthTokensDTO createNewTokens(AuthRequestDTO authenticationRequest) throws Exception {
        AuthTokensDTO authResponseDao;
//        UserInfo userDao;
        HREmpUserInfo hrEmpUserInfo;
        CryptoAlgoUtil cryptoAlgoUtil = new CryptoAlgoUtil(pathName);
        String userName = authenticationRequest.getUsername();
        String password = cryptoAlgoUtil.encrypt(authenticationRequest.getPassword());
//        this.plant = authenticationRequest.getPlant();


        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userName, password));
        } catch (Exception e) {
            authResponseDao = new AuthTokensDTO(null, null, HttpStatus.UNAUTHORIZED.value(), "Invalid Credentials");
            return authResponseDao;
        }

        hrEmpUserInfo = authDao.findByEmployeeLoginId2(userName);
        EmployeeMaster employeeMaster = authDao.getEmployeeByUsername(hrEmpUserInfo.getPlant(), hrEmpUserInfo.getEmpUserId());

        if(authenticationRequest.getType().equals("SUPERVISOR")) {

            boolean isEmployeeSupervisor = authDao.checkEmployeeType(hrEmpUserInfo.getPlant(), hrEmpUserInfo.getEmpUserId(), "SUPERVISOR");


            if(isEmployeeSupervisor) {
//                boolean hasMultipleProjects = authDao.checkSupervisorHasMultipleProjects(employeeDao.getEMPNO(), plant);
//
//                if(hasMultipleProjects) {
//                    authResponseDao = new AuthTokensDTO(null, null, HttpStatus.FORBIDDEN.value(), "Supervisor is involved in multiple projects");
//                    return authResponseDao;
//                }

//                FinRecycleProject finRecycleProject = authDao.findProjectBySupervisor(employeeDao.getEMPNO(), plant);

//                if(finRecycleProject == null) {
//                    List<FinProjectMultiSupervisor> finProjectMultiSupervisorList = authDao.findMultiSupervisorsById(employeeDao.getEMPNO(), plant);
//                    List<Integer> projectHDRIdList = new ArrayList<>();
//                    for(FinProjectMultiSupervisor finProjectMultiSupervisor: finProjectMultiSupervisorList) {
//                        projectHDRIdList.add(finProjectMultiSupervisor.getProjectHdrId());
//                    }
//
//                    finRecycleProject = authDao.findProjectByIds(projectHDRIdList, plant);
//                }
//
//                if(finRecycleProject == null) {
//                    authResponseDao = new AuthTokensDTO(null, null, HttpStatus.FORBIDDEN.value(), "Supervisor has not assigned to any projects");
//                    return authResponseDao;
//                }

                JwtUtil.plt = hrEmpUserInfo.getPlant();


//                String projectNo = finRecycleProject.getProject();
//                Map<String, Object> claims = ClaimsSet.setClaimsDetails(employeeDao, projectNo, employeeDao.getEMPUSERID());
                Map<String, Object> claims = ClaimsSet.setClaimsDetails(hrEmpUserInfo, employeeMaster.getEMPNO());
//
//                if(finRecycleProject.getProjectSiteType() == null || finRecycleProject.getProjectSiteType().isEmpty()) {
//                    authResponseDao = new AuthTokensDTO(null, null, HttpStatus.FORBIDDEN.value(), "The Project Type is not specified");
//
//                    return authResponseDao;
//                }

//                PlantDTO plantDTO = plantDao.getPlantByPlantId(plant);
//
//                claims.put("iaslin", plantDTO.getIsAutoSupervisorLogin());
//                claims.put("iaslot", plantDTO.getIsAutoSupervisorLogout());
//                claims.put("typ", finRecycleProject.getProjectSiteType());

                String accessToken = jwtTokenUtil.generateAccessToken(hrEmpUserInfo, claims);
                String refreshToken = jwtTokenUtil.generateRefreshToken(employeeMaster.getEMPNO(), claims);


                authResponseDao = new AuthTokensDTO(accessToken, refreshToken, HttpStatus.OK.value(), "SUCCESS");

                return authResponseDao;
            } else {
                authResponseDao = new AuthTokensDTO(null, null, HttpStatus.FORBIDDEN.value(), "The User is not Supervisor");

                return authResponseDao;
            }


        } else if(authenticationRequest.getType().equals("EXECUTIVE")) {

            boolean isEmployeeExecutive = authDao.checkEmployeeType(hrEmpUserInfo.getPlant(), hrEmpUserInfo.getEmpUserId(), "EXECUTIVE");
            if(isEmployeeExecutive) {

                JwtUtil.plt = hrEmpUserInfo.getPlant();

//                Map<String, Object> claims = ClaimsSet.setClaimsDetails(employeeDao, "", employeeDao.getEMPUSERID());
                Map<String, Object> claims = ClaimsSet.setClaimsDetails(hrEmpUserInfo, employeeMaster.getEMPNO());

//            PlantDTO plantDTO = plantDao.getPlantByPlantId(plant);

//            claims.put("iaslin", plantDTO.getIsAutoSupervisorLogin());
//            claims.put("iaslot", plantDTO.getIsAutoSupervisorLogout());
                String accessToken = jwtTokenUtil.generateAccessToken(hrEmpUserInfo, claims);
                String refreshToken = jwtTokenUtil.generateRefreshToken(employeeMaster.getEMPNO(), claims);


                authResponseDao = new AuthTokensDTO(accessToken, refreshToken, HttpStatus.OK.value(), "SUCCESS");

                return authResponseDao;
            } else {
                authResponseDao = new AuthTokensDTO(null, null, HttpStatus.FORBIDDEN.value(), "The User is not Executive");

                return authResponseDao;
            }

        } else {
            authResponseDao = new AuthTokensDTO(null, null, HttpStatus.FORBIDDEN.value(), "Please specify a type");

            return authResponseDao;
        }
    }

    public Map<String, String> createAccessToken(AuthTokensDTO authTokens) throws Exception {
        Map<String, String> accessToken = new HashMap<>();
        try {
            String userName = jwtTokenUtil.getUsernameFromToken(authTokens.getRefreshToken());
            final Claims claims = jwtTokenUtil.getAllClaimsFromToken(authTokens.getRefreshToken());
            accessToken.put("accessToken", jwtTokenUtil.generateAccessToken(userName, claims));

        } catch (ExpiredJwtException e) {
            throw new Exception("Token Expired ,Pls re-login");
        } catch (Exception e) {
            throw new Exception("invalid auth token");
        }
        return accessToken;
    }

    public List<UserInfo> getByPlantStore(String plt,String loc) throws Exception {
        List<UserInfo> val = new ArrayList<UserInfo>();
        try {
            List<UserInfo> userlist = authRepository.findByStore(plt);
            for(UserInfo userInfo : userlist) {
                UserLocation userLocation = userLocationService.getbyuserid(userInfo.getUiPKey());
                if(userLocation.getLoc().equalsIgnoreCase(loc)){
                    val.add(userInfo);
                }
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }

    public List<UserInfo> getByPlantDelivery(String plt) throws Exception {
        List<UserInfo> val = new ArrayList<UserInfo>();
        try {
            val = authRepository.findByDelivery(plt);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }
}
