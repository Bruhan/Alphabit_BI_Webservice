package com.facility.management.usecases.table_control;

import com.facility.management.persistence.models.TableControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableControlRepository extends JpaRepository<TableControl, Long> {
    TableControl findByPlantAndFunc(String pk0, String pk1);
}
