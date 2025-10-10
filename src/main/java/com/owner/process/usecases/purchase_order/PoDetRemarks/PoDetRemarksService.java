package com.owner.process.usecases.purchase_order.PoDetRemarks;

import com.owner.process.persistence.models.PoDetRemarks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoDetRemarksService {
    @Autowired
    PoDetRemarksRepository poDetRemarksRepository;

    public List<PoDetRemarks> getByPoNo(String pk0) throws Exception{
        List<PoDetRemarks> getVal;
        try{
            getVal = poDetRemarksRepository.findByPoNo(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<PoDetRemarks> getByUKey(String pk0) throws Exception{
        List<PoDetRemarks> getVal;
        try{
            getVal = poDetRemarksRepository.findByUniqueKey(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public void save(PoDetRemarks poDetRemarks) throws Exception{
        try{
            poDetRemarksRepository.save(poDetRemarks);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void delete(PoDetRemarks poDetRemarks) throws Exception{
        try{
            poDetRemarksRepository.delete(poDetRemarks);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void deleteall(List<PoDetRemarks> poDetRemarks) throws Exception{
        try{
            poDetRemarksRepository.deleteAll(poDetRemarks);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


}
