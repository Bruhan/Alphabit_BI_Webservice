package com.owner.process.usecases.sales_order.customer_master;

import com.owner.process.persistence.models.CustomerMst;
import com.owner.process.usecases.sales_order.dao.create_sales_order.BillingAddress;
import com.owner.process.usecases.sales_order.dao.create_sales_order.Customer;
import com.owner.process.usecases.sales_order.dao.create_sales_order.DefaultAddress;
import com.owner.process.usecases.sales_order.dao.create_sales_order.SalesOrderMain;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerMstModel {
    @Autowired
    CustomerMstService customerMstService;


    public CustomerMst setShippingAddressModel(SalesOrderMain salesOrderMain, String plant) {
        CustomerMst customerMst = new CustomerMst();
        //customer details
        Customer customerDetail;
        customerDetail = salesOrderMain.getCustomer();
        //Customer Default Address
        DefaultAddress defaultAddress;
        defaultAddress = customerDetail.getDefaultAddress();

        customerMst.setPlant(plant);
        customerMst.setEmail(salesOrderMain.getEmail());
        customerMst.setWorkPhone(customerDetail.getPhone());
        customerMst.setName(defaultAddress.getFirstName());
        customerMst.setLastName(defaultAddress.getLastName());

        customerMst.setAddressROne(defaultAddress.getAddress1());
        customerMst.setAddressRTwo(defaultAddress.getAddress2());
        customerMst.setAddressRFour(defaultAddress.getCity());
        customerMst.setState(defaultAddress.getProvince());
        customerMst.setCountry(defaultAddress.getCountry());
        customerMst.setZip(defaultAddress.getZip());
        customerMst.setCustomerName(defaultAddress.getName());
        //Default value for custmst
        //customerMst.setCrAt(createdAt);
        customerMst.setIsActive("Y");
        //customerMst.setCrBy(createdBy);
        //v2
        customerMst.setIsCreditLimit("N");
        customerMst.setCreditLimitBy("NOLIMIT");
        return customerMst;
    }

    public CustomerMst setBillingAddressModel(SalesOrderMain salesOrderMain, String plant) {
        CustomerMst customerMst = new CustomerMst();
        //customer details
        BillingAddress billingAddress = salesOrderMain.getBillingAddress();

        customerMst.setPlant(plant);
        customerMst.setWorkPhone(billingAddress.getPhone());
        customerMst.setName(billingAddress.getFirstName());
        customerMst.setLastName(billingAddress.getLastName());

        customerMst.setAddressROne(billingAddress.getAddress1());
        customerMst.setAddressRTwo(billingAddress.getAddress2());
        customerMst.setAddressRFour(billingAddress.getCity());
        customerMst.setState(billingAddress.getProvince());
        customerMst.setCountry(billingAddress.getCountry());
        customerMst.setZip(billingAddress.getZip());
        customerMst.setCustomerName(billingAddress.getName());
        //Default value for custmst
        //customerMst.setCrAt(createdAt);
        customerMst.setIsActive("Y");
        //customerMst.setCrBy(createdBy);
        //v2
        customerMst.setIsCreditLimit("N");
        customerMst.setCreditLimitBy("NOLIMIT");
        return customerMst;
    }

//    public Boolean checkCustomerExistStatusUsgNum(String number,String name) throws Exception {
//        Boolean customerName = false;
//        List<CustomerMst> customerMstList = customerMstService.getCustomerNoPkUsgWorkPhone(number);
//        if(customerMstList != null){
//           for(int i=0;i<customerMstList.size();i++){
//               if(name.equals(customerMstList.get(i).getCustomerName())){
//                   customerName = true;
//                   break;
//               }
//           }
//        }
//        return customerName;
//    }
//
//    public Boolean checkCustomerExistStatusUsgEmail(String email,String name) throws Exception {
//        Boolean customerName = false;
//        List<CustomerMst> customerMstList = customerMstService.getCustomerNoPkUsgEmail(email);
//        if(customerMstList != null){
//            for(int i=0;i<customerMstList.size();i++){
//                if(name.equals(customerMstList.get(i).getCustomerName())){
//                    customerName = true;
//                    break;
//                }
//            }
//        }
//        return customerName;
//    }

}
