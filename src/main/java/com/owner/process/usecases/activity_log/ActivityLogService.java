package com.owner.process.usecases.activity_log;

import com.owner.process.persistence.models.ActivityLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityLogService {
    @Autowired
    ActivityLogRepository activityLogRepository;

    public String setActivityLog(ActivityLog activityLog) throws Exception {
        Integer val;
        String plant = activityLog.getPlant();
        String dirType = activityLog.getDirType();
        String transDate = activityLog.getTransactionDate();
        String recId = activityLog.getRecId();
        String orderNumber = activityLog.getOrdNum();
        String crBy = activityLog.getCrBy();
        String remarks = activityLog.getRemarks();
        String crAt = activityLog.getCrAt();

        try {
            val = activityLogRepository.saveActivity(plant, dirType, transDate, recId, orderNumber, crBy, remarks, crAt);
            if (val == -1) {
                return "-1";
            }
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return "0";
    }

    public String setActivityLogDetails(ActivityLog val) throws Exception {
        try {
            activityLogRepository.save(val);
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return "1";
    }
}
