package com.owner.process.usecases.sales_order.do_det_remarks;

import com.owner.process.persistence.models.DoDet;
import com.owner.process.persistence.models.DoDetRemarks;

public class DoDetRemarksModel {
    public DoDetRemarks setDoDetRemarksModel(DoDet doDet, String plant, String createdAt,
                                             String createdBy) {
        DoDetRemarks doDetRemarks = new DoDetRemarks();
        doDetRemarks.setPlant(plant);
        doDetRemarks.setCrAt(createdAt);
        doDetRemarks.setCrBy(createdBy);
        doDetRemarks.setDoLineNo(doDet.getDoLineNo());
        doDetRemarks.setDoNo(doDet.getDoNo());
        doDetRemarks.setItem(doDet.getItem());
        doDetRemarks.setRemarks("");
        doDetRemarks.setIdRemarks(0);
        return doDetRemarks;
    }
}
