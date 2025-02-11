package com.facility.management.helpers.common.token;

import com.facility.management.persistence.models.EmployeeMaster;
import com.facility.management.persistence.models.HREmpUserInfo;
import com.facility.management.usecases.plant.dao.PlantDao;
import com.facility.management.usecases.plant.dto.PlantDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.facility.management.helpers.utils.JwtUtil;
import com.facility.management.persistence.models.UserInfo;
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

//    public static Map<String, Object> setClaimsDetails(EmployeeMaster empMst, String projectNo, String empUserId) {
//        Map<String, Object> claims = new HashMap<>();
//
//        claims.put("plt", empMst.getPLANT());
//        claims.put("eid", empMst.getEMPNO());
//        claims.put("prj", projectNo);
//        claims.put("usr", empUserId);
//
//
//        return claims;
//    }

    public static Map<String, Object> setClaimsDetails(HREmpUserInfo empMst, String empUserId) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("plt", empMst.getPlant());
        claims.put("eid", empUserId);
        claims.put("usr", empMst.getEmpUserId());

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
