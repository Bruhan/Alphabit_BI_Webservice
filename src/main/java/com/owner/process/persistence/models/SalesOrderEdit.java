package com.owner.process.persistence.models;


import com.owner.process.usecases.products.productDao.productDao;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SalesOrderEdit {
    private String doNo;
    private List<productDao> products;
}
