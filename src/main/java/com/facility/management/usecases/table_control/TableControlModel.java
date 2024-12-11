package com.facility.management.usecases.table_control;

import com.facility.management.persistence.models.TableControl;

public class TableControlModel {

    public TableControl setTableControlModel(String plant, String createdAt, String createdBy, String type, TableControl tableControl) {
        String customerNo = "C";
        long sequence = 1;
        if (tableControl != null) {
            //update into next sequence
            sequence = Long.parseLong(tableControl.getNxtSeq()) + sequence;
            tableControl.setNxtSeq(String.valueOf(sequence));
            tableControl.setUpAt(createdAt);
            tableControl.setUpBy(createdBy);
        } else {
            //need to add insert query in table control
            tableControl = new TableControl();
            tableControl.setPlant(plant);
            tableControl.setFunc(type);
            tableControl.setPrefix(customerNo);
            tableControl.setMinSeq("0000");
            tableControl.setMaxSeq("9999");
            tableControl.setNxtSeq(String.valueOf(sequence));
            tableControl.setCrAt(createdAt);
            tableControl.setCrBy(createdBy);
        }
        return tableControl;
    }

    public String generateNewCustomerKey(String sequence) {
        String customerNo = "C";

        int size = sequence.length();
        if (size == 4) {
            customerNo = customerNo + sequence;
        } else if (size == 3) {
            customerNo = customerNo + "0" + sequence;
        } else if (size == 2) {
            customerNo = customerNo + "00" + sequence;
        } else {
            customerNo = customerNo + "000" + sequence;
        }
        return customerNo;
    }

}
