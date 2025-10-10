package com.owner.process.usecases.hq_process.prod_bom_mst;


import com.owner.process.helpers.exception.custom.ResourceNotFoundException;
import com.owner.process.persistence.models.ProdBOMMst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdBOMMstService {

    @Autowired
    ProdBOMMstRepository prodBOMMstRepository;

    public List<ProdBOMMst> getbyPitemAndBomType(String item, String type) throws Exception{
        List<ProdBOMMst> val;
        try{
            val = prodBOMMstRepository.findByPitemAndBomType(item,type);
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return val;
    }
}
