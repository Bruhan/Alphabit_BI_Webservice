package com.owner.process.helpers.common.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.owner.process.helpers.utils.JwtUtil;
import com.owner.process.persistence.models.CustomerInfo;
import com.owner.process.persistence.models.EmployeeMaster;
import com.owner.process.persistence.models.HrEmpUserInfo;
import com.owner.process.persistence.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClaimsSet {
    @Autowired
    JwtUtil jwtUtil;

    public ClaimsSet() {
        super();
    }

//    public Boolean setClaimsDetailsFromToken(String token) throws Exception {
//        ClaimsDao claimsDao = getClaimsDetails(token);
//        jwtUtil.setOrgDetails(claimsDao.getPlt(), claimsDao.getEid());
//        return true;
//    }

    public static Map<String, Object> setClaimsDetails(UserInfo userDao, String baseCur, String numOfDeci) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("plt", userDao.getDept());
        claims.put("eid", userDao.getUserName());
        claims.put("unt", baseCur);
        claims.put("cid", "");
        claims.put("cnm", "");
        claims.put("nod", numOfDeci);
        claims.put("manacs", "USER");
        claims.put("prno", "0");
        return claims;
    }

    public static Map<String, Object> setClaimsDetailsCust(CustomerInfo customerDao, String baseCur, String numOfDeci,String prno) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("plt", customerDao.getPlant());
        claims.put("cid", customerDao.getCustNo());
        claims.put("eid", customerDao.getUserId());
        claims.put("cnm", customerDao.getCustDesc());
        claims.put("unt", baseCur);
        claims.put("nod", numOfDeci);
        claims.put("manacs", "CUSTOMER");
        claims.put("prno", prno);
        return claims;
    }

    public static Map<String, Object> setClaimsDetailsEmp(HrEmpUserInfo userDao, String baseCur, EmployeeMaster employeeMastern,String prno) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("plt", userDao.getPlant());
        claims.put("cid", "");
        claims.put("eid", employeeMastern.getEMPNO());
        claims.put("cnm", userDao.getUserId());
        claims.put("unt", baseCur);
        claims.put("nod", "");
        claims.put("manacs", employeeMastern.getDESGINATION());
        claims.put("prno", prno);
        return claims;
    }

    public ClaimsDao getClaimsDetailsAfterSet(String token) throws Exception {
        ClaimsDao claimsDao = getClaimsDetails(token);
        jwtUtil.setOrgDetails(claimsDao.getPlt(), claimsDao.getEid());
        return claimsDao;
    }

    public String getClaimsDetailsAfterSetWtAuth(String plant) throws Exception {
        jwtUtil.setOrgDetails(plant, "");
        return plant;
    }

    public ClaimsDao getClaimsDetailsAfterSetNewPlt(String token, String plant) throws Exception {
        ClaimsDao claimsDao = getClaimsDetails(token);
        jwtUtil.setOrgDetails(plant, claimsDao.getEid());
        return claimsDao;
    }

    public ClaimsDao getClaimsDetails(String token) throws Exception {
        try {
            java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
            token = token.substring(7);
            String[] parts = token.split("\\.");
            String payloadJson = new String(decoder.decode(parts[1]));
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(payloadJson, ClaimsDao.class);
        } catch (JsonProcessingException e) {
            throw new Exception("Json error");
        }
    }
}
