package com.owner.process.usecases.User_role;

public interface userRoleDao {
    int getUserRoleID(String plant, String userrolename, String urlname);
    int getAccessStatusOfRoll(String plant, int umpkey);
}
