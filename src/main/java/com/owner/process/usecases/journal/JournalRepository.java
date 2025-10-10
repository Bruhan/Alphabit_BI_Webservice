package com.owner.process.usecases.journal;

import com.owner.process.persistence.models.FinJournalDet;
import com.owner.process.persistence.models.FinJournalHdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends JpaRepository<FinJournalHdr,Integer> {
}
interface JournalDetRepository extends JpaRepository<FinJournalDet,Integer> {
}
