package com.owner.process.usecases.table_control;

import com.owner.process.persistence.models.TableControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableControlRepository extends JpaRepository<TableControl, Long> {
    TableControl findByPlantAndFunc(String pk0, String pk1);
}
