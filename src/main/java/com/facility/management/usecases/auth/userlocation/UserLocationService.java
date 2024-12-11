package com.facility.management.usecases.auth.userlocation;

import com.facility.management.helpers.exception.custom.ResourceNotFoundException;
import com.facility.management.persistence.models.UserLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLocationService {

    @Autowired
    UserLocationRepository userLocationRepository;

    public UserLocation getbyuserid(int userid) throws Exception{
        UserLocation val;
        try{
            val = userLocationRepository.findByUserId(userid);
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }
}
