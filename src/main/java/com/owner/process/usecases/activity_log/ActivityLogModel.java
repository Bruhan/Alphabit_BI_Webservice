package com.owner.process.usecases.activity_log;

import com.owner.process.persistence.models.ActivityLog;

public class ActivityLogModel {
    public ActivityLog setActivityLogModel(String plant, String dirType, String ordNum,
                                           String lnNo, String item, double qty, String cstNo,
                                           String transactionDate, String createdAt, String createdBy,
                                           String remarks) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setPlant(plant);
        activityLog.setDirType(dirType);
        activityLog.setOrdNum(ordNum);
        activityLog.setLnNo(lnNo);
        activityLog.setItem(item);
        activityLog.setQty(qty);
        activityLog.setCustNo(cstNo);
        activityLog.setTransactionDate(transactionDate);
        activityLog.setCrAt(createdAt);
        activityLog.setCrBy(createdBy);
        activityLog.setRemarks(remarks);

        return activityLog;
    }

    public ActivityLog setActivityLogModelForStock(String plant, String dirType, String ordNum, String item, double qty, String cstNo,
                                           String transactionDate, String createdAt, String createdBy,
                                           String remarks,String movTid,String batch,String lfrom,String lto) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setPlant(plant);
        activityLog.setDirType(dirType);
        activityLog.setMovTid(movTid);
        activityLog.setOrdNum(ordNum);
        activityLog.setCustNo(cstNo);
        activityLog.setItem(item);
        activityLog.setQty(qty);
        activityLog.setBatNo(batch);
        activityLog.setLoc(lfrom);
        activityLog.setUserFldOne(lto);
        activityLog.setTransactionDate(transactionDate);
        activityLog.setCrAt(createdAt);
        activityLog.setCrBy(createdBy);
        activityLog.setRemarks(remarks);

        return activityLog;
    }
}
