package com.owner.process.usecases.currency;

import com.owner.process.helpers.configs.LoggerConfig;
import com.owner.process.persistence.models.CurrencyMst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {
    @Autowired
    CurrencyRepository currencyRepository;

    public CurrencyMst getCurrencyDetailById(String id) throws Exception {
        CurrencyMst val;
        try {
            val = currencyRepository.findByCurrencyId(id);
//        }catch (JpaSystemException e){
//            LoggerConfig.logger.info("Error currencyUseQt " + e);
//            throw new Exception("Null value was assigned to a primitive");
//        } catch (ResourceNotFoundException e) {
//            LoggerConfig.logger.info("Error currencyUseQt " + e);
//            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            LoggerConfig.logger.info("Error currencyUseQt " + e);
            throw new Exception("SQL Error!!!");
        }
        return val;
    }
}