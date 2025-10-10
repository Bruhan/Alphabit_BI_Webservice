package com.owner.process.usecases.auth.userlocation;

import com.owner.process.helpers.exception.custom.ResourceNotFoundException;
import com.owner.process.persistence.models.StoreHdr;
import com.owner.process.persistence.models.UserLocation;
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
