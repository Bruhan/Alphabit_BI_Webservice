package com.owner.process.usecases.outlets_and_terminals.POSOutlets;

import com.owner.process.persistence.models.POSOutlets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface POSOutletsRepository extends JpaRepository<POSOutlets, Integer>{
    @Query(value = "SELECT TOP 1 * FROM ##plant##POSOUTLETS where OUTLET =?1 ", nativeQuery = true)
    POSOutlets findByOUTLET(String outlet);
}
