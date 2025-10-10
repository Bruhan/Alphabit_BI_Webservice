package com.owner.process.usecases.purchase_order.poDet;

import com.owner.process.persistence.models.PoDet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoDetService {
    @Autowired
    PoDetRepository poDetRepository;

    public List<PoDet> getByPoNo(String pk0) throws Exception{
        List<PoDet> getVal;
        try{
            getVal = poDetRepository.findByPoNo(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<PoDet> getByUKey(String pk0) throws Exception{
        List<PoDet> getVal;
        try{
            getVal = poDetRepository.findByUniqueKey(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public void save(PoDet poDet) throws Exception{
        try{
            poDetRepository.save(poDet);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void delete(PoDet poDet) throws Exception{
        try{
            poDetRepository.delete(poDet);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void deleteall(List<PoDet> poDet) throws Exception{
        try{
            poDetRepository.deleteAll(poDet);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
