package com.facility.management.usecases.journal;

import com.facility.management.persistence.models.FinJournalDet;
import com.facility.management.persistence.models.FinJournalHdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends JpaRepository<FinJournalHdr,Integer> {
}
interface JournalDetRepository extends JpaRepository<FinJournalDet,Integer> {
}
