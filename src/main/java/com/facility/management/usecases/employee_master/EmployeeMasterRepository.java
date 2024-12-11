package com.facility.management.usecases.employee_master;

import com.facility.management.persistence.models.EmployeeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeMasterRepository extends JpaRepository<EmployeeMaster, Integer> {
    EmployeeMaster findById(int id);

    @Query(value = "select * from ##plant##EMP_MST where EMPNO = ?1",nativeQuery = true)
    List<EmployeeMaster> findByEmpCode(String empcode);
}
