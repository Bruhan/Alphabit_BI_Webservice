package com.owner.process.usecases.auth;


import com.owner.process.helpers.common.token.ClaimsSet;
import com.owner.process.helpers.exception.custom.ResourceNotFoundException;
import com.owner.process.helpers.utils.CryptoAlgoUtil;
import com.owner.process.helpers.utils.JwtUtil;
import com.owner.process.persistence.models.*;
import com.owner.process.usecases.User_role.userRoleDao;
import com.owner.process.usecases.auth.dao.AuthRequestDao;
import com.owner.process.usecases.auth.dao.AuthTokensDao;
import com.owner.process.usecases.auth.project.AuthProjectDao;
import com.owner.process.usecases.auth.userlocation.UserLocationService;
import com.owner.process.usecases.company.CompanyService;
import com.owner.process.usecases.employee_master.EmployeeMasterRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    AuthRepositoryCust authRepositoryCust;
    @Autowired
    CompanyService companyService;
    @Autowired
    UserLocationService userLocationService;
    @Autowired
    AuthRepositoryEmp authRepositoryEmp;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtTokenUtil;
    @Autowired
    EmployeeMasterRepository employeeMasterRepository;
    @Autowired
    com.owner.process.usecases.User_role.userRoleDao userRoleDao;
    @Autowired
    AuthProjectDao authProjectDao;

    @Value("${spring.crypto.key-path}")
    private String pathName;

    public AuthService() {
    }

    //used in Jwtrequestfilter
    @Override
    public UserDetails loadUserByUsername(String empUserName) throws UsernameNotFoundException {
        int check = 0;
        UserInfo userDao = authRepository.findByUserId(empUserName);
        CustomerInfo customerInfo = new CustomerInfo();
        HrEmpUserInfo hrEmpUserInfo = new HrEmpUserInfo();
        if (userDao == null) {
            customerInfo = authRepositoryCust.findByUserId(empUserName);
            if (customerInfo == null) {
                hrEmpUserInfo = authRepositoryEmp.findByUserId(empUserName);
                if (hrEmpUserInfo == null) {
                    throw new BadCredentialsException("Invalid Credentials");
                }else{
                    check =2;
                }
            }else{
                check =1;
            }
            //throw new BadCredentialsException("Username wrong");
        }
        if(check == 0) {
            return new User(userDao.getUserId(), userDao.getPassword(),
                    new ArrayList<>());
        }else if(check == 1){
            return new User(customerInfo.getUserId(), customerInfo.getPassword(),
                    new ArrayList<>());
        }else{
            return new User(hrEmpUserInfo.getUserId(), hrEmpUserInfo.getPassword(),
                    new ArrayList<>());
        }
    }

    public AuthTokensDao createNewTokens(AuthRequestDao authenticationRequest) throws Exception {
        AuthTokensDao authResponseDao;
        UserInfo userDao;
        CryptoAlgoUtil cryptoAlgoUtil = new CryptoAlgoUtil(pathName);
        String userName = authenticationRequest.getUsername();
        String password = cryptoAlgoUtil.encrypt(authenticationRequest.getPassword());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userName, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Password Wrong");
        } catch (Exception e) {
            throw new Exception("USERNAME");
        }

        if(authenticationRequest.getApplication().equalsIgnoreCase("User")) {

            userDao = authRepository.findByUserId(userName);
           /* Short owneracs = 0;
            if (userDao.getIsAccessOwnerApp() != null) {
                owneracs = userDao.getIsAccessOwnerApp();
            }

            if (authenticationRequest.getApplication().equalsIgnoreCase("Owner")) {
                if (owneracs != 1) {
                    throw new Exception("No Access For This User");
                }
            } else {
                throw new Exception("Only Access For Owners");
            }*/
            if (userDao == null) {
                HrEmpUserInfo hrEmpUserInfo = authRepositoryEmp.findByUserId(userName);
                String baseCurrency = companyService.getBaseCurrency(hrEmpUserInfo.getPlant());
                JwtUtil.plt = hrEmpUserInfo.getPlant();
                EmployeeMaster emp = employeeMasterRepository.findById(hrEmpUserInfo.getEmpNoId());
                if(emp.getDESGINATION().equalsIgnoreCase("Executive") || emp.getDESGINATION().equalsIgnoreCase("Supervisor")){
                    String prno="0";
                    if(emp.getDESGINATION().equalsIgnoreCase("Supervisor")){
                        FinRecycleProject finRecycleProject = authProjectDao.findProjectBySupervisor(emp.getEMPNO(), hrEmpUserInfo.getPlant());

                        if(finRecycleProject == null) {
                            List<FinProjectMultiSupervisor> finProjectMultiSupervisorList = authProjectDao.findMultiSupervisorsById(emp.getEMPNO(), hrEmpUserInfo.getPlant());
                            List<Integer> projectHDRIdList = new ArrayList<>();
                            for(FinProjectMultiSupervisor finProjectMultiSupervisor: finProjectMultiSupervisorList) {
                                projectHDRIdList.add(finProjectMultiSupervisor.getProjectHdrId());
                            }

                            finRecycleProject = authProjectDao.findProjectByIds(projectHDRIdList, hrEmpUserInfo.getPlant());
                            prno=finRecycleProject.getProject();
                        }else{
                            prno=finRecycleProject.getProject();
                        }
                    }
                    Map<String, Object> claims = ClaimsSet.setClaimsDetailsEmp(hrEmpUserInfo,baseCurrency,emp,prno);
                    String accessToken = jwtTokenUtil.generateAccessTokenEmp(hrEmpUserInfo, claims);
                    String refreshToken = jwtTokenUtil.generateRefreshTokenEmp(hrEmpUserInfo.getUserId(), claims);
                    //authResponseDao = new AuthTokensDao(accessToken, refreshToken);
                    authResponseDao = new AuthTokensDao(accessToken, refreshToken,0,0,0,0,0,0,0);

                    return authResponseDao;
                }else{
                    throw new Exception("Invalid Credentials");
                }
            }else{
                String plt = userDao.getDept();
                String rolename = userDao.getUserLevel();

                int ownappOpenStkvalue = 0;
                int ownClsStkvalue = 0;
                int ownappPurchase = 0;
                int ownappErpsales = 0;
                int ownappPossales = 0;
                int ownappProjectmanagment = 0;
                int ownappPurchaseapproval = 0;

                int id1 = userRoleDao.getUserRoleID(plt, rolename, "ownappOpenStkvalue");
                ownappOpenStkvalue = userRoleDao.getAccessStatusOfRoll(plt, id1);
                int id2 = userRoleDao.getUserRoleID(plt, rolename, "ownClsStkvalue");
                ownClsStkvalue = userRoleDao.getAccessStatusOfRoll(plt, id2);
                int id3 = userRoleDao.getUserRoleID(plt, rolename, "ownappPurchase");
                ownappPurchase = userRoleDao.getAccessStatusOfRoll(plt, id3);
                int id4 = userRoleDao.getUserRoleID(plt, rolename, "ownappErpsales");
                ownappErpsales = userRoleDao.getAccessStatusOfRoll(plt, id4);
                int id5 = userRoleDao.getUserRoleID(plt, rolename, "ownappPossales");
                ownappPossales = userRoleDao.getAccessStatusOfRoll(plt, id5);
                int id6 = userRoleDao.getUserRoleID(plt, rolename, "ownappProjectmanagment");
                ownappProjectmanagment = userRoleDao.getAccessStatusOfRoll(plt, id6);
                int id7 = userRoleDao.getUserRoleID(plt, rolename, "ownappPurchaseapproval");
                ownappPurchaseapproval = userRoleDao.getAccessStatusOfRoll(plt, id7);

                String baseCurrency = companyService.getBaseCurrency(userDao.getDept());
                Map<String, Object> claims = ClaimsSet.setClaimsDetails(userDao, baseCurrency, "");
                String accessToken = jwtTokenUtil.generateAccessToken(userDao, claims);
                String refreshToken = jwtTokenUtil.generateRefreshToken(userDao.getUserId(), claims);
                authResponseDao = new AuthTokensDao(accessToken, refreshToken, ownappOpenStkvalue, ownClsStkvalue, ownappPurchase,
                        ownappErpsales, ownappPossales, ownappProjectmanagment, ownappPurchaseapproval);

                return authResponseDao;
            }
        }else if(authenticationRequest.getApplication().equalsIgnoreCase("customer")){
            CustomerInfo customerDao = authRepositoryCust.findByUserId(userName);
            String baseCurrency = companyService.getBaseCurrency(customerDao.getPlant());
            String numberOfDecimal = companyService.getNumberOfDecimal(customerDao.getPlant());
           /* String managerAccess = "Denied";
            if(customerDao.getIsManagerAppAccess() == null){
                managerAccess = "Denied";
            }else{
                if(customerDao.getIsManagerAppAccess() == 1){
                    managerAccess = "Allowed";
                }else{
                    managerAccess = "Denied";
                }
            }*/
            FinRecycleProject finRecycleProject = authProjectDao.findProjectByCustomer(customerDao.getCustNo(), customerDao.getPlant());
            String prno=finRecycleProject.getProject();
            Map<String, Object> claims = ClaimsSet.setClaimsDetailsCust(customerDao,baseCurrency,numberOfDecimal,prno);
            String accessToken = jwtTokenUtil.generateAccessTokenCust(customerDao, claims);
            String refreshToken = jwtTokenUtil.generateRefreshTokenCust(customerDao.getUserId(), claims);
            authResponseDao = new AuthTokensDao(accessToken, refreshToken,0,0,0,0,0,0,0);

            return authResponseDao;
        }else{
            throw new Exception("Invalid Credentials");
        }
    }

    public Map<String, String> createAccessToken(AuthTokensDao authTokens) throws Exception {
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
