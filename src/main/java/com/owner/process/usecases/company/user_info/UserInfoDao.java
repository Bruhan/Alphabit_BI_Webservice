package com.owner.process.usecases.company.user_info;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserInfoDao {
    private String userName;
    private String userId;
    private String email;
    private int noOfCompanies;
}
